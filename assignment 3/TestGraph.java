import java.util.*;

/* Program for testing the Graph methods. */
public class TestGraph {

    public static void main (String[] args) {

        /* Exceptions testing */

	System.out.println("");
	System.out.println("======================================================");
	System.out.println("TestGraph");
	System.out.println("======================================================");
	System.out.println("");

	Graph G = new Graph (1);
	GraphNode u = new GraphNode(0), v = new GraphNode (1);
	GraphEdge uv;
	Iterator neighbours;

	try {
	    G.insertEdge(u,v,1,"");
	    System.out.println("    Test 1 failed: Method insertEdge must throw an exception when");
	    System.out.println("           trying to insert and invalid edge.");
	    u = G.getNode(5);
	    System.out.println("   Test 1 failed: Method getNode must throw an exception when");
	    System.out.println("          trying to access inexistent node.");
	    uv = G.getEdge(u,v);
	    System.out.println("    Test 1 failed: Method getEdge must throw an exception when");
	    System.out.println("           trying to access an invalid edge!");
	    neighbours = G.incidentEdges(v);
	    System.out.println("    Test 1 failed: Method incidentEdges must throw an exception when");
	    System.out.println("           trying to access an invalid node!");
	    boolean adjacent = G.areAdjacent(u,v);
	    System.out.println("    Test 1 failed: Method areAdjacent must throw an exception when");
	    System.out.println("           trying to access an invalid node!");
	}
	catch (GraphException e){
	    System.out.println("    Test 1 passed");
	}


        /* Create a graph with 9 nodes and 11 edges. Query the graph and check that
           all edges are stored correctly                                           */

        /* Degree of current node */
        int degree;
        int node1, node2;

        int numNodes = 14;

        /* Set of nodes of the graph */
        GraphNode[] V = new GraphNode[numNodes];

        /* Degrees pf the nodes in test graph */
        int NodeDegree[] = {4, 3, 2, 3, 6, 3, 4, 6, 3, 6, 1, 1, 1, 1 };

        /* Adjacency matrix for test graph */
        int M[][] = {{0, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
                     {2, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 1
                     {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 2
                     {1, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0}, // 3
                     {1, 1, 1, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0}, // 4
                     {0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0}, // 5
                     {0, 0, 0, 0, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0}, // 6
                     {0, 1, 0, 2, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0}, // 7
                     {0, 0, 0, 0, 0, 0, 2, 1, 0, 1, 0, 0, 0, 0}, // 8
                     {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 1, 1, 1}, // 9
                     {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0}, // 10
                     {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // 11
                     {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // 12
                     {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}  //13
                    };

	String s;
	boolean failed;
	int i = 0, j = 0;

        G = new Graph(numNodes);

	failed = false;
        try {
	    /* Get all nodes of the graph */
	    for (i = 0; i < numNodes; ++i) {
		V[i] = G.getNode(i);
		if (V[i].getName() != i) failed = true;
	    }

	    V[2].mark(true);
	    if (V[2].isMarked() == false) failed = true;

	    if (!failed) System.out.println("    Test 2 passed");
	    else  System.out.println("    Test 2 failed");
	}
	catch (GraphException e) {
		System.out.println("    Test 2 failed");
	}

	failed = false;
	try {
	/* Insert the edges */
	for (i = 0; i < numNodes; ++i)
	    for (j = 0; j < i; ++j)
		if (M[i][j] == 1) G.insertEdge(V[i],V[j],1,"door");
		else if (M[i][j] == 2) G.insertEdge(V[i],V[j],0,"corridor");
	}
	catch (GraphException e) {
	    System.out.println("    Test 3 failed");
	    failed = true;
	}
	if (!failed) System.out.println("    Test 3 passed");

	failed = false;
	int result;
	try {
	    for (i = 0; i < numNodes; ++i)
		for (j = 0; j < i; ++j) {
		    if (M[i][j] != 0) {
			uv = G.getEdge(V[i],V[j]);
			result = uv.getType();
			if ((M[i][j] == 1 && result != 1) ||
			    (M[i][j] == 2 && result != 0)) failed = true;
			u = uv.firstEndpoint();
			if ((u.getName() != i) && u.getName() != j) failed = true;
			v = uv.secondEndpoint();
			if ((v.getName() != i) && v.getName() != j) failed = true;
			if (u.getName() == v.getName()) failed = true;
		    }
		}
	    if (!failed) System.out.println("    Test 4 passed");
	    else  System.out.println("    Test 4 failed");
	}
	catch (GraphException e) {
	    System.out.println("    Test 4 failed");
	}

	failed = false;
	try {
	    for (i = 0; i < numNodes; ++i)
		for (j = 0; j < i; ++j) 
		    if (M[i][j] != 0) {
			if (!G.areAdjacent(V[i],V[j]) || !G.areAdjacent(V[j],V[i]))
			    failed = true;
		    }
	    if (!failed)
		System.out.println("    Test 5 passed");
	    else System.out.println("    Test 5 failed");
	}
	catch (GraphException e) {
	    System.out.println("    Test 5 failed");
	}

        try {
            for (i = 0; i < numNodes; ++i) {
                u = G.getNode(i);
                neighbours = G.incidentEdges(u);
                degree = 0;
                while (neighbours.hasNext()) {
                    uv = (GraphEdge)neighbours.next();
                    ++degree;
                    node1 = uv.firstEndpoint().getName();
                    node2 = uv.secondEndpoint().getName();

                    if (M[node1][node2] == 0) {
                        System.out.println("         Error: There should not be an edge between");
                        System.out.println("                 nodes "+node1+" and "+node2);
                        failed = true;
                    }
                    else if ((uv.getType() == 1) && (M[node1][node2] != 1)) {
                        System.out.println("         Error: There should not be an edge of type \'door\' between");
                        System.out.println("                nodes "+node1+" and "+node2);
                        failed = true;
                    }
                    else if ((uv.getType() == 0) &&(M[node1][node2] != 2)) {
                        System.out.println("         Error:There should not be an edge of type \'corridor\' between");
                        System.out.println("               nodes "+node1+" and "+node2);
                        failed = true;
                    }
                }
                if (degree != NodeDegree[i]) {
                    System.out.println("         Error:The degree of node "+i+" should be "+NodeDegree[i]+", not "+degree);
                    failed = true;
                }
            }

	    if (!failed) System.out.println("    Test 6 passed");
	    else  System.out.println("    Test 6 failed");

        }
        catch(GraphException e) {
	    System.out.println("    Test 6 failed");
        }

    }
}


