package com.github.codemaster.fundamentals.sorting;

/**
 * Bubble sort: repeatedly swap adjacent out-of-order pairs, so the largest
 * remaining element "bubbles" to the end each pass.
 *
 * <p>Time O(n^2) average and worst, O(n) best on already sorted input thanks to
 * the early exit. Space O(1). Stable.</p>
 *
 * <p>Nobody should use this. It is here because it is the clearest possible
 * example of a quadratic algorithm, and because the early-exit flag shows how a
 * best case can differ from a worst case.</p>
 */
public class BubbleSort implements Sorter {

    @Override
    public void sort(int[] array) {
        for (int pass = 0; pass < array.length - 1; pass++) {
            boolean swapped = false;
            // after `pass` passes the last `pass` elements are final
            for (int i = 0; i < array.length - 1 - pass; i++) {
                if (array[i] > array[i + 1]) {
                    Sorter.swap(array, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                return; // a clean pass means the array is sorted
            }
        }
    }
}
