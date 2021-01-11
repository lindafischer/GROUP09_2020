import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
public class RLF {
  public static int run(ArrayList<Vertex> vertices) {
    ArrayList<Vertex> G = (ArrayList<Vertex>) vertices.clone();

    int k = 0;
    while(G.size() != 0) {
      k++;
      ArrayList<Integer> orderedSet = chooseVertex(G, G);
      Vertex v = getVertexById(G, orderedSet.get(orderedSet.size()-1));

      ArrayList<Vertex> C = constructColorClass(G, v);
      G = HelperFunctions.getSetMinus(G, C);
    }
    return k;
  }

  private static ArrayList<Integer> chooseVertex(ArrayList<Vertex> U, ArrayList<Vertex> W) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    int[][] relativeDegrees = new int[U.size()][2];
    for(int i = 0; i < U.size(); i++) {
      relativeDegrees[i][0] = countNeighboursIn(W, U.get(i));
      relativeDegrees[i][1] = U.get(i).getId();
    }
    HelperFunctions.recursiveTwoDSortHelper(relativeDegrees);
    for(int i = 0; i < relativeDegrees.length; i++) {
      result.add(relativeDegrees[i][1]);
    }
    return result;
  }

  private static ArrayList<Vertex> constructColorClass(ArrayList<Vertex> U, Vertex v) {
    ArrayList<Vertex> W = (ArrayList<Vertex>) v.getNeighbours().clone();
    ArrayList<Vertex> C = new ArrayList<Vertex>();
    C.add(v);
    U.remove(v);
    U = HelperFunctions.getSetMinus(U, W);
    while(U.size() != 0) {
      ArrayList<Integer> orderedSet = chooseVertex(U, W);
      Vertex u = null;
      if(U.size() > 1) {
        if(orderedSet.get(orderedSet.size()-1) == orderedSet.get(orderedSet.size()-2)) {
          orderedSet = chooseVertex(U, U);
          u = getVertexById(U, orderedSet.get(0));
        }
      }
      if(u == null) {
        u = getVertexById(U, orderedSet.get(orderedSet.size()-1));
      }
      C.add(u);
      ArrayList<Vertex> neighboursOfU = u.getNeighbours();
      for(Vertex j : neighboursOfU) {
        if(U.contains(j)) {
          W.add(j);
        }
      }
      U.remove(u);
      U = HelperFunctions.getSetMinus(U, W);
    }
    return C;
  }

  private static int countNeighboursIn(ArrayList<Vertex> W, Vertex v) {
    int result = 0;
    for(Vertex u : W) {
      if(u.getNeighbours().contains(v)) {
        result++;
      }
    }
    return result;
  }
  private static Vertex getVertexById(ArrayList<Vertex> set, int id) {
    for(Vertex v : set) {
      if(v.getId() == id) {
        return v;
      }
    }
    return null;
  }
}
