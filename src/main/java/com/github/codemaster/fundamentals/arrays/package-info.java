/**
 * Arrays: contiguous memory, fixed size, O(1) random access.
 *
 * <p>An array is one block of memory. Element {@code i} lives at
 * {@code base + i * elementSize}, which is why indexing is O(1) and why the
 * size cannot change - the memory after the block belongs to someone else.</p>
 *
 * <pre>
 *   access by index     O(1)
 *   search (unsorted)   O(n)
 *   search (sorted)     O(log n)   see the searching package
 *   insert / delete     O(n)       everything after the hole must shift
 *   append (dynamic)    O(1)       amortized, O(n) on the resize step
 * </pre>
 *
 * <p>{@link com.github.codemaster.fundamentals.arrays.DynamicArray} shows how
 * {@code ArrayList} actually works: a fixed array plus a size counter, copied
 * into a bigger array when full. Doubling the capacity (not adding a constant)
 * is what makes append O(1) amortized.</p>
 *
 * <p>{@link com.github.codemaster.fundamentals.arrays.ArrayOps} collects the
 * classic single-array techniques - reversal, rotation, prefix sums, Kadane,
 * Dutch national flag - each in place where possible.</p>
 */
package com.github.codemaster.fundamentals.arrays;
