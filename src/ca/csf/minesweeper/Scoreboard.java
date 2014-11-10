package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Scoreboard {
	private Score easyHighScore;
	private Score mediumHighScore;
	private Score hardHighScore;
	private String scoresFileName;
	
	public Scoreboard(String scoresFileName) {
		
		easyHighScore = null;
		mediumHighScore = null;
		hardHighScore = null;
		
		this.scoresFileName = scoresFileName;
		openScoreFile(this.scoresFileName);
	}
	
	public boolean isEasyHighScore(int time){
		return (easyHighScore.time > time);
	}
	
	public boolean isMediumHighScore(int time){
		return (mediumHighScore.time > time);
	}
	
	public boolean isHardHighScore(int time){
		return (hardHighScore.time > time);
	}
	
	public boolean setEasyHighScore(String name, int time){
		if (name.trim().isEmpty() || !isEasyHighScore(time)){
			return false;
		}
		easyHighScore = new Score(name, time);
		saveScoreFile(scoresFileName);
		return true;
	}
	
	public boolean setMediumHighScore(String name, int time){
		if (name.trim().isEmpty() || !isMediumHighScore(time)){
			return false;
		}
		mediumHighScore = new Score(name, time);
		saveScoreFile(scoresFileName);
		return true;
	}
	
	public boolean setHardHighScore(String name, int time){
		if (name.trim().isEmpty() || !isHardHighScore(time)){
			return false;
		}
		hardHighScore = new Score(name, time);
		saveScoreFile(scoresFileName);
		return true;
	}
	
	private void openScoreFile(String filename){
		try {
			
			File file = new File(filename);
			
			if (!file.exists()){
				resetScoreboard();
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			easyHighScore = new Score(br.readLine().trim(), Integer.valueOf(br.readLine().trim()));
			mediumHighScore = new Score(br.readLine().trim(), Integer.valueOf(br.readLine().trim()));
			hardHighScore = new Score(br.readLine().trim(), Integer.valueOf(br.readLine().trim()));
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
			
			bw.write(easyHighScore.name);
			bw.newLine();
			bw.write(Integer.toString(easyHighScore.time));
			bw.newLine();
			
			bw.write(mediumHighScore.name);
			bw.newLine();
			bw.write(Integer.toString(mediumHighScore.time));
			bw.newLine();
			
			bw.write(hardHighScore.name);
			bw.newLine();
			bw.write(Integer.toString(hardHighScore.time));
			bw.newLine();
			
			bw.flush();
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetScoreboard(){
		easyHighScore = new Score("-----", 999);
		mediumHighScore = new Score("-----", 999);
		hardHighScore = new Score("-----", 999);
		saveScoreFile(scoresFileName);
	}

	public Score getEasyHighScore(){
		return this.easyHighScore;
	}
	
	public Score getMediumHighScore() {
		return this.mediumHighScore;
	}
	
	public Score getHardHighScore(){
		return this.hardHighScore;
	}
}
