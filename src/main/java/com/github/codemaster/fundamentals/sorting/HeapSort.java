package com.github.codemaster.fundamentals.sorting;

/**
 * Heap sort: build a max-heap in the array itself, then repeatedly swap the
 * root to the end and shrink the heap.
 *
 * <p>Time O(n log n) in every case, space O(1) - the only algorithm here that
 * has both. Not stable.</p>
 *
 * <p>Why it is not the default anyway: it jumps around the array (parent to
 * child is a distance that doubles), so it misses the CPU cache constantly and
 * loses to quicksort in wall-clock time despite the better worst case. It is
 * the safety net inside introsort - quicksort that switches to heap sort when
 * recursion goes too deep.</p>
 *
 * <p>The array layout is the same one used by
 * {@link com.github.codemaster.fundamentals.heap.BinaryHeap}:
 * {@code left(i) = 2i+1}, {@code right(i) = 2i+2}.</p>
 */
public class HeapSort implements Sorter {

    @Override
    public void sort(int[] array) {
        int n = array.length;
        // build a max-heap bottom up: O(n), not O(n log n)
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n);
        }
        // the root is the largest value: park it at the end, shrink, repair
        for (int end = n - 1; end > 0; end--) {
            Sorter.swap(array, 0, end);
            siftDown(array, 0, end);
        }
    }

    /** Pushes {@code index} down until the max-heap property holds. O(log n). */
    private void siftDown(int[] array, int index, int heapSize) {
        while (true) {
            int left = 2 * index + 1;
            int right = left + 1;
            int largest = index;
            if (left < heapSize && array[left] > array[largest]) {
                largest = left;
            }
            if (right < heapSize && array[right] > array[largest]) {
                largest = right;
            }
            if (largest == index) {
                return;
            }
            Sorter.swap(array, index, largest);
            index = largest;
        }
    }
}
