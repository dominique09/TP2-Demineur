package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Minesweeper {
	private Cell[][] cellArray;
	private int sizeX;
	private int sizeY;
	private int nbMines;
	private int[] minesPositions;
	private boolean playerIsDead;
	
	public static enum Difficulty {
		EASY(10, 9, 9), MEDIUM(40, 16, 16), HARD(99, 20, 20);

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

	public void newGame(Difficulty difficulty) throws IndexOutOfBoundsException {
		newGame(difficulty.getNbMines(), difficulty.getSizeX(), difficulty.getSizeY());
	}

	public void newGame(int nbMines, int sizeX, int sizeY) throws IndexOutOfBoundsException {
		
		if (sizeX < 0 || sizeY < 0 || nbMines < 1){
			throw new IndexOutOfBoundsException("Les valeurs doivent être positives");
		}
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.nbMines = nbMines;
		
		playerIsDead = false;
		
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
		initializeCellArray();
	}
	
	void initializeCellArray() {
		for (int i = 0; i < getSizeY(); i++){
			for (int j = 0; j < getSizeX(); ++j){
				cellArray[j][i] = new Cell(Cell.CellType.EMPTY, true);
			}
        }
		
		for (int element : minesPositions) {
			(this.cellArray[(element % getSizeY())][(element / getSizeX())]).type = Cell.CellType.MINE;
		}
		displayCellArray();
	}
	
	public void activate(int coordX, int coordY){
		
		if (cellArray[coordX][coordY].type == Cell.CellType.MINE){
			playerIsDead = true;
			
			// Show all mines
			
			for (Cell[] row : cellArray){
				for (Cell cell : row){
					if (cell.type == Cell.CellType.MINE){
						cell.isHidden = false;
					}
				}
			}
		}
		
	}
	
	public void displayCellArray(){
		for (int i = 0; i< sizeX; ++i){
			for (int j = 0; j < sizeY; ++j){
				switch (cellArray[i][j].type.toString()){
				case "MINE":
					System.out.print("*");
					break;
				case "EMPTY":
					System.out.print("-");
					break;
				}
			}
			System.out.println();
		}
		System.out.println("===END===");
	}
	
	public Cell[][] getCellArray(){
		return this.cellArray;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}
}
