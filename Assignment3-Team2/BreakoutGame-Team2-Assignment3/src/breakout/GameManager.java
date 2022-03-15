/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 12, 2021
 * @editors: Aditi Pednekar, Snehal Patare, Abhishek Tiwari, Ethan Behar
 **/
package breakout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import command.pattern.CommandListener;
import command.pattern.BrickChangeColorCommand;
import game.engine.DrawObject;
import game.engine.ObjectPooler;
import game.engine.PausableGameEngine;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviours.NoMovement;
import movement.behaviours.SimpleMovement;
import rendering.DrawCircle;
import rendering.DrawRectangle;
import rendering.DrawText;

/**
 * If we implement a level loader... Also, then we would need to implement an
 * object pool.
 * 
 * TODO This should be abstracted to a GameManager class/interface And then
 * Extended/Implemented to BreakoutGameManager
 **/
public class GameManager {

	private PausableGameEngine gameEngine;
	private ObjectPooler objectPooler;
	private CommandListener commandListener;

	public GameManager(ObjectPooler objectPooler, CommandListener commandListener) {
		this.objectPooler = objectPooler;
		this.commandListener = commandListener;
	}

	public void setEngine(PausableGameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public void restartFromLoadFile(ObjectPooler newObjectPooler) {
		objectPooler = newObjectPooler;
	}
	
	public void reset() {
		Iterator<DrawObject> drawObjectIterator = objectPooler.getDrawObjectIterator();
		while (drawObjectIterator.hasNext()) {
			drawObjectIterator.next().reset();
		}
	}

	public void loadGame() {
		// TODO encapsulate in a level loader component
		// Since we only have 1 level
		// It's reasonable to let it live here for now
		// The moment we have multiple levels a level loader is necessary

		BuildSeriousLevel();
		// BuildBallCollisionTests();

		Ball ball = (Ball) objectPooler.createObject(Ball.class);
		ball.setDrawBehaviour(new DrawCircle());
		ball.setOriginalColor(Color.WHITE);
		ball.setOriginalPosition(new Point2D(400, 395));
		ball.setOriginalDimensions(new Point2D(10, 10));
		ball.setMoveBehaviour(new SimpleMovement());
		ball.setOriginalSpeed(200);
		ball.setOriginalMoveDirection(new Point2D(1, -1));

		Paddle paddle = (Paddle) objectPooler.createObject(Paddle.class);
		paddle.setDrawBehaviour(new DrawRectangle());
		paddle.setOriginalColor(Color.BLACK);
		paddle.setOriginalPosition(new Point2D(350, 550));
		paddle.setOriginalDimensions(new Point2D(100, 10));
		paddle.setMoveBehaviour(new SimpleMovement());
		paddle.setOriginalSpeed(550);

		DigitalTimer clock = (DigitalTimer) objectPooler.createObject(DigitalTimer.class);
		clock.setDrawBehaviour(new DrawText());
		clock.setOriginalColor(Color.BLACK);
		clock.setOriginalPosition(new Point2D(1, 30));
		clock.setOriginalDimensions(new Point2D(0, 0)); // This is weird
		clock.setOriginalFont("Verdana", 30);
		clock.setOriginalLabel("Time: 00:00");

		// TODO eventually remove this
		// It's only here to remind us what key presses execute the buttons.
		// Once the buttons are back we'll delete this code.
//		Text debugText = (Text) objectPooler.createObject(Text.class);
//		debugText.setDrawBehaviour(new DrawText());
//		debugText.setOriginalColor(Color.BLACK);
//		debugText.setOriginalPosition(new Point2D(1, 599));
//		debugText.setOriginalDimensions(new Point2D(0, 0)); // This is weird
//		debugText.setOriginalFont("Verdana", 12);
//		debugText.setOriginalLabel(
//				"DEBUG: 1:Pause  2:Resume  3:Restart  4:Undo  5:Rewind  6:Redo  7:Fast Forward  8:Replay  9:Random Color  Q:Save  E:Load");
	}

	// TODO Should this be here?
	public void toggleColorUpdate() {
		// Don't do this unless game is running.
		// It won't function properly if we can execute this action
		// and the engine is paused.
		if (gameEngine.getIsPaused()) {
			return;
		}

		Random rand = new Random();
		Iterator<DrawObject> brickIterator = objectPooler.getIteratorForObjectsOfType(Brick.class);
		while (brickIterator.hasNext()) {
			double r = rand.nextDouble();
			double g = rand.nextDouble();
			double b = rand.nextDouble();
			commandListener
					.receiveCommand(new BrickChangeColorCommand((Brick) brickIterator.next(), Color.color(r, g, b)));
		}
	}

	// TODO add in a separate LoadLevel class
	private void BuildSeriousLevel() {
		Brick brick = null;
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.YELLOW);
		colors.add(Color.GREENYELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.TEAL);
		colors.add(Color.BLUE);
		colors.add(Color.INDIGO);
		colors.add(Color.VIOLET);

		int x = 15;
		int y = 60;
		for (Color color : colors) {
			y = 60;
			for (int i = 0; i < 7; i++) {
				brick = (Brick) objectPooler.createObject(Brick.class);
				brick.setDrawBehaviour(new DrawRectangle());
				brick.setOriginalColor(color);
				brick.setOriginalPosition(new Point2D(x, y));
				brick.setOriginalDimensions(new Point2D(50, 25));
				brick.setMoveBehaviour(new NoMovement());

				y += 35;
			}
			x += 90;
		}
	}

//	@SuppressWarnings("unused")
//	private void BuildBallCollisionTests() {
//		Ball ball = new Ball(new DrawCircle(), Color.BLACK, 600, 600, 10, 10, new SimpleMovement());
//		ball.setMoveDirection(new Point2D(-1, 1));
//		addObject(ball);
//
//		ball = new Ball(new DrawCircle(), Color.WHITE, 0, 0, 10, 10, new SimpleMovement());
//		ball.setMoveDirection(new Point2D(1, -1));
//		addObject(ball);
//
//		ball = new Ball(new DrawCircle(), Color.GREEN, 0, 600, 10, 10, new SimpleMovement());
//		ball.setMoveDirection(new Point2D(1, 1));
//		addObject(ball);
//
//		ball = new Ball(new DrawCircle(), Color.RED, 202, 398, 10, 10, new SimpleMovement());
//		ball.setMoveDirection(new Point2D(-1, -1));
//		addObject(ball);
//	}
}
