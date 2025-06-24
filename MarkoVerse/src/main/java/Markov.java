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
    public Pair<Integer, Integer> search(String text) {
        List<Pair<Integer, Integer>> foundIndices = AC.search(text);
        int ind = random.nextInt(foundIndices.size());
        return foundIndices.get(ind);
    }

    // Специальный класс для 2D
    private class Match {
        int rule_id;
        int row_index;
        int column_index;
        int direction;
        /*
             0
            2+3
             1
        */
        Match(int _rule_id, int _row_index, int _column_index, int _direction) {
            rule_id = _rule_id;
            row_index = _row_index;
            column_index = _column_index;
            direction = _direction;
        }

        public int[] to_int_array() {
            int[] res = new int[4];
            res[0] = rule_id;
            res[1] = row_index;
            res[2] = column_index;
            res[3] = direction;
            return res;
        }
    }

    // Поиск вхождений в матрице
    public int[] search(char[][] matrix) {
        List<Match> foundMatches = new ArrayList<>();
        for (int row_i = 0; row_i < matrix.length; row_i++) {
            // добавляем вхождения из строки
            List<Pair<Integer, Integer>> pos_rule = AC.search(String.valueOf(matrix[row_i]));
            for (int i = 0; i < pos_rule.size(); i++) {
                int pos = pos_rule.get(i).getFirst();
                int rule_id = pos_rule.get(i).getSecond();
                foundMatches.add(new Match(rule_id, row_i, pos, 3));
            }

            // переворачиваем строку
            int n = matrix[row_i].length;
            char[] reversed = new char[n];
            for (int i = 0; i < n; i++) {
                reversed[i] = matrix[row_i][n - i - 1];
            }
            // добавляем вхождения из перевернутой строки
            pos_rule = AC.search(String.valueOf(reversed));
            for (int i = 0; i < pos_rule.size(); i++) {
                int pos = pos_rule.get(i).getFirst();
                int rule_id = pos_rule.get(i).getSecond();
                foundMatches.add(new Match(rule_id, row_i, n - pos - 1, 2));
            }
        }
        for (int j = 0; j < matrix[0].length; j++) {
            // создаем столбец
            char[] column = new char[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                column[i] = matrix[i][j];
            }

            // добавляем вхождения из столбца
            List<Pair<Integer, Integer>> pos_rule = AC.search(String.valueOf(column));
            for (int i = 0; i < pos_rule.size(); i++) {
                int pos = pos_rule.get(i).getFirst();
                int rule_id = pos_rule.get(i).getSecond();
                foundMatches.add(new Match(rule_id, pos, j, 1));
            }

            // переворачиваем столбец
            int n = column.length;
            char[] reversed = new char[n];
            for (int i = 0; i < n; i++) {
                reversed[i] = column[n - i - 1];
            }
            // добавляем вхождения из перевернутого столбца
            pos_rule = AC.search(String.valueOf(reversed));
            for (int i = 0; i < pos_rule.size(); i++) {
                int pos = pos_rule.get(i).getFirst();
                int rule_id = pos_rule.get(i).getSecond();
                foundMatches.add(new Match(rule_id, n - pos - 1, j, 0));
            }
        }
        int ind = random.nextInt(foundMatches.size());
        Match match = foundMatches.get(ind);
        return match.to_int_array();
    }
}
