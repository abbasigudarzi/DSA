/**
 * Sorting: seven algorithms that produce the same output and teach different
 * lessons.
 *
 * <pre>
 *   algorithm   best        average     worst       space     stable
 *   bubble      O(n)        O(n^2)      O(n^2)      O(1)      yes
 *   selection   O(n^2)      O(n^2)      O(n^2)      O(1)      no
 *   insertion   O(n)        O(n^2)      O(n^2)      O(1)      yes
 *   merge       O(n log n)  O(n log n)  O(n log n)  O(n)      yes
 *   quick       O(n log n)  O(n log n)  O(n^2)      O(log n)  no
 *   heap        O(n log n)  O(n log n)  O(n log n)  O(1)      no
 *   counting    O(n + k)    O(n + k)    O(n + k)    O(n + k)  yes
 * </pre>
 *
 * <p><b>O(n log n) is a hard floor</b> for any sort that works by comparing
 * elements: n! possible orderings need log2(n!) ~ n log n comparisons to tell
 * apart. Counting sort beats it only by refusing to compare - it uses the
 * values as array indices, which is why it needs a small known value range k
 * and cannot sort arbitrary objects.</p>
 *
 * <p><b>Stable</b> means equal elements keep their relative order. It matters
 * when sorting by one field after another: sort by name, then stably by
 * department, and within each department the names remain sorted. Java sorts
 * objects with a stable merge sort (TimSort) and primitives with an unstable
 * dual-pivot quicksort, precisely because primitives have no identity to
 * preserve.</p>
 *
 * <p><b>What to actually use:</b> {@code Arrays.sort}. These implementations
 * exist so the trade-offs are understood, not to be used in production.</p>
 */
package com.github.codemaster.fundamentals.sorting;
