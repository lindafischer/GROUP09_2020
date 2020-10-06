// n = number of vertices;
// v = current vertex 
// m = number of colors to try
// int [] q = array of colors used for the vertices
// int input[][] = adjacency matrix

//colors graph using minimum number of colors, returns chromatic number
static int color(){ 
    for(int i = 1; i <= n; i++) { // colors from 1 to n
        if(color(1,i)){ // if graph can be colored using i colors starting at vertex 0
            return i;
		}
	}
    return i; // program will never get to this line
}

//colors graph using m colors starting at vertex v
static boolean color(int v, int m) {
	if(v > n-1) { //if all vertices have been colored
        return true;
	}
    else {
        for(int i = 1; i <= m; i++) {
			boolean match = false;
			q[v-1] = i; //assign color i to vertex v

			for(int j = 1; j < n; j++) {
				if(input[v-1][j-1] == 1) { // if the current vertex is adjacient with all other vertices
					if(i == q[j-1]) // if it is, check if they have the same color 
                    match = true; 
                }
            }

            if(match == false) { // if they are not adjacient
                if(color(v+1,m)) // use recursion for the next vertex
                    return true;
                
			}
		}
		q[v-1] = 0; //if v is close to another vertex with the same color, assign color 0 to vertex v and fail
		return false;
	}
}
