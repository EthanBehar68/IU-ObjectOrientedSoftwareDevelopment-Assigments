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
import java.util.Iterator;

import breakout.GameManager;
import collision.detection.CollisionHandler2D;
import command.pattern.CommandInvoker;
import input.KeyPolling;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import observer.pattern.Observable;
import observer.pattern.Observer;
import rendering.Renderer;

public class PausableGameEngine implements Observable {

	// Game Engine Vars
	private AnimationTimerGameLoop gameLoop;
	private float timeDelta = 0;
	private boolean undoAll = false;
	private boolean redoAll = false;
	private Renderer renderer;
	private CollisionHandler2D collisionHandler;
	private ObjectPooler objectPooler;
	private CommandInvoker commandInvoker;
	private GameManager gameManager;
	private Canvas gameCanvas;
	private Scene gameScene;
	private ArrayList<Observer> observers;

	public PausableGameEngine() {
	}

	public PausableGameEngine(Renderer renderer, CollisionHandler2D collisionHandler, ObjectPooler objectPooler,
			CommandInvoker commandInvoker, GameManager gameManager, Canvas gameCanvas, Scene gameScene) {
		this.renderer = renderer;
		this.collisionHandler = collisionHandler;
		this.objectPooler = objectPooler;
		this.commandInvoker = commandInvoker;
		this.gameManager = gameManager;
		this.gameCanvas = gameCanvas;
		this.gameScene = gameScene;
		observers = new ArrayList<Observer>();
		instantiateGameCanvas();
		instantiateGameLoop();
	}

	// TODO REMOVE THIS GAMEENGINE/GAMEMANAGER IN KEYPOLLING
	// THIS STRICTLY FOR DEBUGGING WHILE BUTTONS ARE BEING ADDED
	// BY CHRISTIAN!!!
	private void instantiateGameCanvas() {
		// Give renderer the canvas
		renderer.setGraphicsContext(gameCanvas.getGraphicsContext2D());

		// Let the input handlers poll the scene
		// KeyPolling.getInstance().pollScene(gameScene);
		KeyPolling.getInstance().pollScene(gameScene);
	}

	public void setScreenBounds() {
		// Interesting... width and height isn't populated until gameStage.show() is //
		// called
		collisionHandler.setGameSceneDimensions(gameCanvas.getWidth(), gameCanvas.getHeight());
	}

	private void instantiateGameLoop() {
		gameLoop = new AnimationTimerGameLoop() {
			@Override
			public void update(float secondsSinceLastFrame) {
				timeDelta = secondsSinceLastFrame;

				tick();
			}
		};
	}

	public void start() {
		System.out.println("GameEngine Start");
		// Load our game
		gameManager.loadGame();

		// Once loading is done - register objects to the engine
		addObjectsToEngine(objectPooler.getDrawObjectIterator());

		// get ready for resume button and playing.
		prepareForPlay();
	}

	private void prepareForPlay() {
		// render initial state
		renderer.render();

		// get the gameloop ready but don't play
		gameLoop.start();
	}

	public void restartFromLoadFile(CommandInvoker newCommandInvoker, ObjectPooler newObjectPooler) {
		// Do a hard reset on big 5
		// Load from file gives us all new objects
		// So any currently loaded objects get the boot
		renderer.restartFromLoadFile();
		collisionHandler.restartFromLoadFile();
		gameManager.restartFromLoadFile(newObjectPooler);
		commandInvoker.restartFromLoadFile();
		commandInvoker = null;
		commandInvoker = newCommandInvoker;
		objectPooler.restartFromLoadFile();
		objectPooler = null;
		objectPooler = newObjectPooler;

		// restart the game engine
		restartEngine();
	}

	public void reset() {
		System.out.println("GameEngine reset");

		// Dump all commands
		commandInvoker.reset();

		// Tell objects to go back to original state
		objectPooler.reset();

		// restart game engine
		restartEngine();
	}

	private void restartEngine() {
		// Restart engine vars
		undoAll = false;
		redoAll = false;

		// Make a new game loop
		gameLoop.stop();
		gameLoop = null;
		instantiateGameLoop();

		// clear observers
		observers.clear();
		observers = null;
		observers = new ArrayList<Observer>();

		// get new objects for engine
		addObjectsToEngine(objectPooler.getDrawObjectIterator());

		// get ready for resume button and playing.
		prepareForPlay();
	}

	public void pause() {
		System.out.println("GameEngine pause");
		gameLoop.pause();
	}

	/*
	 * We are paused if any of the three booleans are true.
	 */
	public boolean getIsPaused() {
		if (gameLoop.isPaused() || undoAll || redoAll) {
			return true;
		} else {
			return false;
		}
	}

	public void resume() {
		System.out.println("GameEngine resume");
		gameLoop.play();
	}

	public void undo() {
		System.out.println("GameEngine undo");
		// Pause first then do 1 undo
		pause();

		if (commandInvoker.undosAvailable()) {
			commandInvoker.undoCurrentTickCommands();
		}
		renderer.render();
	}

	public void undoAll() {
		System.out.println("GameEngine rewind");
		// turn on game engine and set state to rewind
		resume();
		undoAll = true;
		redoAll = false;
	}

	public void redo() {
		System.out.println("GameEngine redo");
		// Pause first then do 1 redo
		pause();

		if (commandInvoker.redosAvailable()) {
			commandInvoker.redoCurrentTickCommands();
		}
		renderer.render();
	}

	public void redoAll() {
		System.out.println("GameEngine fastForward");
		// turn on game engine and set state to fast forward
		resume();
		redoAll = true;
		undoAll = false;
	}

	public void replay() {
		System.out.println("GameEngine replay");
		// Reset objects to initial state
		objectPooler.reset();
		// Tell commandInvoker to get ready
		commandInvoker.prepareForReplay();
		// Just fast forward at this point
		redoAll();
	}

	public void addObjectsToEngine(Iterator<DrawObject> objects) {
		while (objects.hasNext()) {
			addObjectToEngine(objects.next());
		}
	}

	public void addObjectToEngine(Object object) {
		if (object instanceof Observer) {
			registerObserver((Observer) object);
		}
		if (object instanceof DrawObject) {
			renderer.addDrawable((DrawObject) object);
			((DrawObject) object).setCommandListener(commandInvoker);
		}
		if (object instanceof GameObject) {
			collisionHandler.addGameObject((GameObject) object);
		}
	}

	@Override
	public void registerObserver(Observer observer) {
		// Prevent double registration
		if (!observers.contains(observer)) {
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
	
	/*
	 * Main Game Loop!
	 */
	@Override
	public void tick() {
		if (!undoAll && !redoAll) {
			// System.out.println("New Tick: " + timeDelta);

			// TODO
			// Capture Input
			// Update objects that care about input

			// Update observers with timeDelta
			for (Observer observer : observers) {
				observer.update(timeDelta);
			}
			// Invoke the commands!
			commandInvoker.executeCurrentTickCommands(false);

			// Process collisions
			collisionHandler.processCollisions();
			// Invoke the commands!
			commandInvoker.executeCurrentTickCommands(true);

			// Draw frame
			renderer.render();
		} else {
			if (undoAll) {
				if (commandInvoker.undosAvailable()) {
					// System.out.println("Invoker undo");
					commandInvoker.undoCurrentTickCommands();
					renderer.render();
				} else {
					System.out.println("Out of undos - pausing - setting to fastForward mode.");
					undoAll = false;
					redoAll = true;
					pause();
				}
			} else if (redoAll) {
				if (commandInvoker.redosAvailable()) {
					// System.out.println("Invoker redo");
					commandInvoker.redoCurrentTickCommands();
					renderer.render();
				} else {
					System.out.println("Out of redos - pausing");
					redoAll = false;
					pause();
				}
			}
		}
	}
}
