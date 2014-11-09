package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Stack;

public class Scoreboard {
	private Stack<Score> scores;
	private String scoresFileName;
	
	public Scoreboard(String scoresFileName) {
		scores = new Stack<Score>();
		
		this.scoresFileName = scoresFileName;
		openScoreFile(this.scoresFileName);
	}
	public boolean addScore(int time, String name){ // Returns true if score is high score
		if (name.trim() == ""){
			return false;
		}
		scores.push(new Score(time, name));
		saveScoreFile(scoresFileName);
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
	
	private void openScoreFile(String filename){
		try {
			
			File file = new File(filename);
			
			if (!file.exists()){
				file.createNewFile();
			}
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String tempName = new String("");
			String tempScore = new String("");
			
			while (br.ready()){
				tempName = br.readLine().trim();
				if (tempName.isEmpty()){
					continue;
				}
				
				tempScore = br.readLine().trim();
				if (tempScore.isEmpty()){
					continue;
				}
					
				scores.push(new Score(Integer.parseInt(tempScore), tempName));
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveScoreFile(String filename){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			
			for (Score score : scores){	
				bw.write(score.name);
				bw.newLine();
				bw.write(Integer.toString(score.time));
				bw.newLine();
			}			
			bw.flush();
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stack<Score> getScoreStack() {
		return this.scores;
	}
}
