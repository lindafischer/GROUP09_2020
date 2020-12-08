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
import java.util.*;
import javafx.concurrent.*;

public class Gamemode3 extends Gamemode {
  /**
  This class extends the Gamemode class and is used to integrate the third Gamemode
  (Coloring in a random order)
  */
  public Gamemode3(Stage someStage, Scene menu, Image background, String title) {
    super(someStage, menu, background, title);
  }

  /**
  This method is used to show Gamemode3's window. It first calls the super's show method
  and then adds the things that make this gamemode different: It creates a random
  order in which the nodes should be colored using the Fisher-Yates shuffling algorithm
  and disables all nodes except the first node in the random order. The enabling of the
  next nodes is handled in the overriden setNodeColor method (see below).
  */
  public void show() {
    super.show();
    order = new int[nodes.length];
    for(int i = 0; i < order.length; i++) {
      order[i] = Integer.parseInt(nodes[i].getText());
    }
    Random rand = new Random();
    for(int i = 0; i < order.length; i++) {
      int randomInt = rand.nextInt(order.length - i) + i;
      int tmp = order[i];
      order[i] = order[randomInt];
      order[randomInt] = tmp;
    }
    for(int i = 0; i < nodes.length; i++) {
      if(Integer.parseInt(nodes[i].getText()) != order[nodeIterator]) {
        nodes[i].setDisable(true);
      }
      else {
        nodes[i].setDisable(false);
        nodes[i].setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2; -fx-background-color: white;");
        selectedNode = nodes[i];
      }
    }

    Text orderNote = new Text("Please color the selected node!");
    orderNote.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 24));
    orderNote.setLayoutX(300);
    orderNote.setLayoutY(100);
    gameRoot.getChildren().add(orderNote);
  }

  /**
  This method is used to set the color of the currently selected node and select the next
  node in the random order initialized at the start of this gamemode.
  @param c A String containing the CSS color value to color the currently selected node in
  @param node The currently selected node to be colored
  */
  @Override
  public void setNodeColor(String c, Node node) {
    super.setNodeColor(c, node);
    if(!node.getColor().equals("white")) {
      nodeIterator++;
      //Limit the nodeIterator to prevent ArrayOutOfBounds Exceptions
      if(nodeIterator >= nodes.length) {
        nodeIterator--;
        nodes[order[nodeIterator]].setDisable(true);
      }
      else {
        for(int i = 0; i < nodes.length; i++) {
          if(Integer.parseInt(nodes[i].getText()) != order[nodeIterator]) {
            nodes[i].setDisable(true);
          }
          else {
            nodes[i].setDisable(false);
            nodes[i].setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2; -fx-background-color: white;");
          }
        }
      }
    }
    //Select the next node
    selectedNode = nodes[order[nodeIterator]];
    //reset the edges
    int[][] adj = g.getAdj();
    for(int i = 0; i < adj.length; i++) {
      for(int j = i+1; j < adj[i].length; j++) {
        if(adj[i][j] == 1) {
          edges[i][j].setStrokeWidth(1);
          edges[i][j].setStyle("-fx-stroke: black;");
          edges[j][i].setStrokeWidth(1);
          edges[j][i].setStyle("-fx-stroke: black;");
        }
      }
    }
    //And highlight the currently edges adjacent to the currently selected node
    int k = Integer.parseInt(selectedNode.getText());
    for(int j = 0; j < edges[k].length; j++) {
      if(adj[k][j] == 1) {
        edges[k][j].setStrokeWidth(4);
        edges[j][k].setStrokeWidth(4);
        if(!nodes[j].getColor().equals("white")) {
          edges[k][j].setStyle("-fx-stroke: " + nodes[j].getColor() + ";");
          edges[j][k].setStyle("-fx-stroke: " + nodes[j].getColor() + ";");
        }
      }
    }
  }

  /**
  This method is used to handle submissions initiated by pressing the submit button.
  It works almost like the superclass' submit method, with the following exception:
  If the user used more colors than actually needed, a WinScreen with the title resembling
  a Game Over screen will be shown. This is because if the user uses more colors than needed,
  they will not be able to undo any mistakes, so the game is over.
  */
  @Override
  public void submit() {
    if(checkCompletion()) {
      if(task != null && !task.isCancelled() && chromaticNumber == 0) {
        task.cancel();
        Menu.threads[0] = null;
        WinScreen win = new WinScreen(window, background, menuScene, "Congratulations!\nYou beat our bruteforcing algorithm!\n Our lower bound: " + boundArray[0] + "\nOur upper bound: " + boundArray[1] +"\n\n\n\n\n\n\n");
        win.show();
      }
      int colorCount = countUserColors();
      if(colorCount == chromaticNumber) {
        WinScreen win = new WinScreen(window, background, menuScene, "You found the chromatic number!\n Well done!\n\n\n\n\n\n\n");
        win.show();
      }
      else if(colorCount > chromaticNumber){
        WinScreen win = new WinScreen(window, background, menuScene, "Game Over!\nYou did not find the chromatic number!\nActual chromatic number: " + chromaticNumber + "\nYou used " + colorCount + " colors.\n\n\n\n\n\n\n");
        win.show();
      }
    }
    else{
      if(!gameRoot.getChildren().contains(noteCompletion)) {
        gameRoot.getChildren().add(noteCompletion);
      }
    }
  }

  private int[] order;
  private int nodeIterator;
}
