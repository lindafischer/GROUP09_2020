import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph_BF
		{
		
		
		public final static boolean DEBUG = false;
		
		public final static String COMMENT = "//";
		
		public static void main( String args[] )
			{
			if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}

				
			String inputfile = args[0];
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
												
					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ ){
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}
			}	
			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			
			
				
			 int[] colors= new int[n];
			int [][] adj_matrix= new int[n+1][n+1];
			
			
			for(int i=0;i<m;i++){
				
				int x=e[i].u;
				int y=e[i].v;
				
				adj_matrix[x][y]=1;
				adj_matrix[y][x]=1;
				
			}	
				
					
						
		if(DEBUG)	for(int i=1;i<n+1;i++){
				for(int j=1;j<n+1;j++){
					System.out.print(adj_matrix[i][j]+ " ");
				}
				System.out.println();
			}
            
			
			System.out.println("The Chromatic number is : " + ExactChromNumber(n,colors,adj_matrix));
			
			
			//		DONE WITH ADJ MATRIX
		}
			
		
// n = number of vertices;
// v = current vertex 
// m = number of colors to try
// int [] q = array of colors used for the vertices
// int input[][] = adjacency matrix

//colors graph using minimum number of colors, returns chromatic number
	static int ExactChromNumber(int n,int [] colors,int[][] adj_matrix){ 
		for(int i = 1; i <= n; i++) { // colors from 1 to n
			if(Colorings(1,i,colors,n,adj_matrix)){ // if graph can be colored using i colors starting at vertex 0
				
				return i;
		}
	}
    return 1; // program will never get to this line
}
 
//colors graph using m colors starting at vertex v
 	static boolean Colorings(int v, int m,int [] colors,int n,int [][]adj_matrix) {
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

			
			
			
			

			
			
			
		

 
