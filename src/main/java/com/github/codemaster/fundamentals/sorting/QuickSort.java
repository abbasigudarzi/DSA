package com.github.codemaster.fundamentals.sorting;

import java.util.Random;

/**
 * Quicksort: pick a pivot, partition the array so smaller values sit left and
 * larger values right, then recurse on both sides.
 *
 * <p>Time O(n log n) on average, O(n^2) when the pivot is consistently terrible
 * (a first-element pivot on already sorted input is exactly that). Space
 * O(log n) for the recursion. Not stable.</p>
 *
 * <p>Two defences are applied here:</p>
 * <ul>
 *   <li><b>Random pivot</b> - no fixed input pattern can force the worst case.</li>
 *   <li><b>Tail-call elimination</b> - recurse into the smaller side and loop on
 *       the larger, bounding stack depth to O(log n).</li>
 * </ul>
 *
 * <p>Despite the bad worst case, quicksort is usually the fastest comparison
 * sort in practice: it sorts in place with excellent cache locality and a small
 * constant factor. That is why {@code Arrays.sort(int[])} uses a
 * dual-pivot quicksort.</p>
 */
public class QuickSort implements Sorter {

    private final Random random = new Random();

    @Override
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(array, low, high);
            // recurse into the smaller side, iterate on the larger one
            if (pivotIndex - low < high - pivotIndex) {
                sort(array, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                sort(array, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    /**
     * Lomuto partition around a randomly chosen pivot.
     *
     * <p>Invariant: everything in {@code [low, boundary)} is &lt;= the pivot.
     * The pivot is parked at {@code high} during the scan and swapped into its
     * final place at the end - and that position is genuinely final, which is
     * why the recursion excludes it.</p>
     *
     * @return the final index of the pivot
     */
    private int partition(int[] array, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        Sorter.swap(array, pivotIndex, high);
        int pivot = array[high];
        int boundary = low;
        for (int i = low; i < high; i++) {
            if (array[i] <= pivot) {
                Sorter.swap(array, i, boundary++);
            }
        }
        Sorter.swap(array, boundary, high);
        return boundary;
    }

    /**
     * Quickselect: the k-th smallest element (0-based) in O(n) average time.
     *
     * <p>Same partition, but only one side is followed - the side that can
     * still contain k. Sorting first would cost O(n log n) for the same
     * answer.</p>
     *
     * <p><b>Modifies the array.</b></p>
     */
    public int select(int[] array, int k) {
        if (k < 0 || k >= array.length) {
            throw new IndexOutOfBoundsException("k " + k + " out of bounds for length " + array.length);
        }
        int low = 0;
        int high = array.length - 1;
        while (low < high) {
            int pivotIndex = partition(array, low, high);
            if (pivotIndex == k) {
                return array[k];
            }
            if (pivotIndex < k) {
                low = pivotIndex + 1;
            } else {
                high = pivotIndex - 1;
            }
        }
        return array[k];
    }
}
