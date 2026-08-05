package com.github.codemaster.fundamentals.sorting;

/**
 * Merge sort: split in half, sort both halves, merge the two sorted halves.
 *
 * <p>The classic divide and conquer. Time O(n log n) in <b>every</b> case -
 * log n levels of splitting, O(n) merging work per level. Space O(n) for the
 * scratch buffer, which is its one real cost. Stable, as long as the merge
 * takes from the left half on ties.</p>
 *
 * <p>Merge sort is the right choice when worst-case guarantees matter, when
 * stability matters, or when the data does not fit in memory (external sorting
 * merges sorted runs from disk - quicksort cannot do that).</p>
 */
public class MergeSort implements Sorter {

    @Override
    public void sort(int[] array) {
        if (array.length < 2) {
            return;
        }
        // one buffer allocated once, reused by every merge
        int[] buffer = new int[array.length];
        sort(array, buffer, 0, array.length - 1);
    }

    private void sort(int[] array, int[] buffer, int low, int high) {
        if (low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        sort(array, buffer, low, middle);
        sort(array, buffer, middle + 1, high);
        merge(array, buffer, low, middle, high);
    }

    /**
     * Merges the two sorted halves {@code [low..middle]} and
     * {@code [middle+1..high]}. O(high - low).
     */
    private void merge(int[] array, int[] buffer, int low, int middle, int high) {
        System.arraycopy(array, low, buffer, low, high - low + 1);
        int left = low;
        int right = middle + 1;
        for (int i = low; i <= high; i++) {
            if (left > middle) {
                array[i] = buffer[right++];
            } else if (right > high) {
                array[i] = buffer[left++];
            } else if (buffer[left] <= buffer[right]) {
                array[i] = buffer[left++]; // <= keeps it stable
            } else {
                array[i] = buffer[right++];
            }
        }
    }
}
