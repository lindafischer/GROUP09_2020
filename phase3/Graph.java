import java.util.ArrayList;
public class Graph {
  public int[][] adj;
  public ArrayList<Vertex> vertices;
  public int edgeNumber;
  public int vertexNumber;
  public Graph(ColEdge[] colEdges, int m, int n) {

    adj = HelperFunctions.getAdjacencyMatrix(colEdges, m, n);
    vertices = new ArrayList<Vertex>();
    edgeNumber = m;
    vertexNumber = adj.length;
    for(int i = 0; i < adj.length; i++) {
      vertices.add(new Vertex(i));
    }
    for(int i = 0; i < adj.length; i++) {
      vertices.get(i).setNeighbours(findNeighbours(i));
      vertices.get(i).setConflictingNodes(HelperFunctions.getSetMinus(vertices, vertices.get(i).getNeighbours()));
    }
  }
  private ArrayList<Vertex> findNeighbours(int currNode) {
    ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
    for(int i = 0; i < adj.length; i++) {
      if(adj[currNode][i] == 1) {
        neighbours.add(vertices.get(i));
      }
    }
    return neighbours;
  }
  public ArrayList<Vertex> getVertices() {
    return vertices;
  }
}
