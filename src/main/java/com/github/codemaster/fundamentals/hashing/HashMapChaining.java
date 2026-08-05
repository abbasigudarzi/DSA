package com.github.codemaster.fundamentals.hashing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hash map with separate chaining - each bucket is a small linked list.
 *
 * <p>This is how {@code java.util.HashMap} works (it additionally converts a
 * bucket into a red-black tree once the chain gets long, turning the worst case
 * from O(n) into O(log n)).</p>
 *
 * @param <K> key type
 * @param <V> value type
 */
public class HashMapChaining<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private static final class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] buckets;
    private int size;

    public HashMapChaining() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMapChaining(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.buckets = new Entry[initialCapacity];
    }

    /**
     * Bucket index for a key.
     *
     * <p>{@code hashCode()} may be negative and {@code %} keeps the sign, so the
     * result is masked with {@code Integer.MAX_VALUE} first. The extra xor-shift
     * spreads high bits down - without it, keys whose hashes differ only in the
     * high bits would all collide in a small table.</p>
     */
    private int bucketFor(Object key, int capacity) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        hash ^= (hash >>> 16);
        return (hash & Integer.MAX_VALUE) % capacity;
    }

    /**
     * O(1) average. Replaces the value when the key is already present.
     *
     * @return the previous value, or null
     */
    public V put(K key, V value) {
        int index = bucketFor(key, buckets.length);
        for (Entry<K, V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (Objects.equals(entry.key, key)) {
                V previous = entry.value;
                entry.value = value;
                return previous;
            }
        }
        buckets[index] = new Entry<>(key, value, buckets[index]); // prepend: O(1)
        size++;
        if ((double) size / buckets.length > MAX_LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    /** O(1) average, O(chain length) in the bucket. */
    public V get(K key) {
        int index = bucketFor(key, buckets.length);
        for (Entry<K, V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = bucketFor(key, buckets.length);
        for (Entry<K, V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (Objects.equals(entry.key, key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * O(1) average.
     *
     * @return the removed value, or null when the key was absent
     */
    public V remove(K key) {
        int index = bucketFor(key, buckets.length);
        Entry<K, V> previous = null;
        for (Entry<K, V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (Objects.equals(entry.key, key)) {
                if (previous == null) {
                    buckets[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }
                size--;
                return entry.value;
            }
            previous = entry;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return buckets.length;
    }

    /** Current load factor - entries per bucket. */
    public double loadFactor() {
        return (double) size / buckets.length;
    }

    /** Every key in the map, in no meaningful order. O(capacity + size). */
    public List<K> keys() {
        List<K> keys = new ArrayList<>(size);
        for (Entry<K, V> bucket : buckets) {
            for (Entry<K, V> entry = bucket; entry != null; entry = entry.next) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    /**
     * Doubles the table and rehashes everything. O(n).
     *
     * <p>Rehashing is mandatory, not an optimization: the slot is computed
     * modulo the capacity, so every entry's correct position changes.</p>
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] old = buckets;
        Entry<K, V>[] bigger = new Entry[old.length * 2];
        for (Entry<K, V> bucket : old) {
            Entry<K, V> entry = bucket;
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = bucketFor(entry.key, bigger.length);
                entry.next = bigger[index];
                bigger[index] = entry;
                entry = next;
            }
        }
        buckets = bigger;
    }
}
