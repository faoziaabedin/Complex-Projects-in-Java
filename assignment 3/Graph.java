/*
 * Faozia Abedin
 * 251358251 fabedin4
 * Graph.java
 */

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;

public class Graph implements GraphADT {

    private int numVertices; // Number of vertices in the graph
    private GraphNode[] vertexArray; // Array to store the graph nodes
    private GraphEdge[][] adjacencyMatrix; // Adjacency matrix to store edges

    /**
     * Constructor: Initializes the graph with the specified number of vertices.
     *
     * @param vertexCount The number of vertices in the graph
     */
    public Graph(int vertexCount) {
        numVertices = vertexCount;
        vertexArray = new GraphNode[vertexCount];
        adjacencyMatrix = new GraphEdge[vertexCount][vertexCount];

        int index = 0;
        while (index < vertexCount) {
            vertexArray[index] = new GraphNode(index); // Initialize each node
            index++;
        }
    }

    /**
     * Inserts an edge between two nodes with the specified type and label.
     *
     * @param node1 The first node
     * @param node2 The second node
     * @param type  The type of the edge
     * @param label The label of the edge
     * @throws GraphException If the nodes do not exist or the edge already exists
     */
    @Override
    public void insertEdge(GraphNode node1, GraphNode node2, int type, String label) throws GraphException {
        // Validate node existence
        if (!isValidNode(node1) || !isValidNode(node2)) {
            throw new GraphException("One or both nodes do not exist in the graph.");
        }
        // Check if the edge already exists
        if (adjacencyMatrix[node1.getName()][node2.getName()] != null) {
            throw new GraphException("An edge already exists between these nodes.");
        }

        // Create and insert the edge
        GraphEdge edge = new GraphEdge(node1, node2, type, label);
        adjacencyMatrix[node1.getName()][node2.getName()] = edge;
        adjacencyMatrix[node2.getName()][node1.getName()] = edge;
    }

    /**
     * Returns the node with the specified index.
     *
     * @param index The index of the node
     * @return The node at the given index
     * @throws GraphException If the node does not exist
     */
    @Override
    public GraphNode getNode(int index) throws GraphException {
        if (index < 0 || index >= numVertices || vertexArray[index] == null) {
            throw new GraphException("Node does not exist in the graph.");
        }
        return vertexArray[index];
    }

    /**
     * Returns an iterator over all edges incident to the specified node.
     *
     * @param node The node whose incident edges are needed
     * @return Iterator over the incident edges, or null if there are none
     * @throws GraphException If the node does not exist
     */
    @Override
    public Iterator<GraphEdge> incidentEdges(GraphNode node) throws GraphException {
        if (!isValidNode(node)) {
            throw new GraphException("The node does not exist in the graph.");
        }

        List<GraphEdge> edgeList = new ArrayList<>();
        int position = 0;

        while (position < numVertices) {
            if (adjacencyMatrix[node.getName()][position] != null) {
                edgeList.add(adjacencyMatrix[node.getName()][position]);
            }
            position++;
        }

        return edgeList.isEmpty() ? null : edgeList.iterator();
    }

    /**
     * Returns the edge connecting two nodes.
     *
     * @param node1 The first node
     * @param node2 The second node
     * @return The edge between the two nodes
     * @throws GraphException If the nodes do not exist or no edge exists between them
     */
    @Override
    public GraphEdge getEdge(GraphNode node1, GraphNode node2) throws GraphException {
        if (!isValidNode(node1) || !isValidNode(node2)) {
            throw new GraphException("One or both nodes do not exist in the graph.");
        }
        if (adjacencyMatrix[node1.getName()][node2.getName()] == null) {
            throw new GraphException("No edge exists between the specified nodes.");
        }
        return adjacencyMatrix[node1.getName()][node2.getName()];
    }

    /**
     * Determines whether two nodes are adjacent.
     *
     * @param node1 The first node
     * @param node2 The second node
     * @return True if the nodes are adjacent, false otherwise
     * @throws GraphException If the nodes do not exist
     */
    @Override
    public boolean areAdjacent(GraphNode node1, GraphNode node2) throws GraphException {
        if (!isValidNode(node1) || !isValidNode(node2)) {
            throw new GraphException("One or both nodes do not exist in the graph.");
        }
        return adjacencyMatrix[node1.getName()][node2.getName()] != null;
    }

    /**
     * Validates whether a node exists in the graph.
     *
     * @param node The node to validate
     * @return True if the node exists, false otherwise
     */
    private boolean isValidNode(GraphNode node) {
        int nodeIndex = node.getName();
        return nodeIndex >= 0 && nodeIndex < numVertices && vertexArray[nodeIndex] != null;
    }
}
