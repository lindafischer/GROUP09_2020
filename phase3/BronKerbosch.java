import java.util.ArrayList;
public class BronKerbosch {
  public static ArrayList<Vertex> recordClique = new ArrayList<Vertex>();
  public static ArrayList<Vertex> V;
  public static ArrayList<Vertex> run(ArrayList<Vertex> vertices) {

    ArrayList<Vertex> verticesTmp = (ArrayList<Vertex>) vertices.clone();
    recursivePart(new ArrayList<Vertex>(), verticesTmp, new ArrayList<Vertex>());
    return recordClique;
  }
  public static void recursivePart(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X) {
    if(P.size() == 0 && X.size() == 0) {
      if(R.size() > recordClique.size()) {
        recordClique = R;
      }
    }
    ArrayList<Vertex> P1 = (ArrayList<Vertex>) P.clone();
    ArrayList<Vertex> Q = tomitaPivoting(R, P, X);
    for(int i = 0; i < Q.size(); i++) {
      Vertex v = Q.get(i);
      ArrayList<Vertex> RUnionSetOfV = (ArrayList<Vertex>) R.clone();
      RUnionSetOfV.add(v);
      ArrayList<Vertex> neighboursOfV = v.getNeighbours();
      ArrayList<Vertex> PIntersectNeighboursofV = HelperFunctions.getIntersect(P1, neighboursOfV);
      ArrayList<Vertex> XIntersectNeighboursofV = HelperFunctions.getIntersect(X, neighboursOfV);
      recursivePart(RUnionSetOfV, PIntersectNeighboursofV, XIntersectNeighboursofV);
      //P \ {v}
      P1.remove(v);
      //X UNION {v}
      X.add(v);
    }
  }
  public static ArrayList<Vertex> tomitaPivoting(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X) {
    if(P.size() != 0) {
      ArrayList<Vertex> PUnionX = HelperFunctions.getUnion(P, X);
      Vertex recordVertex = null;
      int record = -1;
      for(int i = 0; i < PUnionX.size(); i++) {
        Vertex q = PUnionX.get(i);
        ArrayList<Vertex> PInterSectNeighboursOfQ = HelperFunctions.getIntersect(P, q.getNeighbours());
        if(PInterSectNeighboursOfQ.size() > record) {
          recordVertex = q;
          record = PInterSectNeighboursOfQ.size();
        }
      }
      return HelperFunctions.getSetMinus(P, recordVertex.getNeighbours());
    }
    else {
      return new ArrayList<Vertex>();
    }
  }

  public static ArrayList<Vertex> revisedTomitaPivotSelection(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X) {
    Vertex q = null;
    int least = Integer.MAX_VALUE;
    for(int i = 0; i < X.size(); i++) {
      int count = Math.min(least, HelperFunctions.getIntersect(P, X.get(i).getConflictingNodes()).size());
      if(count < least) {
        if(count <= 2) {
          if(count == 0 || count == 2) {
            q = X.get(i);
            return conclude(P, q);
          }
          else {
            Vertex w = HelperFunctions.getIntersect(P, X.get(i).getConflictingNodes()).get(0);
            processInPlace(R, P, X, w);
            if(w.getConflictingNodes().contains(q)) {
              return revisedTomitaPivotSelection(R, P, X);
            }
          }
        }
        else {
          q = X.get(i);
          least = count;
        }
      }
    }
    for(Vertex v : P) {
      int count = Math.min(least, HelperFunctions.getUnion(P, v.getConflictingNodes()).size());
      if(count < least) {
        if(count <= 2) {
          if(count == 2) {
            q = v;
            return conclude(P, q);
          }
          else {
            processInPlace(R, P, X, v);
            if(v.getConflictingNodes().contains(q)) {
              return revisedTomitaPivotSelection(R, P, X);
            }
          }
        }
        else {
          q = v;
          least = count;
        }
      }
    }
    return conclude(P, q);
  }

  private static ArrayList<Vertex> conclude(ArrayList<Vertex> P, Vertex q) {
    if(q != null) {
      return HelperFunctions.getIntersect(P, q.getConflictingNodes());
    }
    else {
      return new ArrayList<Vertex>();
    }
  }
  private static void processInPlace(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X, Vertex u) {
    R.add(u);
    P = HelperFunctions.getIntersect(P, u.getNeighbours());
    X = HelperFunctions.getSetMinus(X, u.getConflictingNodes());
  }
}
