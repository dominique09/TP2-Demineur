package ca.csf.minesweeper;

import javafx.stage.StageStyle;
import ca.csf.simpleFx.SimpleFXApplication;
import ca.csf.simpleFx.SimpleFXApplicationLauncher;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;

public class Main extends SimpleFXApplication {
		
	public static void main(String[] args) {
		SimpleFXApplicationLauncher.startSimpleFXApplication(Main.class, args);
	}

	public void start() {
		try {
			SimpleFXScene mainFXScene = new SimpleFXScene(
					MainController.class.getResource("Main.fxml"),
					MainController.class.getResource("application.css"),
					new MainController());

			SimpleFXStage mainFXStage = new SimpleFXStage("Démineur", StageStyle.DECORATED, mainFXScene, this);
			mainFXStage.setResizable(false);
			mainFXStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
