package com.github.codemaster.fundamentals.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * AVL tree - a binary search tree that rebalances itself, so height stays
 * O(log n) and every operation is O(log n) in the <b>worst</b> case.
 *
 * <p><b>Invariant:</b> for every node, |height(left) - height(right)| &lt;= 1.
 * After an insert or delete the invariant is restored on the way back up the
 * recursion with <b>rotations</b> - local pointer rewirings that change the
 * shape but preserve the in-order sequence.</p>
 *
 * <p>Four cases, decided by where the offending subtree hangs:</p>
 * <pre>
 *   left-left    single right rotation
 *   right-right  single left rotation
 *   left-right   rotate the left child left, then rotate right
 *   right-left   rotate the right child right, then rotate left
 * </pre>
 *
 * <p>Compared with a red-black tree: AVL is more strictly balanced, so lookups
 * are slightly faster and writes do slightly more rotation work. {@code TreeMap}
 * chose red-black; databases often choose AVL-like structures for read-heavy
 * indexes.</p>
 *
 * @param <T> element type, must be comparable
 */
public class AvlTree<T extends Comparable<? super T>> {

    private static final class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        int height; // in edges: a leaf is 0

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Height in edges; empty tree is -1. Guaranteed O(log n). */
    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        return node == null ? -1 : node.height;
    }

    private int balanceFactor(Node<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    /** Inserts, then rebalances on the way back up. O(log n). Duplicates are ignored. */
    public boolean insert(T value) {
        int before = size;
        root = insert(root, value);
        return size > before;
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            size++;
            return new Node<>(value);
        }
        int comparison = value.compareTo(node.value);
        if (comparison < 0) {
            node.left = insert(node.left, value);
        } else if (comparison > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // duplicate
        }
        return rebalance(node);
    }

    /** Deletes, then rebalances on the way back up. O(log n). */
    public boolean delete(T value) {
        int before = size;
        root = delete(root, value);
        return size < before;
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int comparison = value.compareTo(node.value);
        if (comparison < 0) {
            node.left = delete(node.left, value);
        } else if (comparison > 0) {
            node.right = delete(node.right, value);
        } else {
            size--;
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node<T> successor = min(node.right); // smallest value on the right
            node.value = successor.value;
            size++; // the recursive delete below decrements for the successor
            node.right = delete(node.right, successor.value);
        }
        return rebalance(node);
    }

    private Node<T> min(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /** Restores the invariant at one node. O(1) - at most two rotations. */
    private Node<T> rebalance(Node<T> node) {
        updateHeight(node);
        int balance = balanceFactor(node);
        if (balance > 1) { // left heavy
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left); // left-right case
            }
            return rotateRight(node);
        }
        if (balance < -1) { // right heavy
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right); // right-left case
            }
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * <pre>
     *       y            x
     *      / \          / \
     *     x   C   -&gt;   A   y
     *    / \              / \
     *   A   B            B   C
     * </pre>
     * In-order stays A x B y C. O(1).
     */
    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y); // the lower node first
        updateHeight(x);
        return x;
    }

    /** Mirror image of {@link #rotateRight}. O(1). */
    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /** O(log n). */
    public boolean contains(T value) {
        Node<T> current = root;
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return true;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return false;
    }

    /** Sorted contents. O(n). */
    public List<T> inOrder() {
        List<T> result = new ArrayList<>(size);
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    /** True when every node satisfies the AVL invariant. Used by the tests. O(n). */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node<T> node) {
        if (node == null) {
            return true;
        }
        return Math.abs(balanceFactor(node)) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right);
    }
}
