
import javafx.scene.control.Button;


 class ColorButton extends Button {

    public ColorButton(String var_name)
    {
        this.var_name=var_name;
    }


    String var_name;
    int x_coords=50;
    int y_coords=30;
    int button_counter=0;

    public Button setup(ColorButton color_b)

    {
        color_b.setLayoutX(x_coords);
        color_b.setLayoutY(y_coords);
        color_b.setStyle("-fx-background-color: "+var_name+";");

        return color_b;


    }


}
