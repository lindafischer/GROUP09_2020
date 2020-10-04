public class Backtracking {
  public static void main (String[] args) {
    int n = 4;
    int m = 6;
    int [][] adj = {{0,1,1,1},{1,0,1,1},{1,1,0,1},{1,1,1,0}};
    int [] x = new int [n];
  }
  
  public static int [] GraphColor (int [][] adj, int n, int m, int [] x) {
    for (int node = 1; node <= n; node++) {
      for (int color = 1; color <= n; color++) {
        if (isValid(node, color)) {
          x [node] = color;
        }
        if (
}
