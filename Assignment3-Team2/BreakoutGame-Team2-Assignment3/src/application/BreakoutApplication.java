/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package application;

import breakout.GameManager;
import collision.detection.CollisionHandler2D;
import command.pattern.CommandInvoker;
import composite.pattern.LayoutManager;
import composite.pattern.RootSavable;
import game.engine.ObjectPooler;
import game.engine.PausableGameEngine;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import rendering.Renderer;

public class BreakoutApplication {
	
	// Window Related
	private Stage appStage;
	private LayoutManager layoutManager;
	
	// Breakout Game Related
	private PausableGameEngine pausableGameEngine;
	private GameManager gameManager;
	private CommandInvoker commandInvoker;
	private ObjectPooler objectPooler;
	private CollisionHandler2D collisionHandler;
	private Renderer renderer;
	
	public BreakoutApplication() {
	}
	
	public BreakoutApplication(Stage stage) {
		
		appStage = stage;
    	layoutManager = new LayoutManager(appStage);
    	layoutManager.buildStage();
    	
    	Canvas gameCanvas = layoutManager.getGameCanvas();
		Scene gameScene = layoutManager.getGameScene();
		
		renderer = new Renderer();
        collisionHandler = new CollisionHandler2D();
        objectPooler = new ObjectPooler();
        commandInvoker = new CommandInvoker();
    	gameManager = new GameManager(objectPooler, commandInvoker);
    	
    	
    	pausableGameEngine = new PausableGameEngine(renderer, collisionHandler, 
    			objectPooler, commandInvoker, gameManager, gameCanvas, gameScene);

    	layoutManager.createButtons(pausableGameEngine, gameManager, this);
    	
    	gameManager.setEngine(pausableGameEngine);
    	layoutManager.showStage();
	}
	
	public void run() {
		pausableGameEngine.setScreenBounds();
		pausableGameEngine.start();
	}
	
	public void saveGame() {
		System.out.println("Attempting to save");
		SaveLoadManager saveManager = new SaveLoadManager();
		try {
			RootSavable savedState = new RootSavable(commandInvoker, objectPooler);
			 saveManager.save(savedState, appStage);
		} catch (Exception e) {
			System.out.println("Failed to save.");
			e.printStackTrace();
		}
	}

	public void loadGame() {
		System.out.println("Attempting to load");
		SaveLoadManager saveManager = new SaveLoadManager();
		try {
			 RootSavable savedState = saveManager.load(appStage);

			if (savedState.getSuccesfulLoad()) {
				pausableGameEngine.restartFromLoadFile(savedState.getCommandInvoker(), savedState.getObjectPooler());
			}
		} catch (Exception e) {
			System.out.println("Failed to load.");
			e.printStackTrace();
		}
	}
}
