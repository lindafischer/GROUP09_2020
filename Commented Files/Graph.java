import java.util.Random;

public class Graph {
  /**
  This class is used to integrate unified Graph objects which can be used by the
  different gamemodes and whose variables are used by the different algorithms for
  determining the lower and upper bound, detect triangles and compute the exact
  chromatic number. This constructor is only used when the user provides a graph
  using the uploading functionality in Uploader.java
  @param colEdges The ColEdge array containing all edges between the different vertices
  @param n An integer resembling the number of vertices
  @param m An integer resembling the number of edges
  */
  public Graph(ColEdge[] colEdges, int n, int m) {
    adj = HelperFunctions.getAdjacencyMatrix(colEdges, m, n);
    col = colEdges;
    edgeNumber = m;
    vertexNumber = n;
  }

  /**
  This constructor overloads the one above in case only the number of vertices and edges
  is given as parameters. This is the case when a new graph needs to be created
  because the user provided the number of vertices and edges in the InsertView.
  */
  public Graph(int n, int m) {
    adj = new int[n][n];
    vertexNumber = n;
    edgeNumber = m;
    int edgeCount = 0;
    while(edgeCount < edgeNumber) {
      int i = (int) (Math.random() * n);
      int j = (int) (Math.random() * n);
      if(i != j && adj[i][j] != 1) {
        adj[i][j] = 1;
        adj[j][i] = 1;
        edgeCount++;
      }
    }
    col = computeColEdges();
  }

  /**
  This method is used to create a ColEdge array from a given adjacency matrix.
  This ColEdge array is needed for our trianlge detection algorithm
  @return An array of <i>ColEdge</i> objects containing all of the graph's edges between the vertices
  */
  public ColEdge[] computeColEdges() {
    ColEdge[] colEdges = new ColEdge[edgeNumber];
    int counter = 0;
    for(int i = 0; i < adj.length; i++) {
      for(int j = i+1; j < adj[i].length; j++) {
        if(adj[i][j] == 1) {
          colEdges[counter] = new ColEdge();
          colEdges[counter].u = i;
          colEdges[counter].v = j;
          counter++;
        }
      }
    }
    return colEdges;
  }

  /**
  This method is used to get the graph's ColEdge array
  @return An array of <i>ColEdge</i> objects containing all of the graph's edges between the vertices
  */
  public ColEdge[] getColEdgeArray() {
    return col;
  }

  /**
  This method is used to get the graph's adjacency matrix
  @return The graphs adjacency matrix as a 2-dimensional array of integers
  */
  public int[][] getAdj() {
    return adj;
  }

  /**
  This method is used to get the number of vertices in the graph
  @return The number of vertices as an integer
  */
  public int getVertexNumber() {
    return vertexNumber;
  }
  /**
  This method is used to get the number of edges in the graph
  @return The number of edges as an integer
  */
  public int getEdgeNumber() {
    return edgeNumber;
  }
  /**
  This method is used to get the graph's chromatic number
  @return The chromatic number of type int
  */
  public int getChromaticNumber() {
    return chromaticNumber;
  }

  private int[][] adj;
  private Random rand = new Random();
  private int vertexNumber;
  private int edgeNumber;
  private int chromaticNumber;
  private ColEdge[] col;
}
