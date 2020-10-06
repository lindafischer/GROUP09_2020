import java.util.*;
public class DSatur {
  public static HelperFunctions lib = new HelperFunctions();
  public static int[][] getOrderedVertices(int[] deg) {
    return lib.TwoDSort(deg);
  }
  public static int run(int[] deg, int[][] adj) {

    //Declaration
    int[][] orderedVertices;
    int[] colors = new int[deg.length];
    for(int i = 0; i < colors.length; i++) {
      //Initialize colors as -1 to determine uncolored vertices from colored ones
      colors[i] = -1;
    }
    //Get vertices ordered by degree

    orderedVertices = getOrderedVertices(deg);
    //Select a vertex of maximal degree and colour it with the first colour.

    colors[orderedVertices[orderedVertices.length-1][1]] = 0;
    //Remove it from the uncolored array
    int[] uncoloredVertices = new int[orderedVertices.length-1];
    for(int i = 0; i < uncoloredVertices.length; i++) {
      uncoloredVertices[i] = orderedVertices[i][1];
    }
    System.out.println("Vertices: " + Arrays.toString(uncoloredVertices));
    return recursivePart(colors, uncoloredVertices, adj);
  }

  public static int recursivePart(int[] c, int[] v, int[][] adj) {
    System.out.println(Arrays.toString(c));
    if(v.length == 0) {
      System.out.println(Arrays.toString(c));
      return lib.getMax(c) + 1;
    }
    else{
      int nextVertex = getHighestSaturation(c, adj, v);
      System.out.println(nextVertex);
      boolean[] possibleColors = new boolean[adj.length];
      for(int i = 0; i < possibleColors.length; i++) {
        possibleColors[i] = true;
      }
      for(int i = 0; i < c.length; i++) {
        if(c[i] != -1) {
          if(adj[i][nextVertex] == 1) {
            possibleColors[c[i]] = false;
          }
        }
      }
      int selectedColor = 0;
      for(int i = 0; i < possibleColors.length; i++) {
        if(possibleColors[i]) {
          selectedColor = i;
          break;
        }
      }
      c[nextVertex] = selectedColor;
      ArrayList<Integer> vertices = new ArrayList<>();
      for(int i = 0; i < v.length; i++) {
        vertices.add(v[i]);
      }
      for(int i = 0; i < v.length; i++) {
        if(v[i] == nextVertex) {
          vertices.remove(i);
          break;
        }
      }
      int[] newV = new int[vertices.size()];
      for(int i = 0; i < vertices.size(); i++) {
        newV[i] = vertices.get(i);
      }
      return recursivePart(c, newV, adj);
    }
  }

  public static int getHighestSaturation(int[] c, int[][] adj, int[] v) {
    ArrayList<Integer> AdjacentNodes = new ArrayList<>();
    int[] SatScores = new int[adj.length];
    for(int i = 0; i < SatScores.length; i++) {
      SatScores[i] = -1;
    }
    for(int i = 0; i < v.length; i++) {
      AdjacentNodes.clear();
      int SatScore = 0;
      for(int j = 0; j < adj[0].length; j++) {
        if(adj[v[i]][j] == 1) {
          AdjacentNodes.add(j);
        }
      }
      for(int j = 0; j < AdjacentNodes.size(); j++) {
        if(c[AdjacentNodes.get(j)] != -1) {
          SatScore++;
        }
      }
      SatScores[v[i]] = SatScore;
      boolean debug = true;
      if(debug) {
        System.out.println("Node: " + v[i]);
        System.out.println("Score " + SatScores[v[i]]);
        System.out.println(AdjacentNodes);
      }
    }
    System.out.println(Arrays.toString(SatScores));
    return lib.getMaxIndex(SatScores);
  }
}
