public class HelperFunctions {
  /**
  This function returns the adjacency matrix for a given graph (1 if two nodes
  are connected, 0 if not)
  @param e <i>List of ColEdge objects</i>
  @param m Number of edges in the graph
  @param n Number of vertices in the graph
  @return Adjacency Matrix
  */
  public static int[][] getAdjacencyMatrix(ColEdge[] e, int m, int n) {
    int [][] adj_matrix= new int[n][n];
    for(int i = 0; i < m; i++){
      int x=e[i].u;
      int y=e[i].v;
      adj_matrix[x-1][y-1]=1;
      adj_matrix[y-1][x-1]=1;
    }
    return adj_matrix;
  }

  /**
  This function returns a list of degrees for each node, where the index is the
  corresponding node/vertex
  @param a The adjacency matrix, represented by a 2D-array, can be computed using the <i>getAdjacencyMatrix</i> method
  @return List of degrees
  */
  public static int[] getDegrees(int[][] a) {
    int[] degrees = new int[a.length];
    for(int i = 0; i < a.length; i++) {
      for(int j = 0; j < a.length; j++) {
        if(a[j][i] == 1) {
          degrees[i]++;
        }
      }
    }
    return degrees;
  }

  /**
  Custom max function for any 1D-array of arbitrary length
  @param arr <i>Array to compute the max of</i>
  @return Maximum int value for the given array
  */
  public static int getMax(int[] arr) {
    int record = 0;
    for(int i = 0; i < arr.length; i++) {
      if(arr[i] > record) {
        record = arr[i];
      }
    }
    return record;
  }

  /**
  This functions prepares the given array for a 2-dimensional sorting
  (Needed to not loose track of which node has the highest degree when sorting by degree)
  <h2>Important Note</h2>
  Please use this method to sort your arrays while preserving the indices, not recursive2DSortHelper.
  (See explanation under recursive2DSortHelper)
  @param arr The array to prepare
  @return The prepared 2D array
  */
  public static int[][] TwoDSort(int[] arr) {
    int[][] result = new int[arr.length][2];
    for(int i = 0; i < arr.length; i++) {
      result[i][0] = arr[i];
      result[i][1] = i;
    }
    return recursiveTwoDSortHelper(result);
  }

  /**
  This function sorts any 1D array while preserving the indices
  <h2>Important Note</h2>
  This function is called within TwoDSort and therefore is just a helper function to the before mentioned,
  to sort your array please use TwoDSort instead.
  @param arr The array to sort
  @return A sorted 2D array with arr[i][0] being the value and arr[i][1] the original index
  */
  public static int[][] recursiveTwoDSortHelper(int[][] arr) {
    int sortedCount = 0;
    for(int i = 1; i < arr.length; i++) {
      if(arr[i-1][0] > arr[i][0]) {
        int temp0 = arr[i-1][0];
        int temp1 = arr[i-1][1];
        arr[i-1][0] = arr[i][0];
        arr[i-1][1] = arr[i][1];
        arr[i][0] = temp0;
        arr[i][1] = temp1;
      }
      else {
        sortedCount++;
      }
    }
    if(sortedCount == arr.length-1) {
      return arr;
    }
    return recursiveTwoDSortHelper(arr);
  }

  /**
  This function returns the trivial upper bound
  @param arr Adjacency Matrix for a graph
  @return Trivial upper bound (MaxDegree + 1)
  */
  public static int getTrivialUpperBound(int[][] arr) {
    int[] deg = getDegrees(arr);
    return getMax(deg) + 1;
  }

  /**
  This function returns the most trivial lower bound (1 if the graph has no edges, 2 otherwise)
  @param m Number of edges
  @return Trivial lower bound
  */
  public static int getTrivialLowerBound(int m) {
    if(m == 0) return 1;
    else return 2;
  }

  /**
  This function returns the index of the maximum value in the array
  @param arr Array to find the maxIndex in
  @return Index of the maximum value
  */
  public static int getMaxIndex(int[] arr) {
    int maxVal = getMax(arr);
    for(int i = 0; i < arr.length; i++) {
      if(arr[i] == maxVal) {
        return i;
      }
    }
    return -1;
  }

  /**
  This function detects triangular structures and returns a more precise lower bound based on the acquired information
  @param e An array containing the ColEdge objects of the provided graph
  @param td A <i>TriangleDetection</i> object
  @param timeLimitParam The time limit in whole seconds which, if exceeded, will cause the algorithm to abort
  @return Lower bound (either 2 if no triangles were detected, 3 if otherwise)
  */
  public static int getLowerBoundsByTriangleDetection(ColEdge[] e, int timeLimitParam) {
    return TriangleDetection.run(e, timeLimitParam);
  }

  /**
  This function checks if a graph is complete
  @param m Number of edges
  @param n Number of vertices
  @return -1 if the graph is not complete, n otherwise (since the chromatic number of a complete graph is n)
  */
  public static int checkIfComplete(int m, int n) {
    if((n * (n-1) / 2) == m) {
      return n;
    }
    else {
      return -1;
    }
  }

  public static int getChromaticNumber(ColEdge[] e, int m, int n) {
    //Initialize the algorithm classes!
    //Get the adjacency matrix and degrees array!
    int[][] res = getAdjacencyMatrix(e, m, n);
    int[] deg = getDegrees(res);
    //Perform a check if the graph is complete
    int completenessCheck = checkIfComplete(m, n);
    if(completenessCheck != -1) {
      System.out.println("This is a complete graph, so its chromatic number is: " + completenessCheck);
      return completenessCheck;
    }
    else {
      System.out.println("The provided graph is not complete, proceeding with the algorithms...");
    }
    //Run the algorithms!
    int upperBound = getTrivialUpperBound(res);
    int lowerBound = getTrivialLowerBound(m);
    System.out.println("Trivial upper bound: " + upperBound);
    System.out.println("Most trivial lower bound is: " + lowerBound);
    //Here you can provide a time limit for triangle detection (third parameter, in seconds)
    int triangleDetectionResult = getLowerBoundsByTriangleDetection(e, 20);
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
    int chromaticNumber = lowerBound;
    if(lowerBound == upperBound) {
      System.out.println("Found the chromatic number! It is " + chromaticNumber);
      System.out.println("PS: Did not need to bruteforce, since the lower and upper bound were the same.");
      return chromaticNumber;
    }
    else {
      System.out.println("Attempting brute force search for " + timeLimit + " seconds...");
      start = System.nanoTime();
      int bfResult = Bruteforce.run(n, m, res, timeLimit, lowerBound, upperBound);
      if(bfResult == -1) {
        System.out.println("Brute forcing took too long :(. Please see the upper and lower bound results instead!");
        return bfResult;
      }
      System.out.println("Result from brute force search: " + bfResult);
      System.out.println("Brute-forcing took " + (System.nanoTime() - start) / 1000000000.0 + " seconds.");
      return bfResult;
    }
  }
}
