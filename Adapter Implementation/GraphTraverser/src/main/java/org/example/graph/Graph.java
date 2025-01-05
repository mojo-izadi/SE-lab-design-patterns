package org.example.graph;

import java.util.Collection;

public interface Graph {
    void addVertex(int v);
    void addEdge(String name, int sourceVertex, int destinationVertex);
    Collection<Integer> getNeighbors(int v);
}
