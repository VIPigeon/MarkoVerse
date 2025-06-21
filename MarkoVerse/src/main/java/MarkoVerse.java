import java.util.*;

public class MarkoVerse {
    private char[] current_word;
    private int limit = 1000;  // максимальное количество итераций алгоритм
    private List<Pair<String, String>> rules;
    private Markov markov = new Markov();

    public void build(String input_word, List<Pair<String, String>> _rules) {
        current_word = input_word.toCharArray();
        rules = _rules;
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).getFirst().length() != rules.get(i).getSecond().length()) {
                throw new RuntimeException("The rules are incorrect: <pattern> must be the same length as <replacement_string>");
            }
            markov.insert(rules.get(i).getFirst(), i);
        }
        markov.build();
    }

    public boolean update() {
        Pair<Integer, Integer> match = markov.search(String.valueOf(current_word));
        int pos = match.getFirst();
        int rule_id = match.getSecond();
        if (pos == -1) {
            return false;
        }

        String replacement_string = rules.get(rule_id).getSecond();

        for (int i = pos; i < pos + replacement_string.length(); i++) {
            current_word[i] = replacement_string.charAt(i - pos);
        }
        return true;
    }

    public void set_limit(int _limit) {
        limit = _limit;
    }

    public String get_current_word() {
        return String.valueOf(current_word);
    }

    public String run() {
        for (int i = 0; i < limit; i++) {
            if (!this.update()) {
                return String.valueOf(current_word);
            }
        }
        throw new RuntimeException("The number of iterations has exceeded the limit");
    }
}

