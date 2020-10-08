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
		Scanner in = new Scanner(System.in);
		//Initialize the algorithm classes
		HelperFunctions HF = new HelperFunctions();
		DSatur ds = new DSatur();
		Bruteforce bf = new Bruteforce();
		Backtracking bt = new Backtracking();
		TriangleDetection td = new TriangleDetection();
		//Initialize the adjacency matrix (res) and the degrees array (deg)
		int[][] res = HF.getAdjacencyMatrix(e, m, n);
		int[] deg = HF.getDegrees(res);
		//Perform a check if the graph is complete
		int completenessCheck = HF.checkIfComplete(m, n);
		String userChoice = "y";
		if(completenessCheck != -1) {
			System.out.println("This is a complete graph, so its chromatic number is: " + completenessCheck);
			System.out.println("Do you still want to run the other algorithms (Triangle detection, DSatur, Bruteforce and Backtracking)?");
			System.out.print("(y / n): ");
			userChoice = in.next();
		}
		else {
			System.out.println("The provided graph is not complete, proceeding with the algorithms...");
		}
		//Run the algorithms!
		if(userChoice.equals("y")) {
			System.out.println("Trivial upper bound: " + HF.getTrivialUpperBound(res));
			System.out.println("Most trivial lower bound is: " + HF.getTrivialLowerBound(m));
			//Here you can provide a time limit for triangle detection (third parameter, in seconds)
			int triangleDetectionResult = HF.getLowerBoundsByTriangleDetection(e, td, 20);
			if(triangleDetectionResult == -1) {
				System.out.println("Triangle detection took too long :(. Please see the most trivial lower bound instead!");
			}
			else {
				System.out.println("Lower Bounds (after triangle detection): " + triangleDetectionResult);
			}
			System.out.println("Running the DSatur algorithm to find an upper bound...");
			long start = System.nanoTime();
			System.out.println("DSatur upper bound: " + ds.run(deg, res));
			long end = System.nanoTime();
			double duration = (end - start) / 1000000000.0;
			System.out.println("Time needed for DSatur: " + duration + " seconds");
			System.out.print("How long do you want to attempt brute-forcing? (in seconds): ");
			int timeLimit = in.nextInt();
			System.out.println("Attempting brute force search for " + timeLimit + " seconds...");
			start = System.nanoTime();
			int bfResult = bf.run(n, m, res, timeLimit);
			if(bfResult == -1) {
				System.out.println("Brute forcing took too long :(. Please see the upper and lower bound results instead!");
			}
			else {
				System.out.println("Result from brute force search: " + bfResult);
				System.out.println("Brute-forcing took " + (System.nanoTime() - start) / 1000000000.0 + " seconds.");
			}
			System.out.print("How long do you want to attempt backtracking? (in seconds): ");
			timeLimit = in.nextInt();
			System.out.println("Attempting backtracking search for " + timeLimit + " seconds...");
			start = System.nanoTime();
			int btResult = bt.run(res, timeLimit);
			if(btResult == -1) {
				System.out.println("Backtracking took too long :(. Please see the lower and upper bound results instead!");
			}
			else {
				start = System.nanoTime();
				System.out.println("Result from backtracking: " + btResult);
				System.out.println("Backtracking took " + (System.nanoTime() - start) / 1000000000.0 + " seconds.");
			}
		}
		else {
			System.out.println("Ok, exiting now... Goodbye!");
			System.exit(0);
		}
	}
}
