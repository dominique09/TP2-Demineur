package ca.csf.minesweeper;

public class Cell {
	public static enum CellType {
		EMPTY, MINE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}
	
	public boolean isHidden;
	public boolean isFlagged;
	public boolean isNotSure;
	private int nbOfMinesTouched;
	
	public CellType type;
	
	public Cell(boolean isHidden) {
		this.type = null;
		this.isHidden = isHidden;
		this.isFlagged = false;
		this.isNotSure = false;
		this.nbOfMinesTouched = 0;
	}
	
	public void setNbOfMinesTouched(int nbOfMines){
		this.nbOfMinesTouched = nbOfMines;
		switch(nbOfMinesTouched){
		case 0:
			type = CellType.EMPTY;
			break;
		case 1:
			type = CellType.ONE;
			break;
		case 2:
			type = CellType.TWO;
			break;
		case 3:
			type = CellType.THREE;
			break;
		case 4:
			type = CellType.FOUR;
			break;
		case 5:
			type = CellType.FIVE;
			break;
		case 6:
			type = CellType.SIX;
			break;
		case 7:
			type = CellType.SEVEN;
			break;
		case 8:
			type = CellType.EIGHT;
			break;
		}
	}
	
	public int getNbMinesTouched(){
		return this.nbOfMinesTouched;
	}
}
