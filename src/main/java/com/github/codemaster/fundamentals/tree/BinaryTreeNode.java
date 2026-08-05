package com.github.codemaster.fundamentals.tree;

/**
 * A binary tree node: a value and up to two children.
 *
 * <p>Shared by {@link BinarySearchTree} and {@link TreeTraversals} so the
 * traversal algorithms can be studied independently of any particular tree.</p>
 *
 * @param <T> value type
 */
public class BinaryTreeNode<T> {

    public T value;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    public BinaryTreeNode(T value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
