/*
 * Faozia Abedin
 * 251358251 fabedin4
 * GraphEdge.java
 */

 public class GraphEdge {

	private GraphNode first_endpoint;
	private GraphNode second_endpoint;
	private int type;
	private String label;
		
		public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
			this.first_endpoint = u;
			this.second_endpoint = v;
			this.type = type;
			this.label = label;
		}
		
		public GraphNode firstEndpoint() {
			return this.first_endpoint;
		}
		
		public GraphNode secondEndpoint() {
			return this.second_endpoint;
		}
		
		public int getType() {
			return this.type;
		}
		
		public void setype(int type) {
			this.type = type;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}
		
	}
	