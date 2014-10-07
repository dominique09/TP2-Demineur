package ca.csf.minesweeper;

import ca.csf.minesweeper.Minesweeper.Difficulty;

public class TestClass {

	public static void main(String[] args) {
		Minesweeper msGame = new Minesweeper();
		try {
			msGame.newGame(Minesweeper.Difficulty.HARD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
