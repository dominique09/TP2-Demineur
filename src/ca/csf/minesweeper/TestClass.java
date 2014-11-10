package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClass {

	public static void main(String[] args) {
		Minesweeper msGame = new Minesweeper();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			msGame.newGame(Minesweeper.Difficulty.MEDIUM);
			
			Scoreboard sb = new Scoreboard("scores.txt");
			sb.setEasyHighScore("PATATE", 17);
			sb.setHardHighScore("FUCK", 37);
			
			while (true){
			System.out.println("Entrez un X");
			int x = Integer.parseInt(br.readLine());
			System.out.println("Entrez un Y");
			int y = Integer.parseInt(br.readLine());
			msGame.activate(x, y);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
