package org.example.graph;

import edu.uci.ics.jung.graph.SparseMultigraph;

import java.util.Collection;

public class JungGraphAdapter implements Graph {
    SparseMultigraph<Integer, String> graph = new SparseMultigraph<>();

    @Override
    public void addVertex(int v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String name, int sourceVertex, int destinationVertex) {
        graph.addEdge(name, sourceVertex, destinationVertex);
    }

    @Override
    public Collection<Integer> getNeighbors(int v) {
        return graph.getNeighbors(v);
    }
}
