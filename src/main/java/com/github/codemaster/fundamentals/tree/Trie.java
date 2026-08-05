package com.github.codemaster.fundamentals.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Trie (prefix tree): the <b>path</b> spells the word, the node only records
 * whether a word ends there.
 *
 * <p>Cost depends on the length of the key, not on how many keys are stored:
 * inserting, finding or prefix-matching a word of length L is O(L), even with a
 * million words in the trie. A hash map matches that for exact lookup but
 * cannot answer "give me everything starting with 'algo'" without scanning
 * every key.</p>
 *
 * <pre>
 *   insert / contains / startsWith   O(L)
 *   wordsWithPrefix                  O(L + size of the matched subtree)
 *   space                            O(total characters), shared prefixes stored once
 * </pre>
 *
 * <p>Used for autocomplete, spell checking, IP routing tables and word-search
 * puzzles.</p>
 */
public class Trie {

    private static final class Node {
        final Map<Character, Node> children = new HashMap<>();
        boolean endOfWord;
    }

    private final Node root = new Node();
    private int size;

    /** Number of distinct words stored. */
    public int size() {
        return size;
    }

    /** O(L). Inserting an existing word is a no-op. */
    public void insert(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            current = current.children.computeIfAbsent(word.charAt(i), key -> new Node());
        }
        if (!current.endOfWord) {
            current.endOfWord = true;
            size++;
        }
    }

    /** O(L). True only for a complete inserted word, not for a bare prefix. */
    public boolean contains(String word) {
        Node node = walk(word);
        return node != null && node.endOfWord;
    }

    /** O(L). True when any stored word starts with this prefix. */
    public boolean startsWith(String prefix) {
        return walk(prefix) != null;
    }

    /**
     * Every stored word beginning with {@code prefix}, in no particular order.
     * O(L + characters in the matched subtree).
     */
    public List<String> wordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        Node start = walk(prefix);
        if (start != null) {
            collect(start, new StringBuilder(prefix), words);
        }
        return words;
    }

    private void collect(Node node, StringBuilder path, List<String> words) {
        if (node.endOfWord) {
            words.add(path.toString());
        }
        for (Map.Entry<Character, Node> child : node.children.entrySet()) {
            path.append(child.getKey());
            collect(child.getValue(), path, words);
            path.deleteCharAt(path.length() - 1); // undo before trying the next branch
        }
    }

    /** Walks the path for a string, or null when it runs out of edges. O(L). */
    private Node walk(String text) {
        Node current = root;
        for (int i = 0; i < text.length(); i++) {
            current = current.children.get(text.charAt(i));
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    /**
     * Removes a word, pruning nodes that no longer lead anywhere. O(L).
     *
     * @return true when the word was present
     */
    public boolean delete(String word) {
        boolean removed = delete(root, word, 0);
        if (removed) {
            size--;
        }
        return removed;
    }

    private boolean delete(Node node, String word, int depth) {
        if (depth == word.length()) {
            if (!node.endOfWord) {
                return false;
            }
            node.endOfWord = false;
            return true;
        }
        char character = word.charAt(depth);
        Node child = node.children.get(character);
        if (child == null) {
            return false;
        }
        boolean removed = delete(child, word, depth + 1);
        if (removed && child.children.isEmpty() && !child.endOfWord) {
            node.children.remove(character); // nothing below, drop the edge
        }
        return removed;
    }
}
