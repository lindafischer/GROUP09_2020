public class Backtracking {
  public static void main (String[] args) {
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
	  
	int [] colors = new int [n];
	for (int k = 0; k <= n; k++) { // k = current index to test
		int ChromaticNumber = GraphColoring(adj_matrix, k, colors, n)
	}
			
public static int GraphColoring(int adj_matrix[][], int k, int colors[], int n) {
	for (int c = 1; c <= m; c++) { 
		if (isValid(adj_matrix, k, colors, n)) { 
			colors[k] = c; 
		}
		if ((k+1) < n) {
			GraphColoring(adj_matrix, k+1, colors, n);
		}
		else {
		// length of the filled part of the array colors = chromatic number 
		return ChromaticNumber;
		}
	}
	return 0;
}
			
public static boolean isValid (int adj_matrix[][], int k, colors[], int n) {
	for (int i = 0; i < n; i++) {
		if (adj_matrix[k][i] == 1 && c == x[i]) {
			return false;
		}
	}
	return true;
}
