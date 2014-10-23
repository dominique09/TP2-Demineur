package ca.csf.minesweeper;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

import ca.csf.minesweeper.Minesweeper.Difficulty;

public class TestClass {

	public static void main(String[] args) {
		Minesweeper msGame = new Minesweeper();
		try {
			Console console = System.console();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			msGame.newGame(Minesweeper.Difficulty.EASY);
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
