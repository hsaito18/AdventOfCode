

import java.util.*;


public class Algorithms {

    /**
     * *********************** Algorithms ****************************
     *
     * Please see the README for this machine problem for a more detailed
     * specification of the behavior of each method that one should
     * implement.
     */

    /**
     * This is provided as an example to indicate that this method and
     * other methods should be implemented here.
     * <p>
     * You should write the specs for this and all other methods.
     *
     * Computes the shortest distance from vertex a to b on the graph
     * @param graph graph must contain vertices a and b
     * @param a     starting vertex
     * @param b     destination vertex
     * @return the shortest distance from a to b as an int
     */
    public static <T> int shortestDistance(Graph<T> graph, Vertex<T> a, Vertex<T> b) {
        // TODO: Implement this method and others
        // Note that this method can be invoked as follows:
        //      Algorithms.<String>shortestDistance(g, a, b)
        // when the graph contains vertices of type Vertex<String>.
        // The compiler can also perform type inference so that we can simply use:
        //      Algorithms.shortestDistance(g, a, b)


        //AdjacencyMatrixGraph g = new AdjacencyMatrixGraph<>(graph);
        List<Integer> allDistances = new ArrayList<>(startComputeDistance(graph, a, b));

        int minDistance = allDistances.get(0);
        for (Integer i : allDistances) {
            if (minDistance > i)
                minDistance = i;
        }
        return minDistance;
    }

    /**
     * Start the recursive function computing all distances from v1 to v2
     * @param graph the graph being used containing v1 and v2
     * @param v1    v1 is in the graph, acts as the starting point
     * @param v2    v2 is in the graph, acts as the end point
     * @return the list of integers containing distances of all paths from v1 to v2
     */
    public static <T> List<Integer> startComputeDistance(Graph<T> graph, Vertex<T> v1, Vertex<T> v2) {
        List<Vertex<T>> remaining = new ArrayList<>(graph.getVertices());
        remaining.remove(v1);
        return computeDistance(graph, v1, v2, 0, remaining);
    }

    /**
     * Computes the distance from start point to end point recursively
     *
     * @param graph     graph used to compute distance
     * @param start     starting point, must exist on the graph
     * @param end       destination, must exist on the graph
     * @param distance  current distance on the path
     * @param remaining the remaining points on the graph that hasn't been visited
     * @return list containing the distance taken to reach the end
     */
    public static <T> List<Integer> computeDistance(Graph<T> graph, Vertex<T> start, Vertex<T> end, int distance, List<Vertex<T>> remaining) {
        List<Integer> totDistance = new ArrayList<>();
        List<Vertex<T>> remainingList = new ArrayList<>(remaining);
        if (start == end) {
            totDistance.add(distance);
            return totDistance;
        }
        List<Vertex<T>> neighbors = graph.getNeighbors(start);
        for (Vertex<T> neighbor : neighbors) {
            if (remaining.contains(neighbor)) {
                remainingList.remove(neighbor);
                totDistance.addAll(computeDistance(graph, neighbor, end, distance+1, remainingList));
            }
        }

        return totDistance;
    }

    /**
     * Perform a complete depth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a DFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param graph to be traversed
     * @return a set of lists that contains the vertices in a depth first search order, starting at every vertices.
     */
    public static <T> Set<List<Vertex<T>>> depthFirstSearch(Graph<T> graph) {
        Set<List<Vertex<T>>> dfsPath = new HashSet<List<Vertex<T>>>();
        List<Vertex<T>> allVertices = new ArrayList<>();
        allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices) {
            List<Vertex<T>> dfsSubPath = new ArrayList<>();
            dfsRecursive(v, dfsSubPath, graph);
            dfsPath.add(dfsSubPath);
        }
        return dfsPath;

    }

    public static <T> HashMap<List<Vertex<T>>, String> DFSLabels(Graph<T> graph){
        HashMap<List<Vertex<T>>, String> dfsPath = new HashMap<>();
        List<Vertex<T>> allVertices = new ArrayList<>();
        allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices) {
            List<Vertex<T>> dfsSubPath = new ArrayList<>();
            dfsRecursive(v, dfsSubPath, graph);
            dfsPath.put(dfsSubPath, v.getLabel());
        }
        return dfsPath;

    }

    /**
     * Adds the current path to a list and moves to the next vertex with the lexicographically smaller label.
     *
     * @param graph      graph that contains all the vertices and edges
     * @param vertex     the current node
     * @param dfsSubPath the current path traversed, and to be added upon
     */
    public static <T> void dfsRecursive(Vertex<T> vertex, List<Vertex<T>> dfsSubPath, Graph<T> graph) {
        dfsSubPath.add(vertex);
        List<Vertex<T>> neighbor = new ArrayList<>(graph.getNeighbors(vertex));
        neighbor.sort(Comparator.comparing(Vertex::getLabel));
        if (!neighbor.isEmpty()) {
            for (Vertex<T> n : neighbor) {
                if (!dfsSubPath.contains(n)) {
                    dfsRecursive(n, dfsSubPath, graph);
                }
            }
        }
    }

    /**
     * Perform a complete breadth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a BFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param graph to be traversed
     * @return a set of lists that contains the vertices in a breadth first search order, starting at every vertices.
     */
    public static <T> Set<List<Vertex<T>>> breadthFirstSearch(Graph<T> graph) {
        Set<List<Vertex<T>>> bfsPath = new HashSet<List<Vertex<T>>>();
        List<Vertex<T>> allVertices = new ArrayList<>();
        allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices) {
            List<Vertex<T>> bfsSubPath = new ArrayList<>();
            List<Vertex<T>> queue = new ArrayList<>();
            queue.add(v);
            while (!queue.isEmpty()) {
                bfsSubPath.add(queue.get(0));
                List<Vertex<T>> neighbor = new ArrayList<>(graph.getNeighbors(queue.get(0)));
                neighbor.sort(Comparator.comparing(Vertex::getLabel));
                queue.remove(0);
                if (!neighbor.isEmpty()) {
                    for (Vertex<T> n : neighbor) {
                        if (!bfsSubPath.contains(n) && !queue.contains(n)) {
                            queue.add(n);
                        }
                    }
                }
            }
            bfsPath.add(bfsSubPath);
        }
        return bfsPath;
    }

    /**
     * You should write the spec for this method
     */
    public static <T> int diameter(Graph<T> graph) {
        // TODO: Implement this method
        List<Integer> allDistances = new ArrayList<>();

        //get the distance between all possible pairs
        for (Vertex<T> v1 : graph.getVertices()) {
            for (Vertex<T> v2 : graph.getVertices()) {
                if (v1 != v2) {
                    allDistances.addAll(startComputeDistance(graph, v1, v2));
                    allDistances.addAll(startComputeDistance(graph, v2, v1));
                }
            }
        }

        //get the maximum of all distances
        int diameter = allDistances.get(0);
        for (Integer i : allDistances) {
            if (diameter < i)
                diameter = i;
        }
        return diameter;
    }

    /**
     * Returns a list containing all of the common upstream vertices of a and b.
     *
     * @param graph directed graph containing vertex a and vertex b.
     * @param a : Vertex a. graph must contain a.
     * @param b : Vertex b. graph must contain b.
     *
     * @return List<Vertex<T>> a list of vertices such that a and b are both neighbours for all
     * vertices in the list. Returns an empty list if there are no such vertices.
     *
     */
    public static <T> List<Vertex<T>> upstreamVertices(Graph<T> graph, Vertex<T> a, Vertex<T> b){
        List<Vertex<T>> commonVertices = new ArrayList<>();
        List<Vertex<T>> allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices){
            List<Vertex<T>> neighbours = graph.getNeighbors(v);
            if (neighbours.contains(a) && neighbours.contains(b)){
                commonVertices.add(v);
            }
        }
        return commonVertices;
    }

    /**
     *Returns a list containing all of the common downstream vertices of a and b.
     *
     *@param graph directed graph containing vertex a and vertex b.
     *@param a : Vertex a. graph must contain a.
     *@param b : Vertex b. graph must contain b.
     *
     *@return List<Vertex<T>> a list of vertices v such that v is a neighbour of both a and b.
     * returns an empty list if no such vertices exist.
     */
    public static <T> List<Vertex<T>> downstreamVertices(Graph<T> graph, Vertex<T> a, Vertex<T> b){
        List<Vertex<T>> commonVertices = new ArrayList<>();
        List<Vertex<T>> aNeighbours = graph.getNeighbors(a);
        List<Vertex<T>> bNeighbours = graph.getNeighbors(b);

        for (Vertex<T> v : aNeighbours){
            if (bNeighbours.contains(v)){
                commonVertices.add(v);
            }
        }
        return commonVertices;
    }

}
