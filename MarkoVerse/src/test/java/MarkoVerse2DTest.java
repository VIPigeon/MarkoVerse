import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarkoVerse2DTest {
    MarkoVerse2D markoverse2D = new MarkoVerse2D();
    Random random = new Random(0);  // карго-культ момент

    @Test
    public void BasicTest() {
        List<Pair<String, String>> rules = Arrays.asList(
            new Pair<String, String>("10", "11")
        );
        int n = 9;
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '0';
            }
        }
        matrix[n/2][n/2] = '1';
        markoverse2D.build(matrix, rules);
        char[][] result = markoverse2D.run();

        // // Выводим промежуточные значения массива result
        // System.out.println("Результат выполнения:");
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         System.out.print(result[i][j] + " "); // Вывод значений
        //     }
        //     System.out.println(); // Переход на новую строку
        // }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Assert.assertEquals("ALL values must be '1'", result[i][j], '1');
            }
        }
    }
}
