package ca.csf.minesweeper;

public interface MinesweeperObserver {

	public void playerIsDead();

	public void gameIsWon();

	public void scoreIsHighScore();

	public void updateCell(int coordX, int coordY, Cell cell);

	public void setNumberOfFlagsLeft(int nbFlags);
}
