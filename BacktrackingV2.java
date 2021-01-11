public class BacktrackingV2 {
  public static int[][] adj;
  public static int run(int lowerbound, int[][] adj_matrix, boolean debug) {
    boolean done = false;
    adj = adj_matrix;
    int chromaticNumber = lowerbound;
    while(!done) {
      if(debug) {
        System.out.println("Currently trying " + chromaticNumber + "-coloring");
      }
      int[] vertices = new int[adj.length];
      done = recursivePart(vertices, chromaticNumber, 0, 1);
      chromaticNumber++;
    }
    chromaticNumber--;
    return chromaticNumber;
  }
  private static boolean recursivePart(int[] vertices, int colors, int currV, int colorStart) {
    if(colorStart > colors) {
      boolean allChecked = true;
      for(int i = 0; i < currV; i++) {
        if(vertices[i] != colors) {
          allChecked = false;
        }
      }
      return false;
    }
    boolean done = true;
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] == 0) {
        done = false;
      }
    }
    if(done) {
      return true;
    }
    for(int i = colorStart; i <= colors; i++) {
      if(validColor(currV, i, vertices)) {
        vertices[currV] = i;
        return recursivePart(vertices, colors, currV+1, 1);
      }
    }
    //Backtrackingstep
    return recursivePart(vertices, colors, currV-1, vertices[currV-1]+1);
  }

  private static boolean validColor(int v, int c, int[] vertices) {
    for(int i = 0; i < adj.length; i++) {
      if(adj[v][i] == 1 && vertices[i] == c) {
        return false;
      }
    }
    return true;
  }
}
