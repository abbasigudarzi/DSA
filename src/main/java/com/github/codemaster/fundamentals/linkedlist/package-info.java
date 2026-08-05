/**
 * Linked lists: nodes scattered in memory, wired together by references.
 *
 * <p>The trade against an array is exact: a linked list gives up O(1) indexing
 * to get O(1) insert and delete <b>at a known node</b>. No shifting, no
 * resizing, but also no random access and one object header plus one reference
 * of overhead per element.</p>
 *
 * <pre>
 *   access by index          O(n)   walk from the head
 *   search                   O(n)
 *   insert/delete at head    O(1)
 *   insert/delete at tail    O(1) with a tail pointer, O(n) without
 *   insert/delete at a node  O(1) singly, given the previous node
 * </pre>
 *
 * <p>Three techniques cover most linked-list problems:</p>
 * <ol>
 *   <li><b>Dummy head</b> - a fake node before the real head removes the
 *       "am I deleting the first element?" special case.</li>
 *   <li><b>Two pointers, different speeds</b> - slow moves one step, fast moves
 *       two. Finds the middle in one pass and detects a cycle
 *       (Floyd's algorithm).</li>
 *   <li><b>Three-pointer reversal</b> - previous, current, next. Reversal is
 *       the single most asked linked-list question.</li>
 * </ol>
 */
package com.github.codemaster.fundamentals.linkedlist;
