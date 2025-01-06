package org.example.graph;

import org.jgrapht.graph.SimpleGraph;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class JGraphTAdapter implements Graph {
    SimpleGraph<Integer, String> graph = new SimpleGraph<>(String.class);

    @Override
    public void addVertex(int v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String name, int sourceVertex, int destinationVertex) {
        graph.addEdge(sourceVertex, destinationVertex, name);
    }

    @Override
    public Collection<Integer> getNeighbors(int v) {
        return graph.edgesOf(v).stream()
                .flatMap(edge -> {
                    Integer source = graph.getEdgeSource(edge);
                    Integer target = graph.getEdgeTarget(edge);
                    return source.equals(v) ? Set.of(target).stream() : Set.of(source).stream();
                })
                .collect(Collectors.toSet());
    }
}
