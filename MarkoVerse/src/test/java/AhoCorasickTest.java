import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AhoCorasickTest {
    AhoCorasick ac = new AhoCorasick();

    @Test
    public void BasicTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        String text = "ushers";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        List<Pair<Integer, Integer>> expectedMatches = List.of(new Pair<>(2, 0));
        assertListsOfPairsAreEqual(expectedMatches, matches);
    }

    private void buildACFromList(List<String> keywords) {
        for (int i = 0; i < keywords.size(); i++) {
            ac.insert(keywords.get(i), i);
        }

        ac.build();
    }

    private void assertListsOfPairsAreEqual(List<Pair<Integer, Integer>> expected, List<Pair<Integer, Integer>> actual) {
        Assert.assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            var firstPair = expected.get(i);
            var secondPair = actual.get(i);

            Assert.assertEquals(firstPair.first, secondPair.first);
            Assert.assertEquals(firstPair.second, secondPair.second);
        }
    }

    @Test
    public void NoSubstringsTest() {
        ac.build();
        String text = "ushers";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        assertListsOfPairsAreEqual(List.of(new Pair<>(-1, -1)), matches);
    }

    @Test
    public void EmptyTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        String text = "";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        assertListsOfPairsAreEqual(List.of(new Pair<>(-1, -1)), matches);
    }

    @Test
    public void EmptyTextWithNoSubstringsTest() {
        ac.build();

        String text = "";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        assertListsOfPairsAreEqual(List.of(new Pair<>(-1, -1)), matches);
    }

    @Test
    public void AtTheStartOfTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers", "her");

        buildACFromList(keywords);

        String text = "hersey";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        List<Pair<Integer, Integer>> expectedMatches = List.of(new Pair<>(0, 0));
        assertListsOfPairsAreEqual(expectedMatches, matches);
    }

    @Test
    public void AtTheEndOfTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        String text = "porshe";
        List<Pair<Integer, Integer>> matches = ac.search(text);
        List<Pair<Integer, Integer>> expectedMatches = List.of(new Pair<>(4, 0));
        assertListsOfPairsAreEqual(expectedMatches, matches);
    }

    @Test
    public void MultipleTextsTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        List<String> texts = Arrays.asList("ushers", "hersey", "porshe", "this");
        List<List<Pair<Integer, Integer>>> matches = List.of(
                List.of(new Pair<>(2, 0)),
                List.of(new Pair<>(0, 0)),
                List.of(new Pair<>(4, 0)),
                List.of(new Pair<>(1, 2))
        );
        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            List<Pair<Integer, Integer>> expectedMatches = matches.get(i);

            List<Pair<Integer, Integer>> actualMatches = ac.search(text);
            assertListsOfPairsAreEqual(expectedMatches, actualMatches);
        }
    }
}
