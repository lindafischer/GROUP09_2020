public class TriangleDetection {
  /**
  This function is used to run the triangle detection algorithm
  @param edges An array containing the ColEdge objects of the provided graph
  @return The estimated lower bound based on the detection results
  */
  public static int run(ColEdge[] edges) {
    int lowerBounds = 2;
    if (edges.length == 1) {
      lowerBounds = 1;
    } else if (edges.length == 2) {
      lowerBounds = 2;
    } else if (getNumberOfTriangles(edges)>=1) {
      lowerBounds = 3;
    }
    return lowerBounds;
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
    for(int i=0;i<edges.length-1;i++) {
      for(int j=i+1; j<edges.length; j++) {
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

  //NOT SURE WHAT THIS FUNCTION DOES, PLEASE ADD DOCUMENTATION
  public static boolean isInEdges(int u, int v, ColEdge[] edgs) {
    boolean ret = false;
    int i=0;
    while (!ret && i<edgs.length) {
      if ((edgs[i].u==u && edgs[i].v==v) || (edgs[i].v==u && edgs[i].u==v) ) ret=true;
      i++;
    }
    return ret;
  }

  //PLEASE ADD DOCUMENTATION
  public static ColEdge[] bubbleSortEdges(ColEdge[] e) {
    for(int i=0;i<e.length; i++) {
      for(int j=i+1; j<e.length; j++) {
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
}
