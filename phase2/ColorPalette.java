public class ColorPalette {
  /**
  This class is used to integrate many ColorButton objects into a "palette" to display
  them with relative positioning to each other
  @param colors A String array containing the CSS color codes for all possible colors
  */
  public ColorPalette(String[] colors) {
    width = 100;
    height = 100;
    possibleColors = colors;
    buttons = new ColorButton[3];
    rowNumber = 1;
    colNumber = 3;
    //Draw three colors in the first row
    for(int i = 0; i < 3; i++) {
      buttons[i] = new ColorButton(colors[i], (i * 40) + 30, 30);
    }
  }

  /**
  This method is used to add a new color from the possibleColors array if not all
  colors are displayed yet.
  */
  public void addColor() {
    if(buttons.length < possibleColors.length) {
      ColorButton[] tmpButtons = buttons;
      buttons = new ColorButton[tmpButtons.length+1];
      for(int i = 0; i < tmpButtons.length; i++) {
        buttons[i] = tmpButtons[i];
      }
      if(iterator%4 == 0) {
        rowNumber++;
        colNumber = 0;
      }
      buttons[iterator] = new ColorButton(possibleColors[iterator], (colNumber * 40) + 30, rowNumber*30);
      colNumber++;
      iterator++;
    }
  }

  /**
  This method is used to get all current ColorButton objects. This is needed to
  add the event handlers in the Gamemode.java
  @return A ColorButton array containing all currently displayed ColorButtons
  */
  public ColorButton[] getButtons() {
    return buttons;
  }

  private int width;
  private int height;
  private String[] possibleColors;
  private ColorButton[] buttons;
  private int iterator = 3;
  private int rowNumber;
  private int colNumber;
}
