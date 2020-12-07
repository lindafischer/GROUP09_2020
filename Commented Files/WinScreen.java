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

public class WinScreen {
  public WinScreen(Stage someStage, Image backgroundImage, Scene menu, String text) {
    window = someStage;
    background = backgroundImage;
    menuScene = menu;
    width = 70;
    height = 40;
    textContent = text;
  }

  /***
   * This function displays the scene after
   * succesful completion of the graph
   * also adds home button as option
   * to return to home screen
   */
  public void show() {
    StackPane root = new StackPane();
    root.setPrefSize(1050, 600);
    Scene scene = new Scene(root);
    window.setScene(scene);
    ImageView backGroundView = new ImageView(background);
    backGroundView.setFitWidth(1050);
    backGroundView.setFitHeight(600);
    //Win Text
    Text winText = new Text(textContent);
    winText.setTextAlignment(TextAlignment.CENTER);
    winText.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 32));
    winText.setFill(Color.WHITE);
    winText.setStrokeWidth(2);
    winText.setStroke(Color.BLACK);
    //Home button
    Image homeImage = new Image("assets\\home.jpg");
    ImageView homeView = new ImageView(homeImage);
    homeView.setFitWidth(200);
    homeView.setFitHeight(80);
    Button homeButton = new Button("", homeView);
    homeButton.setPrefWidth(width);
    homeButton.setPrefHeight(height);
    double actualX = (scene.getWidth()/2) - width/2;
    double actualY = (scene.getHeight() - 100);
    homeButton.setLayoutX(actualX);
    homeButton.setLayoutY(actualY);
    homeButton.setOnAction(e -> window.setScene(menuScene));
    root.getChildren().addAll(backGroundView, winText, homeButton);
  }

  private Stage window;
  private Image background;
  private Scene menuScene;
  private double width;
  private double height;
  private String textContent;
}
