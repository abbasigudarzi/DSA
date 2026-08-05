package com.github.codemaster.fundamentals.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Core string techniques: two pointers, frequency counting and the sliding
 * window, plus naive and KMP substring search side by side.
 */
public final class StringOps {

    private StringOps() {
    }

    /** Reverses with two pointers. Time O(n), space O(n) for the result. */
    public static String reverse(String input) {
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = temp;
        }
        return new String(chars);
    }

    /**
     * Palindrome check ignoring case and every non alphanumeric character.
     *
     * <p>Two pointers walk inward and skip what does not count, so no cleaned
     * copy of the string is allocated. Time O(n), space O(1).</p>
     */
    public static boolean isPalindrome(String input) {
        int left = 0;
        int right = input.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(input.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(input.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(input.charAt(left)) != Character.toLowerCase(input.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Anagram check by character frequency: count up for one string, down for
     * the other, and every counter must land back on zero.
     *
     * <p>Time O(n), space O(k) where k is the alphabet actually used. Sorting
     * both strings also works but costs O(n log n).</p>
     */
    public static boolean isAnagram(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < first.length(); i++) {
            counts.merge(first.charAt(i), 1, Integer::sum);
            counts.merge(second.charAt(i), -1, Integer::sum);
        }
        for (int count : counts.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Length of the longest substring with no repeated character.
     *
     * <p>A sliding window with a "last seen index" map. When a repeat is found
     * inside the window, the left edge jumps past the previous occurrence -
     * moving it one step at a time would be correct but slower.</p>
     *
     * <p>Time O(n), space O(k).</p>
     */
    public static int longestUniqueSubstring(String input) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int best = 0;
        int windowStart = 0;
        for (int end = 0; end < input.length(); end++) {
            char current = input.charAt(end);
            Integer previous = lastSeen.get(current);
            if (previous != null && previous >= windowStart) {
                windowStart = previous + 1;
            }
            lastSeen.put(current, end);
            best = Math.max(best, end - windowStart + 1);
        }
        return best;
    }

    /**
     * Naive substring search - try every starting offset.
     * Time O(n * m) worst case, space O(1). Kept as the baseline for
     * {@link #kmpSearch}.
     *
     * @return first index of {@code pattern} in {@code text}, or -1
     */
    public static int naiveSearch(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        for (int start = 0; start + pattern.length() <= text.length(); start++) {
            int offset = 0;
            while (offset < pattern.length() && text.charAt(start + offset) == pattern.charAt(offset)) {
                offset++;
            }
            if (offset == pattern.length()) {
                return start;
            }
        }
        return -1;
    }

    /**
     * Knuth-Morris-Pratt search. Time O(n + m), space O(m).
     *
     * <p>The idea: after a mismatch the naive version throws away everything it
     * just learned and restarts one character later. KMP precomputes, for every
     * prefix of the pattern, the length of the longest proper prefix that is
     * also a suffix ({@link #failureTable}). On a mismatch it slides the
     * pattern by that much instead of by one, and never moves the text pointer
     * backward.</p>
     *
     * @return first index of {@code pattern} in {@code text}, or -1
     */
    public static int kmpSearch(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int[] failure = failureTable(pattern);
        int matched = 0;
        for (int i = 0; i < text.length(); i++) {
            while (matched > 0 && text.charAt(i) != pattern.charAt(matched)) {
                matched = failure[matched - 1];
            }
            if (text.charAt(i) == pattern.charAt(matched)) {
                matched++;
            }
            if (matched == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    /**
     * KMP failure table: {@code table[i]} is the length of the longest proper
     * prefix of {@code pattern[0..i]} that is also a suffix of it.
     * Time O(m), space O(m).
     */
    public static int[] failureTable(String pattern) {
        int[] table = new int[pattern.length()];
        int length = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (length > 0 && pattern.charAt(i) != pattern.charAt(length)) {
                length = table[length - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
            }
            table[i] = length;
        }
        return table;
    }

    /**
     * Character frequency map. The building block behind anagram checks,
     * permutation problems and most sliding-window counting.
     * Time O(n), space O(k).
     */
    public static Map<Character, Integer> frequencies(String input) {
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            counts.merge(input.charAt(i), 1, Integer::sum);
        }
        return counts;
    }
}
