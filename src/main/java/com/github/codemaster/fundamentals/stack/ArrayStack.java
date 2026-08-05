package com.github.codemaster.fundamentals.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Stack backed by a growable array. The top of the stack is the end of the
 * array, which is why push and pop are O(1): no element ever moves.
 *
 * @param <T> element type
 */
public class ArrayStack<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] items;
    private int size;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.items = new Object[initialCapacity];
    }

    /** O(1) amortized. */
    public void push(T value) {
        if (size == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[size++] = value;
    }

    /** O(1). @throws EmptyStackException when empty */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        T value = (T) items[--size];
        items[size] = null; // do not hold the reference alive
        return value;
    }

    /** Top element without removing it. O(1). */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return (T) items[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("bottom [");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(items[i]);
        }
        return builder.append("] top").toString();
    }
}
