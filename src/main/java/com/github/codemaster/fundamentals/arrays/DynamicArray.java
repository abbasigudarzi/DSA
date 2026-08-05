package com.github.codemaster.fundamentals.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A growable array - {@code ArrayList} rebuilt from scratch.
 *
 * <p>Two fields carry the whole idea: a fixed backing array and a {@code size}
 * counter. {@code items.length} is the capacity (memory owned);
 * {@code size} is how much of it is in use.</p>
 *
 * <p>When the array is full, capacity is <b>doubled</b>. Doubling is what makes
 * append O(1) amortized: n appends cost n + n/2 + n/4 + ... &lt; 2n copies in
 * total. Growing by a constant instead would make n appends O(n^2).</p>
 *
 * @param <T> element type
 */
public class DynamicArray<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] items;
    private int size;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.items = new Object[initialCapacity];
        this.size = 0;
    }

    /** Number of elements stored. O(1). */
    public int size() {
        return size;
    }

    /** Memory currently owned, in elements. O(1). */
    public int capacity() {
        return items.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** O(1) - the whole point of an array. */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index, size);
        return (T) items[index];
    }

    /** O(1). Returns the replaced element. */
    @SuppressWarnings("unchecked")
    public T set(int index, T value) {
        checkIndex(index, size);
        T previous = (T) items[index];
        items[index] = value;
        return previous;
    }

    /** Append. O(1) amortized, O(n) on the resize step. */
    public void add(T value) {
        ensureCapacity(size + 1);
        items[size++] = value;
    }

    /**
     * Insert at an index. O(n) - every element from {@code index} onward shifts
     * one slot right to open a hole.
     */
    public void add(int index, T value) {
        checkIndex(index, size + 1);
        ensureCapacity(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = value;
        size++;
    }

    /**
     * Remove by index. O(n) - everything after the hole shifts one slot left.
     *
     * @return the removed element
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index, size);
        T removed = (T) items[index];
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[--size] = null; // let the garbage collector reclaim it
        return removed;
    }

    /** O(n) linear scan. Returns -1 when absent. */
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /** O(n). */
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    /** O(n) - nulls every slot so references are not held alive. */
    public void clear() {
        Arrays.fill(items, 0, size, null);
        size = 0;
    }

    /** Copy of the used region. O(n). */
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    private void ensureCapacity(int required) {
        if (required <= items.length) {
            return;
        }
        int newCapacity = Math.max(items.length * 2, required);
        items = Arrays.copyOf(items, newCapacity);
    }

    private static void checkIndex(int index, int bound) {
        if (index < 0 || index >= bound) {
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for size " + bound);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) items[cursor++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(items[i]);
        }
        return builder.append(']').toString();
    }
}
