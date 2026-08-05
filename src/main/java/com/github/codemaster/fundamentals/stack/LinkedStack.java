package com.github.codemaster.fundamentals.stack;

import java.util.EmptyStackException;

/**
 * Stack backed by a singly linked list, pushing and popping at the head.
 *
 * <p>Compared with {@link ArrayStack}: no resize copy ever happens and the
 * capacity is unbounded, but each element costs an extra object plus a
 * reference, and the nodes are scattered in memory so the CPU cache helps far
 * less. In practice the array version is faster; this one is here to show that
 * the abstract data type is independent of the storage.</p>
 *
 * @param <T> element type
 */
public class LinkedStack<T> {

    private static final class Node<T> {
        final T value;
        final Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> top;
    private int size;

    /** O(1), always - no resizing. */
    public void push(T value) {
        top = new Node<>(value, top);
        size++;
    }

    /** O(1). */
    public T pop() {
        if (top == null) {
            throw new EmptyStackException();
        }
        T value = top.value;
        top = top.next;
        size--;
        return value;
    }

    /** O(1). */
    public T peek() {
        if (top == null) {
            throw new EmptyStackException();
        }
        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
