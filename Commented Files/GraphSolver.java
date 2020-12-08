public class GraphSolver {
  /***
   *This method checks the completion of the graph
   * it also runs all the internal algorithms used
   * to compute exact number,lowerbound,upperbound
   * @param res the adjacency matrix of the given grah
   * @param n number of vertices of the given graph
   * @param m number of edges of the given graph
   * @param e the ColEdge Object Array
   * @return exact chromatic number if graph is complete
   * else keep running algorithms,return array of bounds
   */
  public static int[] run(int[][] res, int n, int m, ColEdge[] e) {
    //Initialize the algorithm classes!
    //Get the degrees array!
    int[] deg = HelperFunctions.getDegrees(res);
    //Perform a check if the graph is complete
    int completenessCheck = HelperFunctions.checkIfComplete(m, n);
    if(completenessCheck != -1) {
      System.out.println("This is a complete graph, so its chromatic number is: " + completenessCheck);
      int[] completeReturn = {0, 0, completenessCheck};
      return completeReturn;
    }
    else {
      System.out.println("The provided graph is not complete, proceeding with the algorithms...");
    }
    //Run the algorithms!
    int upperBound = HelperFunctions.getTrivialUpperBound(res);
    int lowerBound = HelperFunctions.getTrivialLowerBound(m);
    System.out.println("Trivial upper bound: " + upperBound);
    System.out.println("Most trivial lower bound is: " + lowerBound);
    //Here you can provide a time limit for triangle detection (third parameter, in seconds)
    int triangleDetectionResult = HelperFunctions.getLowerBoundsByTriangleDetection(e, 20);
    if(triangleDetectionResult == -1) {
      System.out.println("Triangle detection took too long :(. Please see the most trivial lower bound instead!");
    }
    else {
      lowerBound = triangleDetectionResult;
      System.out.println("Lower Bounds (after triangle detection): " + triangleDetectionResult);
    }
    System.out.println("Running the DSatur algorithm to find an upper bound...");
    long start = System.nanoTime();
    upperBound = DSatur.run(deg, res);
    System.out.println("DSatur upper bound: " + upperBound);
    long end = System.nanoTime();
    double duration = (end - start) / 1000000000.0;
    System.out.println("Time needed for DSatur: " + duration + " seconds");
    System.out.println("Trying backtracking...");
    int timeLimit = 100;
    System.out.println("Attempting backtracking search for " + timeLimit + " seconds...");
    start = System.nanoTime();
    int btResult = Backtracking.run(res, timeLimit);
    if(btResult == -1) {
      System.out.println("Backtracking took too long :(. Please see the lower and upper bound results instead!");
    }
    else {
      System.out.println("Result from backtracking: " + btResult);
      System.out.println("Backtracking took " + (System.nanoTime() - start) / 1000000000.0 + " seconds.");
      if(btResult < upperBound) {
        upperBound = btResult;
        System.out.println("Found better upper bound!");
        System.out.println("New upper bound is: " + upperBound);
      }
    }
    int[] boundArray = {lowerBound, upperBound};
    return boundArray;
  }
}
