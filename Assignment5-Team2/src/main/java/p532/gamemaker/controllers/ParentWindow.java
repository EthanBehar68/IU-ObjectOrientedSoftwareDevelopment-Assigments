package p532.gamemaker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.stage.Stage;
import p532.gamemaker.Constants;
import p532.gamemaker.views.GameDesignScene;
import p532.gamemaker.views.GamePlayScene;

import java.io.IOException;

/**
 * Holds all views for the game maker. The app starts from here.
 */
public class ParentWindow extends Application {
	private static GameDesignScene gameDesignScene = null;
	private static GamePlayScene gamePlayScene = null;
	private static Stage stage = null;

	public static GameDesignScene getGameDesignScene() {
		return gameDesignScene;
	}

	public static void setGameDesignScene(GameDesignScene gameDesignScene) {
		ParentWindow.gameDesignScene = gameDesignScene;
	}

	public static GamePlayScene getGamePlayScene() {
		return gamePlayScene;
	}

	public static void setGamePlayScene(GamePlayScene gamePlayScene) {
		ParentWindow.gamePlayScene = gamePlayScene;
	}

	/**
	 * Configures the window by loading in the design & play scenes
	 */
	@Override
	public void start(Stage primaryStage) throws InterruptedException, IOException {
		// Load in the XML layout of the editor window
		Parent editorRoot = FXMLLoader.load(getClass().getClassLoader().getResource("parent-editor-window.fxml"));

		// Load in the XML of the testing window
		Parent playRoot = FXMLLoader.load(getClass().getClassLoader().getResource("parent-play-window.fxml"));

		// Set up the scenes
		gameDesignScene = new GameDesignScene(editorRoot, Constants.INITIAL_SCREEN_WIDTH,
				Constants.INITIAL_SCREEN_HEIGHT);
		gamePlayScene = new GamePlayScene(playRoot, Constants.INITIAL_SCREEN_WIDTH, Constants.INITIAL_SCREEN_HEIGHT);
		primaryStage.setScene(gameDesignScene);
		primaryStage.show();

		// Change background colors
		editorRoot.setStyle("-fx-background-color: #AAAAAA");
		playRoot.setStyle("-fx-background-color: #AAAAAA");

		// Set up the window
		primaryStage.setTitle("Game Maker");
		primaryStage.show();
		ParentWindow.stage = primaryStage;
	}

	/**
	 * End the game when the window closes
	 * 
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		getGamePlayScene().getTickObservable().stopGameLoop();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static GameDesignScene switchToGameDesignScene() {
		stage.setScene(gameDesignScene);
		return gameDesignScene;
	}

	public static GamePlayScene switchToGamePlayScene() {
		stage.setScene(gamePlayScene);
		return gamePlayScene;
	}
}
