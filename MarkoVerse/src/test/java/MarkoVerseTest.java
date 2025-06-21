import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarkoVerseTest {
    MarkoVerse markoverse = new MarkoVerse();
    Random random = new Random(0);

    @Test
    public void BasicTest() {
        List<Pair<String, String>> rules = Arrays.asList(
            new Pair<String, String>("b", "w")
        );

        markoverse.build("bbbb", rules);
        String result = markoverse.run();
        Assert.assertEquals("Результат должен быть 'wwww'", result, "wwww");
    }

    @Test
    public void InfinityLoopTest() {
        List<Pair<String, String>> rules = Arrays.asList(
            new Pair<String, String>("bb", "ww"),
            new Pair<String, String>("ww", "bb")
        );

        markoverse.build("bbbb", rules);
        try {
            String result = markoverse.run();
            Assert.assertTrue("markoverse.run() должно было вызвать исключение", false);
        } catch (RuntimeException e) {
            Assert.assertTrue("Тест успешен", true);
        }
    }

    @Test
    public void InncorrectRulesTest_1() {
        List<Pair<String, String>> rules = Arrays.asList(
            new Pair<String, String>("bb", "w")
        );

        try {
            markoverse.build("bbww", rules);
            Assert.assertTrue("markoverse.build(...) должно было вызвать исключение", false);
        } catch (RuntimeException e) {
            Assert.assertTrue("Тест успешен", true);
        }
    }

    @Test
    public void InncorrectRulesTest_2() {
        List<Pair<String, String>> rules = Arrays.asList(
            new Pair<String, String>("bb", "www")
        );

        try {
            markoverse.build("bbww", rules);
            Assert.assertTrue("markoverse.build(...) должно было вызвать исключение", false);
        } catch (RuntimeException e) {
            Assert.assertTrue("Тест успешен", true);
        }
    }
}
