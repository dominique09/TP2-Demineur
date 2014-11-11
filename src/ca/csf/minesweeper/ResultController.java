package ca.csf.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ca.csf.simpleFx.SimpleFXController;

public class ResultController extends SimpleFXController {
	@FXML
	private Label timeEasy;
	@FXML
	private Label timeMedium;
	@FXML
	private Label timeHard;
	@FXML
	private Label nameEasy;
	@FXML
	private Label nameMedium;
	@FXML
	private Label nameHard;

	private Minesweeper minesweeperGameReference;

	public ResultController(Minesweeper minesweeper) {
		this.minesweeperGameReference = minesweeper;
	}

	@FXML
	public void initialize() {
		writeResult();
	}

	private void writeResult() {
		timeEasy.setText(Integer.toString(minesweeperGameReference.getScoreboard().getEasyHighScore().time) + " s");
		nameEasy.setText(minesweeperGameReference.getScoreboard().getEasyHighScore().name);
		timeMedium.setText(Integer.toString(minesweeperGameReference.getScoreboard().getMediumHighScore().time) + " s");
		nameMedium.setText(minesweeperGameReference.getScoreboard().getMediumHighScore().name);
		timeHard.setText(Integer.toString(minesweeperGameReference.getScoreboard().getHardHighScore().time) + " s");
		nameHard.setText(minesweeperGameReference.getScoreboard().getHardHighScore().name);
	}

	@FXML
	public void eraseTime() {
		this.minesweeperGameReference.getScoreboard().resetScoreboard();
		writeResult();
	}

	@FXML
	public void closeResult() {
		this.getSimpleFxStage().close();
	}

}
