package com.github.codemaster.fundamentals.graph;

/**
 * Union-Find (disjoint set union): tracks which elements are in the same group
 * under repeated merging.
 *
 * <p>Each set is a tree; the root is the set's identity. {@link #find} walks to
 * the root, {@link #union} attaches one root under the other.</p>
 *
 * <p>Two optimizations turn a potentially O(n) chain into effectively
 * constant time:</p>
 * <ul>
 *   <li><b>Union by rank</b> - always hang the shallower tree under the deeper
 *       one, so trees stay shallow.</li>
 *   <li><b>Path compression</b> - on the way back from a find, point every node
 *       visited straight at the root.</li>
 * </ul>
 *
 * <p>Together the amortized cost per operation is O(alpha(n)), where alpha is
 * the inverse Ackermann function - below 5 for any n that fits in this
 * universe.</p>
 *
 * <p>Used for Kruskal's minimum spanning tree, connected components in a stream
 * of edges, and cycle detection while adding edges to an undirected graph.</p>
 */
public class UnionFind {

    private final int[] parent;
    private final int[] rank;
    private int componentCount;

    /** Starts with {@code size} elements, each alone in its own set. */
    public UnionFind(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size must be >= 0, was " + size);
        }
        parent = new int[size];
        rank = new int[size];
        componentCount = size;
        for (int i = 0; i < size; i++) {
            parent[i] = i; // every element is its own root
        }
    }

    /**
     * Representative of the set containing {@code element}, compressing the
     * path on the way. O(alpha(n)) amortized.
     */
    public int find(int element) {
        checkBounds(element);
        int root = element;
        while (parent[root] != root) {
            root = parent[root];
        }
        while (parent[element] != root) { // second pass: point everything at the root
            int next = parent[element];
            parent[element] = root;
            element = next;
        }
        return root;
    }

    /**
     * Merges the two sets. O(alpha(n)) amortized.
     *
     * @return false when both elements were already in the same set - which, on
     *         an edge of an undirected graph, means that edge closes a cycle
     */
    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;
        }
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++; // equal ranks: the merged tree got one level deeper
        }
        componentCount--;
        return true;
    }

    /** True when both elements are in the same set. */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    /** Number of disjoint sets remaining. */
    public int componentCount() {
        return componentCount;
    }

    public int size() {
        return parent.length;
    }

    private void checkBounds(int element) {
        if (element < 0 || element >= parent.length) {
            throw new IndexOutOfBoundsException("element " + element + " out of bounds for size " + parent.length);
        }
    }
}
