import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarkovTest {
    Markov markov = new Markov(0);
    Random random = new Random(0);

    @Test
    public void BasicTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        for (int i = 0; i < keywords.size(); i++) {
            markov.insert(keywords.get(i), i);
        }

        markov.build();

        String text = "ushers";
        int match = markov.search(text);

        List<Integer> expectedMatches = List.of(1, 0, 3);
        int expectedMatch = expectedMatches.get(random.nextInt(3));
        Assert.assertEquals(expectedMatch, match);
    }
}
