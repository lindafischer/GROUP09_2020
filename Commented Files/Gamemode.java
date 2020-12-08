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

public class Gamemode {
  /**
  This class is used for the integration of Gamemode 1 and is also used as a
  super class for gamemodes 2 and 3
  @param someStage The current Stage object (the window)
  @param menu The menu's Scene (this is used to integrate the "back" button)
  @param backGroundImage The image to be drawn in the views before the actual
  (see OptionsView, InsertView and WinScreen)
  @param title The String containing the gamemode's title
  */
  public Gamemode(Stage someStage, Scene menu, Image backGroundImage, String title) {
    window = someStage;
    menuScene = menu;
    background = backGroundImage;
    gameTitle = title;
    options = new OptionsView(window, background, menuScene);
  }

  /**
  This method is used to run the Gamemode (calls OptionsView -> InsertView/Uploader -> Gamemode.show())
  */
  public void run() {
    options.setChild(this);
    options.show();
  }

  /**
  This method is used to set the graph for this gamemode and compute its chromatic number
  @param someGraph The Graph object of the graph to be colored
  */
  public void setGraph(Graph someGraph) {
    g = someGraph;
    computeChromaticNumber();
  }

  /**
  This method is used to create a new Graph from the inputs received from the InsertView
  and compute its chromatic number
  @param n The number of vertices in the graph
  @param m The number of edges in the graph
  */
  public void createGraph(int n, int m) {
    g = new Graph(n, m);
    computeChromaticNumber();
  }

  /**
  This method is used to compute the chromatic number of the given graph
  */
  public void computeChromaticNumber() {
    boundArray = GraphSolver.run(g.getAdj(), g.getVertexNumber(), g.getEdgeNumber(), g.getColEdgeArray());
    if(boundArray[0] == boundArray[1] && boundArray[0] != 0) {
      System.out.println("Found the chromatic number! It is " + boundArray[0]);
      System.out.println("PS: Did not need to bruteforce, since the lower and upper bound were the same.");
      chromaticNumber = boundArray[0];
    }
    else if(boundArray.length == 3) {
      chromaticNumber = boundArray[2];
      boundArray[0] = chromaticNumber;
      boundArray[1] = chromaticNumber;
    }
    else {
      task = Bruteforce.getTask(g.getVertexNumber(), g.getEdgeNumber(), g.getAdj(), boundArray[0], boundArray[1]);
      new Thread(task).start();
      Menu.threads[0] = task;
      task.setOnSucceeded(e -> {chromaticNumber = ((Integer) task.getValue()).intValue();});
    }
  }

  /**
  This method is used to show the graph and is called from either the InsertView or the Uploader
  after the graph has been set
  */
  public void show() {
    gameRoot = new Pane();
    gameRoot.setPrefSize(1050, 600);
    gameScene = new Scene(gameRoot);
    gameRoot.setStyle("-fx-background-color: beige;");
    window.setScene(gameScene);
    helperButtons();
    Text hints = new Text("Hints:");
    hints.setLayoutY(570);
    hints.setLayoutX(50);
    hints.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 20));
    gameRoot.getChildren().addAll(hints);
    invalidColor = new Text("Cannot use that color, please pick another one!");
    invalidColor.setLayoutX(gameScene.getWidth() - 280);
    invalidColor.setLayoutY(gameScene.getHeight() - 500);
    noteCompletion = new Text("Please color all vertices first!");
    noteCompletion.setStrokeWidth(4);
    noteCompletion.setLayoutX(gameScene.getWidth() - 250);
    noteCompletion.setLayoutY(gameScene.getHeight() - 520);
    tooManyColorsNote = new Text("This graph can be colored in less colors!");
    tooManyColorsNote.setFill(Color.RED);
    tooManyColorsNote.setLayoutX(gameScene.getWidth() - 250);
    tooManyColorsNote.setLayoutY(gameScene.getHeight() - 500);
    drawGraph();
  }

  /**
  This method is used to draw the gamemode's graph
  */
  public void drawGraph() {
    Random rand = new Random();
    int[][] adj = g.getAdj();
    nodes = new Node[adj.length];
    int m = g.getEdgeNumber();
    int nodeCount = 0;
    Ellipse buttonShape = new Ellipse(30, 30);
    while(nodeCount < adj.length) {
      nodes[nodeCount] = new Node("" + nodeCount, buttonShape);
      double x = 140 + ((gameScene.getWidth()-140) - 140) * rand.nextDouble();
      double y = 140 + ((gameScene.getHeight()-140) - 140) * rand.nextDouble();
      if(checkOverlap(x, y, nodeCount)) {
        nodes[nodeCount].setPosition(x, y);
        nodeCount++;
      }
    }
    edges = new Edge[nodes.length][nodes.length];
    for(int i = 0; i < adj.length; i++) {
      double startX = nodes[i].getX();
      double startY = nodes[i].getY();
      for(int j = i+1; j < adj[i].length; j++) {
        if(adj[i][j] == 1) {
          double endX = nodes[j].getX();
          double endY = nodes[j].getY();
          edges[i][j] = new Edge(startX, startY, endX, endY);
          edges[j][i] = new Edge(startX, startY, endX, endY);
        }
      }
    }
    for(int i = 0; i < edges.length; i++) {
      for(int j = i+1; j < edges[i].length; j++) {
        if(adj[i][j] == 1) {
          gameRoot.getChildren().addAll(edges[i][j]);
        }
      }
    }
    for(int i = 0; i < nodes.length; i++) {
      nodes[i].setOnAction(e -> handleColoring(e.getSource()));
      gameRoot.getChildren().addAll(nodes[i]);
    }
  }

  /**
  This method is used to handle the coloring of the nodes (aka the button presses
  originating from them)
  @param eventSource The Object that is the source of the ActionEvent
  */
  public void handleColoring(Object eventSource) {
    if(gameRoot.getChildren().contains(invalidColor)) {
      gameRoot.getChildren().remove(invalidColor);
    }
    selectedNode = (Node) eventSource;
    int[][] adj = g.getAdj();
    //Reset the lines
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
    //And highlight the currently chosen lines
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
    setupColorButtons();
  }

  /**
  This method is used to setup the ColorButtons and add their event handlers
  */
  public void setupColorButtons() {
    ColorButton[] colorButtons = possibleColors.getButtons();
    for(int i = 0; i < colorButtons.length; i++) {
      gameRoot.getChildren().remove(colorButtons[i]);
    }
    for(int i = 0; i < colorButtons.length; i++) {
      String tmpColor = colorButtons[i].getColor();
      colorButtons[i].setOnAction(e -> setNodeColor(tmpColor, selectedNode));
      gameRoot.getChildren().add(colorButtons[i]);
    }
  }

  /**
  This method is used to set the color of the currently selected node
  @param c A String containing the CSS color value to color the node in
  @param node The currently selected node to be colored
  */
  public void setNodeColor(String c, Node node) {
    if(node != null) {
      if(gameRoot.getChildren().contains(invalidColor)) {
        gameRoot.getChildren().remove(invalidColor);
      }
      if(checkColoring(c, node)) {
        node.setColor(c);
      }
      else {
        gameRoot.getChildren().add(invalidColor);
      }
    }
  }

  /**
  This method is used to check if the selected node can be colored in the selected
  color
  @param c A String containing the CSS color value of the selected color
  @param node The currently selected node to be colored
  @return true if the node can be colored using the given color, false if otherwise
  */
  public boolean checkColoring(String c, Node node) {
    int[][] adj = g.getAdj();
    int i = Integer.parseInt(node.getText());
    for(int j = 0; j < adj[i].length; j++) {
      if(adj[i][j] == 1 && nodes[j].getColor().equals(c)) {
        return false;
      }
    }
    return true;
  }

  /**
  This method is used to prevent overlapping of nodes when the graph is rendered.
  It checks whether the given node overlaps one of the already drawn nodes
  @param x The x-position of the node to check for
  @param y The y-position of the node to check for
  @return true if the given position is possible, false if the node would
  overlap with another already drawn node
  */
  public boolean checkOverlap(double x, double y, int currI) {
    for(int i = 0; i < currI; i++) {
      if((Math.abs(x - nodes[i].getX()) < 40) && (Math.abs(y - nodes[i].getY()) < 40)) {
        return false;
      }
    }
    return true;
  }

  /**
  This method is used to check if all nodes are colored, aka if the graph coloring
  is completed
  @return true if all nodes are colored, false if otherwise
  */
  public boolean checkCompletion() {
    for(int i = 0; i < nodes.length; i++) {
      if(nodes[i].getColor().equals("white")) {
        return false;
      }
    }
    return true;
  }

  /**
  This method is used to handle button presses on the submit button. If the graph
  is colored correctly, then a WinScreen will be shown, if not (so if the user used
  more colors than needed (gamemode specific, see Gamemode2 and Gamemode3) or if not
  all nodes are colored yet) then there will be notes displayed on the screen according
  to the cases.
  */
  public void submit() {
    if(checkCompletion()) {
      //If the brute forcing thread is still not finished
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
        if(!gameRoot.getChildren().contains(tooManyColorsNote)) {
          gameRoot.getChildren().add(tooManyColorsNote);
        }
      }
    }
    else{
      if(!gameRoot.getChildren().contains(noteCompletion)) {
        gameRoot.getChildren().add(noteCompletion);
      }
    }
  }

  /**
  This method is used to count in how many colors the user has colored the graph
  @return An integer containing the number of colors used to color the graph
  */
  public int countUserColors() {
    String[] colors = new String[nodes.length];
    for(int i = 0; i < nodes.length; i++) {
      colors[i] = nodes[i].getColor();
    }
    Arrays.sort(colors);
    String prevColor = "";
    int colorCount = 0;
    for(int i = 0; i < colors.length; i++) {
      if(!colors[i].equals(prevColor)) {
        colorCount++;
        prevColor = colors[i];
      }
    }
    return colorCount;
  }

  /**
  This method is used to provide the triangle hint, aka to show the user a triangle
  in the graph (if there are any triangles) to start coloring from. This way the
  program helps the user to divide the graph into smaller subgraphs and can also
  help the user to determine the lower bound by showing if there are any triangles
  */
  public void trianglehint() {
    if (!gameRoot.getChildren().contains(hintTriangle)) {
      ColEdge[] e = g.getColEdgeArray();
      TriangleDetection.resetTimer();
      if (TriangleDetection.getNumberOfTriangles(e) >= 1) {
        String triangleNodes = HelperFunctions.getHintTriangles(e);
        hint = "The nodes " + triangleNodes + " form a triangle. \n You can start to color \n that part of the graph!";
      } else {
        hint = "Sorry, there is no triangle in this graph";
      }
      hintTriangle = new Text(hint);
      hintTriangle.setLayoutX(gameScene.getWidth() - 850);
      hintTriangle.setLayoutY(gameScene.getHeight() - 560);
      gameRoot.getChildren().addAll(hintTriangle);
    }
  }

  /**
  This method is used to provide the upper bound for the number of colors needed
  to color the graph as a hint to the user.
  */
  public void upperhint () {
    if (!gameRoot.getChildren().contains(hintUpper)) {
      String upperbound = Integer.toString(boundArray[1]);
      hintUpper = new Text("The upper bound of \n the chromatic number is "+upperbound);
      hintUpper.setLayoutX(gameScene.getWidth() - 640);
      hintUpper.setLayoutY(gameScene.getHeight() - 560);
      gameRoot.getChildren().addAll(hintUpper);
    }
  }

  /**
  This method is used to provide the lower bound for the number of colors needed
  to color the graph as a hint to the user.
  */
  public void lowerhint () {
    if (!gameRoot.getChildren().contains(hintLower)) {
      String lowerbound = Integer.toString(boundArray[0]);
      hintLower = new Text("The lower bound of \n the chromatic number is "+lowerbound);
      hintLower.setLayoutX(gameScene.getWidth() - 480);
      hintLower.setLayoutY(gameScene.getHeight() - 560);
      gameRoot.getChildren().addAll(hintLower);
    }
  }

  /**
  This method is used to get the gamemode's title
  @return <i>String</i> A String containing the gamemode's title
  */
  public String getTitle() {
    return gameTitle;
  }

  /**
  This method is used to set the gamemode's title
  @param title A String containing the gamemode's title
  */
  public void setTitle(String title) {
    gameTitle = title;
  }

  /**
  This method is used to setup the hint buttons, the submit button, the button to get
  new colors and the quit button.
  */
  public void helperButtons() {
    //Hint Buttons
    Button hintButton = new Button("Triangle Detection Hint");
    hintButton.setLayoutX(150);
    hintButton.setLayoutY(550);
    hintButton.setOnAction(e -> trianglehint());
    Button hintButton1 = new Button("Upper Bound Hint");
    hintButton1.setLayoutX(350);
    hintButton1.setLayoutY(550);
    hintButton1.setOnAction(e -> upperhint());
    Button hintButton2 = new Button("Lower Bound Hint");
    hintButton2.setLayoutX(550);
    hintButton2.setLayoutY(550);
    hintButton2.setOnAction(e -> lowerhint());
    //Quit button
    Button quitButton = new Button("Quit");
    quitButton.setLayoutX(gameScene.getWidth()-100);
    quitButton.setLayoutY(gameScene.getHeight()-40);
    quitButton.setOnAction(e -> System.exit(0));
    //Submit button
    Button submitButton = new Button("Submit your answer!");
    submitButton.setLayoutX(gameScene.getWidth()-150);
    submitButton.setLayoutY(gameScene.getHeight()-580);
    submitButton.setOnAction(e -> submit());
    //New Color button
    Button newColorButton = new Button("Get a new color!");
    newColorButton.setLayoutX(gameScene.getWidth()-300);
    newColorButton.setLayoutY(gameScene.getHeight()-580);
    newColorButton.setOnAction(e -> {possibleColors.addColor(); setupColorButtons();});
    //Add the buttons
    gameRoot.getChildren().addAll(quitButton, submitButton, newColorButton, hintButton, hintButton1, hintButton2);
  }

  public Stage window;
  public Scene menuScene;
  public Image background;
  public OptionsView options;
  public Graph g;
  public Pane gameRoot;
  public Scene gameScene;
  public Node[] nodes;
  public Edge[][] edges;
  /*
  All possible colors (note: not all of them are shown directly, only the first three.
  You can add them one by one using the "Get new color" button)
  */
  public String[] colors = {"red", "green", "blue", "yellow", "orange", "cyan", "purple", "pink", "gray", "brown", "black"};
  public ColorPalette possibleColors = new ColorPalette(colors);
  public Text invalidColor;
  public Text noteCompletion;
  public Text hintTriangle;
  public Text hintUpper;
  public Text hintLower;
  public String hint;
  public int chromaticNumber;
  public String gameTitle;
  public Task task;
  public int[] boundArray;
  public Node selectedNode;
  public Text tooManyColorsNote;
}
