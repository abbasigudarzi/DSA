package com.github.codemaster.fundamentals.stack;

/**
 * Bracket matching - the canonical "why a stack" problem.
 *
 * <p>A closing bracket must match the <b>most recent</b> unclosed opening
 * bracket. "Most recent" is the definition of LIFO, so the stack does all the
 * work. Counting brackets instead of stacking them fails on {@code "([)]"}.</p>
 */
public final class BalancedBrackets {

    private BalancedBrackets() {
    }

    /**
     * Time O(n), space O(n) - worst case every character is an opening bracket.
     *
     * @return true when every bracket is closed in the right order
     */
    public static boolean isBalanced(String input) {
        ArrayStack<Character> open = new ArrayStack<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            switch (current) {
                case '(', '[', '{' -> open.push(current);
                case ')', ']', '}' -> {
                    if (open.isEmpty() || open.pop() != matching(current)) {
                        return false;
                    }
                }
                default -> {
                    // ignore everything that is not a bracket
                }
            }
        }
        return open.isEmpty(); // leftover openings mean unclosed brackets
    }

    private static char matching(char closing) {
        return switch (closing) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            default -> throw new IllegalArgumentException("not a closing bracket: " + closing);
        };
    }
}
