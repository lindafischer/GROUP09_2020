public class TriangleDetection {
  //For time measuring
  public static long start;
  public static int timeLimit;
  /**
  This function is used to run the triangle detection algorithm
  @param edges An array containing the ColEdge objects of the provided graph
  @param timeLimitParam The time limit in whole seconds which, if exceeded, will cause the algorithm to abort
  @return The estimated lower bound based on the detection results
  */
  public static int run(ColEdge[] edges, int timeLimitParam) {
    timeLimit = timeLimitParam;
    start = System.nanoTime();
    int lowerBounds = 2;
    if (edges.length == 0) {
      lowerBounds = 1;
    } else if (edges.length == 2) {
      lowerBounds = 2;
    }
    int triangleResult = getNumberOfTriangles(edges);
    if (triangleResult>=1) {
      lowerBounds = 3;
    }
    else if(triangleResult == -1) {
      lowerBounds = -1;
    }
    return lowerBounds;
  }

  public static void resetTimer () {
    start = System.nanoTime();
    timeLimit = 100;
  }

  /**
  This function counts the number of triangles in a given graph
  @param edg An array containing the ColEdge objects of the provided graph
  @return The number of triangles
  */
  public static int getNumberOfTriangles(ColEdge[] edg) {
    int counter=0;
    ColEdge[] edges = new ColEdge[edg.length];
    edges = bubbleSortEdges(edg);
    if(edges.length == 0) {
      return -1;
    }
    for(int i=0;i<edges.length-1;i++) {
      for(int j=i+1; j<edges.length; j++) {
        double duration = (System.nanoTime() - start) / 1000000000.0;
        if(duration > timeLimit) {
          return -1;
        }
        ColEdge e1 = edges[i];
        ColEdge e2 = edges[j];
        if (e1.u == e2.u) {
          if (isInEdges(e1.v, e2.v, edges)) {
            counter++;
          }
        }
      }
    }
    return counter;
  }

  /**
  This function always compares two vertices with eachother. If two vertices have the same starting vertix, the function will
  check whether the two end vertices are also connected. If this is the case then you have a triangle.
  @param u part
  @param v
  @param edgs An array containing the ColEdge objects of the provided graph
  @return true if two vertices with the same starting point also are connected, false if otherwise
  */
  public static boolean isInEdges(int u, int v, ColEdge[] edgs) {
    boolean ret = false;
    int i=0;
    while (!ret && i<edgs.length) {
      if ((edgs[i].u==u && edgs[i].v==v) || (edgs[i].v==u && edgs[i].u==v) ) ret=true;
      i++;
    }
    return ret;
  }

  /**
  This function is comparing an arry of edges, by always comparing two edges with eachother. In an ascending order they will be
  sorted, so this serves later when the funtion isInEdges searches the connection between the two end vertices that were connected with one
  starting vertix
  @param e An array containing the ColEdge objects of the provided graph
  @return sorted <i>ColEdge</i> array
  */
  public static ColEdge[] bubbleSortEdges(ColEdge[] e) {
    for(int i=0;i<e.length; i++) {
      for(int j=i+1; j<e.length; j++) {
        double duration = (System.nanoTime() - start) / 1000000000.0;
        if(duration > timeLimit) {
          return new ColEdge[0];
        }
        if (e[i].u>e[j].u) {
          ColEdge tmpE = new ColEdge();
          tmpE.u = e[i].u;
          tmpE.v = e[i].v;
          e[i].v = e[j].v;
          e[i].u = e[j].u;
          e[j].u = tmpE.u;
          e[j].v = tmpE.v;
        }
        else if (e[i].u==e[j].u) {
          if (e[i].v>e[j].v) {
            ColEdge tmpE = new ColEdge();
            tmpE.u = e[i].u;
            tmpE.v = e[i].v;
            e[i].v = e[j].v;
            e[i].u = e[j].u;
            e[j].u = tmpE.u;
            e[j].v = tmpE.v;
          }
        }
      }
    }
    return e;
  }

  /**
  This method is used to get the nodes that are part of a triangle. This is used
  for the triangle hint.
  @param edg The array of ColEdge objects representing the graph
  @return A 2-dimension integer array containing the different triangles and their contributing
  nodes
  */
  public static int[][] getNodesOfTriangles(ColEdge[] edg) {
    int numberOfTriangles = getNumberOfTriangles(edg);
    int[][] triangleNodes = new int[0][0];
    if (numberOfTriangles != -1) {
      triangleNodes = new int[numberOfTriangles][3];
      int counter = 0;
      ColEdge[] edges = new ColEdge[edg.length];
      edges = bubbleSortEdges(flipFirstToLow(edg));
      for (int i = 0; i < edges.length - 1; i++) {
        for (int j = i + 1; j < edges.length; j++) {
          ColEdge e1 = edges[i];
          ColEdge e2 = edges[j];
          if (e1.u == e2.u) {
            if (isInEdges(e1.v, e2.v, edges)) {
              triangleNodes[counter][0] = e1.u;
              triangleNodes[counter][1] = e1.v;
              triangleNodes[counter][2] = e2.v;
              counter++;
            }
          }
        }
      }
    }
    return triangleNodes;
  }

  /**
  This method is used to flip the elements in the given graph's ColEdge array
  @param e The given graph's ColEdge array
  @return The ColEdge array with flipped entries
  */
  public static ColEdge[] flipFirstToLow(ColEdge[] e) {
    for(int i = 0; i<e.length; i++) {
      if (e[i].v<e[i].u) {
        int tmp = e[i].u;
        e[i].u = e[i].v;
        e[i].v = tmp;
      }
    }
    return e;
  }
}
