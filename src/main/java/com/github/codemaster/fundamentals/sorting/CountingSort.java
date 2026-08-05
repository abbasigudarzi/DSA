package com.github.codemaster.fundamentals.sorting;

/**
 * Counting sort: no comparisons at all. Count how often each value occurs, then
 * write the values back out in order.
 *
 * <p>Time O(n + k), space O(n + k), where k is the size of the value range.
 * This beats the O(n log n) comparison-sort floor because it never compares two
 * elements - it uses the values themselves as array indices.</p>
 *
 * <p>The catch is k. Sorting a million values in the range 0-100 is excellent;
 * sorting ten values in the range 0-2 billion allocates a two-billion-entry
 * counter array. Use it when the range is small and known.</p>
 *
 * <p>Stable, in the prefix-sum form implemented here - which is what makes it
 * usable as the inner pass of radix sort.</p>
 */
public class CountingSort implements Sorter {

    /**
     * Sorts any int array, including negatives, by shifting the range to start
     * at zero.
     *
     * @throws IllegalArgumentException when the value range is wider than
     *         {@link Integer#MAX_VALUE}, which would overflow the counter array
     */
    @Override
    public void sort(int[] array) {
        if (array.length < 2) {
            return;
        }
        int min = array[0];
        int max = array[0];
        for (int value : array) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        long range = (long) max - min + 1;
        if (range > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("value range too wide for counting sort: " + range);
        }

        int[] counts = new int[(int) range];
        for (int value : array) {
            counts[value - min]++;
        }
        // prefix sums: counts[i] becomes "how many values are <= i",
        // which is exactly the end position of value i in the output
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        int[] output = new int[array.length];
        // walk backwards so equal elements keep their original order (stability)
        for (int i = array.length - 1; i >= 0; i--) {
            output[--counts[array[i] - min]] = array[i];
        }
        System.arraycopy(output, 0, array, 0, array.length);
    }
}
