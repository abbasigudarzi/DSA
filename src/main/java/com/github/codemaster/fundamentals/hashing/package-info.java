/**
 * Hashing: turning a key into an array index.
 *
 * <p>A hash map is an array plus a function. {@code hash(key) % capacity} gives
 * a slot, so lookup is O(1) on average - not because searching got clever, but
 * because no searching happens at all.</p>
 *
 * <p>Two keys can land in the same slot. That is a <b>collision</b>, and it is
 * unavoidable (the key space is bigger than the array). The two classic
 * answers are implemented here:</p>
 * <ul>
 *   <li><b>Separate chaining</b> - each slot holds a linked list of entries.
 *       Simple, degrades gracefully.
 *       {@link com.github.codemaster.fundamentals.hashing.HashMapChaining}</li>
 *   <li><b>Open addressing</b> - one entry per slot; on collision probe for the
 *       next free slot. Better cache behaviour, but deletion needs tombstones.
 *       {@link com.github.codemaster.fundamentals.hashing.OpenAddressingMap}</li>
 * </ul>
 *
 * <pre>
 *   get / put / remove   O(1) average, O(n) worst case (everything collides)
 *   resize               O(n), amortized away like a dynamic array
 * </pre>
 *
 * <p><b>Load factor</b> = entries / capacity. Above roughly 0.75 collisions
 * dominate, so the table grows and every key is <b>rehashed</b> - the slot
 * depends on the capacity, so old positions are meaningless after a resize.</p>
 *
 * <p>The contract that makes all of this work: if {@code a.equals(b)} then
 * {@code a.hashCode() == b.hashCode()}. Break it - by overriding
 * {@code equals} without {@code hashCode}, or by mutating a key after
 * insertion - and entries become unreachable while still sitting in the
 * table.</p>
 */
package com.github.codemaster.fundamentals.hashing;
