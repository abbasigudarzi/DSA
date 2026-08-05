package com.github.codemaster.fundamentals.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Doubly linked list: every node also points back at its predecessor.
 *
 * <p>The extra reference costs memory but buys O(1) {@link #removeLast} and
 * O(1) removal of any node you already hold. This is the structure behind
 * {@code java.util.LinkedList} and behind LRU caches (hash map to node, plus a
 * doubly linked list to reorder in O(1)).</p>
 *
 * @param <T> element type
 */
public class DoublyLinkedList<T> implements Iterable<T> {

    private static final class Node<T> {
        T value;
        Node<T> previous;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** O(1). */
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.previous = node;
            head = node;
        }
        size++;
    }

    /** O(1). */
    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = tail = node;
        } else {
            node.previous = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /** O(1). */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        return unlink(head);
    }

    /** O(1) - this is what the backward reference is for. */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("list is empty");
        }
        return unlink(tail);
    }

    /** O(n) to find the node, O(1) to unlink it. */
    public boolean remove(T value) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (Objects.equals(current.value, value)) {
                unlink(current);
                return true;
            }
        }
        return false;
    }

    /** O(1) once the node is known - the whole point of the structure. */
    private T unlink(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        node.previous = null;
        node.next = null;
        size--;
        return node.value;
    }

    public T first() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        return head.value;
    }

    public T last() {
        if (tail == null) {
            throw new NoSuchElementException("list is empty");
        }
        return tail.value;
    }

    /** O(n). */
    public boolean contains(T value) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (Objects.equals(current.value, value)) {
                return true;
            }
        }
        return false;
    }

    /** Iterates tail to head. O(n). */
    public Iterable<T> reversed() {
        return () -> new Iterator<>() {
            private Node<T> current = tail;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.previous;
                return value;
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Node<T> current = head; current != null; current = current.next) {
            builder.append(current.value);
            if (current.next != null) {
                builder.append(" <-> ");
            }
        }
        return builder.append(']').toString();
    }
}
