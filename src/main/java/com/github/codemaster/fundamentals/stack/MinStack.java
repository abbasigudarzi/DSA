package com.github.codemaster.fundamentals.stack;

import java.util.EmptyStackException;

/**
 * A stack that also reports its minimum in O(1).
 *
 * <p>The trick: keep a second stack of minima. Every push records "the smallest
 * value seen at or below this depth", so popping the main stack pops that
 * answer away with it. Scanning for the minimum on demand would be O(n); this
 * trades O(n) extra memory for O(1) queries.</p>
 *
 * <p>All four operations are O(1).</p>
 */
public class MinStack {

    private final ArrayStack<Integer> values = new ArrayStack<>();
    private final ArrayStack<Integer> minima = new ArrayStack<>();

    public void push(int value) {
        values.push(value);
        minima.push(minima.isEmpty() ? value : Math.min(value, minima.peek()));
    }

    public int pop() {
        if (values.isEmpty()) {
            throw new EmptyStackException();
        }
        minima.pop();
        return values.pop();
    }

    public int peek() {
        if (values.isEmpty()) {
            throw new EmptyStackException();
        }
        return values.peek();
    }

    /** Smallest value currently on the stack. O(1). */
    public int min() {
        if (minima.isEmpty()) {
            throw new EmptyStackException();
        }
        return minima.peek();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }
}
