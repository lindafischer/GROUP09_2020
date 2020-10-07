import java.util.*;
public class Backtracking {
  public static HelperFunctions lib = new HelperFunctions();
  public static long startTime;
  public static int timeLimit;
  public static int[][] adj_matrix;

  /**
  This function is the main function to run the backtracking algorithm
  @param adj The adjacency matrix
  @param timeLimitParam The time limit to attempt backtracking for
  */
  public static int run (int[][] adj, int timeLimitParam) {
    timeLimit = timeLimitParam;
    adj_matrix = adj;
    startTime = System.nanoTime();
    int [] colors = new int [adj.length];
    colors[0] = 1;
    int k = 1; // k = current index/vertix to test
    int ChromaticNumber = GraphColoring(k, colors);
    return ChromaticNumber;
  }

  /**
  This function tires to color the graph recursively using backtracking
  @param k The index of the currently looked-upon vertex
  @param colors An array containing the colorings of each vertex
  @return The estimated chromatic number
  */
  public static int GraphColoring(int k, int colors[]) { //maybe a boolean would be better?
    double elapsedTime = (System.nanoTime() - startTime) / 1000000000.0;
    if(elapsedTime > timeLimit) {
      return -1;
    }
    for (int c = 1; c <= adj_matrix.length; c++) {  // c = current color to test
      if (isValid(k, colors, c)) { // check if the color can be used
        colors[k] = c; // assign the color to the index

        if ((k+1) < adj_matrix.length) { //check if the next index is less than the number of vertices
          return GraphColoring(k+1, colors); // recursive call for the next index
        } else { // if all the vertices have been assigned to a color
          return lib.getMax(colors); // then get the smallest number from the array of the colors
        }
      }
    }
    return 0;
  }

  /**
  This function checks whether a color is valid for the given vertex
  @param adj_matrix The adjacency matrix
  @param k The currently looked-upon vertex
  @param colors An array containing the coloring of each vertex
  @param c The color that needs to be checked
  @return true if the color is possible, false if not
  */
  public static boolean isValid (int k, int colors[], int c) {
    for (int i = 0; i < adj_matrix.length; i++) {
      if (adj_matrix[k][i] == 1 && c == colors[i]) { // check if there is an edge between the vertices && if the color was already assigned to the vertix nearby
        return false;
      }
    }
    return true;
  }
}
