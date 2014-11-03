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

	public void stopTimer() {
		timeLine.stop();
	}

	public void resetTimer() {
		this.time = 0;
		startTimer();
		//TODO should resetTimer start the timer ?
	}

	public void addObserver(TimerUtilsObserver observer) {
		observers.add(observer);
	}

	public void timeChange() {
		time++;
		for (TimerUtilsObserver observer : observers) {
			observer.timeChange((this.time).toString());
		}
	}
	
	public int getTime(){
		return this.time;
	}
}
