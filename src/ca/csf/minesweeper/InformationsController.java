package ca.csf.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ca.csf.simpleFx.SimpleFXController;

public class InformationsController extends SimpleFXController {

	@FXML
	public void closeInformations(ActionEvent event){
		this.getSimpleFxStage().close();
	}
	
}
