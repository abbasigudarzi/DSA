/**
 * Stacks: last in, first out (LIFO).
 *
 * <p>A stack is not a new storage idea - it is an array or a linked list with
 * the API deliberately narrowed to three operations: {@code push},
 * {@code pop}, {@code peek}. That restriction is the value. When a problem
 * fits a stack, the code becomes short and obviously correct.</p>
 *
 * <pre>
 *   push / pop / peek   O(1)   (array version: O(n) on the resize step)
 *   search              O(n)   and you should not be searching a stack
 * </pre>
 *
 * <p>Recognise a stack problem by the phrase "most recent unmatched":</p>
 * <ul>
 *   <li>bracket / tag matching ({@link com.github.codemaster.fundamentals.stack.BalancedBrackets})</li>
 *   <li>undo history, browser back button</li>
 *   <li>expression evaluation, infix to postfix</li>
 *   <li>monotonic stack problems - next greater element, largest rectangle</li>
 *   <li>iterative depth-first traversal, which is recursion with the call stack
 *       made explicit</li>
 * </ul>
 *
 * <p>The JVM itself runs on one: every method call pushes a frame, every return
 * pops it, and infinite recursion overflows it.</p>
 */
package com.github.codemaster.fundamentals.stack;
