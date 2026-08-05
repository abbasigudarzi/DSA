package com.github.codemaster.fundamentals.queue;

import java.util.NoSuchElementException;

/**
 * FIFO queue on a circular buffer.
 *
 * <p>{@code head} is the index of the next element to leave, {@code tail} is
 * the index where the next element will be written, and both wrap with
 * {@code % capacity}. Because indices wrap instead of elements moving, enqueue
 * and dequeue are O(1).</p>
 *
 * <p>{@code head == tail} is ambiguous - it means both empty and full - so this
 * implementation keeps an explicit {@code size} counter rather than wasting a
 * slot. The buffer doubles when full.</p>
 *
 * @param <T> element type
 */
public class CircularArrayQueue<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] items;
    private int head;
    private int tail;
    private int size;

    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CircularArrayQueue(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.items = new Object[initialCapacity];
    }

    /** Adds at the tail. O(1) amortized. */
    public void enqueue(T value) {
        if (size == items.length) {
            grow();
        }
        items[tail] = value;
        tail = (tail + 1) % items.length;
        size++;
    }

    /** Removes from the head. O(1). */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        T value = (T) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        size--;
        return value;
    }

    /** Head element without removing it. O(1). */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        return (T) items[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return items.length;
    }

    /**
     * Copies into a bigger array, unrolling the wrap so the new buffer starts
     * at index 0. O(n), and it happens rarely enough to stay O(1) amortized.
     */
    private void grow() {
        Object[] bigger = new Object[items.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = items[(head + i) % items.length];
        }
        items = bigger;
        head = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("head [");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(items[(head + i) % items.length]);
        }
        return builder.append("] tail").toString();
    }
}
