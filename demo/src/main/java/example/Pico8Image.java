package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Pico8Image extends Application {

    // Определение палитры PICO-8
    private static final Color[] PICO8_PALETTE = {
            Color.rgb(0, 0, 0),   // 0 - черный
            Color.rgb(29, 43, 83), // 1 - темно-синий
            Color.rgb(126, 37, 83), // 2 - фиолетовый
            Color.rgb(0, 135, 81), // 3 - зеленый
            Color.rgb(171, 82, 54), // 4 - красный
            Color.rgb(95, 87, 79), // 5 - серый
            Color.rgb(194, 195, 199), // 6 - светло-серый
            Color.rgb(255, 241, 232), // 7 - белый
            Color.rgb(255, 0, 77), // 8 - розовый
            Color.rgb(255, 163, 0), // 9 - оранжевый
            Color.rgb(255, 255, 0), // 10 - желтый
            Color.rgb(0, 255, 0), // 11 - ярко-зеленый
            Color.rgb(0, 255, 255), // 12 - ярко-голубой
            Color.rgb(0, 0, 255), // 13 - синий
            Color.rgb(255, 0, 255), // 14 - фиолетовый
            Color.rgb(255, 0, 0)  // 15 - красный
    };

    // Матрица символов
    private static final char[][] CHAR_MATRIX = {
            {'0', '1', '2', '3', '4'},
            {'5', '6', '7', '8', '9'},
            {'A', 'B', 'C', 'D', 'E'},
            {'F', '0', '1', '2', '3'},
            {'4', '5', '6', '7', '8'}
    };

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(CHAR_MATRIX[0].length * 20, CHAR_MATRIX.length * 20);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Рисуем изображение на основе матрицы символов
        for (int y = 0; y < CHAR_MATRIX.length; y++) {
            for (int x = 0; x < CHAR_MATRIX[y].length; x++) {
                char symbol = CHAR_MATRIX[y][x];
                int colorIndex = Character.getNumericValue(symbol);
                gc.setFill(PICO8_PALETTE[colorIndex]);
                gc.fillRect(x * 20, y * 20, 20, 20);
            }
        }

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 100, 100);
        primaryStage.setTitle("PICO-8 Image");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

