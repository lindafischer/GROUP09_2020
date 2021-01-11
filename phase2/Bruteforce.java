import javafx.concurrent.Task;

public class Bruteforce {
	// n = number of vertices;
	// v = current vertex
	// m = number of colors to try
	// int [] q = array of colors used for the vertices
	// int input[][] = adjacency matrix

	//colors graph using minimum number of colors, returns chromatic number
	public static Task<Integer> getTask(int n, int m, int[][] adj, int lowerBound, int upperBound){
		int[] colors= new int[n];
		int[][] adj_matrix = adj;
		//Initiate a new thread to run the bruteforcing in
		Task<Integer> task = new Task<Integer>() {
			@Override
			public Integer call() throws Exception{
				for(int i = lowerBound; i <= upperBound; i++) { // colors from 1 to n
					System.out.println("Currently trying " + i + "-coloring...");
					int temp = Colorings(0,i,colors,n,adj_matrix, this);
					if(temp == 1){ // if graph can be colored using i colors starting at vertex 0
						System.out.println("Bruteforce result: " + i);
						return i;
					}
					else if(temp == -2) {
						System.out.println("Aborting bruteforce...");
						break;
					}
				}
				return -1;
			}
		};
		return task;
	}

	//colors graph using m colors starting at vertex v
	static int Colorings(int v, int m,int [] colors,int n,int [][]adj_matrix, Task task) {
		if(task.isCancelled()) {
			return -2;
		}
		if(v > n-1) { //if all vertices have been colored
			return 1;
		}
		else {
			for(int i = 1; i <= m; i++) {
				boolean match = false;
				colors[v] = i; //assign color i to vertex v
				for(int j = 0; j < n; j++) {
					if(adj_matrix[v][j] == 1) { // if the current vertex is adjacient with all other vertices
						if(i == colors[j]) // if it is, check if they have the same color
						match = true;
					}
				}
				if(match == false) { // if they are not adjacient
					if(Colorings(v+1,m,colors,n,adj_matrix, task) == 1) // use recursion for the next vertex
					return 1;
				}
			}
			colors[v] = 0; //if v is close to another vertex with the same color, assign color 0 to vertex v and fail
			return 0;
		}
	}
}
