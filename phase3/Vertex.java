import java.util.ArrayList;
public class Vertex implements Comparable{
  int id;
  public ArrayList<Vertex> neighbours;
  public ArrayList<Vertex> conflictingNodes;
  public static Vertex recordHolder = null;
  public int degree;
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
    degree = neighbours.size();
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
  public int getDegree() {
    return degree;
  }
  public void removeNeighbour(Vertex v) {
    neighbours.remove(v);
    degree--;
  }
  public int getId() {
    return id;
  }
  public void setId(int newId) {
    id = newId;
  }
  @Override
  public int compareTo(Object v) {
    int vDegree = ((Vertex) v).getDegree();
    return degree-vDegree;
  }
}
