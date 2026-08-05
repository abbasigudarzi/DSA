package com.github.codemaster.fundamentals.sorting;

/**
 * One interface for every sorting algorithm, so they can be swapped, timed and
 * tested against each other.
 *
 * <p>Sorts {@code int[]} in place: primitives keep the code about the algorithm
 * rather than about generics and boxing.</p>
 */
public interface Sorter {

    /** Sorts the array ascending, in place. */
    void sort(int[] array);

    /** Algorithm name, for benchmark output. */
    default String name() {
        return getClass().getSimpleName();
    }

    /** Swaps two slots - used by most implementations. */
    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /** True when the array is sorted ascending. O(n). Handy in tests. */
    static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
