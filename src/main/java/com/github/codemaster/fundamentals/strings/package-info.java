/**
 * Strings: an array of characters that cannot be modified.
 *
 * <p>In Java a {@code String} is <b>immutable</b>. Every concatenation builds a
 * new object and copies both sides, so building a string inside a loop with
 * {@code +} is O(n^2). {@link java.lang.StringBuilder} keeps a mutable
 * {@code char[]} and appends in O(1) amortized - use it whenever the result is
 * built piece by piece.</p>
 *
 * <pre>
 *   charAt(i)            O(1)
 *   substring(a, b)      O(b - a)   copies in modern JDKs
 *   concat with +        O(n + m)   new object every time
 *   naive search         O(n * m)
 *   KMP search           O(n + m)
 * </pre>
 *
 * <p>Watch out for the character model: {@code char} is a UTF-16 code unit, not
 * a character. Emoji and many CJK characters are surrogate pairs of two chars.
 * For real text processing iterate over {@code codePoints()}; the algorithms
 * here use {@code char} for clarity, which is the usual interview convention.</p>
 */
package com.github.codemaster.fundamentals.strings;
