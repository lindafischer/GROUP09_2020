package application;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
* The timer starts to work with start command and stops with stop command
* Then the get time commands get the time between start and stop
* And lastly the timeparser returns time in the unit that we want. In this case seconds
*/
public class Timer extends Label {

	Long time;
	Boolean proccessing;
	
	public Timer() {
	
	}
	
	public void start() {
		time = 0L;
		proccessing = true;
				
		buildThread().start();
	}
	
	public void stop() {
		proccessing = false;
	}
	
	public Thread buildThread() {
		return new Thread(() -> {
			while (proccessing) {
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
	
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
	public Boolean getProccessing() {
		return proccessing;
	}
	
	public void setProccessing(Boolean proccessing) {
		this.proccessing = proccessing;
	}
	
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

