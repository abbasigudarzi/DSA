package com.github.codemaster.fundamentals.queue;

import java.util.NoSuchElementException;

/**
 * Double-ended queue on a circular buffer: add and remove at both ends in O(1).
 *
 * <p>A deque subsumes both of the earlier structures - use one end only and it
 * is a stack, use opposite ends and it is a queue. This is why
 * {@code java.util.ArrayDeque} is the recommended implementation for both in
 * modern Java.</p>
 *
 * <p>The one subtlety is moving {@code head} backward: {@code head - 1} can go
 * negative, so it must wrap with {@code (head - 1 + capacity) % capacity}.</p>
 *
 * @param <T> element type
 */
public class CircularDeque<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] items;
    private int head;
    private int size;

    public CircularDeque() {
        this(DEFAULT_CAPACITY);
    }

    public CircularDeque(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.items = new Object[initialCapacity];
    }

    /** O(1) amortized. */
    public void addFirst(T value) {
        if (size == items.length) {
            grow();
        }
        head = (head - 1 + items.length) % items.length;
        items[head] = value;
        size++;
    }

    /** O(1) amortized. */
    public void addLast(T value) {
        if (size == items.length) {
            grow();
        }
        items[(head + size) % items.length] = value;
        size++;
    }

    /** O(1). */
    @SuppressWarnings("unchecked")
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        T value = (T) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        size--;
        return value;
    }

    /** O(1). */
    @SuppressWarnings("unchecked")
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        int last = (head + size - 1) % items.length;
        T value = (T) items[last];
        items[last] = null;
        size--;
        return value;
    }

    /** O(1). */
    @SuppressWarnings("unchecked")
    public T peekFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        return (T) items[head];
    }

    /** O(1). */
    @SuppressWarnings("unchecked")
    public T peekLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        return (T) items[(head + size - 1) % items.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void grow() {
        Object[] bigger = new Object[items.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = items[(head + i) % items.length];
        }
        items = bigger;
        head = 0;
    }
}
