package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;



public class Main extends Application implements EventHandler<ActionEvent>
{
    private int colorX_coords=50;
    private int colorY_coords=30;
    private int button_counter=0;

    private ColorButton red=new ColorButton("red");
    private ColorButton blue=new ColorButton("blue");
    private ColorButton green=new ColorButton("green");
    private ColorButton yellow=new ColorButton("yellow");
    private ColorButton pink=new ColorButton("pink");
    private ColorButton turquoise=new ColorButton("turquoise");


    private int[][] matrix=new int[0][0];
    private Pane layout=new Pane();

    private Text invalid_c=new Text();
    private Button new_color=new Button();

    private Random rand=new Random();
    private Button[] button_list=new Button[50];
    private Line line=new Line();
    private int choice;
    int newc_counter=0;
    int styleButtonCOunter=0;




    class ColorButton extends Button
    {

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
            button_counter++;
        }


    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("GRAPH_COLOR_TEST");


        int numberOfVertices=rand.nextInt(20);
        int[][] adj_matrix=createMatrix(numberOfVertices);

        setupButtonList(numberOfVertices);
        setupEdges(adj_matrix);
        setupButtons();

        matrix=adj_matrix;
        Scene scene=new Scene(layout,1200,800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent)
    {

        helperButtons();
        setOnAction();
        if(check_completion())
        {
            System.out.println("CONGRATULATIONS");

        }else styleButtonCOunter++;



        for(int i=0;i<matrix.length;i++)
        {

            if (actionEvent.getSource() == button_list[i])
            {
                add();
                choice=i;
            }
        }

            if (actionEvent.getSource() == red)
            {

                button_list[choice].setStyle(red.getStyle());

                check_adj();
                remove();

            } else if (actionEvent.getSource() == blue)
            {
                button_list[choice].setStyle(blue.getStyle());

                check_adj();
                remove();

            } else if (actionEvent.getSource() == green)
            {
                button_list[choice].setStyle(green.getStyle());

                check_adj();
                remove();

            } else if (actionEvent.getSource() == yellow)
            {
                button_list[choice].setStyle(yellow.getStyle());

                check_adj();
                remove();

            } else if (actionEvent.getSource() == pink)
            {
                button_list[choice].setStyle(pink.getStyle());

                check_adj();
                remove();

            } else if (actionEvent.getSource() == turquoise)
            {
                button_list[choice].setStyle(turquoise.getStyle());

                check_adj();
                remove();

            }
            else if(actionEvent.getSource()== new_color)
            {
                newc_counter++;

                colorCounter(newc_counter);

            }


    }
    public void colorCounter(int color_counter) //WORK IN PROGRESS
    {
        if (color_counter == 1) {
            layout.getChildren().add(yellow);
            layout.getChildren().remove(new_color);
        }
        if (color_counter == 2) {
            layout.getChildren().add(yellow);
            layout.getChildren().add(pink);
            layout.getChildren().remove(new_color);
        } else

            layout.getChildren().add(yellow);
        layout.getChildren().add(pink);
        layout.getChildren().add(turquoise);
        layout.getChildren().remove(new_color);


    }
    public void remove()
    {

        layout.getChildren().remove(red);
        layout.getChildren().remove(blue);
        layout.getChildren().remove(green);
        layout.getChildren().remove(yellow);
        layout.getChildren().remove(new_color);
        layout.getChildren().remove(pink);
        layout.getChildren().remove(turquoise);

    }
    public void add()
    {
        layout.getChildren().add(new_color);
        layout.getChildren().add(red);
        layout.getChildren().add(blue);
        layout.getChildren().add(green);

        new_color.setOnAction(this);

    }
    public void check_adj()
    {
        for (int j = 0; j < matrix[choice].length; j++)
        {
            if ((matrix[choice][j] == 1 || matrix[j][choice] == 1)
                    &&( button_list[j].getStyle().equals(button_list[choice].getStyle()) ) && choice!=j)
            {
                button_list[choice].setStyle("-fx-background-color: white;");
                layout.getChildren().add(invalid_c);

                break;
            } else
                layout.getChildren().remove(invalid_c);

        }
    }
    public void helperButtons()
    {
        invalid_c.setLayoutX(200);
        invalid_c.setLayoutY(30);
        invalid_c.setText("Cannot use that color \n Please choose another");

        new_color.setText("Set a new color");
        new_color.setLayoutX(1050);
        new_color.setLayoutY(20);
    }
    public void setupButtonList(int numberOfVertices)
    {
        for(int i=0;i<=numberOfVertices;i++)
        {
            button_list[i]=new Button();

            button_list[i].setLayoutX(rand.nextInt(901));
            button_list[i].setLayoutY(rand.nextInt(701));
            button_list[i].setText(Integer.toString(i));
            button_list[i].setStyle("-fx-background-color: white;");

            layout.getChildren().add(button_list[i]);
            button_list[i].setOnAction(this);


        }


    }
    public void setupEdges(int[][] adj_matrix)
    {
        for(int i=0;i<adj_matrix.length;i++)
        {
            System.out.println();
            for(int j=0;j<adj_matrix.length;j++)
            {
                System.out.print(adj_matrix[i][j]+ "  " );
                if(adj_matrix[i][j]==1)
                {
                    line=new Line();
                    line.setStartX(button_list[i].getLayoutX());
                    line.setStartY(button_list[i].getLayoutY());

                    line.setEndY(button_list[j].getLayoutY());
                    line.setEndX(button_list[j].getLayoutX());
                    layout.getChildren().add(line);


                }

            }
        }

    }
    public void setupButtons()
    {
        red.setup(red);
        blue.setup(blue);
        green.setup(green);
        yellow.setup(yellow);
        pink.setup(pink);
        turquoise.setup(turquoise);

    }
    public void setOnAction()
    {
        red.setOnAction(this);
        blue.setOnAction(this);
        green.setOnAction(this);
        yellow.setOnAction(this);
        pink.setOnAction(this);
        turquoise.setOnAction(this);
    }
    public int[][] createMatrix(int numberOfVertices)
    {

        int numberOfEdges=rand.nextInt(10);

        int[][] adj_matrix=new int[numberOfVertices+1][numberOfVertices+1];
        int counterOfEdges=0;
        while(counterOfEdges<=numberOfEdges)
        {
            int n=rand.nextInt(numberOfVertices);
            int m=rand.nextInt(numberOfVertices);
            adj_matrix[n][m]=1;
            counterOfEdges++;
        }
        return adj_matrix;
    }
    public boolean check_completion() //WORK IN PROGRESS
    {
        boolean completed=false;
        for(int i=0;i<matrix.length;i++)
        {
            if(styleButtonCOunter==matrix.length && !(button_list[i].getStyle().equals("-fx-background-color: white;")))
            {
                completed=true;
            }
        }
        return completed;
    }

    public static void main(String[] args) {launch(args);};

}
