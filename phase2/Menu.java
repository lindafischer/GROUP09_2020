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

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.concurrent.*;

public class Menu extends Application
{
  public static Object[] threads = new Object[2]; //Array of all of the background Threads (bruteforce algorithm and timer)

  /**
   The main method to start the application
   */
  public static void main(String[] args)
  {
    launch(args);
  }

  public void start(Stage theStage) throws Exception {
    Stage window = theStage;

    //Setup main window
    Pane root = new Pane();
    root.setPrefSize(1050, 600);
    Scene menuScene = new Scene(root);
    window.setTitle("Graph Coloring Game");
    window.setScene(menuScene);

    //Set the background
    Image background = new Image("assets\\sfondo1.jpg");
    ImageView backgroundView = new ImageView(background);
    backgroundView.setFitWidth(1050);
    backgroundView.setFitHeight(600);

    //Game title
    Text title = new Text("GRAPH COLORING GAME");
    title.setTranslateX(200);
    title.setTranslateY(150);
    title.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 50));
    title.setFill(Color.WHITE);
    title.setStrokeWidth(2);
    title.setStroke(Color.BLACK);
    root.getChildren().addAll(backgroundView, title);

    //Gamemode 1
    Image image1 = new Image("assets\\gamemode1.jpg");
    ImageView imageView1 = new ImageView(image1);
    imageView1.setFitWidth(300);
    imageView1.setFitHeight(80);
    Button button1 = new Button("", imageView1);
    button1.setLayoutX(20);
    button1.setLayoutY(290);
    button1.setOnAction(e -> new Gamemode(window, menuScene, background, "To the bitter end!").run());

    //Gamemode 2
    Image image2 = new Image("assets\\gamemode2.jpg");
    ImageView imageView2 = new ImageView(image2);
    imageView2.setFitWidth(300);
    imageView2.setFitHeight(80);
    Button button2 = new Button("", imageView2);
    button2.setLayoutX(370);
    button2.setLayoutY(290);
    button2.setOnAction(e -> new Gamemode2(window, menuScene, background, "Against the clock!").run());

    //Gamemode 3
    Image image3 = new Image("assets\\gamemode3.jpg");
    ImageView imageView3 = new ImageView(image3);
    imageView3.setFitWidth(300);
    imageView3.setFitHeight(80);
    Button button3 = new Button("", imageView3);
    button3.setLayoutX(720);
    button3.setLayoutY(290);
    button3.setOnAction(e -> new Gamemode3(window, menuScene, background, "Random order").run());

    //Add the buttons to the scene
    root.getChildren().addAll(button1, button2, button3);

    window.show();
  }

  /**
   This function stops the Bruteforce algorithm and the timer (threads), when the user decides to close the application window.
   This is needed in order to avoid draining the computing power.
   */
  public void stop() {
    //Stop the timer
    if(threads[0] != null) {
      Timer timer = (Timer) threads[1];
      timer.stop();
    }
    //Stop the Bruteforce algorithm
    if(threads[1] != null) {
      Task bf = (Task) threads[0];
      bf.cancel();
    }
    System.exit(0);
  }
}
