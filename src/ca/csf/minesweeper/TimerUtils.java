package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class TimerUtils {
	private List<TimerUtilsObserver> observers;
	
	public TimerUtils(){
		observers = new ArrayList<TimerUtilsObserver>();
	}
	
	public void addObserver(TimerUtilsObserver observer){
		observers.add(observer);
	}
	
	public void timeChange(){
		
		for(TimerUtilsObserver observer : observers){
			observer.timeChange("00:12", true);
		}
	}
}
