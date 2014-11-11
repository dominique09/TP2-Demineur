package ca.csf.testMinesweeper;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.csf.minesweeper.Score;
import ca.csf.minesweeper.Scoreboard;

public class ScoreboardTest {

	@Test
	public void whenAddValidScore_ThenScoreAdded() {
		Scoreboard sb = new Scoreboard("ScoreboardTest.txt");
		sb.resetScoreboard();
		assertTrue(sb.setEasyHighScore("ME", 30));
		assertEquals("ME", sb.getEasyHighScore().name);
		assertEquals(30, sb.getEasyHighScore().time);
	}

	@Test
	public void whenAddInvalidScore_ThenAddScoreReturnsFalse() {
		Scoreboard sb = new Scoreboard("ScoreboardTest.txt");
		assertFalse(sb.setEasyHighScore("    ", 1));
	}

	@Test
	public void whenScoresCantBeFind_ThenExceptionThrow() {

		Scoreboard sb = new Scoreboard("dumbFile.txt");
		Score testScore = sb.getHardHighScore();

		assertEquals(testScore.time, 999);

	}

}
