package com.github.codemaster.fundamentals.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * The four traversals, each in the form worth memorising.
 *
 * <p>All are O(n) time. Space is O(h) for the depth-first ones (the call stack
 * or an explicit stack) and O(width) for level order - which for a full tree
 * means the bottom level, about n/2 nodes.</p>
 *
 * <p>Depth-first vs breadth-first is the same choice as in graphs, because a
 * tree <i>is</i> a graph: DFS uses a stack (or recursion), BFS uses a queue.</p>
 */
public final class TreeTraversals {

    private TreeTraversals() {
    }

    /** Left, node, right. On a BST this produces sorted output. */
    public static <T> List<T> inOrder(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private static <T> void inOrder(BinaryTreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    /** Node, left, right. Use to copy or serialise a tree. */
    public static <T> List<T> preOrder(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private static <T> void preOrder(BinaryTreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    /** Left, right, node. Children are handled before the parent - use to free or fold bottom-up. */
    public static <T> List<T> postOrder(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    private static <T> void postOrder(BinaryTreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.value);
    }

    /**
     * In-order without recursion, using an explicit stack.
     *
     * <p>Worth writing once by hand: it shows that recursion is just a stack the
     * language manages for you, and it does not blow up on a degenerate tree of
     * a million nodes.</p>
     */
    public static <T> List<T> inOrderIterative(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        Deque<BinaryTreeNode<T>> stack = new ArrayDeque<>();
        BinaryTreeNode<T> current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) { // dive left, remembering the way back
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.value);
            current = current.right;
        }
        return result;
    }

    /**
     * Pre-order without recursion. The right child is pushed first so the left
     * child is popped first.
     */
    public static <T> List<T> preOrderIterative(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<BinaryTreeNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BinaryTreeNode<T> node = stack.pop();
            result.add(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /** Breadth first, one flat list. Uses a queue - swap the queue for a stack and it becomes DFS. */
    public static <T> List<T> levelOrder(BinaryTreeNode<T> root) {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<BinaryTreeNode<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BinaryTreeNode<T> node = queue.poll();
            result.add(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return result;
    }

    /**
     * Breadth first, grouped one list per level.
     *
     * <p>The trick is to read the queue size <i>before</i> draining it: that
     * count is exactly the current level, because everything added during the
     * loop belongs to the next one.</p>
     */
    public static <T> List<List<T>> levelOrderByLevel(BinaryTreeNode<T> root) {
        List<List<T>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }
        Deque<BinaryTreeNode<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<T> level = new ArrayList<>(levelSize);
            for (int i = 0; i < levelSize; i++) {
                BinaryTreeNode<T> node = queue.poll();
                level.add(node.value);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }

    /** Node count. O(n). */
    public static <T> int size(BinaryTreeNode<T> root) {
        return root == null ? 0 : 1 + size(root.left) + size(root.right);
    }

    /** Height in edges: empty is -1, a leaf is 0. O(n). */
    public static <T> int height(BinaryTreeNode<T> root) {
        return root == null ? -1 : 1 + Math.max(height(root.left), height(root.right));
    }
}
