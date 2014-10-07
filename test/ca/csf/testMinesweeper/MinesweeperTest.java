package ca.csf.testMinesweeper;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.csf.minesweeper.Cell;
import ca.csf.minesweeper.Cell.CellType;
import ca.csf.minesweeper.Minesweeper;
import ca.csf.minesweeper.Minesweeper.Difficulty;

public class MinesweeperTest {

	
	@Test
	public void whenGameCreatedInEasy_ThenParametersCorrect(){
		
		Minesweeper testGame = new Minesweeper();
		
		testGame.newGame(Difficulty.EASY);
		
		assertEquals(testGame.getNbMines(), 10);
		assertEquals(testGame.getSizeX(), 9);
		assertEquals(testGame.getSizeY(), 9);
	}
	
	@Test
	public void whenGameCreatedInMedium_ThenParametersCorrect(){
		
		Minesweeper testGame = new Minesweeper();
		
		testGame.newGame(Difficulty.MEDIUM);
		
		assertEquals(testGame.getNbMines(), 40);
		assertEquals(testGame.getSizeX(), 16);
		assertEquals(testGame.getSizeY(), 16);
	}
	
	@Test
	public void whenGameCreatedInHard_ThenParametersCorrect(){
		
		Minesweeper testGame = new Minesweeper();
		
		testGame.newGame(Difficulty.HARD);
		
		assertEquals(testGame.getNbMines(), 99);
		assertEquals(testGame.getSizeX(), 30);
		assertEquals(testGame.getSizeY(), 16);
	}
	
	@Test
	public void whenGameCreated_ThenPlayerIsNotDead(){
		
		Minesweeper testGame = new Minesweeper();
		
		testGame.newGame(Difficulty.EASY);
		
		assertEquals(false, testGame.getPlayerIsDead());

	}
	
	@Test
	public void whenMineClicked_ThenPlayerIsDead(){
		
		Minesweeper testGame = new Minesweeper();
		
		testGame.newGame(Difficulty.EASY);
		Cell testMine = null;
		Cell[][] testGameArray = testGame.getCellArray();
		int positionX = 0;
		int positionY = 0;
		int mineX;
 		int mineY;
		do{
			testMine = testGameArray[positionX][positionY];
			mineX = positionX;
			mineY = positionY;
			if(positionX == 8){
				positionY++;
				positionX = 0;
			}else{
				positionX++;
			}
		}while(testMine.type != CellType.MINE );
		testGame.activate(mineX, mineY);
		
		assertEquals(true, testGame.getPlayerIsDead());

	}
}
