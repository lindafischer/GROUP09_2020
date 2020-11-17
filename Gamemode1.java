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

public class Gamemode1 implements EventHandler<ActionEvent>{
  public ColorButton red=new ColorButton("red");
  public ColorButton blue=new ColorButton("blue");
  public ColorButton green=new ColorButton("green");
  public ColorButton yellow=new ColorButton("yellow");
  public ColorButton pink=new ColorButton("pink");
  public ColorButton turquoise=new ColorButton("turquoise");
  public int[][] matrix=new int[0][0];
  public Pane layout=new Pane();
  public Pane root;
  public Text invalid_c=new Text();
  public Text status_submit=new Text();
  public Button new_color=new Button();
  public Button submit_button=new Button();
  public Button quit_button=new Button();
  public Random rand=new Random();
  public Button[] button_list=new Button[101];
  public Line[] color_lineList=new Line[81];
  public Line line=new Line();
  public Line border_line=new Line();
  public int choice;
  public int newc_counter=0;
  public boolean fixerForSubmitB=true;
  public static String[] style_list= new String[6];
  public static String[] list_holder= new String[6];
  public Button v_button;
  public Button e_button;
  public TextField v_textField;
  TextField e_textField;
  int numberOfVertices=0;
  int numberOfEdges=0;
  int determine_scene=0;
  ImageView base_background;
  public Scene scene1,scene2;
  Stage window;
  public Gamemode1(Stage someStage, Pane root1) {
    window = someStage;
    root = root1;
    numberOfVertices = 1;
    numberOfEdges= 2;
  }

  public void run() {
    System.out.println("Test");
    window.setTitle("GRAPH_COLOR_TEST");
    layout.setStyle("-fx-background-color: beige;");
    Image background=new Image("file:beige_background.jpg");
    ImageView backgroundView=new ImageView(background);
    backgroundView.setFitHeight(600);
    backgroundView.setFitWidth(1050);
    base_background = backgroundView;
    scene1 = new Scene(createParent(), 1050, 600);
    scene2 = new Scene(layout, 1050, 600);
    window.setScene(scene1);
    determine_scene++;
  }

  private Pane createParent() {
    root.setPrefSize(1200, 800);
    v_textField = new TextField();
    v_textField.setText("...");
    Text v_text = new Text("Input vertix number: ");
    v_textField = new TextField();
    v_textField.setText("");
    v_button = new Button("Submit");
    Text e_text = new Text("Input edges number: ");
    e_textField = new TextField();
    e_textField.setText("...");
    Text text = new Text("Input vertices number: ");
    e_textField = new TextField();
    e_textField.setText("");
    setCoords(v_textField,v_button,v_text,e_textField,e_text);
    Image background=new Image("file:background2.jpg");
    ImageView backgroundView=new ImageView(background);
    backgroundView.setFitHeight(1200);
    backgroundView.setFitWidth(1200);
    root.getChildren().addAll(backgroundView,v_textField,v_button,v_text);
    root.getChildren().addAll(e_text,e_textField);
    return root;
  }

  @Override
  public void handle(ActionEvent actionEvent) {

    if (actionEvent.getSource() == v_button) {
      numberOfVertices = Integer.parseInt(v_textField.getText());
      numberOfEdges = Integer.parseInt(e_textField.getText());
      System.out.println("Number of Vertices: " + numberOfVertices);
      System.out.println("Number of Edges: " + numberOfEdges);
      root.getChildren().remove(scene1);
      matrix = createMatrix();
      setupButtonList(numberOfVertices);
      setupEdges(matrix);
      setupButtons();
      window.setScene(scene2);
    }
    helperButtons();
    setOnAction();
    for (int i = 0; i < matrix.length; i++) {
      if (actionEvent.getSource() == button_list[i]) {
        add();
        colorLines(matrix, i);
        choice = i;
      }
    }
    if (actionEvent.getSource() == red) {
      button_list[choice].setStyle(red.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == blue) {
      button_list[choice].setStyle(blue.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == green) {
      button_list[choice].setStyle(green.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == yellow) {
      button_list[choice].setStyle(yellow.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == pink) {
      button_list[choice].setStyle(pink.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == turquoise) {
      button_list[choice].setStyle(turquoise.getStyle());
      check_adj();
      remove();
    }
    else if (actionEvent.getSource() == new_color) {
      newc_counter++;
      colorCounter();
    }
    else if (actionEvent.getSource() == quit_button) {
      exit();
    }
    else if (actionEvent.getSource() == submit_button) {
      style_list = list_holder;
      boolean value = true;
      for (int i = 0; i < matrix.length; i++) {
        if (button_list[i].getStyle().equals("-fx-background-color: white;")) {
          value = false;

        }
      }
      if (value) {
        if (check_completion()) {
          status_submit.setText("CONGRATULATIONS \n Answer is correct!");
          layout.getChildren().add(status_submit);
        } else if (!check_completion()) {
          status_submit.setText("You do not have the exact number");
          layout.getChildren().add(status_submit);
        }
      } else if (!value) {
        status_submit.setText("Please fill all the graph");
        layout.getChildren().add(status_submit);
      }
    }
  }

  public void colorCounter() {
    if (newc_counter == 1) layout.getChildren().add(yellow);
    if (newc_counter == 2) layout.getChildren().add(pink);
    if (newc_counter == 3) layout.getChildren().add(turquoise);
  }

  public void remove() {

    layout.getChildren().remove(red);
    layout.getChildren().remove(blue);
    layout.getChildren().remove(green);
    layout.getChildren().remove(yellow);
    layout.getChildren().remove(new_color);
    layout.getChildren().remove(pink);
    layout.getChildren().remove(turquoise);
    layout.getChildren().remove(status_submit);

    for(int i=0;i<color_lineList.length;i++)
    {
      layout.getChildren().remove(color_lineList[i]);
    }
  }

  public void add() {

    layout.getChildren().add(new_color);
    layout.getChildren().add(red);
    layout.getChildren().add(blue);
    layout.getChildren().add(green);

    if(fixerForSubmitB) {
      layout.getChildren().add(submit_button);
      layout.getChildren().add(quit_button);
      new_color.setOnAction(this);
      submit_button.setOnAction(this);
      quit_button.setOnAction(this);
    }
    if (newc_counter >= 1) layout.getChildren().add(yellow);
    if (newc_counter >= 2) layout.getChildren().add(pink);
    if (newc_counter >= 3) layout.getChildren().add(turquoise);
    fixerForSubmitB=false;
  }

  public void check_adj() {
    for (int j = 0; j < matrix[choice].length; j++) {
      if ((matrix[choice][j] == 1 || matrix[j][choice] == 1) && (button_list[j].getStyle().equals(button_list[choice].getStyle())) && choice != j) {
        button_list[choice].setStyle("-fx-background-color: white;");
        layout.getChildren().add(invalid_c);
        break;
      } else
      layout.getChildren().remove(invalid_c);
    }
  }

  public void helperButtons() {
    invalid_c.setLayoutX(200);
    invalid_c.setLayoutY(30);
    invalid_c.setText("Cannot use that color \n Please choose another");
    status_submit.setLayoutX(300);
    status_submit.setLayoutY(30);
    new_color.setText("Set a new color");
    new_color.setLayoutX(900);
    new_color.setLayoutY(20);
    submit_button.setLayoutX(1050);
    submit_button.setLayoutY(20);
    submit_button.setText("Submit Answer");
    quit_button.setLayoutX(1100);
    quit_button.setLayoutY(700);
    quit_button.setText("Quit");
  }

  public void setupButtonList(int numberOfVertices) {
    for (int i = 0; i <= numberOfVertices; i++) {
      button_list[i] = new Button();
      int max_bound = 70000;
      int min_bound = 10000;
      int j = 0;
      boolean buttons_close = false;
      double X_coordinate=0;
      double Y_coordinate=0;
      while (j <= numberOfVertices) {
        X_coordinate = (double)rand.nextInt(110000)/100;
        Y_coordinate = (double)ThreadLocalRandom.current().nextInt(min_bound, max_bound + 1)/100;
        if (button_list[j] == null) {
          break;
        }
        buttons_close = false;
        boolean close_x = X_coordinate - button_list[j].getLayoutX() < 10 || button_list[j].getLayoutX() - X_coordinate < 10;
        boolean close_y = Y_coordinate - button_list[j].getLayoutY() < 10 || button_list[j].getLayoutY() - Y_coordinate < 10;
        if (close_x && close_y) {
          buttons_close = true;
          System.out.println("CHANGED LAYOUT");
        }
        j++;
      }
      if(button_list[i]==null)
      {
        break;
      }
      if (buttons_close)
      {
        X_coordinate += 30;
        Y_coordinate += 30;
        button_list[i].setLayoutX(X_coordinate);
        button_list[i].setLayoutY(Y_coordinate);
      }

      button_list[i].setLayoutX(X_coordinate);
      button_list[i].setLayoutY(Y_coordinate);
      button_list[i].setText(Integer.toString(i));
      button_list[i].setStyle("-fx-background-color: white;");
      layout.getChildren().add(button_list[i]);
      button_list[i].setOnAction(this);
    }
  }

  public void setupEdges(int[][] adj_matrix) {

    for (int i = 0; i < adj_matrix.length; i++) {
      System.out.println();
      for (int j = 0; j < adj_matrix.length; j++) {
        System.out.print(adj_matrix[i][j] + "  ");
        if (adj_matrix[i][j] == 1) {
          line = new Line();
          line.setStartX(button_list[i].getLayoutX()+10);
          line.setStartY(button_list[i].getLayoutY()+10);
          line.setEndY(button_list[j].getLayoutY()+10);
          line.setEndX(button_list[j].getLayoutX()+10);
          layout.getChildren().add(line);
        }
      }
    }
  }

  public void setupButtons() {
    red.setup(red);
    blue.setup(blue);
    green.setup(green);
    yellow.setup(yellow);
    pink.setup(pink);
    turquoise.setup(turquoise);
  }

  public void setOnAction() {
    red.setOnAction(this);
    blue.setOnAction(this);
    green.setOnAction(this);
    yellow.setOnAction(this);
    pink.setOnAction(this);
    turquoise.setOnAction(this);
  }

  public int[][] createMatrix() {
    int[][] adj_matrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    int counterOfEdges = 0;
    while (counterOfEdges <= numberOfEdges) {
      int n = rand.nextInt(numberOfVertices);
      int m = rand.nextInt(numberOfVertices);
      adj_matrix[n][m] = 1;
      counterOfEdges++;
    }
    return adj_matrix;
  }

  public void colorLines(int [][] adj_matrix,int i) {

    int counter = 0;
    for (int j = 0; j < adj_matrix.length; j++) {
      if ((adj_matrix[i][j] == 1 || adj_matrix[j][i] == 1) && i != j) {
        line = new Line();
        line.setStartX(button_list[i].getLayoutX() + 11);
        line.setStartY(button_list[i].getLayoutY() + 11);
        line.setEndY(button_list[j].getLayoutY() + 11);
        line.setEndX(button_list[j].getLayoutX() + 11);
        if (button_list[i].getStyle().equals("-fx-background-color: white;"))
        {
          line.setStyle("-fx-stroke-width: 3;-fx-stroke: black;");
        } else {
          String line_style=button_list[i].getStyle().substring(21);
          line.setStyle("-fx-stroke-width: 3;-fx-stroke:"+line_style);
        }
        layout.getChildren().add(line);
        color_lineList[counter] = line;
        counter++;
      }
    }
  }

  public String[] remove_element(String[] List,int index) {
    String[] augmented_List=new String[List.length-1];
    for(int i=0,k=0;i<List.length;i++)
    {
      if(i==index)
      {
        continue;
      }
      augmented_List[k++]=List[i];
    }
    System.out.println(index+".STYLE LIST REMOVED"+style_list[index]);
    return augmented_List;
  }

  public boolean check_completion() {
    int count=0;
    boolean value = true;
    String[] unaltered_list=new String[style_list.length];
    for (int i = 0; i < matrix.length; i++)
    {
      if (button_list[i].getStyle().equals("-fx-background-color: white;"))
      {
        value = false;
      }
    } if(!value) return false;
    if (value)
    {
      for (int i = 0; i < matrix.length; i++)
      {
        for (int j = 0; j < style_list.length; j++)
        {
          unaltered_list[j]=style_list[j];
          if (button_list[i].getStyle().equals(style_list[j]))
          {
            style_list=remove_element(style_list,j);
            count++;
            System.out.println("COUNT : "+ count);
          }
        }
      }
      if(count==3) { return true;}
      style_list=unaltered_list;
    }
    return false;
  }

  public void setCoords(TextField v_textField,Button v_button,Text v_text, TextField e_textField,Text e_text) {
    v_textField.setLayoutX(100);
    v_textField.setLayoutY(100);
    v_button.setLayoutX(270);
    v_button.setLayoutY(150);
    v_button.setOnAction(this);
    v_text.setLayoutX(100);
    v_text.setLayoutY(90);
    e_textField.setLayoutX(100);
    e_textField.setLayoutY(200);
    e_text.setLayoutX(100);
    e_text.setLayoutY(190);
  }
}
