package com.github.codemaster.fundamentals.sorting;

/**
 * Selection sort: find the smallest element in the unsorted region and swap it
 * into place.
 *
 * <p>Time O(n^2) in every case - it always scans the whole remaining region,
 * so sorted input is no faster. Space O(1). Not stable (the long-distance swap
 * can jump one equal element over another).</p>
 *
 * <p>Its one real property: exactly n-1 swaps, the minimum possible. That
 * matters only when writing is far more expensive than reading, such as sorting
 * data in flash memory.</p>
 */
public class SelectionSort implements Sorter {

    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[smallest]) {
                    smallest = j;
                }
            }
            if (smallest != i) {
                Sorter.swap(array, i, smallest);
            }
        }
    }
}
