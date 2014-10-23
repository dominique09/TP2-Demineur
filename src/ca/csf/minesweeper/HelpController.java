package ca.csf.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import ca.csf.simpleFx.SimpleFXController;

public class HelpController extends SimpleFXController{

	@FXML
	private WebView helpWebView;
	
	@FXML
	public void initialize(){
		helpWebView.getEngine().load(HelpController.class.getResource("resources/help.html").toExternalForm());
	}
	
	@FXML
	public void closeHelp(){
		this.getSimpleFxStage().close();
	}
	
}
