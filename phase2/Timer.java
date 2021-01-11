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

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer extends Label {

	Long time;
	Boolean processing;
	private WinScreen gameOver;
	/**
	This class is used to integrate the timer needed in Gamemode2
	@param xPos The x-position at which the timer should be drawn
	@param yPos The y-position at which the timer should be drawn
	@param someStage The current Stage (the window) in which the timer should be drawn in
	@param menu The Scene object containing the menu screen (this is used to provide a "back" button when the
	time limit is exceeded)
	@param background The background image to use
	@param title A String containing the title for the GameOver-WinScreen
	*/
	public Timer(double xPos, double yPos, Stage someStage, Scene menu, Image background, String title) {
		setLayoutX(xPos);
		setLayoutY(yPos);
		setFont(Font.font("TimesRoman", FontWeight.BOLD, FontPosture.ITALIC, 28));
		gameOver = new WinScreen(someStage, background, menu, title);
	}

	/**
	This method is used to start the timer
	*/
	public void start() {
		time = 0L;
		processing = true;

		buildThread().start();
	}
	/**
	This method is used to stop the timer
	*/
	public void stop() {
		processing = false;
	}
	/**
	This method builds a new thread in which the timer runs and stops the timer and
	displays the GameOver-WinScreen after the 5 minute time limit is exceeded.
	@return The thread in which the timer is running
	*/
	public Thread buildThread() {
		return new Thread(() -> {
			while (processing) {
				if(time >= 300000){
					Platform.runLater(() -> {
						setTextFill(Color.RED);
						gameOver.show();
					});
					processing = false;
				}
				Platform.runLater(() -> setText(timeParser(time)));
				try {
					Thread.sleep(1000);
					time = time + 1000;
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	This method is used to get the current time
	@return <i>Long</i> the current time
	*/
	public Long getTime() {
		return time;
	}

	/**
	This method is used to check whether the timer is still running
	@return true if it is, false otherwise
	*/
	public Boolean getProcessing() {
		return processing;
	}

	/**
	This method is used to set the timer to be running or not.
	@param processing A Boolean (true if it should be running, false if not)
	*/
	public void setProcessing(Boolean processing) {
		this.processing = processing;
	}

	/**
	This method is used to format the current to a String of format HH:MM:SS
	@param time The variable of type Long that contains the current time
	@return A String containing the current time in the format HH:MM:SS
	*/
	public static String timeParser(Long time) {
		Double t = time.doubleValue();
		Double msecPerMinute = 1000.0 * 60.0;
		Double msecPerHour = msecPerMinute * 60.0;

		Double hours = Math.floor(t / msecPerHour);
		t = t - (hours * msecPerHour);
		Double minutes = Math.floor(t / msecPerMinute);
		t = t - (minutes * msecPerMinute);

		Double seconds = Math.floor(t / 1000);

		return (hours < 10 ? "0" : "") + hours.intValue() + ":" + (minutes < 10 ? "0" : "") + minutes.intValue() + ":"
				+ (seconds < 10 ? "0" : "") + seconds.intValue();
	}
}
