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
		
		private Difficulty(int nbMines, int sizeX, int sizeY){
			this.nbMines = nbMines;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}
	}
	
	public Minesweeper(Difficulty difficulty) {
	}
	
	public Minesweeper(int nbMines, int sizeX, int sizeY){
		
	}
}
