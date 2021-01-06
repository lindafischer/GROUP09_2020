import java.util.ArrayList;
public class Vertex {
  int id;
  public ArrayList<Vertex> neighbours;
  public ArrayList<Vertex> conflictingNodes;
  public static Vertex recordHolder = null;
  public Vertex(int id) {
    this.id = id;
    if(recordHolder == null) {
      recordHolder = this;
    }
  }
  public ArrayList<Vertex> getNeighbours() {
    return neighbours;
  }
  public void setNeighbours(ArrayList<Vertex> neighbourList) {
    neighbours = neighbourList;
    if(neighbours.size() > recordHolder.getNeighbours().size()) {
      recordHolder = this;
    }
  }
  public void setConflictingNodes(ArrayList<Vertex> conflictingNodesList) {
    conflictingNodes = conflictingNodesList;
  }
   public ArrayList<Vertex> getConflictingNodes() {
     return conflictingNodes;
   }
  public Vertex getRecordHolder() {
    return recordHolder;
  }
}
