


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/******************************************************************************
 *  Dependencies: Graph.java Vertex.java
 *
 *  A data type that represents a Graph using Adjacency Lists.
 *
 *  Abstraction function: Represents a graph as a HashMap of Lists:
 *      Keys represent the vertices of the graph.
 *      Corresponding List contains all of the downstream vertices from the key vertex.
 *
 *  Representation Invariant: There exists no vertex A in the graph HashMap such that
 *  no other vertex contains A in its list AND vertex A does not contain any other vertex in
 *  its own list.
 *
 ******************************************************************************/


public class AdjacencyListGraph<T> implements Graph<T> {

    HashMap<Vertex<T>, List<Vertex<T>>> graph;

    public AdjacencyListGraph(){
        graph = new HashMap<>();
    }

    public AdjacencyListGraph(Vertex<T> v){
        graph = new HashMap<>();
        graph.put(v, new ArrayList<>());
    }
    /**
     * Adds a vertex to the graph.
     *
     * @param v vertex to be added to the graph. Must not already be in the graph.
     *
     * @return mutates HashMap by adding a key of vertex v and an empty arraylist as the value.
     */
    public void addVertex(Vertex<T> v){
        graph.put(v, new ArrayList<>());
    }

    /**
     * @param v1 Vertex v1 that will be upstream neighbour of v2. Must be a vertex in the graph.
     *
     * @param v2 Vertex v2 that will be downstream neighbour of v1. Must be a vertex in the graph.
     *
     * @return mutates List<Vertex<T>> in the graph hashmap mapped
     * to key v1 by adding v2 to the list.
     */
    public void addEdge(Vertex<T> v1, Vertex<T> v2){
        List<Vertex<T>> v1List = graph.get(v1);
        v1List.add(v2);
        graph.put(v1, v1List);
    }

    /**
     * Check if there is an edge from v1 to v2.
     * @param v1 is upstream vertex. Must be in the graph.
     * @param v2 is downstream vertex. Must be in the graph.
     * @return true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex<T> v1, Vertex<T> v2){
        List<Vertex<T>> v1List = graph.get(v1);
        if (v1List.contains(v2)){
            return true;
        }
        return false;
    }

    /**
     * Get an array containing all vertices adjacent to v.
     * <p>
     * Precondition: v is a vertex in the graph
     * </p>
     * <p>
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     * </p>
     */
    public List<Vertex<T>> getNeighbors(Vertex<T> v){
        return graph.get(v);
    }

    /**
     * Get all vertices in the graph.
     * <p>
     * Postcondition: returns a list containing all vertices in the graph,
     * sorted by label in non-descending order.
     * This method should return a list of size 0 iff the graph has no vertices.
     * </p>
     */
    public List<Vertex<T>> getVertices(){
        List<Vertex<T>> vertices = new ArrayList<>();
        for (Vertex<T> v : graph.keySet()){
            vertices.add(v);
        }
        Collections.sort(vertices, (a,b) -> a.getLabel().compareTo(b.getLabel()));
        return vertices;
    }

    // This class should implement the Graph interface
}
