package com.github.codemaster.fundamentals.tree;

import java.util.NoSuchElementException;

/**
 * Unbalanced binary search tree - the plain version, kept honest about its
 * worst case.
 *
 * <p>Every operation costs O(h). With random input h is about 1.39 log2(n);
 * with sorted input h is n and the tree is a linked list wearing a costume.
 * {@link #height()} makes that visible, and {@link AvlTree} fixes it.</p>
 *
 * <p>Duplicates are rejected (insert returns false) to keep the invariant
 * unambiguous.</p>
 *
 * @param <T> element type, must be comparable
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    private BinaryTreeNode<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Root node, or null. Exposed so {@link TreeTraversals} can walk the tree. */
    public BinaryTreeNode<T> root() {
        return root;
    }

    /**
     * Inserts iteratively. O(h).
     *
     * @return false when the value is already present
     */
    public boolean insert(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(value);
            size++;
            return true;
        }
        BinaryTreeNode<T> current = root;
        while (true) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return false;
            }
            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new BinaryTreeNode<>(value);
                    size++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new BinaryTreeNode<>(value);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    /** O(h) - each comparison throws away one subtree. */
    public boolean contains(T value) {
        BinaryTreeNode<T> current = root;
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return true;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return false;
    }

    /**
     * Deletes a value. O(h).
     *
     * <p>Three cases, and the third is the only interesting one:</p>
     * <ol>
     *   <li>leaf - detach it</li>
     *   <li>one child - splice the child in</li>
     *   <li>two children - copy the <b>in-order successor</b> (the smallest
     *       value in the right subtree) into this node, then delete that
     *       successor, which by construction has at most one child</li>
     * </ol>
     *
     * @return true when something was removed
     */
    public boolean delete(T value) {
        int before = size;
        root = delete(root, value);
        return size < before;
    }

    private BinaryTreeNode<T> delete(BinaryTreeNode<T> node, T value) {
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
            BinaryTreeNode<T> successor = min(node.right);
            node.value = successor.value;
            size++; // the recursive call below decrements again for the successor
            node.right = delete(node.right, successor.value);
        }
        return node;
    }

    /** Smallest value: walk left until you cannot. O(h). */
    public T min() {
        if (root == null) {
            throw new NoSuchElementException("tree is empty");
        }
        return min(root).value;
    }

    private BinaryTreeNode<T> min(BinaryTreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /** Largest value: walk right until you cannot. O(h). */
    public T max() {
        if (root == null) {
            throw new NoSuchElementException("tree is empty");
        }
        BinaryTreeNode<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    /**
     * Height in edges; an empty tree is -1 and a single node is 0. O(n).
     *
     * <p>Compare this against {@code log2(size)}: the gap is how unbalanced the
     * tree has become.</p>
     */
    public int height() {
        return height(root);
    }

    private int height(BinaryTreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Verifies the BST invariant over the whole tree. O(n).
     *
     * <p>Checking only {@code left < node < right} locally is the classic wrong
     * answer - it accepts trees where a deep left-subtree node is larger than
     * the root. The bounds must be carried down.</p>
     */
    public boolean isValid() {
        return isValid(root, null, null);
    }

    private boolean isValid(BinaryTreeNode<T> node, T lowerBound, T upperBound) {
        if (node == null) {
            return true;
        }
        if (lowerBound != null && node.value.compareTo(lowerBound) <= 0) {
            return false;
        }
        if (upperBound != null && node.value.compareTo(upperBound) >= 0) {
            return false;
        }
        return isValid(node.left, lowerBound, node.value)
                && isValid(node.right, node.value, upperBound);
    }
}
