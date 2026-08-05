package com.github.codemaster.fundamentals.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Topological sort: order the vertices of a directed acyclic graph so every
 * edge points forward.
 *
 * <p>This is the "what must happen before what" algorithm - build systems, task
 * schedulers, course prerequisites, dependency resolvers. It exists only for a
 * DAG: a cycle means the constraints contradict each other, and both methods
 * here detect that instead of returning nonsense.</p>
 *
 * <p>Both are O(V + E). The order is usually not unique.</p>
 */
public final class TopologicalSort {

    private TopologicalSort() {
    }

    /**
     * Kahn's algorithm (BFS flavour): repeatedly take a vertex with no
     * remaining incoming edges, output it, and remove its outgoing edges.
     *
     * <p>If the queue empties before every vertex is output, the leftovers all
     * depend on each other - that is a cycle.</p>
     *
     * @return the order, or {@link Optional#empty()} when the graph has a cycle
     */
    public static <V> Optional<List<V>> kahn(Graph<V> graph) {
        requireDirected(graph);
        Map<V, Integer> inDegree = new LinkedHashMap<>(graph.inDegrees());
        Deque<V> ready = new ArrayDeque<>();
        for (Map.Entry<V, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                ready.add(entry.getKey());
            }
        }
        List<V> order = new ArrayList<>(graph.vertexCount());
        while (!ready.isEmpty()) {
            V current = ready.poll();
            order.add(current);
            for (V neighbour : graph.neighbours(current)) {
                int remaining = inDegree.merge(neighbour, -1, Integer::sum);
                if (remaining == 0) {
                    ready.add(neighbour);
                }
            }
        }
        return order.size() == graph.vertexCount() ? Optional.of(order) : Optional.empty();
    }

    /**
     * DFS flavour: a vertex is prepended to the result once all of its
     * descendants are finished, so it always lands before them.
     *
     * @return the order, or {@link Optional#empty()} when the graph has a cycle
     */
    public static <V> Optional<List<V>> depthFirst(Graph<V> graph) {
        requireDirected(graph);
        Set<V> visited = new HashSet<>();
        Set<V> onPath = new HashSet<>();
        List<V> order = new ArrayList<>(graph.vertexCount());
        for (V vertex : graph.vertices()) {
            if (!visited.contains(vertex) && !visit(graph, vertex, visited, onPath, order)) {
                return Optional.empty();
            }
        }
        Collections.reverse(order); // finished-last is topologically first
        return Optional.of(order);
    }

    /** @return false when a cycle is found */
    private static <V> boolean visit(Graph<V> graph, V current, Set<V> visited, Set<V> onPath, List<V> order) {
        visited.add(current);
        onPath.add(current);
        for (V neighbour : graph.neighbours(current)) {
            if (onPath.contains(neighbour)) {
                return false;
            }
            if (!visited.contains(neighbour) && !visit(graph, neighbour, visited, onPath, order)) {
                return false;
            }
        }
        onPath.remove(current);
        order.add(current); // finished: everything reachable from here is already recorded
        return true;
    }

    private static <V> void requireDirected(Graph<V> graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("topological sort needs a directed graph");
        }
    }
}
