package com.github.codemaster.fundamentals.searching;

/**
 * Binary search and its useful variants. Every method requires a
 * <b>sorted ascending</b> array; on unsorted input they return wrong answers
 * rather than failing loudly.
 */
public final class BinarySearch {

    private BinarySearch() {
    }

    /**
     * Classic binary search. Time O(log n), space O(1).
     *
     * @return an index of {@code target}, or -1 when absent (which index, when
     *         duplicates exist, is unspecified - use {@link #lowerBound})
     */
    public static int search(int[] sorted, int target) {
        int low = 0;
        int high = sorted.length - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2; // overflow-safe
            if (sorted[middle] == target) {
                return middle;
            }
            if (sorted[middle] < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    /**
     * Same result, recursively. Time O(log n), space O(log n) for the stack -
     * the iterative version is strictly better here, which is worth knowing.
     */
    public static int searchRecursive(int[] sorted, int target) {
        return searchRecursive(sorted, target, 0, sorted.length - 1);
    }

    private static int searchRecursive(int[] sorted, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int middle = low + (high - low) / 2;
        if (sorted[middle] == target) {
            return middle;
        }
        return sorted[middle] < target
                ? searchRecursive(sorted, target, middle + 1, high)
                : searchRecursive(sorted, target, low, middle - 1);
    }

    /**
     * First index whose value is &gt;= {@code target}, or {@code length} when
     * none is. This is the insertion point that keeps the array sorted.
     * O(log n).
     */
    public static int lowerBound(int[] sorted, int target) {
        int low = 0;
        int high = sorted.length; // exclusive: the answer may be past the end
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (sorted[middle] < target) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    /**
     * First index whose value is &gt; {@code target}. O(log n).
     *
     * <p>{@code upperBound - lowerBound} is how many times the target occurs -
     * a count in O(log n) instead of O(n).</p>
     */
    public static int upperBound(int[] sorted, int target) {
        int low = 0;
        int high = sorted.length;
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (sorted[middle] <= target) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    /** How many times {@code target} appears. O(log n). */
    public static int countOccurrences(int[] sorted, int target) {
        return upperBound(sorted, target) - lowerBound(sorted, target);
    }

    /**
     * Binary search in a sorted array that has been rotated, e.g.
     * {@code [4, 5, 6, 7, 0, 1, 2]}. O(log n).
     *
     * <p>The insight: after cutting at the midpoint, at least one half is still
     * sorted normally. Check whether the target lies inside that sorted half -
     * if yes, search it; if no, search the other one.</p>
     *
     * @return an index of {@code target}, or -1
     */
    public static int searchRotated(int[] rotated, int target) {
        int low = 0;
        int high = rotated.length - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (rotated[middle] == target) {
                return middle;
            }
            if (rotated[low] <= rotated[middle]) { // left half sorted
                if (target >= rotated[low] && target < rotated[middle]) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            } else { // right half sorted
                if (target > rotated[middle] && target <= rotated[high]) {
                    low = middle + 1;
                } else {
                    high = middle - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Linear search, kept for contrast. O(n), and the only option when the data
     * is not sorted.
     */
    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
