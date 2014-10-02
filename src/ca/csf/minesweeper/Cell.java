package ca.csf.minesweeper;

public class Cell {
	private static enum CellType {
		HIDDEN, EMPTY, MINE, FLAG, NOTSURE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}
	
	public CellType type;
	
	public Cell(CellType type) {
		this.type = type;
	}
}
