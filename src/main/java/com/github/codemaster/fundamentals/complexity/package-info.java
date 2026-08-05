/**
 * Complexity analysis: how to describe cost without a stopwatch.
 *
 * <p><b>Big O</b> is an upper bound on growth as input size {@code n} grows.
 * It answers "what happens when n doubles?", not "how many milliseconds?".</p>
 *
 * <p>The ladder, cheapest first:</p>
 * <pre>
 *   O(1)        constant     array index, hash lookup (average)
 *   O(log n)    logarithmic  binary search, balanced tree height
 *   O(n)        linear       one pass over the input
 *   O(n log n)  linearithmic merge sort, heap sort - the comparison-sort floor
 *   O(n^2)      quadratic    nested loop over the same input
 *   O(2^n)      exponential  naive subset / naive recursive fibonacci
 *   O(n!)       factorial    brute-force permutations
 * </pre>
 *
 * <p>Three rules cover most hand analysis:</p>
 * <ol>
 *   <li>Drop constants: {@code O(2n)} is {@code O(n)}.</li>
 *   <li>Drop lower-order terms: {@code O(n^2 + n)} is {@code O(n^2)}.</li>
 *   <li>Sequential blocks add, nested blocks multiply.</li>
 * </ol>
 *
 * <p><b>Amortized</b> is not average. A {@code DynamicArray.add} is O(n) on the
 * resize step but O(1) amortized, because the expensive step pays for the
 * cheap steps that follow it.</p>
 *
 * <p><b>Space complexity</b> counts extra memory beyond the input, and the
 * recursion stack counts. Recursive depth {@code n} means {@code O(n)} space
 * even when no array is allocated.</p>
 *
 * @see com.github.codemaster.fundamentals.complexity.GrowthRates
 * @see com.github.codemaster.fundamentals.complexity.Benchmark
 */
package com.github.codemaster.fundamentals.complexity;
