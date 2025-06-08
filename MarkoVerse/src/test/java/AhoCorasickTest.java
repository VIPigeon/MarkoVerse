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

        for (int i = 0; i < keywords.size(); i++) {
            ac.insert(keywords.get(i), i);
        }

        ac.build();

        String text = "ushers";
        List<Integer> matches = ac.search(text);
        List<Integer> expectedMatches = List.of(1, 0, 3) ;
        Assert.assertEquals(matches, expectedMatches);
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

        for (int i = 0; i < keywords.size(); i++) {
            ac.insert(keywords.get(i), i);
        }

        ac.build();

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
}
