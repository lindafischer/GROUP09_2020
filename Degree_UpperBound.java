public class Degree_UpperBound {
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
	int [] degrees = new int (n);
	for (int i = 1; i < adj_matrix.length; i++) {
		for (int j = 1; j < adj_matrix[0].length; j++) {
			if (adj_matrix[i][j] == 1) {
				degree[i-1] ++;
			}
		}
	}
	int MaxDegree = degrees[0];
	for (int i = 0; i < degrees.length; i++) {
		if (degrees[i] > MaxDegree) {
			MaxDegree = degrees[i]; // MaxDegree is the upper bound
		}
	}
	
			
