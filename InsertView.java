import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class InsertView {

  /**
   This is the constructor of the InsertView window
   @param someStage The stage of the InsertView window from the Menu
   @param backgroundImage The background of the InsertView window from the Menu
   @param options The scene of the OptionView file to make the "back" button work
   @param gmChild The Game Mode to which this scene will be called from
   */
  public InsertView(Stage someStage, Image backgroundImage, Scene options, Gamemode gmChild) {
    window = someStage;
    background = backgroundImage;
    optionsScene = options;
    gamemodeChild = gmChild;
  }

  /**
   This function is used to show this Window by setting visibility to true
   */
  public void show() {
    //Setting screen
    root = new Pane();
    root.setPrefSize(1050, 600);
    Scene scene = new Scene(root);
    window.setScene(scene);
    ImageView backGroundView = new ImageView(background);
    backGroundView.setFitWidth(1050);
    backGroundView.setFitHeight(600);

    //Back button
    Image imageback = new Image("assets\\back.jpg");
    ImageView viewback = new ImageView(imageback);
    viewback.setFitWidth(150);
    viewback.setFitHeight(80);
    Button back = new Button("", viewback);
    back.setLayoutX(800);
    back.setLayoutY(15);
    back.setOnAction(e -> window.setScene(optionsScene));
    
    //Vertices and Edges input Text fields
    v_textField = new TextField();
    v_textField.setText("...");
    v_textField = new TextField();
    v_textField.setText("");
    e_textField = new TextField();
    e_textField.setText("...");
    e_textField = new TextField();
    e_textField.setText("");

    //Vertices and Edges input Text
    Text v_text = new Text("Input number of vertices: ");
    Text e_text = new Text("Input number of edges: ");

    //Submit button
    Button v_button = new Button("Submit");

    //Note to provide sensible input
    senseNote = new Text("Please input a configuration that makes sense!\n(Not 1 node and 1 vertex or 2 nodes and 2 vertices,\nnot 0 vertices,...)\nand at most 100 nodes. Thank you!");
    senseNote.setLayoutX(scene.getWidth()/2);
    senseNote.setLayoutY(scene.getHeight()/2);
    senseNote.setStrokeWidth(4);

    //Positioning the items
    setCoords(v_textField,v_button,v_text,e_textField,e_text);

    //Handling the submit button
    v_button.setOnAction(e -> parseInput());

    //Adding all items to the root
    root.getChildren().addAll(backGroundView,v_textField,v_button,v_text, back);
    root.getChildren().addAll(e_text,e_textField);
  }

  /**
   This function is used to set the coordinates of all the items of the Window
   @param v_textField A TextField that asks for the number of vertices
   @param v_button The Button that checks if the input is sensible and if it is, it creates the graph
   @param v_text The Text that asks for the number of vertices
   @param e_textField The TextField that asks for the number of edges
   @param e_text The Text that asks for the number of edges
   */
  public void setCoords(TextField v_textField,Button v_button,Text v_text, TextField e_textField,Text e_text) {
    v_textField.setLayoutX(100);
    v_textField.setLayoutY(100);
    v_button.setLayoutX(270);
    v_button.setLayoutY(150);
    v_text.setLayoutX(100);
    v_text.setLayoutY(90);
    e_textField.setLayoutX(100);
    e_textField.setLayoutY(200);
    e_text.setLayoutX(100);
    e_text.setLayoutY(190);
  }

  /**
   This function checks if the input vertices and edges can form a graph; if they do, it creates the graph
   */
  public void parseInput() {
    int n = -1; //number of vertices
    int m = -1; //number of edges

    //Note to provide positive integers
    Text validityNote = new Text("Please input only positive integers");
    validityNote.setLayoutX(1050/2);
    validityNote.setLayoutY(600/2 + 70);
    validityNote.setStrokeWidth(4);

    //If the input is not an integer, it gives a note to the user
    try{
      n = Integer.parseInt(v_textField.getText());
      m = Integer.parseInt(e_textField.getText());
    }
    catch(Exception e) {
      if(!root.getChildren().contains(validityNote)) {
        root.getChildren().add(validityNote);
      }
    }
    //If the input cannot form a graph, it gives a note to the user
    if(((n == 1 || n == 2) && (n <= m)) || (n == 0) || ((n * (n-1) / 2) < m) || (n < 0 || m < 0) || (n > 100)) {
      if(!root.getChildren().contains(senseNote)) {
        root.getChildren().add(senseNote);
      }
    }
    //If the input is correct, it creates the graph
    else {
      gamemodeChild.createGraph(n, m);
      gamemodeChild.show();
    }
  }

  //Instance Fields
  private Stage window;
  private Image background;
  private Scene optionsScene;
  private Gamemode gamemodeChild;
  private TextField e_textField;
  private TextField v_textField;
  private Text senseNote;
  private Pane root;
}
