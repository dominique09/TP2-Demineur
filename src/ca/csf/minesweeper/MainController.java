package ca.csf.minesweeper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	@FXML
	private Label minesLabel;
	@FXML
	private Button faceButton;

	private Minesweeper minesweeper;
	private GridPane gameGrid;
	private CellButton[][] cellButtonArray;

	private int sizeX;
	private int sizeY;

	private static final double TOGGLE_BUTTON_HEIGHT = 30.00;
	private static final double TOGGLE_BUTTON_WIDTH = 30.00;

	@FXML
	public void initialize(){
		newGame();
	}
	
	@FXML
	public void newGame() {
		try {
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

			sizeX = minesweeper.getSizeX();
			sizeY = minesweeper.getSizeY();
			
			cellButtonArray = new CellButton[sizeX][sizeY];

			placeTile();
			if (this.getSimpleFxStage() != null)
			{
			this.getSimpleFxStage().sizeToScene();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public class CellButton extends ToggleButton {

		public int x;
		public int y;

		public CellButton(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	private void placeTile() {
		if (gameGrid != null){
			gameContainer.getChildren().removeAll(gameGrid);
		}
		gameGrid = new GridPane();
		gameGrid.setPadding(new Insets(10, 10, 10, 10));

		for (int y = 0; y < sizeY; ++y) {
			for (int x = 0; x < sizeX; ++x) {
				CellButton cellButton = new CellButton(x, y);
				cellButton.setPrefSize(TOGGLE_BUTTON_WIDTH,
						TOGGLE_BUTTON_HEIGHT);

				cellButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								if (event.getButton() == MouseButton.PRIMARY) {
									minesweeper.activate(cellButton.x,
											cellButton.y);
								} else {
									// minesweeper. function have to be determine
								}

								updateGameGrid();
								updateMineNumber();
								updateFaceButton();
							}

						});
				
				cellButtonArray[x][y] = cellButton;

				gameGrid.add(cellButton, x, y);
			}
		}

		gameContainer.getChildren().add(gameGrid);
		gameGrid.setAlignment(Pos.CENTER);
		updateMineNumber();
	}

	private void updateMineNumber() {
		minesLabel.setText("8");
	}

	private void updateGameGrid() {
		Cell[][] cellArray = minesweeper.getCellArray();

		for (int y = 0; y < sizeY; ++y) {
			for (int x = 0; x < sizeX; ++x) {
				cellButtonArray[x][y].setSelected(!cellArray[x][y].isHidden);
				
				if(!cellArray[x][y].isHidden){
					cellButtonArray[x][y].setDisable(true);
					cellButtonArray[x][y].setText("*");
				}
				
				if (cellArray[x][y].isFlagged){
					cellButtonArray[x][y].setText("F");
				}
				
				if (cellArray[x][y].isNotSure){
					cellButtonArray[x][y].setText("?");
				}
			}
		}
	}
	
	private void updateFaceButton(){
		if ( minesweeper.getPlayerIsDead() == true){
			this.faceButton.setText(");");
		} else {
			this.faceButton.setText(":)");
		}
	}

	@FXML
	public void levelChange() {
		newGame();
	}

	@FXML
	public void openResults() {
		try {
			SimpleFXScene resultScene = new SimpleFXScene(
					ResultController.class
							.getResource("Result.fxml"),
					new ResultController());

			SimpleFXStage resultStage = new SimpleFXStage("Meilleurs Temps !",
					StageStyle.UTILITY, resultScene,
					this.getSimpleFXApplication(), this.getSimpleFxStage());

			resultStage.setResizable(false);
			resultStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void hintCheck() {
		minesweeper.hintActivate();
		this.updateGameGrid();
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
					new InformationsController());

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
