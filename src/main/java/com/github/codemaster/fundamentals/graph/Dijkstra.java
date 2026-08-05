package com.github.codemaster.fundamentals.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Dijkstra's shortest path for graphs with <b>non-negative</b> weights.
 *
 * <p>The algorithm is greedy: repeatedly take the unfinished vertex with the
 * smallest known distance, declare it final, and relax its outgoing edges. That
 * is only sound when weights are non-negative - with a negative edge, a vertex
 * finalised early could still be improved later, so use Bellman-Ford
 * instead.</p>
 *
 * <p>Time O((V + E) log V) with a binary heap. This implementation uses "lazy
 * deletion": instead of decreasing a key inside the heap (which
 * {@link PriorityQueue} cannot do), it pushes a second entry and skips stale
 * pops via the {@code finalised} set.</p>
 */
public final class Dijkstra {

    private Dijkstra() {
    }

    /** Result of one run: distances from the source, plus the parent tree. */
    public record Result<V>(Map<V, Integer> distances, Map<V, V> parents) {

        /** Distance to a vertex, or {@link Integer#MAX_VALUE} when unreachable. */
        public int distanceTo(V vertex) {
            return distances.getOrDefault(vertex, Integer.MAX_VALUE);
        }

        /** Shortest path as a vertex list, or empty when unreachable. */
        public List<V> pathTo(V vertex) {
            if (!distances.containsKey(vertex)) {
                return List.of();
            }
            List<V> path = new ArrayList<>();
            V current = vertex;
            while (current != null) {
                path.add(current);
                current = parents.get(current);
            }
            Collections.reverse(path);
            return path;
        }
    }

    /**
     * Runs Dijkstra from {@code source}.
     *
     * @throws IllegalArgumentException when any edge weight is negative
     */
    public static <V> Result<V> shortestPaths(Graph<V> graph, V source) {
        Map<V, Integer> distances = new HashMap<>();
        Map<V, V> parents = new HashMap<>();
        Set<V> finalised = new HashSet<>();
        PriorityQueue<Entry<V>> frontier = new PriorityQueue<>(Comparator.comparingInt(Entry::distance));

        distances.put(source, 0);
        frontier.add(new Entry<>(source, 0));

        while (!frontier.isEmpty()) {
            Entry<V> best = frontier.poll();
            if (!finalised.add(best.vertex())) {
                continue; // stale entry left over from an earlier improvement
            }
            for (Graph.Edge<V> edge : graph.edgesFrom(best.vertex())) {
                if (edge.weight() < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra requires non-negative weights, found " + edge.weight());
                }
                int candidate = best.distance() + edge.weight();
                Integer known = distances.get(edge.target());
                if (known == null || candidate < known) { // relaxation
                    distances.put(edge.target(), candidate);
                    parents.put(edge.target(), best.vertex());
                    frontier.add(new Entry<>(edge.target(), candidate));
                }
            }
        }
        return new Result<>(distances, parents);
    }

    private record Entry<V>(V vertex, int distance) {
    }
}
