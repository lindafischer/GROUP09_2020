import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static javafx.application.Platform.exit;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.YELLOW;

public class ColorButton extends Button
{
  public static int colorX_coords=50;
  public static int colorY_coords=30;
  public static int button_counter=0;
  public ColorButton(String var_name)
  {
    this.var_name=var_name;
  }
  String var_name;
  public void setup(ColorButton color_b)
  {
    if(button_counter==3)
    {
      colorX_coords=50;
      colorY_coords=80;
    }
    color_b.setLayoutX(colorX_coords);
    color_b.setLayoutY(colorY_coords);
    color_b.setStyle("-fx-background-color: "+var_name+";");
    colorX_coords+=50;  
    Gamemode1.style_list[button_counter]=color_b.getStyle();
    Gamemode1.list_holder[button_counter]=color_b.getStyle();
    button_counter++;
  }
}
