package ca.csf.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ca.csf.simpleFx.SimpleFXController;

public class ResultController extends SimpleFXController{
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
	
	@FXML
	public void initialize(){
		timeEasy.setText("");
		nameEasy.setText("");
		timeMedium.setText("");
		nameMedium.setText("");
		timeHard.setText("999 seconds");
		nameHard.setText("Nom Hard");
	}
	
	@FXML
	public void eraseTime(){
		
	}
	
	@FXML
	public void closeResult(){
		this.getSimpleFxStage().close();
	}
	
}
