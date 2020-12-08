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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OptionsView {
  /**
  This class is used to integrate a view in which you can choose between inserting
  the number of vertices and edges for a graph or uploading a .txt file containing
  information about the graph
  @param someStage The Stage (window) in which the OptionsView should be drawn
  @param backgroundImage The image to be drawn as the background
  @param menu The Scene object for the menu to provide the "back" button functionality
  */
  public OptionsView(Stage someStage, Image backgroundImage, Scene menu) {
    background = backgroundImage;
    menuScene = menu;
    window = someStage;
  }

  /**
  This method is used to set the child (the previously selected Gamemode object)
  in order to later return the gathered information (the user's selections) to
  corresponding Gamemode object
  @param gamemodeChild The previously in the menu selected Gamemode object
  */
  public void setChild(Gamemode gamemodeChild) {
    child = gamemodeChild;
  }

  /**
  This method is used to show the OptionsView
  */
  public void show() {
    Pane root = new Pane();
    root.setPrefSize(1050, 600);
    scene = new Scene(root);
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
    window.setScene(scene);
    back.setOnAction(e -> window.setScene(menuScene));
    //Title
    Text title = new Text(child.getTitle());
    title.setTranslateX(translateX);
    title.setTranslateY(200);
    title.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 60));
    title.setFill(Color.WHITE);
    title.setStrokeWidth(2);
    title.setStroke(Color.BLACK);
    //Insert button
    Image insertImage = new Image("assets\\insert.jpg");
    ImageView insertView = new ImageView(insertImage);
    insertView.setFitWidth(600);
    insertView.setFitHeight(80);
    Button insert = new Button(" ", insertView);
    insert.setLayoutX(230);
    insert.setLayoutY(250);
    insert.setOnAction(e -> insert());
    //Upload button
    Image uploadImage = new Image("assets\\upload.jpg");
    ImageView uploadView = new ImageView(uploadImage);
    uploadView.setFitWidth(325);
    uploadView.setFitHeight(60);
    Button upload = new Button(" ", uploadView);
    upload.setLayoutX(350);
    upload.setLayoutY(380);
    upload.setOnAction(e -> upload());
    root.getChildren().addAll(backGroundView, back, insert, upload, title);
  }

  /**
  This method is used to set the x-position of the Gamemode's title
  @param x The x-position of the Gamemode's title as a double
  */
  public void setTitleX(double x) {
    translateX = x;
  }

  /**
  This method handles presses of the "Insert Graph" button. It creates an InsertView
  object and displays it.
  */
  private void insert() {
    InsertView insertView = new InsertView(window, background, scene, child);
    insertView.show();
  }

  /**
  This method handles presses of the "Upload Graph" button. It displays a file dialog
  and asks the user to open a .txt file. If the user did not choose a file and closed
  the dialog, an alter message will be shown.
  */
  private void upload() {
    Graph g = Uploader.getGraphFromFile();
    if(g == null) {
      Alert warning = new Alert(AlertType.WARNING);
      warning.setTitle("Warning");
      warning.setHeaderText("The graph you provided is either too big or you did not choose a graph!");
      warning.setContentText("Please only provide graphs with 100 nodes or less.");
      warning.showAndWait();
    }
    else {
      child.setGraph(g);
      child.show();
    }
  }
  
  private Stage window;
  private Scene menuScene;
  private Image background;
  private Gamemode child;
  private Scene scene;
  private double translateX = 300;
}
