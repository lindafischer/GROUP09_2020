//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
//! e[1] will be the second edge...
//! (and so on)
//! e[m-1] will be the last edge
//! 
//! there will be n vertices in the graph, numbered 1 to n

//! INSERT YOUR CODE HERE!
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
	int k = 1; // k = current index/vertix to test
	int ChromaticNumber = GraphColoring(adj_matrix, k, colors, n)
			
public static int GraphColoring(int adj_matrix[][], int k, int colors[], int n) { //maybe a boolean would be better?
	for (int c = 1; c <= n; c++) {  // c = current color to test
		if (isValid(adj_matrix, k, colors, n)) { // check if the color can be used
			colors[k] = c; // assign the color to the index
		
			if ((k+1) < n) { //check if the next index is less than the number of veritices
				GraphColoring(adj_matrix, k+1, colors, n); // recursive call for the next index
			} else { // if all the vertices have been assigned to a color
				int ChromaticNumber = ChromaticNumber(colors); // then get the smallest number from the array of the colors
			}	
		}
	return ChromaticNumber;
}
			
public static boolean isValid (int adj_matrix[][], int k, int colors[], int n) {
	for (int i = 0; i < n; i++) {
		if (adj_matrix[k][i] == 1 && c == colors[i]) { // check if there is an edge between the vertices && if the color was already assigned to the vertix nearby 
			return false;
		}
	}
	return true;
}
		
public static int ChromaticNumber (int [] colors) {
	int ChromaticNumber = colors[0];
	for (int i = 0; i < colors.length; i++) { 
             if (colors[i] > ChromaticNumber) 
                 ChromaticNumber = colors[i]; 
	}
        
         return ChromaticNumber;  
}
		
		
		
		
		
