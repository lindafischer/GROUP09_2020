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
 
public class MenuProject extends Application 
{
	Stage window;
	Scene menuscene, scene1;
	
    public static void main(String[] args) 
    {
        launch(args);
    }
 
	@Override
    public void start(Stage theStage) throws Exception
    {
		window = theStage;
		
		//Page size
		Pane root = new Pane();
        	root.setPrefSize(1050, 600);
		Scene menuscene = new Scene(root);
		theStage.setTitle("Graph Coloring Game"); 
		theStage.setScene(menuscene);	
        
		//Set background
		Image background = new Image("sfondo1.jpg");
		ImageView backgroundView = new ImageView(background);
		backgroundView.setFitWidth(1050);
        	backgroundView.setFitHeight(600);
		
		//Title of the game
		Text title = new Text ("GRAPH COLORING GAME");
        	title.setTranslateX(200);
        	title.setTranslateY(150);
		title.setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 50));
		title.setFill(Color.WHITE);
		title.setStrokeWidth(2);
		title.setStroke(Color.BLACK); 
		root.getChildren().addAll(backgroundView, title);
		
		//Game Mode 1
		Image image1 = new Image("gamemode1.jpg");
        	ImageView imageView1 = new ImageView(image1);
		imageView1.setFitWidth(300);
        	imageView1.setFitHeight(80);
        	Button button1 = new Button(" ", imageView1);
		button1.setLayoutX(20);
		button1.setLayoutY(290);
		root.getChildren().addAll(button1);
		
		//Game Mode 2
		Image image2 = new Image("gamemode2.jpg");
        	ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(300);
        	imageView2.setFitHeight(80);
        	Button button2 = new Button(" ", imageView2);
		button2.setLayoutX(370);
		button2.setLayoutY(290);
		root.getChildren().addAll(button2);
		
		//Game Mode 3
		Image image3 = new Image("gamemode3.jpg");
        	ImageView imageView3 = new ImageView(image3);
		imageView3.setFitWidth(300);
        	imageView3.setFitHeight(80);
        	Button button3 = new Button(" ", imageView3);
		button3.setLayoutX(720);
		button3.setLayoutY(290);
		root.getChildren().addAll(button3);
		
		//Screen Game Mode 1
		Pane root1 = new Pane();
        	root1.setPrefSize(1050, 600);
		Scene scene1 = new Scene(root1);
		button1.setOnAction(e -> window.setScene(scene1));
		Image imageback = new Image("back.jpg");
		ImageView viewback1 = new ImageView(imageback);
		viewback1.setFitWidth(150);
        	viewback1.setFitHeight(80);
		Button back1 = new Button(" ", viewback1);
		back1.setLayoutX(800);
		back1.setLayoutY(15);
		back1.setOnAction(e -> window.setScene(menuscene));
		ImageView backgroundView1 = new ImageView(background);
		backgroundView1.setFitWidth(1050);
        	backgroundView1.setFitHeight(600); 
		root1.getChildren().addAll(backgroundView1, back1);
		
		//Screen Game Mode 2
		Pane root2 = new Pane();
        	root2.setPrefSize(1050, 600);
		Scene scene2 = new Scene(root2);
		button2.setOnAction(e -> window.setScene(scene2));
		Image imageback2 = new Image("back.jpg");
		ImageView viewback2 = new ImageView(imageback2);
		viewback2.setFitWidth(150);
        	viewback2.setFitHeight(80);
		Button back2 = new Button(" ", viewback2);
		back2.setLayoutX(800);
		back2.setLayoutY(15);
		back2.setOnAction(e -> window.setScene(menuscene));
		ImageView backgroundView2 = new ImageView(background);
		backgroundView2.setFitWidth(1050);
        	backgroundView2.setFitHeight(600);
		root2.getChildren().addAll(backgroundView2, back2);
		
		//Screen Game Mode 3
		Pane root3 = new Pane();
        	root3.setPrefSize(1050, 600);
		Scene scene3 = new Scene(root3);
		button3.setOnAction(e -> window.setScene(scene3));
		Image imageback3 = new Image("back.jpg");
		ImageView viewback3 = new ImageView(imageback3);
		viewback3.setFitWidth(150);
        	viewback3.setFitHeight(80);
		Button back3 = new Button(" ", viewback3);
		back3.setLayoutX(800);
		back3.setLayoutY(15);
		back3.setOnAction(e -> window.setScene(menuscene));
		ImageView backgroundView3 = new ImageView(background);
		backgroundView3.setFitWidth(1050);
        	backgroundView3.setFitHeight(600);
		root3.getChildren().addAll(backgroundView3, back3);
		
		theStage.show();
	}
}
