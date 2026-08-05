/**
 * Heaps and priority queues: fast access to the smallest (or largest) element.
 *
 * <p>A binary heap is a <b>complete</b> binary tree - every level full except
 * possibly the last, which fills left to right. Completeness is what allows the
 * tree to be stored in a flat array with no pointers at all:</p>
 *
 * <pre>
 *   parent(i) = (i - 1) / 2
 *   left(i)   = 2 * i + 1
 *   right(i)  = 2 * i + 2
 * </pre>
 *
 * <p><b>Heap property:</b> every parent is &lt;= both children (min-heap). Note
 * what this does <i>not</i> say: siblings are unordered and the array is not
 * sorted. A heap answers "what is the minimum" in O(1) and nothing else
 * quickly.</p>
 *
 * <pre>
 *   peek         O(1)
 *   offer/poll   O(log n)   sift up / sift down one path
 *   heapify      O(n)       building from an existing array, not O(n log n)
 *   contains     O(n)       there is no search structure here
 * </pre>
 *
 * <p>Where it matters: Dijkstra and A* (pick the closest frontier node),
 * heap sort, top-k problems (keep a size-k heap and every extra element costs
 * O(log k)), scheduling, and median maintenance with two heaps facing each
 * other.</p>
 */
package com.github.codemaster.fundamentals.heap;
