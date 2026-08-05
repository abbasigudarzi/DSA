/**
 * Graphs: vertices connected by edges - the most general structure here. A
 * linked list is a graph, a tree is a graph without cycles.
 *
 * <p><b>Representation.</b> Adjacency list (a map from vertex to its
 * neighbours) costs O(V + E) memory and lists a vertex's neighbours in O(degree)
 * - right for the sparse graphs that occur in practice. An adjacency matrix
 * costs O(V^2) memory but answers "is there an edge u-v" in O(1); use it only
 * for dense graphs. {@link com.github.codemaster.fundamentals.graph.Graph} is an
 * adjacency list.</p>
 *
 * <p><b>The two traversals</b>
 * ({@link com.github.codemaster.fundamentals.graph.GraphTraversals}) differ only
 * in the container holding the frontier:</p>
 * <ul>
 *   <li><b>BFS</b> uses a queue, explores by distance, and therefore finds the
 *       shortest path in an <i>unweighted</i> graph.</li>
 *   <li><b>DFS</b> uses a stack (or recursion), dives to the bottom first, and
 *       is the basis of cycle detection, topological sort and connected
 *       components.</li>
 * </ul>
 * <p>Both are O(V + E). Both need a {@code visited} set - without it a cycle
 * makes them loop forever.</p>
 *
 * <p><b>Shortest paths</b>, in order of generality:</p>
 * <pre>
 *   unweighted            BFS               O(V + E)
 *   non-negative weights  Dijkstra          O((V + E) log V) with a heap
 *   negative weights      Bellman-Ford      O(V * E), also detects negative cycles
 *   all pairs             Floyd-Warshall    O(V^3)
 * </pre>
 * <p>Dijkstra is wrong on negative edges - it finalises a vertex the first time
 * it is popped, and a later negative edge could still improve it.</p>
 *
 * <p>Also here: {@link com.github.codemaster.fundamentals.graph.TopologicalSort}
 * (ordering a DAG, e.g. build or course dependencies) and
 * {@link com.github.codemaster.fundamentals.graph.UnionFind} (connected
 * components and Kruskal's MST in near-constant time per operation).</p>
 */
package com.github.codemaster.fundamentals.graph;
