package ca.csf.minesweeper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import ca.csf.simpleFx.SimpleFXController;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;

public class MainController extends SimpleFXController {

	@FXML
	private VBox gameContainer;
	@FXML
	private ToggleGroup level;

	private Minesweeper minesweeper;
	private GridPane gameGrid;
	
	private static final double TOGGLE_BUTTON_HEIGHT = 30.00;
	private static final double TOGGLE_BUTTON_WIDTH = 60.00;

	@FXML
	public void newGame() {
		minesweeper = new Minesweeper();
		String selectedLevel = ((RadioMenuItem) level.getSelectedToggle()).getId();
		switch (selectedLevel) {
		case "EASY":
			minesweeper.newGame(Minesweeper.Difficulty.EASY);
			break;
		case "MEDIUM":
			minesweeper.newGame(Minesweeper.Difficulty.MEDIUM);
			break;
		case "HARD":
			minesweeper.newGame(Minesweeper.Difficulty.HARD);
			break;
		}
		
		placeTile();
	}
	
	private void placeTile(){
		int sizeX = 9;
		int sizeY = 9;
		
		gameGrid = new GridPane();
		
		for (int x=0; x < sizeX; ++x){
			for (int y=0; y < sizeY; ++y){
				ToggleButton toggleButton = new ToggleButton();
				toggleButton.prefHeight(TOGGLE_BUTTON_HEIGHT);
				toggleButton.prefWidth(TOGGLE_BUTTON_WIDTH);
				toggleButton.setId(x + "," + y);
				
				toggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY){
							
						} else {
							
						}							
					}
					
				});
				
				gameGrid.add(toggleButton, x, y);
			}
		}
		
		gameContainer.getChildren().add(gameGrid);
	}
	
	
	@FXML
	public void levelChange() {

	}

	@FXML
	public void openResults() {

	}

	@FXML
	public void hintCheck() {

	}

	@FXML
	public void openHelpWindow() {
		try {
			SimpleFXScene helpScene = new SimpleFXScene(
					HelpController.class.getResource("Help.fxml"),
					new HelpController());

			SimpleFXStage helpStage = new SimpleFXStage("Aide",
					StageStyle.UTILITY, helpScene,
					this.getSimpleFXApplication(), this.getSimpleFxStage());

			helpStage.setResizable(false);
			helpStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void openInformations() {
		try {
			SimpleFXScene informationsScene = new SimpleFXScene(
					InformationsController.class
							.getResource("Informations.fxml"),
					new HelpController());

			SimpleFXStage informationsStage = new SimpleFXStage("À propos !",
					StageStyle.UTILITY, informationsScene,
					this.getSimpleFXApplication(), this.getSimpleFxStage());

			informationsStage.setResizable(false);
			informationsStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
