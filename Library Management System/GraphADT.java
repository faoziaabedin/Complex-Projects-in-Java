import java.util.*;

public interface GraphADT {

  /* Adds to the graph an edge connecting nodes u and v. The type for this new edge 
     is as indicated by the last parameter. This method throws a GraphException if 
	 either node does not exist or if there is already an edge connecting the given vertices. */
  public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException;

  /* Returns the node with the specified name. If no node with this name exists in the graph, the 
     method throws a GraphException.                                                           */
  public GraphNode getNode(int u) throws GraphException;

  /* Returns a Java Iterator storing all the edges incident on node u. It returns null if 
     node u does not have any edges incident on it. A GraphException is thrown if u is not
	 a node of this graph.                                                                  */
  public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException;

  /* Returns the edge connecting nodes u and v. Throws a GraphException if u or v are not nodes
     of this graph or if there is no edge between u and v                                        */
  public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException;

  /* Returns true}if nodes u and v are adjacent; returns false otherwise. Throws a GraphException
     if u or v are not nodes of this graph.                                                       */  
  public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException;
}
