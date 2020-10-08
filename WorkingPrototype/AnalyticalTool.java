import java.io.*;
import java.util.*;

class ColEdge
{
  int u;
  int v;
}

public class AnalyticalTool
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
    HelperFunctions HF = new HelperFunctions();
    int[][] res = HF.getAdjacencyMatrix(e, m, n);
    int[] deg = HF.getDegrees(res);
    DSatur ds = new DSatur();
    BruteForce bf = new BruteForce();
    Backtracking bt = new Backtracking();
    TriangleDetection td = new TriangleDetection();
    int completenessCheck = HF.checkIfComplete(m, n);
		String userChoice = "y";
    if(completenessCheck != -1) {
			System.out.println("This is a complete graph, so its chromatic number is: " + completenessCheck);
			System.out.println("Do you still want to run the other algorithms(Triangle detection, DSatur, Bruteforce and Backtracking)?");
			System.out.print("(y / n): ");
			userChoice = in.next();
		}
		else {
			System.out.println("The provided graph is not complete, proceeding with the algorithms...");
		}
    if(userChoice == "y") {
      System.out.println("Trivial upper bound: " + HF.getTrivialUpperBound(res));
      System.out.println("Most trivial lower bound is: " + HF.getTrivialLowerBound(m));
      int triangleDetectionResult = HF.getLowerBoundsByTriangleDetection(e, td);
			if(triangleDetectionResult == -1) {
				System.out.println("Triangle detection took too long :(. Please see the most trivial lower bound instead!");
			}
			else {
				System.out.println("Lower Bounds (after triangle detection): " + triangleDetectionResult);
			}
      System.out.print("Please choose a time limit (in whole seconds): ");
      Scanner in = new Scanner(System.in);
      int timeLimit = in.nextInt();
      System.out.print("Please choose how many runs you want to compute: ");
      int userRunNumber = in.nextInt();
      double runNumber = userRunNumber / 1.0;
      System.out.println("Note: If brute-forcing fails in the first run it will not be tried again for performance reasons.");
      System.out.println("The same goes for backtracking.");
      int counter = 0;
      boolean runBF = true;
      boolean runBT = true;
      double durationDSatur = 0;
      double durationBF = 0;
      double durationBT = 0;
      int DSaturResult = -1;
      int bfResult = -1;
      int btResult = -1;
      while(counter < runNumber) {
        long start = System.nanoTime();
        DSaturResult = ds.run(deg, res);
        long end = System.nanoTime();
        durationDSatur += (end - start) / 1000000.0;
        if(runBF) {
          start = System.nanoTime();
          bfResult = bf.ExactChromNumber(n, res, timeLimit);
          end = System.nanoTime();
          durationBF += (end - start) / 1000000.0;
        }
        if(bfResult == -1 && runBF) {
          runBF = false;
          durationBF = -1;
        }
        if(runBT) {
          start = System.nanoTime();
          btResult = bt.run(res, timeLimit);
          end = System.nanoTime();
          durationBT += (end - start) / 1000000.0;
        }
        if(btResult == -1) {
          runBT = false;
          durationBT = -1;
        }
        counter++;
      }
      if(bfResult != -1) {
        System.out.println("Bruteforce result: " + bfResult);
        System.out.println("Bruteforce average time: " + durationBF / runNumber + "ms");
      }
      else {
        System.out.println("Bruteforce failed to compute the result in time.");
      }
      System.out.println("DSatur result: " + DSaturResult);
      System.out.println("DSatur average time: " + durationDSatur / runNumber + "ms");
      System.out.println("Backtracking result: " + btResult);
      System.out.println("Backtracking average time: " + durationBT / runNumber + "ms");
    }
    else {
      System.out.println("Ok, exiting now... Goodbye!");
      System.exit(0);
    }
  }
}
