public class BruteForce {
  public static long startTime;
  public static int ExactChromNumber(int n,int [] colors,int[][] adj_matrix){
    startTime = System.nanoTime();
    for(int i = 1; i <= n; i++) { // colors from 1 to n
      int temp = Colorings(1,i,colors,n,adj_matrix);
      if(temp == 1) { // if graph can be colored using i colors starting at vertex 0
        return i;
      }
      else if(temp == -1) {
        return -1;
      }
    }
    return 1; // program will never get to this line
  }
  //colors graph using m colors starting at vertex v
  public static int Colorings(int v, int m,int [] colors,int n,int [][]adj_matrix) {
    double elapsedTime = (System.nanoTime() - startTime) / 1000000000;
    if(elapsedTime > 30) {
      return -1;
    }
    if(v > n-1) { //if all vertices have been colored
      return 1;
    }
    else {
      for(int i = 1; i <= m; i++) {
        boolean match = false;
        colors[v-1] = i; //assign color i to vertex v

        for(int j = 1; j < n; j++) {
          if(adj_matrix[v-1][j-1] == 1) { // if the current vertex is adjacient with all other vertices
            if(i == colors[j-1]) // if it is, check if they have the same color
            match = true;
          }
        }

        if(match == false) { // if they are not adjacient
          if(Colorings(v+1,m,colors,n,adj_matrix) == 1) // use recursion for the next vertex
          return 1;

        }
      }
      colors[v-1] = 0; //if v is close to another vertex with the same color, assign color 0 to vertex v and fail
      return 0;
    }
  }
}
