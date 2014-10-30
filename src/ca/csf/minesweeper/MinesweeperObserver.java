package ca.csf.minesweeper;

public interface MinesweeperObserver {
	public void playerIsDead(boolean playerIsDead);
	public void gameIsWon(boolean gameIsWon);
}
