/**
 * @author: Ethan Taylor Behar, Christian Dummer, Saurabh Gulati
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

import java.util.ArrayList;

import application.BreakoutApplication;
import breakout.GameManager;
import composite.pattern.Layouts.LayoutType;
import game.engine.PausableGameEngine;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LayoutManager implements Layable {
	// App Window
	public Stage appStage;
	public Group rootGroup;
	public Pane rootPane;
	public LayoutType currentLayout = LayoutType.RIGHT;

	// Control Panel Stuff
	public LayablePane controlPane;

	// Game "Window"
	public Group gameRoot;
	public LayableCanvas gameCanvas;
	public Scene gameScene;

	// Layable stuff
	public ArrayList<Layable> layables = new ArrayList<Layable>();

	public LayoutManager(Stage appStage) {
		this.appStage = appStage;
		appStage.setWidth(Layouts.RightLayout.rootPaneWidth);
		appStage.setWidth(Layouts.RightLayout.rootPaneHeight);
	}

	public void buildStage() {
		// Build root group and root pane
		rootGroup = new Group();
		rootPane = new Pane();
		rootPane.setPrefSize(Layouts.RightLayout.rootPaneWidth, Layouts.RightLayout.rootPaneHeight);
		rootPane.setStyle("-fx-background-color: #FF00FF");
		rootGroup.getChildren().add(rootPane);

		// Make the gameCanvas
		gameCanvas = new LayableCanvas(Layouts.RightLayout.gameCanvasWidth, Layouts.RightLayout.gameCanvasHeight);
		layables.add(gameCanvas);
		rootPane.getChildren().add(gameCanvas);
		gameCanvas.setLayoutX(Layouts.RightLayout.gameCanvasX);

		gameScene = new Scene(rootGroup);
		// Not sure if this is right...
		appStage.setScene(gameScene);

		controlPane = new LayablePane();
		layables.add(controlPane);
		controlPane.setPrefSize(Layouts.RightLayout.controlPaneWidth, Layouts.RightLayout.controlPaneHeight);
		rootGroup.getChildren().add(controlPane);
		controlPane.setLayoutX(Layouts.RightLayout.controlPaneX);
		controlPane.setStyle("-fx-background-color: #000000");

	}

	public void createButtons(PausableGameEngine gameEngine, GameManager gameManager,
			BreakoutApplication breakoutApplication) {
		LayableButton button = new LayableButton("Resume");
		button.setOnAction(value -> {
			gameEngine.resume();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Reset");
		button.setOnAction(value -> {
			gameEngine.reset();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Pause");
		button.setOnAction(value -> {
			gameEngine.pause();
		});
		addButtonToControlPanel(button);
		button = new LayableButton("Undo");
		button.setOnAction(value -> {
			gameEngine.undo();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Undo All");
		button.setOnAction(value -> {
			gameEngine.undoAll();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Redo");
		button.setOnAction(value -> {
			gameEngine.redo();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Redo All");
		button.setOnAction(value -> {
			gameEngine.redoAll();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Replay");
		button.setOnAction(value -> {
			gameEngine.replay();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Random Color");
		button.setOnAction(value -> {
			gameManager.toggleColorUpdate();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Save");
		button.setOnAction(value -> {
			gameEngine.pause();
			breakoutApplication.saveGame();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Load");
		button.setOnAction(value -> {
			gameEngine.pause();
			breakoutApplication.loadGame();
		});
		addButtonToControlPanel(button);

		button = new LayableButton("Change Layout");
		button.setOnAction(value -> {
			startChangeLayout();
		});
		addButtonToControlPanel(button);

		changeLayout(currentLayout, 0, 0, 0);
	}

	public void addButtonToControlPanel(LayableButton button) {
		controlPane.AddChild(button);
	}

	public void showStage() {
		appStage.show();
	}

	public Canvas getGameCanvas() {
		return gameCanvas;
	}

	public Scene getGameScene() {
		return gameScene;
	}

	public void startChangeLayout() {
		switch (currentLayout) {
		case LEFT:
			currentLayout = LayoutType.BOTTOM;
			break;
		case BOTTOM:
			currentLayout = LayoutType.RIGHT;
			break;
		case RIGHT:
			currentLayout = LayoutType.TOP;
			break;
		case TOP:
			currentLayout = LayoutType.LEFT;
			break;
		default:
			throw new UnsupportedOperationException("Please implement functionality for LayoutType: " + currentLayout);
		}
		changeLayout(currentLayout, 0, 0, 0);
	}

	@Override
	public void changeLayout(LayoutType layout, int parentX, int parentY, int index) {
		for (Layable lay : layables) {
			lay.changeLayout(layout, parentX, parentY, 0);
			index++;
		}

		System.out.println("Layout has been changed to: " + layout);

		switch (layout) {
		case LEFT:
			appStage.setMinWidth(Layouts.LeftLayout.rootPaneWidth);
			appStage.setMinHeight(Layouts.LeftLayout.rootPaneHeight);
			appStage.setMaxWidth(Layouts.LeftLayout.rootPaneWidth);
			appStage.setMaxHeight(Layouts.LeftLayout.rootPaneHeight);
			break;
		case RIGHT:
			appStage.setMinWidth(Layouts.RightLayout.rootPaneWidth);
			appStage.setMinHeight(Layouts.RightLayout.rootPaneHeight);
			appStage.setMaxWidth(Layouts.RightLayout.rootPaneWidth);
			appStage.setMaxHeight(Layouts.RightLayout.rootPaneHeight);
			break;
		case TOP:
			appStage.setMinWidth(Layouts.TopLayout.rootPaneWidth);
			appStage.setMinHeight(Layouts.TopLayout.rootPaneHeight);
			appStage.setMaxWidth(Layouts.TopLayout.rootPaneWidth);
			appStage.setMaxHeight(Layouts.TopLayout.rootPaneHeight);
			break;
		case BOTTOM:
			appStage.setMinWidth(Layouts.BottomLayout.rootPaneWidth);
			appStage.setMinHeight(Layouts.BottomLayout.rootPaneHeight);
			appStage.setMaxWidth(Layouts.BottomLayout.rootPaneWidth);
			appStage.setMaxHeight(Layouts.BottomLayout.rootPaneHeight);
			break;
		}
	}

	@Override
	public void addLayable(Layable layable) {
		layables.add(layable);
	}

	@Override
	public void removeLayable(Layable layable) {
		layables.remove(layable);
	}
}
