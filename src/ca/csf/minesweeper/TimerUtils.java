package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class TimerUtils {
	private static TimerUtils instance;
	private List<TimerUtilsObserver> observers;
	private Integer time;
	private KeyFrame keyFrame;
	private Timeline timeLine;
	private Boolean isInitialStart;

	private final class EventHandlerImplementation implements
			EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			timeChange();
		}
	}

	static {
		instance = new TimerUtils();
	}
	
	private TimerUtils() {
		time = 0;
		observers = new ArrayList<TimerUtilsObserver>();
		this.isInitialStart = false;
		keyFrame = new KeyFrame(Duration.seconds(1),
				new EventHandlerImplementation());

		timeLine = new Timeline(keyFrame);
		timeLine.setCycleCount(Timeline.INDEFINITE);
	}
	
	public static TimerUtils getInstance(){
		return instance;
	}

	public void startTimer() {
		timeLine.play();
	}

	public void stopTimer(){
		this.isInitialStart = false;
		timeLine.stop();
	}
	
	public void pauseTimer() {
		timeLine.stop();
	}

	public void reloadTimer(){
		this.time = 0;
		showTime();
	}
	
	public void resetTimer() {
		this.isInitialStart = true;
		startTimer();
	}

	public void addObserver(TimerUtilsObserver observer) {
		observers.add(observer);
	}

	public void timeChange() {
		time++;
		showTime();
	}
	
	public void showTime(){
		for (TimerUtilsObserver observer : observers) {
			observer.timeChange((this.time).toString());
		}
	}
	
	public int getTime(){
		return this.time;
	}
	
	public Boolean getIsInitialStart(){
		return this.isInitialStart;
	}
}
