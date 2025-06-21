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

        createMarkovFromList(keywords);

        String text = "ushers";
        Pair<Integer, Integer> match = markov.search(text);

        List<Pair<Integer, Integer>> expectedMatches = List.of(new Pair<>(2, 0));
        Pair<Integer, Integer> expectedMatch = expectedMatches.get(random.nextInt(1));
        assertPairsAreEqual(expectedMatch, match);
    }



    private void createMarkovFromList(List<String> keywords) {
        for (int i = 0; i < keywords.size(); i++) {
            markov.insert(keywords.get(i), i);
        }

        markov.build();
    }

    private void assertPairsAreEqual(Pair<Integer, Integer> expected, Pair<Integer, Integer> actual) {
        Assert.assertEquals(expected.first, actual.first);
        Assert.assertEquals(expected.second, actual.second);
    }

    @Test
    public void RandomTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        createMarkovFromList(keywords);

        String text = "hehehehehe";
        Pair<Integer, Integer> match = markov.search(text);

        List<Pair<Integer, Integer>> expectedMatches = List.of(
                new Pair<>(0, 0),
                new Pair<>(2, 0),
                new Pair<>(4, 0),
                new Pair<>(6, 0),
                new Pair<>(8, 0)
        );
        Pair<Integer, Integer> expectedMatch = expectedMatches.get(random.nextInt(5));
        assertPairsAreEqual(expectedMatch, match);
    }
}
