/**
 * Recursion and backtracking: solving a problem by solving a smaller version of
 * itself.
 *
 * <p>Every correct recursive method has exactly two parts:</p>
 * <ol>
 *   <li>a <b>base case</b> that returns without recursing, and</li>
 *   <li>a <b>recursive step</b> that makes the input strictly smaller, so the
 *       base case is always reached.</li>
 * </ol>
 * <p>Miss either one and you get {@link StackOverflowError} - which is a real
 * cost: each call keeps a frame alive, so recursion depth d costs O(d) memory.
 * Java does not optimize tail calls, so a deep recursion must be rewritten as a
 * loop, not just written tail-recursively.</p>
 *
 * <p><b>Recursion tree = complexity.</b> Count the calls: {@code fib(n)}
 * branches twice per level for depth n, so it is O(2^n) and recomputes the same
 * subproblems constantly. Noticing that overlap is exactly the step from
 * recursion to dynamic programming - see
 * {@link com.github.codemaster.fundamentals.dp}.</p>
 *
 * <p><b>Backtracking</b> is recursion that explores choices and undoes them:</p>
 * <pre>
 *   choose  -&gt;  explore  -&gt;  un-choose
 * </pre>
 * <p>It generates every candidate (subsets, permutations, board placements) and
 * abandons a branch as soon as it cannot lead to a solution - the
 * <b>pruning</b> that makes n-queens finish at all. See
 * {@link com.github.codemaster.fundamentals.recursion.Backtracking}.</p>
 */
package com.github.codemaster.fundamentals.recursion;
