package com.github.codemaster.fundamentals.queue;

import java.util.NoSuchElementException;

/**
 * FIFO queue on a singly linked list: enqueue at the tail, dequeue at the head.
 *
 * <p>Both ends are O(1) because the list keeps a tail pointer and only ever
 * <i>appends</i> there - it never has to find the node before the tail, which
 * is the one thing a singly linked list is bad at.</p>
 *
 * <p>No capacity, no resize copy; the cost is one node object per element.</p>
 *
 * @param <T> element type
 */
public class LinkedQueue<T> {

    private static final class Node<T> {
        final T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /** O(1). */
    public void enqueue(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /** O(1). */
    public T dequeue() {
        if (head == null) {
            throw new NoSuchElementException("queue is empty");
        }
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    /** O(1). */
    public T peek() {
        if (head == null) {
            throw new NoSuchElementException("queue is empty");
        }
        return head.value;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
