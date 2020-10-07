import java.io.*;
import java.util.*;
import java.util.Arrays;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph_B
		{
		public static String[] str_array;
			
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
	  
	
		int k = 1; // k = current index/vertix to test		
		 GraphColoring(adj_matrix, k, colors, n);
			
			}
		

						
	static void  GraphColoring(int adj_matrix[][], int k, int colors[], int n) {
		int ChromaticNumber;
		for (int c = 1; c <= n; c++) {  // c = current color to test
			if(colors[n-1]!=0){ //if last vertice is colored return
				return;
			}
			
			if (isValid(adj_matrix, k, colors, n,c)) { // check if the color can be used
				colors[k-1] = c; // assign the color to the index
		
				if (k < n) { //check if the next index is less than the number of veritices
					GraphColoring(adj_matrix, k+1, colors, n); // recursive call for the next index
				}else { // if all the vertices have been assigned to a color
					if(DEBUG) System.out.println(Arrays.toString(colors));
					 ChromaticNumber = ChromaticNumber(colors);
					 System.out.println("Chromatic number is : "+ ChromaticNumber);
					return;
				
				    
				}
		   }
		}
		
	  }		
    static boolean isValid (int adj_matrix[][], int k, int colors[], int n,int c) {
	for (int i = 0; i < n; i++) {
		if (adj_matrix[k-1][i] == 1 && c == colors[i]) { // check if there is an edge between the vertices && if the color was already assigned to the vertix nearby 
			return false;
		}
	}
	return true;
}
		
	static int ChromaticNumber (int [] colors) {
		
		int ChromaticNumber = colors[0];
		
		for (int i = 0; i < colors.length; i++) { //finding maximum number that represents amount of colors
             if (colors[i] > ChromaticNumber) 
                 ChromaticNumber = colors[i]; 
	    }
        
         return ChromaticNumber;  
    }
			
			
				
}
		
		
		
		
		
