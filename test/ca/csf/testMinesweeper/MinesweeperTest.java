package ca.csf.testMinesweeper;

import static org.junit.Assert.*;
import org.junit.Test;

import ca.csf.minesweeper.Cell;
import ca.csf.minesweeper.Minesweeper;
import ca.csf.minesweeper.Minesweeper.Difficulty;

public class MinesweeperTest {

	@Test
	public void whenGameCreatedInEasy_ThenParametersCorrect() {

		Minesweeper testGame = new Minesweeper();

		testGame.newGame(Difficulty.EASY);

		assertEquals(testGame.getNbMines(), 10);
		assertEquals(testGame.getSizeX(), 9);
		assertEquals(testGame.getSizeY(), 9);
	}

	@Test
	public void whenGameCreatedInMedium_ThenParametersCorrect() {

		Minesweeper testGame = new Minesweeper();

		testGame.newGame(Difficulty.MEDIUM);

		assertEquals(testGame.getNbMines(), 40);
		assertEquals(testGame.getSizeX(), 16);
		assertEquals(testGame.getSizeY(), 16);
	}

	@Test
	public void whenGameCreatedInHard_ThenParametersCorrect() {

		Minesweeper testGame = new Minesweeper();

		testGame.newGame(Difficulty.HARD);

		assertEquals(testGame.getNbMines(), 99);
		assertEquals(testGame.getSizeX(), 30);
		assertEquals(testGame.getSizeY(), 16);
	}

	@Test
	public void whenGameCreated_ThenPlayerIsNotDead() {

		Minesweeper testGame = new Minesweeper();

		testGame.newGame(Difficulty.EASY);

		assertEquals(false, testGame.getPlayerIsDead());

	}

	@Test
	public void whenGameCreatedAndParametersWrong_ThenExceptionThrow() {

		Minesweeper testGame = new Minesweeper();

		try {
			testGame.newGame(10, -1, -1);
			fail("Minesweeper doesn't work properly with wrong args");
		} catch (IndexOutOfBoundsException expectedException) {
			assertTrue(true);
		}

	}

	@Test
	public void whenMineClicked_ThenPlayerIsDead() {

		Minesweeper testGame = new Minesweeper();
	
		Cell cellMine = new Cell(true);
		Cell cellEmpty = new Cell(true);
		
		cellMine.type = Cell.CellType.MINE;
		cellEmpty.type = Cell.CellType.ONE;
		
		Cell[][] cellArray = { { cellEmpty, cellEmpty, cellEmpty },
				{ cellEmpty, cellMine, cellEmpty },
				{ cellEmpty, cellEmpty, cellEmpty } };

		testGame.setCellArray(cellArray);
		testGame.activate(1, 1);

		assertEquals(true, testGame.getPlayerIsDead());

	}
	
	@Test
	public void whenEmptyClicked_ThenDiscover() {

		Minesweeper testGame = new Minesweeper();
	
		Cell cellMine = new Cell(true);
		Cell cellOne = new Cell(true);
		Cell cellEmpty = new Cell(true);
		
		cellMine.type = Cell.CellType.MINE;
		cellOne.type = Cell.CellType.ONE;
		cellEmpty.type = Cell.CellType.EMPTY;
		
		Cell[][] cellArray = { { cellEmpty, cellEmpty, cellEmpty },
				{ cellOne, cellOne, cellOne },
				{ cellOne, cellMine, cellOne } };

		testGame.setCellArray(cellArray);
		testGame.activate(0, 1);

		assertEquals(false, cellArray[0][1].isHidden);

	}
	
	@Test
	public void whenCellToggleState_ThenStateChanged() {

		Minesweeper testGame = new Minesweeper();
	
		Cell cellMine = new Cell(true);
		Cell cellOne = new Cell(true);
		Cell cellEmpty = new Cell(true);
		
		cellMine.type = Cell.CellType.MINE;
		cellOne.type = Cell.CellType.ONE;
		cellEmpty.type = Cell.CellType.EMPTY;
		
		Cell[][] cellArray = { { cellEmpty, cellEmpty, cellEmpty },
				{ cellOne, cellOne, cellOne },
				{ cellOne, cellMine, cellOne } };

		testGame.setCellArray(cellArray);
		
		//First State
		testGame.toggleCellState(2, 1);
		assertEquals(true, cellArray[2][1].isFlagged);
		
		//Second State
		testGame.toggleCellState(2, 1);
		assertEquals(true, cellArray[2][1].isNotSure);

		//Both are returned to normal
		testGame.toggleCellState(2, 1);
		assertEquals(false, cellArray[2][1].isFlagged);
		assertEquals(false, cellArray[2][1].isNotSure);

	}
}
