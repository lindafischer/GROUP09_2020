public class BruteForce {
  public static int ExactChromNumber(int n,int [] colors,int[][] adj_matrix){
    for(int i = 1; i <= n; i++) { // colors from 1 to n
      if(Colorings(1,i,colors,n,adj_matrix)) { // if graph can be colored using i colors starting at vertex 0
        return i;
      }
    }
    return 1; // program will never get to this line
  }
  //colors graph using m colors starting at vertex v
  public static boolean Colorings(int v, int m,int [] colors,int n,int [][]adj_matrix) {
    HelperFunctions HF=new HelperFunctions();
    if(v > n-1) { //if all vertices have been colored
      return true;
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
          if(Colorings(v+1,m,colors,n,adj_matrix)) // use recursion for the next vertex
          return true;

        }
      }
      colors[v-1] = 0; //if v is close to another vertex with the same color, assign color 0 to vertex v and fail
      return false;
    }
  }
}
