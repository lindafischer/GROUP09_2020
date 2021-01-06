public class TreeDetection {
  public static int[][] adj;
  public static boolean run(int[][] adj_matrix) {
    adj = adj_matrix;
    boolean[] visited = new boolean[adj.length];

    if(!dfsRecursivePart(0, -1, visited)) {
      return false;
    }
    for(int i = 0; i < visited.length; i++) {
      if(!visited[i]) {
        return false;
      }
    }
    return true;
  }
  private static boolean dfsRecursivePart(int currV, int parent, boolean[] visited) {
    if(visited[currV]) {
      return false;
    }
    visited[currV] = true;
    for(int i = 0; i < adj.length; i++) {
      if(i != parent && adj[currV][i] == 1) {
        if(!dfsRecursivePart(i, currV, visited)) {
          return false;
        }
      }
    }
    return true;
  }
}
