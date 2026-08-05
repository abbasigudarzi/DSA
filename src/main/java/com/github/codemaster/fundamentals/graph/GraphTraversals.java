package com.github.codemaster.fundamentals.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Breadth-first and depth-first search, plus what each one is good for.
 *
 * <p>Read {@link #breadthFirst} and {@link #depthFirstIterative} next to each
 * other: the code is identical except that one polls a queue and the other pops
 * a stack. That single line is the entire difference between "explore by
 * distance" and "dive to the bottom".</p>
 *
 * <p>Both are O(V + E) time and O(V) space.</p>
 */
public final class GraphTraversals {

    private GraphTraversals() {
    }

    /** BFS visit order from {@code start}. Uses a queue. O(V + E). */
    public static <V> List<V> breadthFirst(Graph<V> graph, V start) {
        List<V> order = new ArrayList<>();
        if (!graph.hasVertex(start)) {
            return order;
        }
        Set<V> visited = new LinkedHashSet<>();
        Deque<V> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start); // mark on ENQUEUE, not on dequeue, or nodes get queued twice
        while (!queue.isEmpty()) {
            V current = queue.poll();
            order.add(current);
            for (V neighbour : graph.neighbours(current)) {
                if (visited.add(neighbour)) {
                    queue.add(neighbour);
                }
            }
        }
        return order;
    }

    /** DFS visit order, recursive. O(V + E), O(V) stack depth. */
    public static <V> List<V> depthFirstRecursive(Graph<V> graph, V start) {
        List<V> order = new ArrayList<>();
        if (!graph.hasVertex(start)) {
            return order;
        }
        depthFirst(graph, start, new HashSet<>(), order);
        return order;
    }

    private static <V> void depthFirst(Graph<V> graph, V current, Set<V> visited, List<V> order) {
        if (!visited.add(current)) {
            return;
        }
        order.add(current);
        for (V neighbour : graph.neighbours(current)) {
            depthFirst(graph, neighbour, visited, order);
        }
    }

    /**
     * DFS with an explicit stack - same traversal, no recursion limit.
     *
     * <p>Neighbours are pushed in reverse so the first neighbour is popped
     * first, matching the recursive order.</p>
     */
    public static <V> List<V> depthFirstIterative(Graph<V> graph, V start) {
        List<V> order = new ArrayList<>();
        if (!graph.hasVertex(start)) {
            return order;
        }
        Set<V> visited = new HashSet<>();
        Deque<V> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            V current = stack.pop();
            if (!visited.add(current)) { // mark on POP: a node can be stacked more than once
                continue;
            }
            order.add(current);
            List<V> neighbours = graph.neighbours(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                if (!visited.contains(neighbours.get(i))) {
                    stack.push(neighbours.get(i));
                }
            }
        }
        return order;
    }

    /** True when any path leads from {@code from} to {@code to}. O(V + E). */
    public static <V> boolean hasPath(Graph<V> graph, V from, V to) {
        return breadthFirst(graph, from).contains(to);
    }

    /**
     * Shortest path in an <b>unweighted</b> graph, as a vertex list, or an empty
     * list when unreachable. O(V + E).
     *
     * <p>BFS reaches every vertex by the fewest possible edges, so recording the
     * vertex each one was discovered from and walking those parents backward
     * yields a shortest path. With weights this is wrong - use
     * {@link Dijkstra}.</p>
     */
    public static <V> List<V> shortestPath(Graph<V> graph, V start, V goal) {
        if (!graph.hasVertex(start) || !graph.hasVertex(goal)) {
            return List.of();
        }
        Map<V, V> parents = new HashMap<>();
        Set<V> visited = new HashSet<>();
        Deque<V> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (current.equals(goal)) {
                return buildPath(parents, start, goal);
            }
            for (V neighbour : graph.neighbours(current)) {
                if (visited.add(neighbour)) {
                    parents.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }
        return List.of();
    }

    private static <V> List<V> buildPath(Map<V, V> parents, V start, V goal) {
        List<V> path = new ArrayList<>();
        V current = goal;
        while (current != null) {
            path.add(current);
            if (current.equals(start)) {
                break;
            }
            current = parents.get(current);
        }
        java.util.Collections.reverse(path);
        return path;
    }

    /**
     * Connected components of an undirected graph: each returned list is one
     * group of mutually reachable vertices. O(V + E).
     */
    public static <V> List<List<V>> connectedComponents(Graph<V> graph) {
        List<List<V>> components = new ArrayList<>();
        Set<V> seen = new HashSet<>();
        for (V vertex : graph.vertices()) {
            if (seen.contains(vertex)) {
                continue;
            }
            List<V> component = breadthFirst(graph, vertex);
            seen.addAll(component);
            components.add(component);
        }
        return components;
    }

    /**
     * Cycle detection.
     *
     * <p>The two cases are genuinely different. Undirected: any edge to an
     * already-visited vertex that is not the one you just came from closes a
     * cycle. Directed: only an edge back into a vertex still on the current
     * recursion path counts - an edge to a finished vertex is a cross edge, not
     * a cycle.</p>
     *
     * <p>O(V + E).</p>
     */
    public static <V> boolean hasCycle(Graph<V> graph) {
        Set<V> visited = new HashSet<>();
        if (graph.isDirected()) {
            Set<V> onPath = new HashSet<>();
            for (V vertex : graph.vertices()) {
                if (!visited.contains(vertex) && hasDirectedCycle(graph, vertex, visited, onPath)) {
                    return true;
                }
            }
            return false;
        }
        for (V vertex : graph.vertices()) {
            if (!visited.contains(vertex) && hasUndirectedCycle(graph, vertex, null, visited)) {
                return true;
            }
        }
        return false;
    }

    private static <V> boolean hasDirectedCycle(Graph<V> graph, V current, Set<V> visited, Set<V> onPath) {
        visited.add(current);
        onPath.add(current);
        for (V neighbour : graph.neighbours(current)) {
            if (onPath.contains(neighbour)) {
                return true; // back edge
            }
            if (!visited.contains(neighbour) && hasDirectedCycle(graph, neighbour, visited, onPath)) {
                return true;
            }
        }
        onPath.remove(current); // leaving this vertex: it is no longer on the path
        return false;
    }

    private static <V> boolean hasUndirectedCycle(Graph<V> graph, V current, V cameFrom, Set<V> visited) {
        visited.add(current);
        for (V neighbour : graph.neighbours(current)) {
            if (neighbour.equals(cameFrom)) {
                continue; // the edge we arrived on is not a cycle
            }
            if (visited.contains(neighbour)) {
                return true;
            }
            if (hasUndirectedCycle(graph, neighbour, current, visited)) {
                return true;
            }
        }
        return false;
    }
}
