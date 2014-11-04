package ca.csf.testMinesweeper;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.csf.minesweeper.Score;
import ca.csf.minesweeper.Scoreboard;

public class ScoreboardTest {

	@Test
	public void whenAddValidScore_ThenScoreAdded(){
		Scoreboard sb = new Scoreboard();
		
		assertTrue(sb.addScore(30, "TheGuyWhosProgrammingThisShit"));
		assertEquals(new Score(30, "TheGuyWhosProgrammingThisShit"), sb.getScoreStack().peek());
	}
	
	@Test
	public void whenAddInvalidScore_ThenAddScoreReturnsFalse(){
		Scoreboard sb = new Scoreboard();
		assertFalse(sb.addScore(1, "    "));
	}
	
	@Test
	public void ifNewScoreTimeIsHigher_ThenIsHighScoreReturnsFalse() {
		Scoreboard sb = new Scoreboard();
		sb.addScore(10, "Bro");
		assertFalse(sb.isHighScore(30));
	}
	
	@Test
	public void ifNewScoreTimeIsLower_ThenIsHighScoreReturnsTrue(){
		Scoreboard sb = new Scoreboard();
		sb.addScore(30, "Doge");
		assertTrue(sb.isHighScore(10));
	}
	
	
	
	
	
}
