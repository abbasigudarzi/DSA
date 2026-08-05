/**
 * Trees: hierarchical structures - one root, no cycles, exactly one path
 * between any two nodes.
 *
 * <p>Vocabulary worth fixing early: <b>height</b> of a node is the longest path
 * down to a leaf, <b>depth</b> is the distance up to the root, a <b>leaf</b>
 * has no children, and a <b>balanced</b> tree keeps its height at O(log n).</p>
 *
 * <p><b>Binary search tree invariant:</b> everything in the left subtree is
 * smaller than the node, everything in the right subtree is larger. That single
 * rule is what makes search O(height) - each comparison discards half the
 * remaining tree.</p>
 *
 * <pre>
 *   search / insert / delete   O(h)      h = height
 *   h of a balanced tree       O(log n)
 *   h of a degenerate tree     O(n)      sorted input inserted in order
 *   traversal                  O(n)
 * </pre>
 *
 * <p>That worst case is not theoretical: inserting 1, 2, 3, 4, 5 into a plain
 * BST produces a linked list. {@link com.github.codemaster.fundamentals.tree.AvlTree}
 * fixes it by rotating after every insert and delete, keeping the two subtree
 * heights within 1 of each other. Red-black trees (what {@code TreeMap} uses)
 * make the same guarantee with fewer rotations.</p>
 *
 * <p><b>Traversals</b> ({@link com.github.codemaster.fundamentals.tree.TreeTraversals}):</p>
 * <ul>
 *   <li>in-order (left, node, right) - visits a BST in sorted order</li>
 *   <li>pre-order (node, left, right) - copying or serialising a tree</li>
 *   <li>post-order (left, right, node) - deleting, or any bottom-up computation</li>
 *   <li>level-order - breadth first, uses a queue, not recursion</li>
 * </ul>
 *
 * <p>A <b>trie</b> ({@link com.github.codemaster.fundamentals.tree.Trie}) is a
 * different tree: the path spells the key, so prefix queries cost O(length of
 * prefix) regardless of how many words are stored.</p>
 */
package com.github.codemaster.fundamentals.tree;
