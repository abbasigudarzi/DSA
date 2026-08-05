package com.github.codemaster.fundamentals.sorting;

/**
 * Insertion sort: take the next element and slide it back into the sorted
 * prefix - how people sort a hand of cards.
 *
 * <p>Time O(n^2) worst, O(n) on nearly sorted input, since each element moves
 * only a little. Space O(1). Stable.</p>
 *
 * <p>This one is genuinely used: real library sorts (including Java's TimSort)
 * switch to insertion sort for small subarrays, because its low constant factor
 * beats the overhead of an O(n log n) algorithm below roughly 32 elements. It
 * is also <b>online</b> - it can sort a stream as elements arrive.</p>
 */
public class InsertionSort implements Sorter {

    @Override
    public void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;
            // shift everything larger one slot right to open a gap
            while (j >= 0 && array[j] > current) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }

    /** Sorts the closed range {@code [from, to]} - used by hybrid sorts. */
    public static void sortRange(int[] array, int from, int to) {
        for (int i = from + 1; i <= to; i++) {
            int current = array[i];
            int j = i - 1;
            while (j >= from && array[j] > current) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }
}
