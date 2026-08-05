package com.github.codemaster.fundamentals.hashing;

import java.util.Objects;

/**
 * Hash map with open addressing and linear probing: one entry per slot, and a
 * collision means "walk forward until a free slot is found".
 *
 * <p>Everything lives in one array, so scanning is cache friendly and there are
 * no per-entry node objects. The price is deletion: simply clearing a slot
 * would cut a probe chain in half and hide later entries. The fix is a
 * <b>tombstone</b> - a marker meaning "empty for insertion, keep looking for
 * lookup".</p>
 *
 * <p>Because tombstones accumulate, this table resizes on
 * {@code (entries + tombstones) / capacity &gt; 0.5}, which also keeps probe
 * chains short - linear probing degrades badly above about half full
 * (primary clustering).</p>
 *
 * @param <K> key type
 * @param <V> value type
 */
public class OpenAddressingMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_FILL = 0.5;

    /** Marker object for a deleted slot. */
    private static final Object TOMBSTONE = new Object();

    private Object[] keys;
    private Object[] values;
    private int size;
    private int tombstones;

    public OpenAddressingMap() {
        this(DEFAULT_CAPACITY);
    }

    public OpenAddressingMap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1, was " + initialCapacity);
        }
        this.keys = new Object[initialCapacity];
        this.values = new Object[initialCapacity];
    }

    private int slotFor(Object key, int capacity) {
        int hash = key == null ? 0 : key.hashCode();
        hash ^= (hash >>> 16);
        return (hash & Integer.MAX_VALUE) % capacity;
    }

    /**
     * O(1) average.
     *
     * @return the previous value, or null
     */
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        if ((double) (size + tombstones + 1) / keys.length > MAX_FILL) {
            resize();
        }
        int index = slotFor(key, keys.length);
        int firstTombstone = -1;
        while (keys[index] != null) {
            if (keys[index] == TOMBSTONE) {
                if (firstTombstone < 0) {
                    firstTombstone = index; // remember it, but keep probing for the key
                }
            } else if (Objects.equals(keys[index], key)) {
                V previous = (V) values[index];
                values[index] = value;
                return previous;
            }
            index = (index + 1) % keys.length;
        }
        if (firstTombstone >= 0) {
            index = firstTombstone; // reuse the deleted slot
            tombstones--;
        }
        keys[index] = key;
        values[index] = value;
        size++;
        return null;
    }

    /** O(1) average. Probing stops only at a truly empty slot, never at a tombstone. */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = findSlot(key);
        return index < 0 ? null : (V) values[index];
    }

    public boolean containsKey(K key) {
        return findSlot(key) >= 0;
    }

    /** O(1) average. Leaves a tombstone so probe chains stay intact. */
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        int index = findSlot(key);
        if (index < 0) {
            return null;
        }
        V removed = (V) values[index];
        keys[index] = TOMBSTONE;
        values[index] = null;
        size--;
        tombstones++;
        return removed;
    }

    private int findSlot(K key) {
        int index = slotFor(key, keys.length);
        int probes = 0;
        while (keys[index] != null && probes < keys.length) {
            if (keys[index] != TOMBSTONE && Objects.equals(keys[index], key)) {
                return index;
            }
            index = (index + 1) % keys.length;
            probes++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return keys.length;
    }

    /**
     * Doubles the table, rehashes live entries and drops every tombstone. O(n).
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        int newCapacity = oldKeys.length * 2;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        size = 0;
        tombstones = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != TOMBSTONE) {
                put((K) oldKeys[i], (V) oldValues[i]);
            }
        }
    }
}
