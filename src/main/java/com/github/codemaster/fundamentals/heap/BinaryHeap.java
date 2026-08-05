package com.github.codemaster.fundamentals.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Binary heap stored in an array - a priority queue built from scratch.
 *
 * <p>Defaults to a min-heap using natural ordering. Pass
 * {@code Comparator.reverseOrder()} for a max-heap: the comparator is the only
 * difference between the two.</p>
 *
 * @param <T> element type
 */
public class BinaryHeap<T> {

    private static final int DEFAULT_CAPACITY = 16;

    private final Comparator<? super T> comparator;
    private Object[] items;
    private int size;

    /** Min-heap over {@link Comparable} elements. */
    public static <T extends Comparable<? super T>> BinaryHeap<T> minHeap() {
        return new BinaryHeap<>(Comparator.naturalOrder());
    }

    /** Max-heap over {@link Comparable} elements. */
    public static <T extends Comparable<? super T>> BinaryHeap<T> maxHeap() {
        return new BinaryHeap<>(Comparator.reverseOrder());
    }

    public BinaryHeap(Comparator<? super T> comparator) {
        this(comparator, DEFAULT_CAPACITY);
    }

    public BinaryHeap(Comparator<? super T> comparator, int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.comparator = comparator;
        this.items = new Object[initialCapacity];
    }

    /**
     * Builds a heap from an existing array in O(n).
     *
     * <p>Sifting down from the last parent to the root is O(n), not
     * O(n log n): most nodes are near the bottom and sift down almost no
     * distance. Inserting the elements one at a time would cost O(n log n).</p>
     */
    public static <T> BinaryHeap<T> heapify(T[] values, Comparator<? super T> comparator) {
        BinaryHeap<T> heap = new BinaryHeap<>(comparator, Math.max(1, values.length));
        System.arraycopy(values, 0, heap.items, 0, values.length);
        heap.size = values.length;
        for (int i = values.length / 2 - 1; i >= 0; i--) {
            heap.siftDown(i);
        }
        return heap;
    }

    /** Adds an element and sifts it up. O(log n). */
    public void offer(T value) {
        if (size == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[size] = value;
        siftUp(size++);
    }

    /**
     * Removes and returns the root (minimum for a min-heap). O(log n).
     *
     * <p>The last element is moved to the root and sifted down - that keeps the
     * tree complete, which is the invariant the array layout depends on.</p>
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        T root = (T) items[0];
        items[0] = items[--size];
        items[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return root;
    }

    /** Root without removing it. O(1). */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        return (T) items[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Moves a node up while it beats its parent. O(log n). */
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(index, parent) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    /** Moves a node down while a child beats it. O(log n). */
    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = left + 1;
            int best = index;
            if (left < size && compare(left, best) < 0) {
                best = left;
            }
            if (right < size && compare(right, best) < 0) {
                best = right;
            }
            if (best == index) {
                return;
            }
            swap(index, best);
            index = best;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(int i, int j) {
        return comparator.compare((T) items[i], (T) items[j]);
    }

    private void swap(int i, int j) {
        Object temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}
