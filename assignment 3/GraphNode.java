/*
 * Faozia Abedin
 * 251358251 fabedin4
 * GraphNode.java
 */
public class GraphNode {

	private int name;
	private boolean is_marked;
	
	public GraphNode(int name) {
		this.name = name; 
		this.is_marked = false;
	}

	
//	setters and getters
	public void mark(boolean mark) {
		this.is_marked = mark; 
	}
	
	public boolean isMarked() {
		return this.is_marked; 
	}
	
	public int getName() {
		return this.name; 
	}
	
}
