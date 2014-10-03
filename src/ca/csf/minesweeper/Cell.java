package ca.csf.minesweeper;

public class Cell {
	public static enum CellType {
		EMPTY, MINE, FLAG, NOTSURE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}
	
	public boolean isHidden;
	
	public CellType type;
	
	public Cell(CellType type, boolean isHidden) {
		this.type = type;
		this.isHidden = isHidden;
	}
}
