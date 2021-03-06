/**
 * @author: Ed Eden-Rump
 * @CreationDate: Sep 1, 2021
 * @editors: Ethan Taylor Behar, Aditi Shivaji Pednekar, Abhishek Tiwari
 * @Info: Be warned Ed's code was bugged and needed to be fixed.
 * @References: 
 * https://github.com/edencoding/javafx-game-dev/blob/master/SpaceShooter/src/main/java/com/edencoding/animation/GameLoopTimer.java
 * https://github.com/edencoding/javafx-animation/blob/master/animation-timer-pause/src/main/java/com/edencoding/animation/PausableAnimationTimer.java
 * https://edencoding.com/animation-timer-pausing/
 **/
package game.engine;

import java.util.ArrayList;
import java.util.List;
import breakout.GameManager;
import collision.detection.CollisionHandler2D;
import command.pattern.CommandInvoker;
import input.ClickPolling;
import input.KeyPolling;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import observer.pattern.Observable;
import observer.pattern.Observer;
import rendering.Renderer;

public class PausableGameEngine implements Observable {

	// Design Pattern Vars
	private ArrayList<Observer> observers;
	private CommandInvoker commandInvoker;
	
	// Game Engine Vars
	private AnimationTimerGameLoop gameLoop;
	private double timeDelta = 0;
	// private double totalTime = 0;
	private boolean rewind = false;
	private boolean fastForward = false;
    private Renderer renderer;
    private CollisionHandler2D collisionHandler;
    private GameManager gameManager;
    
	private Stage gameStage;
	private Scene gameScene;
	
    public PausableGameEngine() {
    }    
    
    public PausableGameEngine(CommandInvoker commandInvoker, Renderer renderer, CollisionHandler2D collisionHandler, GameManager gameManager, Stage gameStage) {
    	this.commandInvoker = commandInvoker;
    	this.renderer = renderer;
    	this.collisionHandler = collisionHandler;
    	this.gameManager = gameManager;
    	this.gameStage = gameStage;
		instantiateGameCanvas();
		observers = new ArrayList<Observer>();
		instantiateGameLoop();
    }
    
    private void instantiateGameCanvas() {
    	// Build root and canvas
    	Group root = new Group();
    	Canvas gameCanvas = new Canvas(900, 600);
    	root.getChildren().add(gameCanvas);
    	
    	// Give renderer the canvas
    	renderer.setCanvas(gameCanvas);

    	// Build scene and give it root
    	Scene gameScene = new Scene(root);
    	this.gameScene = gameScene;
    	gameStage.setScene(gameScene);
    	
    	// Let the input handlers poll the scene
    	KeyPolling.getInstance().pollScene(gameScene);
    	ClickPolling.getInstance().pollScene(gameScene);
    	
    	// Start showing the stage
        gameStage.show();
        
        // Interesting... width and height isn't populated until gameStage.show() is called
    	collisionHandler.setGameSceneDimensions(gameScene.getWidth(), gameScene.getHeight());
    }
    
    private boolean isInvalid(String text) {
        try {
            int number = Integer.parseInt(text);
            if (number < 1 || number > 5)
                return true;
        } catch (NumberFormatException exception) {
            return true;
        }
        return false;
    }

	private void instantiateGameLoop() {
    	gameLoop = new AnimationTimerGameLoop() {
	        @Override
	        public void update(float secondsSinceLastFrame) {
	        	if(!rewind) {
	        		 //totalTime += secondsSinceLastFrame;
	        		timeDelta = secondsSinceLastFrame;
	        	} else { 
	        		 //totalTime -= secondsSinceLastFrame;
	        		timeDelta = -secondsSinceLastFrame;
	        	}
	        	
		        tick();
	        }
	    };
    }
    
	public void start() {
		InitDialog.showInitialDialog(gameStage, gameLoop, gameManager, this.gameScene);
	}
	
	public void restart() {
		// Restart the big 3
    	renderer.restart();
		collisionHandler.restart();
		gameManager.restart();
		commandInvoker.restart();
		
		// Restart engine vars
		rewind = false;
		fastForward = false;
    	gameLoop.stop();
		gameLoop = null;
		instantiateGameLoop();
		observers.clear();
		observers = null;
		observers = new ArrayList<Observer>();
		
		start();
	}
	
	public void pause() {
		gameLoop.pause();
	}
		
	public void resume() {
		gameLoop.play();
	}

	public void undo() {
		if (gameLoop.isPlaying()) {
			pause();
		}
		if(commandInvoker.undosAvailable())
		{
			commandInvoker.undoCurrentTickCommands();
		}
		renderer.render();
	}

	public void rewind() {
		resume();
		rewind = true;
		fastForward = false;
	}
	
	public void redo() {
		if (gameLoop.isPlaying()) {
			pause();
		}
		if(commandInvoker.redosAvailable()) {
			commandInvoker.redoCurrentTickCommands();			
		}
		renderer.render();
	}
	
	public void fastForward() {
		resume();
		fastForward = true;
		rewind = false;
	}
	
	// TODO
	public void replay() {
		commandInvoker.undosToRedos();
		fastForward();
	}
	
	public void addObjectsToEngine(ArrayList<Object> objects) {
		for (Object object : objects) { 
			addObjectToEngine(object);
		}
	}
    
	public void addObjectToEngine(Object object) {
		if (object instanceof Observer) {
			registerObserver((Observer) object);
		}
		if (object instanceof DrawObject) {
			renderer.addDrawable((DrawObject) object);
			((DrawObject)object).setCommandListener(commandInvoker);
		}
		if (object instanceof GameObject) {
			collisionHandler.addGameObject((GameObject) object);
		}
	}
	
	private void removeObjectsFromEngine() {
		List<Object> removables = gameManager.getRemovableObjects();
		for (Object object : removables) {
			if (object instanceof Observer) {
				unregisterObserver((Observer)object);
			}
			if (object instanceof DrawObject) {
				renderer.removeDrawable((DrawObject)object);
			}
			if (object instanceof GameObject) {
				collisionHandler.removeGameObject((GameObject)object);
			}
		}
		gameManager.engineRemovalFinished();
	}

	/*
	 * Main Game Loop!
	 * Move Objects
	 * Detect Collision
	 * Draw
	 * Delete Check
	 */
	@Override
	public void tick() {
		if(!rewind && !fastForward) {
			// System.out.println("New Tick: " + timeDelta);

			// Update observers with timeDelta
			for (Observer observer : observers) {
				observer.update(timeDelta);
			}
		
			// Invoke the commands!
			commandInvoker.executeCurrentTickCommands();
		
			// Process collisions
			collisionHandler.processCollisions(gameLoop);
		
			// Draw frame
			renderer.render();
		
			// Delete objects that are removed from game
			// Currently not really doing anything b/c of Command Pattern
			// Need to figure out how to delete objects but preserve reference in commands
			// TODO it would be nice if there was a pooling system here instead
			if(gameManager.hasObjectsForDeletion()) {
				removeObjectsFromEngine();
			}
		} else {
			if(rewind) {
				if(commandInvoker.undosAvailable()) {
					// System.out.println("Invoker undo");
					commandInvoker.undoCurrentTickCommands();
					renderer.render();
				} else {
					System.out.println("Out of undos - pausing");
					rewind = false;
					pause();
				}
			} else if (fastForward) {
				if(commandInvoker.redosAvailable()) {
					// System.out.println("Invoker redo");
					commandInvoker.redoCurrentTickCommands();
					renderer.render();
				} else {
					System.out.println("Out of redos - pausing");
					fastForward = false;
					pause();
				}
			}
		}
	}

	@Override
	public void registerObserver(Observer observer) {
		// Prevent double registration
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public void unregisterObserver(Observer observer) {
		// Ensure observer is already registered
		int observerIndex = observers.indexOf(observer);
		if (observerIndex >= 0) {
			observers.remove(observerIndex);
		}
	}
}
