package com.github.codemaster.fundamentals.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Singly linked list with head and tail pointers.
 *
 * <p>Keeping a {@code tail} reference is what turns {@link #addLast} from O(n)
 * into O(1). Note that {@link #removeLast} stays O(n) anyway - a singly linked
 * node cannot reach its predecessor, so the list must be walked. That
 * asymmetry is exactly what a doubly linked list buys out.</p>
 *
 * @param <T> element type
 */
public class SinglyLinkedList<T> implements Iterable<T> {

    /** A node owns a value and one reference to the next node. */
    static final class Node<T> {
        T value;
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
        node.next = head;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    /** O(1) thanks to the tail pointer. */
    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    /** O(1). @throws NoSuchElementException when empty */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    /**
     * O(n) - the node before the tail can only be found by walking from the
     * head. A doubly linked list does this in O(1).
     */
    public T removeLast() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        if (head == tail) {
            return removeFirst();
        }
        Node<T> current = head;
        while (current.next != tail) {
            current = current.next;
        }
        T value = tail.value;
        current.next = null;
        tail = current;
        size--;
        return value;
    }

    /**
     * Removes the first node holding {@code value}. O(n).
     *
     * <p>Uses a dummy node in front of the head so deleting the first element
     * needs no special case.</p>
     *
     * @return true when something was removed
     */
    public boolean remove(T value) {
        Node<T> dummy = new Node<>(null);
        dummy.next = head;
        Node<T> previous = dummy;
        while (previous.next != null) {
            if (Objects.equals(previous.next.value, value)) {
                if (previous.next == tail) {
                    tail = previous == dummy ? null : previous;
                }
                previous.next = previous.next.next;
                head = dummy.next;
                if (head == null) {
                    tail = null;
                }
                size--;
                return true;
            }
            previous = previous.next;
        }
        return false;
    }

    /** O(n) - no random access, the list must be walked. */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for size " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
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

    /**
     * Reverses the list in place with three pointers.
     * Time O(n), space O(1).
     *
     * <p>At each step the current node's {@code next} is flipped to point at
     * the previous node; {@code next} must be saved first or the rest of the
     * list is lost.</p>
     */
    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;
        tail = head;
        while (current != null) {
            Node<T> next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
    }

    /**
     * Same result as {@link #reverse}, recursively.
     * Time O(n), space O(n) for the call stack - the recursion is not tail
     * recursive and the JVM does not eliminate tail calls anyway.
     */
    public void reverseRecursive() {
        tail = head;
        head = reverseFrom(head);
    }

    private Node<T> reverseFrom(Node<T> node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node<T> newHead = reverseFrom(node.next);
        node.next.next = node; // the node behind me should point back at me
        node.next = null;
        return newHead;
    }

    /**
     * Middle element by the slow/fast pointer trick. For an even size this
     * returns the second of the two middles. Time O(n), space O(1).
     */
    public T middle() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.value;
    }

    /**
     * Floyd's cycle detection ("tortoise and hare"). If a cycle exists the fast
     * pointer laps the slow one and they meet; otherwise fast reaches the end.
     * Time O(n), space O(1) - a HashSet of visited nodes also works but costs
     * O(n) memory.
     *
     * <p>A list built only through this class can never contain a cycle; the
     * method exists because the technique is the point.</p>
     */
    public boolean hasCycle() {
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
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
                builder.append(" -> ");
            }
        }
        return builder.append(']').toString();
    }
}
