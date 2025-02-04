import java.util.ArrayList;
import java.util.Arrays;

public class BipartiteCheck {
  public static int[][] adj;

  public static boolean run(int[][] adj_matrix) {
    int[] vertices = new int[adj_matrix.length];
    adj = adj_matrix;
    ArrayList<Integer> queue = new ArrayList<Integer>();
    vertices[0] = 1;
    queue.add(0);
    return recursivePart(queue, vertices);
  }
  private static boolean recursivePart(ArrayList<Integer> queue, int[] vertices) {
    if(queue.size() != 0) {
      int currV = queue.get(0); //Works based on FIFO (first in first out)
      queue.remove(0);
      for(int i = 0; i < vertices.length; i++) {
        if(adj[currV][i] == 1 && vertices[i] == 0) {
          vertices[i] = 2 - (vertices[currV]-1);
          queue.add(i);
        }
        else if(adj[currV][i] == 1 && vertices[currV] == vertices[i]) {
          return false;
        }
      }
      return recursivePart(queue, vertices);
    }
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] == 0) {
        vertices[i] = 1;
        queue.add(i);
        return recursivePart(queue, vertices);
      }
    }
    return true;
  }
}
