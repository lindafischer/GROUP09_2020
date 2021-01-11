import javafx.scene.control.Button;

public class ColorButton extends Button {
  /*
  * The ColorButton extends the JavaFX Button class and 
  * is used to provide the ColorButton objects grouped in the ColorPalette
  * in order to color the nodes
  * @param c A String containing the Buttons color as a CSS color value
  * @param xPos The x-position to draw the ColorButton at
  * @param yPos The y-position to draw the ColorButton at
  */
  public ColorButton(String c, int xPos, int yPos) {
    super("");
    setLayoutX(xPos);
    setLayoutY(yPos);
    setStyle(color + c + ";");
    stringColor = c;
  }
  
  /**
  * This method is used to get the ColorButton's color
  * @return A String containing the ColorButton's color as a CSS color value
  */
  public String getColor() {
    return stringColor;
  }
  
  private String stringColor;
  //This is to color the background of the color button (the CSS color value will be appended to this)
  private String color = "-fx-background-color: ";
}
