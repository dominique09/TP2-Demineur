package ca.csf.minesweeper;

import java.util.Hashtable;

public class Minesweeper {
	private Cell[] cellArray;
	private int sizeX;
	private int sizeY;
	private int nbMines;

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

	public Minesweeper(Difficulty difficulty) {
		this(difficulty.getNbMines(), difficulty.getSizeX(), difficulty.getSizeY());
	}

	public Minesweeper(int nbMines, int sizeX, int sizeY) {

	}
}
