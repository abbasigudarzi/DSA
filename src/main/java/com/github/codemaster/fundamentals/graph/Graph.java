package com.github.codemaster.fundamentals.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Weighted graph stored as an adjacency list.
 *
 * <p>One class covers four shapes: directed or undirected (constructor flag),
 * weighted or unweighted (an unweighted edge is just weight 1). An undirected
 * edge is stored twice, once in each direction - that is all "undirected"
 * means here.</p>
 *
 * <p>{@link LinkedHashMap} and {@link LinkedHashSet} are used so iteration
 * order is insertion order; that makes traversal output deterministic and
 * testable, which a plain {@code HashMap} would not.</p>
 *
 * <pre>
 *   addVertex / addEdge     O(1)
 *   neighbours(v)           O(degree)
 *   hasEdge(u, v)           O(degree of u)
 *   memory                  O(V + E)
 * </pre>
 *
 * @param <V> vertex type - anything with sane equals/hashCode
 */
public class Graph<V> {

    /** An outgoing edge: where it goes and what it costs. */
    public record Edge<V>(V target, int weight) {
    }

    private final Map<V, List<Edge<V>>> adjacency = new LinkedHashMap<>();
    private final boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
    }

    /** Undirected graph - the common default. */
    public static <V> Graph<V> undirected() {
        return new Graph<>(false);
    }

    /** Directed graph - edges go one way. */
    public static <V> Graph<V> directed() {
        return new Graph<>(true);
    }

    public boolean isDirected() {
        return directed;
    }

    /** O(1). Adding an existing vertex changes nothing. */
    public void addVertex(V vertex) {
        adjacency.computeIfAbsent(vertex, key -> new ArrayList<>());
    }

    /** Unweighted edge - weight 1. O(1). */
    public void addEdge(V from, V to) {
        addEdge(from, to, 1);
    }

    /**
     * Adds an edge, creating either endpoint if needed. O(1).
     *
     * <p>In an undirected graph the reverse edge is added too.</p>
     */
    public void addEdge(V from, V to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacency.get(from).add(new Edge<>(to, weight));
        if (!directed) {
            adjacency.get(to).add(new Edge<>(from, weight));
        }
    }

    /** Outgoing edges. O(1) to fetch, O(degree) to walk. */
    public List<Edge<V>> edgesFrom(V vertex) {
        return Collections.unmodifiableList(adjacency.getOrDefault(vertex, List.of()));
    }

    /** Neighbouring vertices, ignoring weights. O(degree). */
    public List<V> neighbours(V vertex) {
        List<V> result = new ArrayList<>();
        for (Edge<V> edge : adjacency.getOrDefault(vertex, List.of())) {
            result.add(edge.target());
        }
        return result;
    }

    /** O(degree of {@code from}). */
    public boolean hasEdge(V from, V to) {
        for (Edge<V> edge : adjacency.getOrDefault(from, List.of())) {
            if (edge.target().equals(to)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVertex(V vertex) {
        return adjacency.containsKey(vertex);
    }

    /** All vertices, in insertion order. */
    public Set<V> vertices() {
        return Collections.unmodifiableSet(adjacency.keySet());
    }

    public int vertexCount() {
        return adjacency.size();
    }

    /** Edge count. An undirected edge counts once. */
    public int edgeCount() {
        int total = 0;
        for (List<Edge<V>> edges : adjacency.values()) {
            total += edges.size();
        }
        return directed ? total : total / 2;
    }

    /** Number of edges leaving a vertex. O(1). */
    public int degree(V vertex) {
        return adjacency.getOrDefault(vertex, List.of()).size();
    }

    /**
     * Incoming-edge count per vertex, for a directed graph. O(V + E).
     * Used by Kahn's topological sort.
     */
    public Map<V, Integer> inDegrees() {
        Map<V, Integer> degrees = new LinkedHashMap<>();
        for (V vertex : adjacency.keySet()) {
            degrees.putIfAbsent(vertex, 0);
        }
        for (List<Edge<V>> edges : adjacency.values()) {
            for (Edge<V> edge : edges) {
                degrees.merge(edge.target(), 1, Integer::sum);
            }
        }
        return degrees;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(directed ? "directed graph\n" : "undirected graph\n");
        String arrow = directed ? " -> " : " -- ";
        for (Map.Entry<V, List<Edge<V>>> entry : adjacency.entrySet()) {
            builder.append(entry.getKey()).append(arrow).append('[');
            for (int i = 0; i < entry.getValue().size(); i++) {
                Edge<V> edge = entry.getValue().get(i);
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(edge.target());
                if (edge.weight() != 1) {
                    builder.append('(').append(edge.weight()).append(')');
                }
            }
            builder.append("]\n");
        }
        return builder.toString();
    }
}
