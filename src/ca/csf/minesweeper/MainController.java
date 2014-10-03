package ca.csf.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import ca.csf.simpleFx.SimpleFXController;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;

public class MainController extends SimpleFXController{
	
	
	@FXML
	private VBox gameContainer;
	
	@FXML
	public void newGame(){
		System.out.println("test");
	}
	
	@FXML
	public void openHelpWindow(){
		try {
			SimpleFXScene helpScene = new SimpleFXScene(HelpController.class.getResource("Help.fxml"), new HelpController());
			
			SimpleFXStage helpStage = new SimpleFXStage("Aide", StageStyle.UTILITY, helpScene, this.getSimpleFXApplication(), this.getSimpleFxStage());
			
			helpStage.setResizable(false);
			helpStage.show();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void openInformations(){
		try {
			SimpleFXScene informationsScene = new SimpleFXScene(HelpController.class.getResource("Help.fxml"), new HelpController());
			
			SimpleFXStage informationsStage = new SimpleFXStage("Aide", StageStyle.UTILITY, informationsScene, this.getSimpleFXApplication(), this.getSimpleFxStage());
			
			informationsStage.setResizable(false);
			informationsStage.show();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
