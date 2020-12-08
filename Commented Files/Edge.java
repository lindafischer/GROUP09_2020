import javafx.scene.shape.Line;
public class Edge extends Line {
  /**
  This class is used to just represent the edges between to nodes.
  It basically is like the Line class and is used to make the code
  easier to understand.
  @param x1 The Edge's starting x-position
  @param y1 The Edge's starting y-position
  @param x2 The Edge's ending x-position
  @param y2 The Edge's ending y-position
  */
  public Edge(double x1, double y1, double x2, double y2) {
    super(x1, y1, x2, y2);
  }
}
