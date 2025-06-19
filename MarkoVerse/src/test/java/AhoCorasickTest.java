import org.junit.Assert;
import org.junit.Before;
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
        List<Integer> matches = ac.search(text);
        List<Integer> expectedMatches = List.of(1, 0, 3) ;
        Assert.assertEquals(matches, expectedMatches);
    }

    private void buildACFromList(List<String> keywords) {
        for (int i = 0; i < keywords.size(); i++) {
            ac.insert(keywords.get(i), i);
        }

        ac.build();
    }

    @Test
    public void NoSubstringsTest() {
        ac.build();
        String text = "ushers";
        List<Integer> matches = ac.search(text);
        Assert.assertEquals(List.of(), matches);
    }

    @Test
    public void EmptyTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        String text = "";
        List<Integer> matches = ac.search(text);
        Assert.assertEquals(List.of(), matches);
    }

    @Test
    public void EmptyTextWithNoSubstringsTest() {
        ac.build();

        String text = "";
        List<Integer> matches = ac.search(text);
        Assert.assertEquals(List.of(), matches);
    }

    @Test
    public void AtTheStartOfTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers", "her");

        buildACFromList(keywords);

        String text = "hersey";
        List<Integer> matches = ac.search(text);
        List<Integer> expectedMatches = List.of(0, 4, 3) ;
        Assert.assertEquals(matches, expectedMatches);
    }

    @Test
    public void AtTheEndOfTextTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        String text = "porshe";
        List<Integer> matches = ac.search(text);
        List<Integer> expectedMatches = List.of(1, 0) ;
        Assert.assertEquals(matches, expectedMatches);
    }

    @Test
    public void MultipleTextsTest() {
        List<String> keywords = Arrays.asList("he", "she", "his", "hers");

        buildACFromList(keywords);

        List<String> texts = Arrays.asList("ushers", "hersey", "porshe", "this");
        List<List<Integer>> matches = List.of(List.of(1, 0, 3), List.of(0, 3), List.of(1, 0), List.of(2));
        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            List<Integer> expectedMatches = matches.get(i);

            List<Integer> actualMatches = ac.search(text);
            Assert.assertEquals(actualMatches, expectedMatches);
        }
    }
}
