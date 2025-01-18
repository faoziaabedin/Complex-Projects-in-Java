/*
 * Faozia Abedin
 * 251358251 fabedin4
 * Maze.java
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Maze {

    private Graph mazeGraph; // stores the graph representation of the maze
    private int startNodeIndex; // start node index in the graph
    private int endNodeIndex; // end node index in the graph
    private int availableCoins; // number of coins available for doors
    private List<GraphNode> solutionPath; // stores the solution path
    /*  
     * Constructor: Reads the maze from the input file and initializes the graph.
     *
     * @param inputFile Path to the input maze file
     * @throws MazeException if the file is invalid or cannot be read
     */
    public Maze(String inputFile) throws MazeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            parseInput(reader);
        } catch (IOException | GraphException e) {
            throw new MazeException("Error reading the input file.");
        }
    }

    /*
     * Returns the graph representing the maze.
     *
     * @return Graph object
     * @throws MazeException if the graph is not initialized
     */    
	public Graph getGraph() throws MazeException {
        if (this.mazeGraph == null) {
            throw new MazeException("The maze graph is not defined.");
        }
        return this.mazeGraph;
    }

    /**
     * Solves the maze using DFS and returns an iterator over the solution path.
     *
     * @return Iterator of GraphNode objects in the solution path, or null if no path exists
     */    
	public Iterator<GraphNode> solve() {
        try {
			solutionPath = new ArrayList<>(); // initialize the path list
            // Start DFS from the start node with the available coins
            if (findPath(availableCoins, mazeGraph.getNode(startNodeIndex)) != null) {
                return solutionPath.iterator();
            }
        } catch (GraphException e) {
            e.printStackTrace(); // Print the stack trace if there is a graph-related error
        }
        return null; // If no path is found
    }

    /**
     * DFS to find a solution path from the start to the exit node.
     *
     * @param remainingCoins Coins left for traversal
     * @param currentNode    Current node being processed
     * @return Iterator of the solution path, or null if no path is found
     * @throws GraphException if graph traversal fails
     */    
	private Iterator<GraphNode> findPath(int remainingCoins, GraphNode currentNode) throws GraphException {
        solutionPath.add(currentNode);

        // Base case: Reached the exit node
        if (currentNode.getName() == endNodeIndex) {
            return solutionPath.iterator();
        }

        currentNode.mark(true); // Mark the current node as visited

        Iterator<GraphEdge> edges = mazeGraph.incidentEdges(currentNode);
        while (edges.hasNext()) {
            GraphEdge edge = edges.next();
            GraphNode neighbor = edge.firstEndpoint().equals(currentNode)
                    ? edge.secondEndpoint()
                    : edge.firstEndpoint();

            // Check if neighbor is unvisited and coins are sufficient
            if (!neighbor.isMarked() && remainingCoins >= edge.getType()) {
                Iterator<GraphNode> path = findPath(remainingCoins - edge.getType(), neighbor);
                if (path != null) {
                    return path;  // Found valid path
                }
            }
        }

        currentNode.mark(false); // Unmark during backtracking
        solutionPath.remove(solutionPath.size() - 1);
        return null; // No valid path
    }

    /**
     * Reads the maze file and constructs the graph.
     *
     * @param reader BufferedReader for the input file
     * @throws IOException    if file reading fails
     * @throws GraphException if graph construction fails
     */   
	private void parseInput(BufferedReader reader) throws IOException, GraphException {
        // Read initial maze parameters
        int width = Integer.parseInt(reader.readLine().trim());
        int height = Integer.parseInt(reader.readLine().trim());
        int linkCount = Integer.parseInt(reader.readLine().trim());
        availableCoins = Integer.parseInt(reader.readLine().trim());
        // Initialize the graph (total number of nodes = width * height)
        mazeGraph = new Graph(height * linkCount);

        // Process each row of rooms and their connections
        for (int row = 0; row < linkCount; row++) {
            String roomRow = reader.readLine().trim();
            String corridorRow = (row < linkCount - 1) ? reader.readLine().trim() : null;

            processRoomRow(roomRow, corridorRow, height, row);
        }
    }

    /**
     * Processes a single row of rooms and the corresponding corridor row.
     *
     * @param roomRow     String representing rooms in the row
     * @param corridorRow String representing vertical connections
     * @param width       Width of the maze
     * @param rowIndex    Current row index
     * @throws GraphException if edge insertion fails
     */
    private void processRoomRow(String roomRow, String corridorRow, int width, int rowIndex) throws GraphException {
        // Replaced for loop with a while loop
        int col = 0;
        while (col < width) {
            int nodeIndex = rowIndex * width + col; // Calculate node index for the current room
            char room = roomRow.charAt(2 * col); // Get the character representing the room

            // Switch case to handle room types
            switch (room) {
                case 's':
                    startNodeIndex = nodeIndex; // Start node
                    break;
                case 'x':
                    endNodeIndex = nodeIndex; // End node
                    break;
            }

            // Handle horizontal connections using a ternary operator
            if (col < width - 1) {
                char horizontalLink = roomRow.charAt(2 * col + 1);
                addEdge(nodeIndex, nodeIndex + 1, horizontalLink);
            }

            // Process vertical connections using a ternary operator
            if (corridorRow != null) {
                char verticalLink = corridorRow.charAt(2 * col);
                addEdge(nodeIndex, nodeIndex + width, verticalLink);
            }

            col++; // Increment column
        }
    }

    /**
     * Adds an edge to the graph based on the type of link (corridor, door, or wall).
     *
     * @param node1Index  First node
     * @param node2Index  Second node
     * @param linkType    Connection type ('c', 'w', or a digit for coin cost)
     * @throws GraphException if an invalid connection type is encountered
     */
    private void addEdge(int node1Index, int node2Index, char linkType) throws GraphException {
        // Replaced if-else with a switch case for handling different link types
        switch (linkType) {
            case 'c': 
                // Corridor (cost 0 coins)
                mazeGraph.insertEdge(mazeGraph.getNode(node1Index), mazeGraph.getNode(node2Index), 0, "corridor");
                break;
            case 'w':
                // Wall (no edge)
                // Do nothing
                break;
            default:
                if (Character.isDigit(linkType)) {
                    // Locked door with a coin cost
                    int coinsRequired = Character.getNumericValue(linkType);
                    mazeGraph.insertEdge(mazeGraph.getNode(node1Index), mazeGraph.getNode(node2Index), coinsRequired, "door");
                } else {
                    throw new GraphException("Invalid link type: " + linkType);
                }
        }
    }
}