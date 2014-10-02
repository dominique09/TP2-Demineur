package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Minesweeper {
	private Cell[][] cellArray;
	private int sizeX;
	private int sizeY;
	private int nbMines;
	private int[] minesPositions;
	
	public static enum Difficulty {
		EASY(10, 9, 9), MEDIUM(40, 16, 16), HARD(99, 30, 16);

		private int nbMines;
		private int sizeX;
		private int sizeY;

		private Difficulty(int nbMines, int sizeX, int sizeY) {
			this.nbMines = nbMines;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}

		public int getNbMines() {
			return this.nbMines;
		}

		public int getSizeX() {
			return this.sizeX;
		}

		public int getSizeY() {
			return this.sizeY;
		}
	}

	public void newGame(Difficulty difficulty) {
		newGame(difficulty.getNbMines(), difficulty.getSizeX(), difficulty.getSizeY());
	}

	public void newGame(int nbMines, int sizeX, int sizeY) {
		cellArray = new Cell[sizeX][sizeY];
	// Generate random mines positions
		ArrayList<Integer> randomNumbers = new ArrayList<Integer>(sizeX*sizeY);
		for (int i = 0; i < sizeX*sizeY; ++i){
			randomNumbers.add(i, i);
		}
		Collections.shuffle(randomNumbers);
		
		// Push the first mines in minesPositions
		
		minesPositions = new int[nbMines];
		
		for (int i = 0; i < nbMines; ++i){
			minesPositions[i] = (int) randomNumbers.get(i);
		}
	}
}
