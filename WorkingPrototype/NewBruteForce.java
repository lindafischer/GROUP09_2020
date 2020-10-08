import java.util.*;
public class NewBruteForce {
  public static int[][] adj;
  public static int run(int[][] adj_matrix) {
    adj = adj_matrix;
    int[] c = new int[adj.length];
    for(int k = 1; k <= adj.length; k++) {
      for(int i = 0; i < c.length; i++) {
        c[i] = -1;
      }
      if(possibleColoring(k, c, 0)) {
        return k;
      }
    }
    return -1;
  }

  public static boolean possibleColoring(int k, int[] colorings, int currentVertex) {
    System.out.println(Arrays.toString(colorings));
    if(currentVertex == colorings.length) {
      return true;
    }
    for(int i = 1; i <= k; i++) {
      boolean valid = true;
      colorings[currentVertex] = i;
      for(int j = 0; j < adj.length; j++) {
        if(adj[currentVertex][j] == 1) {
          if(colorings[j] == i) {
            valid = false;
          }
        }
      }
      if(valid) {
        return possibleColoring(k, colorings, currentVertex+1);
      }
    }
    return false;
  }
}
