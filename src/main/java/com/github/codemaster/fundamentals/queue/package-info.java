/**
 * Queues: first in, first out (FIFO), and deques, which are open at both ends.
 *
 * <p>The naive array queue - add at the end, remove from index 0 - is O(n) per
 * removal because every remaining element shifts left. The fix is the
 * <b>circular buffer</b>: keep a {@code head} index and a {@code tail} index
 * and let them wrap around with the modulo operator. Nothing moves, so both
 * operations are O(1). That is
 * {@link com.github.codemaster.fundamentals.queue.CircularArrayQueue}.</p>
 *
 * <pre>
 *   enqueue / dequeue / peek   O(1)
 *   search                     O(n)
 * </pre>
 *
 * <p>Where queues show up:</p>
 * <ul>
 *   <li>breadth-first search - the frontier of a BFS <i>is</i> a queue, which is
 *       why BFS finds shortest paths in an unweighted graph</li>
 *   <li>task scheduling, request buffering, producer/consumer pipelines</li>
 *   <li>level-order tree traversal</li>
 * </ul>
 *
 * <p>A <b>deque</b> (double-ended queue) can push and pop at both ends, so it
 * can act as a stack or a queue. It also powers the sliding-window-maximum
 * technique, where a monotonic deque answers each window in O(1) amortized.</p>
 */
package com.github.codemaster.fundamentals.queue;
