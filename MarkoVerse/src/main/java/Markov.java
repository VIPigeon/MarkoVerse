import java.util.*;

public class Markov {
    private final AhoCorasick AC = new AhoCorasick();
    private List<String> substrings;
    private final Random random;

    public Markov() {
        random = new Random();
    }

    public Markov(int seed) {
        random = new Random(seed);
    }

    // Вставка слова в префиксное дерево
    public void insert(String word, int index) {
        AC.insert(word, index);
    }

    // Построение автоматов
    public void build() {
        AC.build();
    }

    // Поиск вхождений в тексте
    public Integer search(String text) {
        List<Integer> foundIndices = AC.search(text);
        int ind = random.nextInt(foundIndices.size());
        return foundIndices.get(ind);
    }
}
