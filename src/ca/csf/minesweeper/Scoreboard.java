package ca.csf.minesweeper;

import java.util.Stack;

public class Scoreboard {
	private Stack<Score> scores;
	
	public Scoreboard() {
		scores = new Stack<Score>();
	}
	public void addScore(int time, String name){ // Returns true if score is high score
		scores.push(new Score(time, name));
	}
	
	public boolean isHighScore(int time){
		for (Score score : scores){
			if (score.time < time){
				return false;
			}
		}
		return true;
	}
}
