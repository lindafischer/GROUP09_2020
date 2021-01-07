public class HoffmansBound {
  public static int[][] adj;
  public static void run(int[][] adj_matrix) {
    adj = adj_matrix;
    double[] v = new double[adj.length];


    for(int i = 0; i < v.length; i++) {
      v[i] = Math.random();
    }
    double vNorm = getNorm(v);
    for(int i = 0; i < v.length; i++) {
      v[i] = v[i] / vNorm;
    }
    double delta = 2;
    double prevNorm = vNorm;
    while(delta > 0) {
      v = mult(adj, v);
      vNorm = getNorm(v);
      for(int j = 0; j < v.length; j++) {
        v[j] = v[j] / vNorm;
      }
      delta = Math.abs(vNorm - prevNorm);
      prevNorm = vNorm;
    }
    System.out.println("Highest eigenvalue: " + (int) vNorm);
  }

  public static double getNorm(double[] v) {
    double squareSum = 0;
    for(int i = 0; i < v.length; i++) {
      squareSum += (v[i] * v[i]);
    }
    return Math.sqrt(squareSum);
  }

  private static double[] mult(int[][] adj, double[] v) {
    double[] result = new double[v.length];
    for(int i = 0; i < adj.length; i++) {
      for(int j = 0; j < adj.length; j++) {
        result[i] += (adj[i][j] * v[j]);
      }
    }
    return result;
  }
}
