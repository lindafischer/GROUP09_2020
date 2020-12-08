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

public class Gamemode2 extends Gamemode {
  /**
  This class extends Gamemode in order to integrate Gamemode 2 (coloring the graph
  with a timer)
  @param someStage The current Stage object (the window)
  @param menu The menu's Scene (this is used to integrate the "back" button)
  @param backGroundImage The image to be drawn in the views before the actual
  (see OptionsView, InsertView and WinScreen)
  @param title The String containing the gamemode's title
  */
  public Gamemode2(Stage someStage, Scene menu, Image backgroundImage, String title) {
    super(someStage, menu, backgroundImage, title);
    options.setTitleX(300);
  }

  /**
  This method is used to show the playing screen. It first calls Gamemode.show()
  and then adds the timer which is specific to Gamemode 2.
  */
  public void show() {
    super.show();
    timer = new Timer(200, 100, window, menuScene, background, "Game Over!\nYou were not fast enough sadly!\n\n\n\n");
    gameRoot.getChildren().add(timer);
    timer.start();
  }

  /**
  This method is used to compute the player's score based on the number of colors
  they used and the time they needed to color the whole graph.
  @param c The number of colors the player used to color the graph
  @return The player's score as an integer between 0 and 100
  */
  public int computeScore(int c) {
    long timeLeft = 300000 - timer.getTime();
    int colorDifference = c - boundArray[1];
    double tmpScore = timeLeft;
    if(colorDifference > 0) {
      tmpScore += (300000 / colorDifference);
    }
    else {
      tmpScore += 300000;
    }
    int normalizedScore = (int) ((tmpScore/600000.0) * 100);
    return normalizedScore;
  }

  /**
  This method overrides Gamemode.submit() to add the following differences from Gamemode.show():
   - Display the score and add 10 bonus points of the chromatic number was found
   - Display the score even if the user has used more colors than actually needed,
     since the goal is to find the best upper bound in a fixed time frame.
  */
  @Override
  public void submit() {
    if(checkCompletion()) {
      int colorCount = countUserColors();
      if(task != null && !task.isCancelled()) {
        task.cancel();
        timer.stop();
        WinScreen win = new WinScreen(window, background, menuScene, "Congratulations!\nYou used " + colorCount + " colors\nOur upper bound: " + boundArray[1] + "\nYour time: " + timer.timeParser(timer.getTime()) + "\nYour score: " + computeScore(colorCount) + "\n\n\n\n\n\n\n\n");
        win.show();
      }
      if(colorCount == chromaticNumber) {
        timer.stop();
        WinScreen win = new WinScreen(window, background, menuScene, "Congratulations!\nYou used " + colorCount + " colors\nOur upper bound: " + boundArray[1] + "\nYour time: " + timer.timeParser(timer.getTime()) + "\nYou found the chromatic number! You receive 10 bonus points!\nYour score: " + (computeScore(colorCount) + 10) + "\n\n\n\n\n\n\n\n");
        win.show();
      }
      else if(colorCount > chromaticNumber) {
        timer.stop();
        WinScreen win = new WinScreen(window, background, menuScene, "Congratulations!\nYou used " + colorCount + " colors.\nOur upper bound: " + boundArray[1] + "\nYour time: " + timer.timeParser(timer.getTime()) + "\nYour score: " + computeScore(colorCount) + "\n\n\n\n\n\n\n\n");
        win.show();
      }
    }
    else{
      if(!gameRoot.getChildren().contains(noteCompletion)) {
        gameRoot.getChildren().add(noteCompletion);
      }
    }
  }

  private Timer timer;
}
