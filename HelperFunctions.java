public class HelperFunctions {
  /**
  This function returns the adjacency matrix for a given graph (1 if two nodes
  are connected, 0 if not)
  @param e <i>List of ColEdge objects</i>
  @param m Number of edges in the graph
  @param n Number of vertices in the graph
  @return Adjacency Matrix
  */
  public static int[][] getAdjacencyMatrix(ColEdge[] e, int m, int n) {
    int [][] adj_matrix= new int[n][n];
    for(int i = 0; i < m; i++){
      int x=e[i].u;
      int y=e[i].v;
      adj_matrix[x-1][y-1]=1;
      adj_matrix[y-1][x-1]=1;
    }
    return adj_matrix;
  }

  public static int[] getDegrees(int[][] a) {
    int[] degrees = new int[a.length];
    for(int i = 0; i < a.length; i++) {
      for(int j = 0; j < a.length; j++) {
        if(a[j][i] == 1) {
          degrees[i]++;
        }
      }
    }
    return degrees;
  }
}
