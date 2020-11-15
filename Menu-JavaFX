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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
 
public class MenuProject extends Application 
{
    public static void main(String[] args) 
    {
        launch(args);
    }
 
	@Override
    public void start(Stage theStage) throws Exception
    {
		Scene scene = new Scene(createContent());
		theStage.setTitle("Graph Coloring Game"); 
		theStage.setScene(scene);
		theStage.show();	
	}
	
	private Parent createContent() 
	{
		//Page size
		Pane root = new Pane();
    root.setPrefSize(1050, 600);
        
		//Set background
		Image background = new Image("sfondo1.jpg");
		ImageView backgroundView = new ImageView(background);
		backgroundView.setFitWidth(1050);
    backgroundView.setFitHeight(600);
		
		//Title of the game
		Text title = new Text ("GRAPH COLORING GAME");
    title.setTranslateX(200);
    title.setTranslateY(150);
		title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		title.setFill(Color.WHITE);
		title.setStrokeWidth(2);
		title.setStroke(Color.BLACK); 
		root.getChildren().addAll(backgroundView, title);
		
		//Game Mode 1
		Rectangle rectangle1 = new Rectangle(300,80);
    rectangle1.setOpacity(0.4); 
		rectangle1.setX(20);
    rectangle1.setY(290);
		Text mode1 = new Text("Game Mode 1");
		mode1.setFill(Color.WHITE);
		mode1.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 40));
		mode1.setStrokeWidth(1.5);
		mode1.setStroke(Color.BLACK); 
		mode1.setX(28);
		mode1.setY(340);
		root.getChildren().addAll(rectangle1, mode1);
		
		//Game Mode 2
		Rectangle rectangle2 = new Rectangle(300,80);
    rectangle2.setOpacity(0.4); 
		rectangle2.setX(370);
    rectangle2.setY(290);
		Text mode2 = new Text("Game Mode 2");
		mode2.setFill(Color.WHITE);
		mode2.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 40));
		mode2.setStrokeWidth(1.5);
		mode2.setStroke(Color.BLACK); 
		mode2.setX(378);
		mode2.setY(340);
		root.getChildren().addAll(rectangle2, mode2);
		
		//Game Mode 3
		Rectangle rectangle3 = new Rectangle(300,80);
    rectangle3.setOpacity(0.4); 
		rectangle3.setX(720);
    rectangle3.setY(290);
		Text mode3 = new Text("Game Mode 3");
		mode3.setFill(Color.WHITE);
		mode3.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 40));
		mode3.setStrokeWidth(1.5);
		mode3.setStroke(Color.BLACK); 
		mode3.setX(728);
		mode3.setY(340);
		root.getChildren().addAll(rectangle3, mode3);
		return root;
	}
	
}
