import javafx.scene.control.Button;

/*The color button is an extension for the button 
* This class is used to provide the Color Button Objects 
* In order to color the nodes 
*/
public class ColorButton extends Button {
  public ColorButton(String c, int xPos, int yPos) {
    super("");
    setLayoutX(xPos);
    setLayoutY(yPos);
    setStyle(color + c + ";");
    stringColor = c;
  }

  public String getColor() {
    return stringColor;
  }
  private String stringColor;
  
  //This is to color the background of the color button
  private String color = "-fx-background-color: ";
}
