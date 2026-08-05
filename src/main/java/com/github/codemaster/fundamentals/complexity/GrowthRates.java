package com.github.codemaster.fundamentals.complexity;

/**
 * One tiny method per complexity class, so the shape of the code can be matched
 * to the shape of the curve. Each method returns an operation counter rather
 * than a useful result - the counter <i>is</i> the lesson.
 */
public final class GrowthRates {

    private GrowthRates() {
    }

    /**
     * O(1) - the work does not depend on n at all.
     *
     * @return number of basic operations performed
     */
    public static long constant(int[] data) {
        if (data.length == 0) {
            return 1;
        }
        int unused = data[0];
        return 1;
    }

    /**
     * O(log n) - n is halved every step, so the loop runs log2(n) times.
     *
     * @return number of loop iterations
     */
    public static long logarithmic(int n) {
        long steps = 0;
        for (int i = n; i > 0; i /= 2) {
            steps++;
        }
        return steps;
    }

    /**
     * O(n) - one pass.
     *
     * @return number of loop iterations
     */
    public static long linear(int n) {
        long steps = 0;
        for (int i = 0; i < n; i++) {
            steps++;
        }
        return steps;
    }

    /**
     * O(n log n) - an outer linear pass, each doing logarithmic work. This is
     * the lower bound for any comparison-based sort.
     *
     * @return number of inner iterations
     */
    public static long linearithmic(int n) {
        long steps = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n; j > 0; j /= 2) {
                steps++;
            }
        }
        return steps;
    }

    /**
     * O(n^2) - nested loop over the same input. n = 1_000 is a million steps;
     * n = 1_000_000 is a trillion, which is why quadratic dies at scale.
     *
     * @return number of inner iterations
     */
    public static long quadratic(int n) {
        long steps = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                steps++;
            }
        }
        return steps;
    }

    /**
     * O(2^n) - every element is either in or out, so the call tree doubles at
     * each level. Keep n under ~25 unless you enjoy waiting.
     *
     * @return number of recursive calls
     */
    public static long exponential(int n) {
        if (n <= 0) {
            return 1;
        }
        return 1 + exponential(n - 1) + exponential(n - 1);
    }
}
