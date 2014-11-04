package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;

import jdk.nashorn.internal.parser.JSONParser;

public class Scoreboard {
	private Stack<Score> scores;
	private String filepath;
	
	public Scoreboard() {
		scores = new Stack<Score>();
	}
	public boolean addScore(int time, String name){ // Returns true if score is high score
		if (name.trim() == ""){
			return false;
		}
		scores.push(new Score(time, name));
		return true;
	}
	
	public boolean isHighScore(int time){
		for (Score score : scores){
			if (score.time < time){
				return false;
			}
		}
		return true;
	}
	
	private void openScoreFile(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Stack<Score> getScoreStack() {
		return this.scores;
	}
}
