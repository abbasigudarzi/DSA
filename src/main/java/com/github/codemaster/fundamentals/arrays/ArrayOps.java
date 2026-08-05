package com.github.codemaster.fundamentals.arrays;

/**
 * The classic single-array techniques. Each one is worth recognising on sight -
 * a large share of interview and contest problems reduce to one of them.
 */
public final class ArrayOps {

    private ArrayOps() {
    }

    /**
     * Reverses in place with two pointers walking toward each other.
     * Time O(n), space O(1).
     */
    public static void reverse(int[] array) {
        reverse(array, 0, array.length - 1);
    }

    /** Reverses the closed range {@code [from, to]} in place. Time O(to-from). */
    public static void reverse(int[] array, int from, int to) {
        while (from < to) {
            int temp = array[from];
            array[from++] = array[to];
            array[to--] = temp;
        }
    }

    /**
     * Rotates left by {@code k} using the reversal trick: reverse the first k,
     * reverse the rest, reverse the whole thing.
     *
     * <p>Time O(n), space O(1) - the naive "shift one position k times" is
     * O(n*k), and the "copy into a new array" version is O(n) space.</p>
     */
    public static void rotateLeft(int[] array, int k) {
        if (array.length == 0) {
            return;
        }
        int shift = Math.floorMod(k, array.length);
        if (shift == 0) {
            return;
        }
        reverse(array, 0, shift - 1);
        reverse(array, shift, array.length - 1);
        reverse(array, 0, array.length - 1);
    }

    /**
     * Kadane's algorithm: largest sum of a non-empty contiguous subarray.
     *
     * <p>The insight is local: at every index either the best subarray ending
     * here extends the previous one, or it restarts at this element. Whichever
     * is larger wins, and the running maximum records the best seen.</p>
     *
     * <p>Time O(n), space O(1). Brute force over all pairs is O(n^2).</p>
     *
     * @throws IllegalArgumentException when the array is empty
     */
    public static int maxSubarraySum(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("array must not be empty");
        }
        int bestEndingHere = array[0];
        int best = array[0];
        for (int i = 1; i < array.length; i++) {
            bestEndingHere = Math.max(array[i], bestEndingHere + array[i]);
            best = Math.max(best, bestEndingHere);
        }
        return best;
    }

    /**
     * Prefix sums: {@code result[i]} is the sum of the first {@code i} elements,
     * so {@code result[0] == 0} and the array has length {@code n + 1}.
     *
     * <p>Build once in O(n), then answer any range-sum query in O(1) with
     * {@link #rangeSum}. This is the standard trade of preprocessing time for
     * query time.</p>
     */
    public static int[] prefixSums(int[] array) {
        int[] prefix = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            prefix[i + 1] = prefix[i] + array[i];
        }
        return prefix;
    }

    /**
     * Sum of the closed range {@code [from, to]} of the original array, given
     * its {@link #prefixSums}. Time O(1).
     */
    public static int rangeSum(int[] prefix, int from, int to) {
        if (from < 0 || to >= prefix.length - 1 || from > to) {
            throw new IndexOutOfBoundsException("bad range [" + from + ", " + to + "]");
        }
        return prefix[to + 1] - prefix[from];
    }

    /**
     * Dutch national flag partition: sorts an array of only 0, 1 and 2 in a
     * single pass.
     *
     * <p>Three pointers hold three regions - everything before {@code low} is 0,
     * everything after {@code high} is 2, and {@code mid} scans the unknown
     * middle. Time O(n), space O(1).</p>
     */
    public static void sortColors(int[] array) {
        int low = 0;
        int mid = 0;
        int high = array.length - 1;
        while (mid <= high) {
            switch (array[mid]) {
                case 0 -> swap(array, low++, mid++);
                case 1 -> mid++;
                case 2 -> swap(array, mid, high--); // do not advance mid: the swapped-in value is unknown
                default -> throw new IllegalArgumentException("expected 0, 1 or 2 but found " + array[mid]);
            }
        }
    }

    /**
     * Moves every zero to the end while keeping the order of the non-zero
     * elements. Time O(n), space O(1).
     */
    public static void moveZerosToEnd(int[] array) {
        int write = 0;
        for (int read = 0; read < array.length; read++) {
            if (array[read] != 0) {
                array[write++] = array[read];
            }
        }
        while (write < array.length) {
            array[write++] = 0;
        }
    }

    /**
     * Second largest distinct value. Time O(n), space O(1) - one pass beats
     * sorting the array in O(n log n) just to read index n-2.
     *
     * @throws IllegalArgumentException when fewer than two distinct values exist
     */
    public static int secondLargest(int[] array) {
        long largest = Long.MIN_VALUE;
        long second = Long.MIN_VALUE;
        for (int value : array) {
            if (value > largest) {
                second = largest;
                largest = value;
            } else if (value < largest && value > second) {
                second = value;
            }
        }
        if (second == Long.MIN_VALUE) {
            throw new IllegalArgumentException("array needs at least two distinct values");
        }
        return (int) second;
    }

    /** Swaps two slots. O(1). */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
