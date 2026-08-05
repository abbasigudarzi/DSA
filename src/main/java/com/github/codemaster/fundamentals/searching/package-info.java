/**
 * Searching: linear when the data is unordered, binary when it is sorted.
 *
 * <p>Binary search is the payoff for sorting. Each comparison discards half the
 * remaining range, so 1 million elements take about 20 steps and 1 billion take
 * about 30. Sorting first costs O(n log n), so it pays off when the data is
 * searched many times, not once.</p>
 *
 * <pre>
 *   linear search   O(n)        works on anything
 *   binary search   O(log n)    requires sorted input - unsorted gives wrong answers, not slow ones
 * </pre>
 *
 * <p>Two details that cause most of the bugs:</p>
 * <ul>
 *   <li>Compute the midpoint as {@code low + (high - low) / 2}. The obvious
 *       {@code (low + high) / 2} overflows to a negative index on huge arrays -
 *       a real bug that sat in the JDK's own binary search for nine years.</li>
 *   <li>Be deliberate about the loop invariant. {@code while (low <= high)} with
 *       an inclusive {@code high} is the classic form; mixing an exclusive
 *       bound with {@code <=} causes an infinite loop or an off-by-one.</li>
 * </ul>
 *
 * <p>{@link com.github.codemaster.fundamentals.searching.BinarySearch} also has
 * the two boundary variants ({@code lowerBound}, {@code upperBound}), which
 * answer "where would this go?" rather than "is this here?" - the versions
 * actually needed for ranges, counts and insertion points.</p>
 */
package com.github.codemaster.fundamentals.searching;
