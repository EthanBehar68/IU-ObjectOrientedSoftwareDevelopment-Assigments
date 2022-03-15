package gamemaker;

import gamemaker.controller.Controller;
import gamemaker.model.Model;
import gamemaker.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class GameMakerApplication extends Application {

	public static final Logger logger = Logger.getLogger(GameMakerApplication.class);

	@Override
	public void start(Stage stage) throws IOException {
		// log4j configuration
		BasicConfigurator.configure();
//		logger.log(Level.INFO, GameMakerApplication.class.toString());

		// Load the loader
		FXMLLoader fxmlLoader = new FXMLLoader(GameMakerApplication.class.getResource("game-maker-view.fxml"));
		// Load the scene
		Scene scene = new Scene(fxmlLoader.load());
		// Load css for the scene
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		// Get View from the loader
		View view = (View) fxmlLoader.getController();
		
		// Create the Model
		Model model = new Model(view);
		// Give View gameCanvas reference
		view.setGameCanvas(model.getGameCanvas());
		// Now set up Drag Controller
		view.setupDragController();
		
		// Create the Controller
		Controller controller = new Controller(model, view);
		// Give View Controller Reference
		view.setController(controller);

		// Finish set up and show
		stage.setResizable(false);
		stage.setTitle("GameMaker");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}