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
	private Timeline timeLine;

	public TimerUtils() {
		time = 0;
		
		observers = new ArrayList<TimerUtilsObserver>();
		
		keyFrame = new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						time++;
						timeChange();
					}
				});
		timeLine = new Timeline(keyFrame);;
		timeLine.setCycleCount(Timeline.INDEFINITE);
	    timeLine.play();
	}

	public void addObserver(TimerUtilsObserver observer) {
		observers.add(observer);
	}

	public void timeChange() {
		for (TimerUtilsObserver observer : observers) {
			observer.timeChange((this.time).toString());
		}
	}
}
