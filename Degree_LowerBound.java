
public class Degree_LowerBound {

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
	
//im not sure if this is right pls check
  public static int LowerBound(int[][] adj_matrix, int adj_matrix.length, int value) {
       
	   int higher = adj_matrix.length;       
	   int lower = 0;
        
		while (lower < higher) {
            final int middle = (lower + higher) / 2;
         
            if (value <= adj_matrix[middle][middle]) { //investigates if the value is less than the element in the middle
                higher = middle;
            } 
			else {
                lower = middle + 1;
            }
        }
        return lower;
    }
}
  }

