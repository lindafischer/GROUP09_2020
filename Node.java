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
import javafx.scene.shape.*;
import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Node extends Button {
  /**
  This class is used to integrate colorable nodes
  @param title <i>String</i> The String containing te text to be displayed on the node (e.g. The node's number)
  @param buttonShape A Shape object to give the node its circular shape
  */
  public Node(String title, Shape buttonShape) {
    super(title);
    setPrefWidth(30);
    setPrefHeight(30);
    setShape(buttonShape);
    setStyle("-fx-background-color: " + colorVar + ";");
  }

  /**
  This method is used to set the node's color
  @param c A String containing the CSS color value to color the node in
  */
  public void setColor(String c) {
    colorVar = c;
    String tmpColor = color + c + "; ";
    setStyle(tmpColor);
  }

  /**
  This method is used to set the node's position
  @param x <i>double</i> The node's x-position
  @param y <i>double</i> The node's y-position
  */
  public void setPosition(double x, double y) {
    super.setLayoutX(x);
    super.setLayoutY(y);
    xPos = x;
    yPos = y;
    Bounds bounds = getBoundsInParent();
  }

  /**
  This method is used to get the node's x-position
  @return <i>double</i> The node's x-position as a double value
  */
  public double getX() {
    return xPos + 30/2;
  }

  /**
  This method is used to get the node's y-position
  @return <i>double</i> The node's y-position as a double value
  */
  public double getY() {
    return yPos + 30/2;
  }

  /**
  This method is used to get the node's current color
  @return <i>String</i> The node's color as a CSS color value
  */
  public String getColor() {
    return colorVar;
  }
  
  private String color = "-fx-background-color: ";
  private double xPos;
  private double yPos;
  private String colorVar = "white";
}
