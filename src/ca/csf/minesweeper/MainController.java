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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import ca.csf.simpleFx.SimpleFXController;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;
import ca.csf.simpleFx.dialogs.SimpleFXDialogs;
import ca.csf.simpleFx.events.WindowFocusEvent;

public class MainController extends SimpleFXController implements TimerUtilsObserver, MinesweeperObserver {

	@FXML
	private VBox gameContainer;
	@FXML
	private ToggleGroup level;
	@FXML
	private Label minesLabel;
	@FXML
	private Button faceButton;
	@FXML
	Label timeLabel;

	private Minesweeper minesweeper;
	private GridPane gameGrid;
	private CellButton[][] cellButtonArray;

	private static TimerUtils timerUtils;

	private boolean hint = false;

	private int sizeX;
	private int sizeY;

	private static final double TOGGLE_BUTTON_HEIGHT = 30.00;
	private static final double TOGGLE_BUTTON_WIDTH = 30.00;

	@Override
	public void timeChange(String time) {
		timeLabel.setText(time);
	}

	private static class MainWindowFocusHandler implements EventHandler<WindowFocusEvent> {

		@Override
		public void handle(WindowFocusEvent event) {
			if (timerUtils.getIsInitialStart()) {
				if (event.getEventType() == WindowFocusEvent.GET_FOCUS) {
					timerUtils.startTimer();
				} else if (event.getEventType() == WindowFocusEvent.LOOSE_FOCUS) {
					timerUtils.stopTimer();
				}
			}
			event.consume();
		}
	};

	@FXML
	public void initialize() {
		newGame();
	}

	@Override
	public void onLoadedStage() {
		this.getSimpleFxStage().setOnFocusChanged(new MainWindowFocusHandler());
	}

	@FXML
	public void newGame() {
		try {
			minesweeper = new Minesweeper();
			minesweeper.addObserver(this);

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

			timerUtils = TimerUtils.getInstance();
			timerUtils.addObserver(this);

			faceButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/normal.png"))));

			sizeX = minesweeper.getSizeX();
			sizeY = minesweeper.getSizeY();

			cellButtonArray = new CellButton[sizeX][sizeY];

			placeTile();

			if (hint) {
				showMines();
			}

			if (this.getSimpleFxStage() != null) {
				this.getSimpleFxStage().sizeToScene();
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
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

	private final class CellClickedEventHandler implements EventHandler<MouseEvent> {
		private int cellX, cellY;

		CellClickedEventHandler(int cellX, int cellY) {
			this.cellX = cellX;
			this.cellY = cellY;
		}

		@Override
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.PRIMARY) {
				minesweeper.activate(this.cellX, this.cellY);
			} else {
				minesweeper.toggleCellState(this.cellX, this.cellY);
			}

			cellButtonArray[this.cellX][this.cellY].setSelected(!minesweeper.getCellArray()[this.cellX][this.cellY].isHidden);
			if (hint) {
				showMines();

			}
			event.consume();
		}
	}

	private void placeTile() {
		if (gameGrid != null) {
			gameContainer.getChildren().removeAll(gameGrid);
		}

		gameGrid = new GridPane();
		gameGrid.setPadding(new Insets(10, 10, 10, 10));

		for (int y = 0; y < sizeY; ++y) {
			for (int x = 0; x < sizeX; ++x) {
				CellButton cellButton = new CellButton(x, y);
				cellButton.setPrefSize(TOGGLE_BUTTON_WIDTH, TOGGLE_BUTTON_HEIGHT);

				cellButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new CellClickedEventHandler(cellButton.x, cellButton.y));

				cellButtonArray[x][y] = cellButton;
				gameGrid.add(cellButton, x, y);
			}
		}

		gameContainer.getChildren().add(gameGrid);
		gameGrid.setAlignment(Pos.CENTER);
	}

	public void showMines() {
		for (int x = 0; x < sizeX; ++x) {
			for (int y = 0; y < sizeY; ++y) {
				if (minesweeper.getCellArray()[x][y].type == Cell.CellType.MINE && !minesweeper.getCellArray()[x][y].isFlagged
						&& !minesweeper.getCellArray()[x][y].isNotSure) {
					if (this.hint) {
						cellButtonArray[x][y].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/MINE.png"))));
					} else {
						cellButtonArray[x][y].setGraphic(null);
					}
				}
			}
		}
	}

	@FXML
	public void levelChange() {
		newGame();
	}

	@FXML
	public void hintCheck() {
		this.hint = !this.hint;

		showMines();
	}

	@FXML
	public void openResults() {
		try {
			SimpleFXScene resultScene = new SimpleFXScene(ResultController.class.getResource("Result.fxml"), new ResultController(minesweeper));

			SimpleFXStage resultStage = new SimpleFXStage("Meilleurs Temps !", StageStyle.UTILITY, resultScene, this.getSimpleFXApplication(),
					this.getSimpleFxStage());

			resultStage.setResizable(false);
			resultStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void openHelpWindow() {
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
	public void openInformations() {
		try {
			SimpleFXScene informationsScene = new SimpleFXScene(InformationsController.class.getResource("Informations.fxml"), new InformationsController());

			SimpleFXStage informationsStage = new SimpleFXStage("À propos !", StageStyle.UTILITY, informationsScene, this.getSimpleFXApplication(),
					this.getSimpleFxStage());

			informationsStage.setResizable(false);
			informationsStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void playerIsDead() {
		faceButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/lose.png"))));

		gameIsFinish();
	}

	@Override
	public void gameIsWon() {
		faceButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/win.png"))));

		gameIsFinish();
	}

	public void gameIsFinish() {
		for (int x = 0; x < sizeX; ++x) {
			for (int y = 0; y < sizeY; ++y) {
				cellButtonArray[x][y].setDisable(true);
			}
		}
	}

	@Override
	public void updateCell(int coordX, int coordY, Cell cell) {
		cellButtonArray[coordX][coordY].setSelected(!cell.isHidden);

		if (cell.isFlagged) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/FLAG.png"))));
			cellButtonArray[coordX][coordY].setSelected(false);
		} else if (cell.isNotSure) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/QUESTION.png"))));
			cellButtonArray[coordX][coordY].setSelected(false);
		} else if ((!cell.isHidden && !cell.isFlagged) && cell.type == Cell.CellType.MINE) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/MINE.png"))));
		} else if (minesweeper.getPlayerIsDead() && cell.isFlagged && cell.type != Cell.CellType.MINE) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/BAD_FLAG.png"))));
		} else if (minesweeper.getPlayerIsDead() && cell.isNotSure && cell.type != Cell.CellType.MINE) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/BAD_QUESTION.png"))));
		} else if (minesweeper.isGameWon() && cell.type == Cell.CellType.MINE) {
			cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/FLAG.png"))));
		} else {
			if (!cell.isHidden) {
				cellButtonArray[coordX][coordY].setDisable(true);
				cellButtonArray[coordX][coordY].setSelected(true);
				String link = "resources/" + cell.type.toString() + ".png";
				try {
					cellButtonArray[coordX][coordY].setGraphic(new ImageView(new Image(getClass().getResourceAsStream(link))));
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}
			} else {
				cellButtonArray[coordX][coordY].setGraphic(null);
			}
		}

	}

	@Override
	public void setNumberOfFlagsLeft(int nbFlags) {
		this.minesLabel.setText(Integer.toString(nbFlags));
	}

	@Override
	public void scoreIsHighScore() {
		String playerName = "";
		do {
			playerName = SimpleFXDialogs.showInputBox("Meilleur résultat", "Veuillez entrer votre nom : ", this.getSimpleFxStage());
			if (playerName == null) {
				return;
			}
		} while (playerName.trim().isEmpty());

		minesweeper.setScoreboardHighScore(playerName, timerUtils.getTime());
	}
}
