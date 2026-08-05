package com.github.codemaster.fundamentals.complexity;

import java.util.Random;
import java.util.function.IntConsumer;

/**
 * A deliberately small timing harness. Measured time confirms the shape of a
 * curve; it never replaces the Big O analysis.
 *
 * <p>Warning about the JVM: the first runs of any method are interpreted, then
 * the JIT compiles hot code, so a single measurement is noise. {@link #time}
 * runs a warm-up phase for that reason. For real benchmarking use JMH - this
 * class is a teaching aid.</p>
 */
public final class Benchmark {

    private static final int WARMUP_RUNS = 3;

    private Benchmark() {
    }

    /**
     * Runs {@code work} against the given input size and returns elapsed
     * nanoseconds of the measured run.
     */
    public static long time(IntConsumer work, int inputSize) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            work.accept(inputSize);
        }
        long start = System.nanoTime();
        work.accept(inputSize);
        return System.nanoTime() - start;
    }

    /**
     * Doubles the input size {@code steps} times and prints the ratio between
     * consecutive runs. The ratio is the answer to "what happens when n
     * doubles?":
     *
     * <pre>
     *   ratio ~ 1  -&gt; O(1) or O(log n)
     *   ratio ~ 2  -&gt; O(n)
     *   ratio ~ 2+ -&gt; O(n log n)
     *   ratio ~ 4  -&gt; O(n^2)
     * </pre>
     */
    public static void doublingTest(String label, IntConsumer work, int startSize, int steps) {
        long previous = 0;
        int n = startSize;
        System.out.println("=== " + label + " ===");
        for (int i = 0; i < steps; i++) {
            long elapsed = time(work, n);
            String ratio = previous == 0 ? "-" : String.format("%.2f", (double) elapsed / previous);
            System.out.printf("n=%-9d %8.3f ms   ratio=%s%n", n, elapsed / 1_000_000.0, ratio);
            previous = elapsed;
            n *= 2;
        }
    }

    /** Random int array, handy as benchmark input. */
    public static int[] randomArray(int size, long seed) {
        Random random = new Random(seed);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
