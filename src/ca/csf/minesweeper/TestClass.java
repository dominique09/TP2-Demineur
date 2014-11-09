package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClass {

	public static void main(String[] args) {
		//Minesweeper msGame = new Minesweeper();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//msGame.newGame(Minesweeper.Difficulty.MEDIUM);
			
			
			Scoreboard sb = new Scoreboard("ecpizscoresz.txt");
			
			while(!sb.getScoreStack().isEmpty()){
				Score score = sb.getScoreStack().pop();
				System.out.println(score.name + "\t" + score.time + "\n");
			}
			sb.addScore(10, "WorldChampionBro");
			sb.addScore(5, "Dominique");
			
			while (true){
			System.out.println("Entrez un X");
			int x = Integer.parseInt(br.readLine());
			System.out.println("Entrez un Y");
			int y = Integer.parseInt(br.readLine());
			//msGame.activate(x, y);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
