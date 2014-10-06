package ca.csf.minesweeper;

public class Cell {
	public static enum CellType {
		EMPTY, MINE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}
	
	public boolean isHidden;
	public boolean isFlagged;
	public boolean isNotSure;
	
	public CellType type;
	
	public Cell(CellType type, boolean isHidden) {
		this.type = type;
		this.isHidden = isHidden;
		this.isFlagged = false;
		this.isNotSure = false;
	}
}
