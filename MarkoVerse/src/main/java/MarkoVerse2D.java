import java.util.*;

public class MarkoVerse2D {
    private char[][] current_word;
    private int limit = 1000;  // максимальное количество итераций алгоритма
    private List<Pair<String, String>> rules;
    private Markov markov = new Markov();

    public void build(char[][] input_word, List<Pair<String, String>> _rules) {
        current_word = input_word;
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
        int[] match = markov.search(current_word);
        int rule_id = match[0];
        int row_index = match[1];
        int column_index = match[2];
        int direction = match[3];

        if (rule_id == -1) {
            return false;
        }

        String replacement_string = rules.get(rule_id).getSecond();

        // обновление фрагмента
        if (direction == 3) {  // right
            for (int i = column_index; i < column_index + replacement_string.length(); i++) {
                current_word[row_index][i] = replacement_string.charAt(i - column_index);
            }
        }
        else if (direction == 2) {  // left
            for (int i = column_index; i > column_index - replacement_string.length(); i--) {
                current_word[row_index][i] = replacement_string.charAt(column_index - i);
            }
        }
        else if (direction == 1) {  // down
            for (int i = row_index; i < row_index + replacement_string.length(); i++) {
                current_word[i][column_index] = replacement_string.charAt(i - row_index);
            }
        }
        else if (direction == 0) {  // up
            for (int i = row_index; i > row_index - replacement_string.length(); i--) {
                current_word[i][column_index] = replacement_string.charAt(row_index - i);
            }
        }
        else { throw new RuntimeException("Incorrect match direction"); }

        return true;
    }

    public void set_limit(int _limit) {
        limit = _limit;
    }

    public char[][] get_current_word() {
        return current_word;
    }

    public char[][] run() {
        for (int i = 0; i < limit; i++) {
            if (!this.update()) {
                return current_word;
            }
        }
        throw new RuntimeException("The number of iterations has exceeded the limit");
    }
}

