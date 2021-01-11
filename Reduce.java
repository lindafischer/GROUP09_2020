import java.util.ArrayList;
import java.util.Collections;
public class Reduce {
  public static ArrayList<Vertex> vertices;
  public static boolean debug;

  public static int[][] run(ArrayList<Vertex> clique, ArrayList<Vertex> vertexList, boolean debugSwitch) {
    vertices = (ArrayList<Vertex>) vertexList.clone();
    debug = debugSwitch;
    removeDisconnectedVertices();
    ArrayList<Vertex> toRemove = new ArrayList<Vertex>();
    ArrayList<Vertex> VMinusK = HelperFunctions.getSetMinus(vertices, clique);
    if(VMinusK.size() != 0) {
      for(Vertex v : VMinusK) {
        for(Vertex w : clique) {
          if(!toRemove.contains(v) && v.getConflictingNodes().contains(w) && HelperFunctions.isSubset(v.getNeighbours(), w.getNeighbours())) {
            toRemove.add(v);
            if(debug) {
              System.out.println("Vertex " + v.getId() + " will be removed");
              System.out.print("Vertex " + v.getId() + "s neighbours: ");
              for(Vertex j : v.getNeighbours()) {
                System.out.print(j.getId() + " ");
              }
              System.out.println();
            }
          }
        }
      }
      Collections.sort(toRemove);
      while(toRemove.size() != 0) {
        Vertex v = toRemove.get(0);
        toRemove.remove(0);
        for(Vertex w : v.getNeighbours()) {
          w.removeNeighbour(v);
        }
        vertices.remove(v);
        Collections.sort(toRemove);
        if(debug) {
          System.out.println("Removing vertex " + v.getId() + "...");
        }
        removeVerticesRecursively(VMinusK, clique.size());
      }
      updateIds();
    }
    return HelperFunctions.getAdjacencyMatrix(vertices);
  }

  private static void removeVerticesRecursively(ArrayList<Vertex> VMinusK, int cliqueSize) {
    if(VMinusK.size() != 0) {
      Vertex v = VMinusK.get(0);
      VMinusK.remove(0);
      if(v.getDegree() < cliqueSize - 1) {
        if(debug) {
          System.out.println("Removing vertex " + v.getId() + "...");
        }
        for(Vertex w : v.getNeighbours()) {
          w.removeNeighbour(v);
        }
        vertices.remove(v);
        removeVerticesRecursively(VMinusK, cliqueSize);
      }
    }
  }
  private static void updateIds() {
    for(int i = 0; i < vertices.size(); i++) {
      vertices.get(i).setId(i);
    }
  }


  public static int[][] removeDisconnectedVertices() {
    ArrayList<Vertex> verticesTmp = (ArrayList<Vertex>) vertices.clone();
    for(Vertex v : verticesTmp) {
      if(v.getDegree() == 0) {
        vertices.remove(v);
      }
    }
    updateIds();
    return HelperFunctions.getAdjacencyMatrix(vertices);
  }
}
