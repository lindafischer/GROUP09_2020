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
        if ((node + 1) < n)
          GraphColor(
}
      	int [][] adj_matrix= new int[n+1][n+1];
			
			
			for(int i=0;i<m;i++){
				
				int x=e[i].u;
				int y=e[i].v;
				System.out.println("X : "+ x);
				System.out.println("Y : "+ y);
				adj_matrix[x][y]=1;
				adj_matrix[y][x]=1;
				
			}	
				
					
			
			
			
			
			for(int i=1;i<n+1;i++){
				for(int j=1;j<n+1;j++){
					System.out.print(adj_matrix[i][j]+ " ");
				}
				System.out.println();
			}
