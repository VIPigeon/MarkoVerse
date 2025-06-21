import java.util.*;

public class AhoCorasick {
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        TrieNode failLink;
        List<Integer> output = new ArrayList<>();
    }

    private TrieNode root;
    private Map<Integer, String> words;

    public AhoCorasick() {
        root = new TrieNode();
        words = new HashMap<>();
    }

    // Вставка слова в префиксное дерево
    public void insert(String word, int index) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.output.add(index);
        words.put(index, word);
    }

    // Построение автоматов
    public void build() {
        Queue<TrieNode> queue = makeQueueAndAddLinkForRoot();
        addLinkForChildrenAndAddThemTo(queue);
        updateLinkForEachIn(queue);
    }

    private Queue<TrieNode> makeQueueAndAddLinkForRoot() {
        Queue<TrieNode> queue = new LinkedList<>();
        root.failLink = root;
        return queue;
    }

    private void addLinkForChildrenAndAddThemTo(Queue<TrieNode> queue) {
        for (TrieNode child : root.children.values()) {
            child.failLink = root;
            queue.add(child);
        }
    }

    private void updateLinkForEachIn(Queue<TrieNode> queue) {
        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();

            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();

                TrieNode failNode = current.failLink;
                while (failNode != root && !failNode.children.containsKey(c)) {
                    failNode = failNode.failLink;
                }
                child.failLink = failNode.children.getOrDefault(c, root);
                child.output.addAll(child.failLink.output);
                queue.add(child);
            }
        }
    }

    // Поиск вхождений в тексте
    public List<Pair<Integer, Integer>> search(String text) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        TrieNode current = root;
        int maxPriority = Integer.MAX_VALUE;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (current != root && !current.children.containsKey(c)) {
                current = current.failLink;
            }
            current = current.children.getOrDefault(c, root);
            for (int index : current.output) {
                int position = i - words.get(index).length() + 1;

                if (index < maxPriority) {
                    result.clear();
                    maxPriority = index;
                    result.add(new Pair<>(position, index));
                }
                else if (index == maxPriority) {
                    result.add(new Pair<>(position, index));
                }
            }
        }
        if (result.isEmpty())
            result.add(new Pair<>(-1, -1));

        return result;
    }
}
