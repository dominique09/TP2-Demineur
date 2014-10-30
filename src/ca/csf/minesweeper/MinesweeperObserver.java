package ca.csf.minesweeper;

public interface MinesweeperObserver {
	public void playerIsDead(int coordX, int coordY);
	public void gameIsWon(boolean gameIsWon);
	public void updateCell(int coordX, int coordY, Cell cell);
	public void setNumberOfFlagsLeft(int nbFlags);
}