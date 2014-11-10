package ca.csf.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ca.csf.simpleFx.SimpleFXController;

public class InformationsController extends SimpleFXController {
	@FXML
	public ImageView logoImageView;
	
	@FXML
	public void initialize(){
		logoImageView.setImage(new Image(this.getClass().getResource("resources/logo.png").toExternalForm()));;
	}
	
	@FXML
	public void closeInformations(ActionEvent event){
		this.getSimpleFxStage().close();
	}
	
}
