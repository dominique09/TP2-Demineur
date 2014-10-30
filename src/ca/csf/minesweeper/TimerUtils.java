package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public final class TimerUtils {
	private List<TimerUtilsObserver> observers;
	private Integer time;
	private KeyFrame keyFrame;
	private static Timeline timeLine;

	private final class EventHandlerImplementation implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			timeChange();
		}
	}
	
	public TimerUtils() {
		time = 0;
		
		observers = new ArrayList<TimerUtilsObserver>();
		
		keyFrame = new KeyFrame(Duration.seconds(1), new EventHandlerImplementation());
		
		if(timeLine == null){
			timeLine = new Timeline(keyFrame);
			timeLine.setCycleCount(Timeline.INDEFINITE);
		}
	}
	
	public void startTimer(){
		timeLine.play();
	}
	
	public void stopTimer(){
		timeLine.stop();
	}
	
	public void resetTimer(){
		this.time = 0;
		startTimer();
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
}
