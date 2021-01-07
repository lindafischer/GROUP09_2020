import java.io.*;
import java.util.*;

class ColEdge
{
	int u;
	int v;
}

public class ReadGraph
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

		for( int x=1; x<=n; x++ )
		{
			if( seen[x] == false )
			{
				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}

		//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
		//! e[1] will be the second edge...
		//! (and so on)
		//! e[m-1] will be the last edge
		//!
		//! there will be n vertices in the graph, numbered 1 to n
		long programStart = System.nanoTime();
		int[][] adj_matrix = HelperFunctions.getAdjacencyMatrix(e, m, n);
		int chromaticNumber = 0;
		int completenessCheck = HelperFunctions.checkIfComplete(m, n);
		if(completenessCheck != -1) {
			chromaticNumber = completenessCheck;
			System.out.println("This is a complete graph with " + completenessCheck + " nodes, therefore its chromatic number is " + completenessCheck);
			eval(programStart);
		}
		System.out.println("Not complete!");
		System.out.println("Starting tree detection...");
		System.out.println("Starting bipartite check...");
		long start = System.nanoTime();
		boolean isBipartite = BipartiteCheck.run(adj_matrix);
		double durationBipartite = (System.nanoTime() - start) / 1000000.0;
		if(isBipartite) {
			chromaticNumber = 2;
			System.out.println("This graph is bipartite, therefore its chromatic number must be 2!");
			System.out.println("Bipartition check took " + durationBipartite + "ms.");
			eval(programStart);
		}
		System.out.println("Graph is not bipartite!");
		Graph g = new Graph(e, m, n);
		ArrayList<Vertex> vertices = g.getVertices();
		System.out.println("Removing disconnected vertices...");
		adj_matrix = Reduce.removeDisconnectedVertices(vertices);
		System.out.println("Running Bron-Kerbosch with Tomita pivoting...");
		start = System.nanoTime();
		ArrayList<Vertex> largestClique = BronKerbosch.run(vertices);
		double duration = System.nanoTime() - start / 1000000.0;
		System.out.println("Bron-Kerbosch with Tomita pivoting took: " + duration);
		int lowerBound = largestClique.size();
		System.out.println("Result from Bron-Kerbosch: " + lowerBound);
		//Graph reduction
		adj_matrix = Reduce.run(largestClique, vertices, false);
		System.out.println("Reduced the graph by " + (n-adj_matrix.length) + " vertices.");
		int[] degrees = HelperFunctions.getDegrees(adj_matrix);
		System.out.println("Running the DSatur algorithm to find an upper bound...");
		start = System.nanoTime();
		int upperBound = DSatur.run(degrees, adj_matrix);
		duration = (System.nanoTime() - start) / 1000000.0;
		System.out.println("Result from DSatur: " + upperBound);
		System.out.println("DSatur took " + duration + "ms");
		if(lowerBound == upperBound) {
			chromaticNumber = upperBound;
			System.out.println("Did not need to bruteforce, since matching lower and upper bounds were found!");
			System.out.println("The chromatic number is " + chromaticNumber);
			eval(programStart);
		}
		System.out.println("Trying coloring the graph using backtracking...");
		start = System.nanoTime();
		int backtrackingResult = BacktrackingV2.run(lowerBound, adj_matrix, false);
		duration = (System.nanoTime() - start) / 1000000.0;
		System.out.println("Result from Backtracking: " + backtrackingResult);
		System.out.println("Backtracking took " + duration + "ms");
		if(backtrackingResult < upperBound) {
			upperBound = backtrackingResult;
		}
		if(lowerBound == upperBound) {
			chromaticNumber = upperBound;
			System.out.println("Did not need to bruteforce, since matching lower and upper bounds were found!");
			System.out.println("The chromatic number is " + chromaticNumber);
			eval(programStart);
		}
		System.out.println("Current best lower bound: " + lowerBound);
		System.out.println("Current best upper bound: " + upperBound);
	}

	private static void eval(long programStart) {
		System.out.println("The time needed to find the chromatic number is " + ((System.nanoTime() - programStart) / 1000000.0) + "ms");
		System.exit(0);
	}
}
