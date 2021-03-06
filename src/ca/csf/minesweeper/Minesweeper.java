package ca.csf.minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class Minesweeper implements TimerUtilsObserver {

	private ArrayList<MinesweeperObserver> observers;

	public TimerUtils timerUtils;

	private Cell[][] cellArray;
	private int sizeX;
	private int sizeY;
	private int nbMines;
	private int[] minesPositions;
	private boolean playerIsDead;
	private int flagsLeft;
	public Difficulty difficulty;
	private Scoreboard scoreboard;
	private boolean isFirstCellClicked;
	private boolean gameIsWon;

	public Minesweeper() {
		observers = new ArrayList<MinesweeperObserver>();
		timerUtils = TimerUtils.getInstance();
	}

	public void addObserver(MinesweeperObserver observer) {
		observers.add(observer);
	}

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

	public void newGame(Difficulty difficulty) throws IndexOutOfBoundsException {
		newGame(difficulty.getNbMines(), difficulty.getSizeX(), difficulty.getSizeY());
		this.difficulty = difficulty;
	}

	public void newGame(int nbMines, int sizeX, int sizeY) throws IndexOutOfBoundsException {

		if (sizeX < 0 || sizeY < 0 || nbMines < 1) {
			throw new IndexOutOfBoundsException("Les valeurs doivent être positives");
		}
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.nbMines = nbMines;
		this.flagsLeft = nbMines;
		this.playerIsDead = false;
		this.gameIsWon = false;
		this.isFirstCellClicked = false;
		timerUtils.reloadTimer();

		scoreboard = new Scoreboard("scores.txt");

		cellArray = new Cell[sizeX][sizeY];

		// Generate random mines positions
		ArrayList<Integer> randomNumbers = new ArrayList<Integer>(sizeX * sizeY);
		for (int i = 0; i < sizeX * sizeY; ++i) {
			randomNumbers.add(i, i);
		}
		Collections.shuffle(randomNumbers);

		// Push the first mines in minesPositions

		minesPositions = new int[nbMines];

		for (int i = 0; i < nbMines; ++i) {
			minesPositions[i] = (int) randomNumbers.get(i);
		}
		initializeCellArray();

		for (MinesweeperObserver observer : observers) {
			observer.setNumberOfFlagsLeft(flagsLeft);
		}
	}

	void initializeCellArray() {
		for (int i = 0; i < getSizeX(); ++i) {
			for (int j = 0; j < getSizeY(); ++j) {
				cellArray[i][j] = new Cell(true);
			}
		}

		for (int element : minesPositions) {
			(this.cellArray[(element % sizeX)][(element / sizeX)]).type = Cell.CellType.MINE;
		}

		for (int i = 0; i < getSizeX(); ++i) {
			for (int j = 0; j < getSizeY(); ++j) {
				calculateMinesTouched(i, j);
			}
		}
		displayCellArray();
	}

	private void calculateMinesTouched(int coordX, int coordY) {
		if (cellArray[coordX][coordY].type != Cell.CellType.MINE) {
			int startingValueX = -1;
			int startingValueY = -1;
			int endingValueX = 1;
			int endingValueY = 1;

			int nbOfMinesTouched = 0;

			if (coordX == 0) {
				startingValueX = 0;
			} else if (coordX == sizeX - 1) {
				endingValueX = 0;
			}

			if (coordY == 0) {
				startingValueY = 0;
			} else if (coordY == sizeY - 1) {
				endingValueY = 0;
			}

			for (int i = startingValueX; i <= endingValueX; i++) {
				for (int j = startingValueY; j <= endingValueY; j++) {
					if (cellArray[coordX + i][coordY + j].type == Cell.CellType.MINE) {
						nbOfMinesTouched++;
					}
				}
			}

			this.cellArray[coordX][coordY].setNbOfMinesTouched(nbOfMinesTouched);
		}
	}

	public void activate(int coordX, int coordY) {

		if (!isFirstCellClicked) {
			isFirstCellClicked = true;
			timerUtils.resetTimer();
		}

		if (!cellArray[coordX][coordY].isFlagged && !cellArray[coordX][coordY].isNotSure) {
			if (cellArray[coordX][coordY].type == Cell.CellType.MINE) { // If step on a mine
				playerDead(coordX, coordY);
			} else {
				discover(coordX, coordY);
			}

			checkIfGameWon();
			displayCellArray();
		}
	}

	private void playerDead(int coordX, int coordY) {

		timerUtils.stopTimer();

		System.out.println("You are dead.");

		cellArray[coordX][coordY].type = Cell.CellType.MINEEXPLODED;
		this.playerIsDead = true;

		for (int x = 0; x < sizeX; ++x) {
			for (int y = 0; y < sizeY; ++y) {
				if (cellArray[x][y].type == Cell.CellType.MINE || cellArray[x][y].type == Cell.CellType.MINEEXPLODED || cellArray[x][y].isFlagged
						|| cellArray[x][y].isNotSure) {
					cellArray[x][y].isHidden = false;

					for (MinesweeperObserver observer : observers) {
						observer.updateCell(x, y, cellArray[x][y]);
					}
				}
			}
		}

		for (MinesweeperObserver observer : observers) {
			observer.playerIsDead();
		}
	}

	public void toggleCellState(int coordX, int coordY) {
		if (cellArray[coordX][coordY].isHidden) {
			if (!cellArray[coordX][coordY].isFlagged && !cellArray[coordX][coordY].isNotSure) {
				cellArray[coordX][coordY].isFlagged = true;
				flagsLeft--;
			} else if (cellArray[coordX][coordY].isFlagged) {
				cellArray[coordX][coordY].isFlagged = false;
				cellArray[coordX][coordY].isNotSure = true;
				flagsLeft++;
			} else if (cellArray[coordX][coordY].isNotSure) {
				cellArray[coordX][coordY].isFlagged = false;
				cellArray[coordX][coordY].isNotSure = false;
			}

			for (MinesweeperObserver observer : observers) {
				observer.setNumberOfFlagsLeft(flagsLeft);
				observer.updateCell(coordX, coordY, cellArray[coordX][coordY]);
			}
		}
	}

	private void discover(int coordX, int coordY) {
		cellArray[coordX][coordY].isHidden = false;
		displayCellArray();
		if (cellArray[coordX][coordY].type == Cell.CellType.EMPTY) {
			int startingValueX = -1;
			int startingValueY = -1;
			int endingValueX = 1;
			int endingValueY = 1;

			if (coordX == 0) {
				startingValueX = 0;
			} else if (coordX == sizeX - 1) {
				endingValueX = 0;
			}

			if (coordY == 0) {
				startingValueY = 0;
			} else if (coordY == sizeY - 1) {
				endingValueY = 0;
			}

			for (int i = startingValueX; i <= endingValueX; i++) {
				for (int j = startingValueY; j <= endingValueY; j++) {
					if (cellArray[coordX + i][coordY + j].isHidden == true && !cellArray[coordX + i][coordY + j].isFlagged
							&& !cellArray[coordX + i][coordY + j].isNotSure) {
						discover(coordX + i, coordY + j);
					}
				}
			}
		}
		for (MinesweeperObserver observer : observers) {
			observer.updateCell(coordX, coordY, cellArray[coordX][coordY]);
		}
	}

	public void displayCellArray() {
		for (int j = 0; j < sizeY; ++j) {
			for (int i = 0; i < sizeX; ++i) {
				if (cellArray[i][j].isFlagged) {
					System.out.print("F");
					continue;
				}
				if (cellArray[i][j].isNotSure) {
					System.out.print("?");
					continue;
				}
				if (cellArray[i][j].isHidden) {
					System.out.print(".");
					continue;
				}
				switch (cellArray[i][j].type.toString()) {
				case "MINE":
					System.out.print("*");
					break;
				case "ONE":
					System.out.print("1");
					break;
				case "TWO":
					System.out.print("2");
					break;
				case "THREE":
					System.out.print("3");
					break;
				case "FOUR":
					System.out.print("4");
					break;
				case "FIVE":
					System.out.print("5");
					break;
				case "SIX":
					System.out.print("6");
					break;
				case "SEVEN":
					System.out.print("7");
					break;
				case "EIGHT":
					System.out.print("8");
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

	private boolean checkIfGameWon() {
		for (Cell[] cellRow : cellArray) {
			for (Cell cell : cellRow) {
				if (cell.type != Cell.CellType.MINE && cell.isHidden) {
					return false;
				}
			}
		}

		timerUtils.stopTimer();

		System.out.println("Game is won !");
		this.gameIsWon = true;

		for (int x = 0; x < sizeX; ++x) {
			for (int y = 0; y < sizeY; ++y) {
				if (cellArray[x][y].type == Cell.CellType.MINE) {

					for (MinesweeperObserver observer : observers) {
						observer.updateCell(x, y, cellArray[x][y]);
					}
				}
			}
		}

		for (MinesweeperObserver observer : observers) {
			observer.gameIsWon();
		}

		boolean isHighScore = false;
		if (difficulty == Difficulty.EASY) {
			isHighScore = scoreboard.isEasyHighScore(timerUtils.getTime());
		} else if (difficulty == Difficulty.MEDIUM) {
			isHighScore = scoreboard.isMediumHighScore(timerUtils.getTime());
		} else if (difficulty == Difficulty.HARD) {
			isHighScore = scoreboard.isHardHighScore(timerUtils.getTime());
		}

		if (isHighScore) {
			for (MinesweeperObserver observer : observers) {
				observer.scoreIsHighScore();
			}
		}

		return true;
	}

	public Cell[][] getCellArray() {
		return this.cellArray;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getNbMines() {
		return this.nbMines;
	}

	public boolean getPlayerIsDead() {
		return this.playerIsDead;
	}

	public void setCellArray(Cell[][] cellArray) {
		this.cellArray = cellArray;
	}

	public int getFlagsLeft() {
		return this.flagsLeft;
	}

	@Override
	public void timeChange(String time) {
	}

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public boolean setScoreboardHighScore(String name, int time) {
		if (difficulty == Difficulty.EASY) {
			return scoreboard.setEasyHighScore(name, time);
		} else if (difficulty == Difficulty.MEDIUM) {
			return scoreboard.setMediumHighScore(name, time);
		} else {
			return scoreboard.setHardHighScore(name, time);
		}
	}

	public boolean isGameWon() {
		return this.gameIsWon;
	}
}
