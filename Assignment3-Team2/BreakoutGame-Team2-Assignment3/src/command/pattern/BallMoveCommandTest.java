/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 18, 2021
 * @editors:
 **/
package command.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import breakout.Ball;
import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviours.SimpleMovement;
import rendering.DrawCircle;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@TestMethodOrder(OrderAnnotation.class)
class BallMoveCommandTest {

	private static Ball ball;
	private static BallMoveCommand ballMoveCommand;
	private static Point2D undoPosition;
	private static Point2D undoPreviousPosition;
	private static Point2D undoVelocity;
	private static Point2D undoMoveDirection;
	private static Point2D redoPosition;
	private static Point2D redoPreviousPosition;
	private static Point2D redoVelocity;
	private static Point2D redoMoveDirection;

	@BeforeAll
	static void setUpBeforeClass() {
		ObjectPooler objectPooler = new ObjectPooler();
		ball = (Ball) objectPooler.createObject(Ball.class);
		ball.setDrawBehaviour(new DrawCircle());
		ball.setOriginalColor(Color.WHITE);
		ball.setOriginalPosition(new Point2D(400, 395));
		ball.setOriginalDimensions(new Point2D(10, 10));
		ball.setMoveBehaviour(new SimpleMovement());
		ball.setOriginalSpeed(200);
		ball.setOriginalMoveDirection(new Point2D(1, -1));

		ballMoveCommand = new BallMoveCommand(ball,0.5);
	}

	@Test
	@Order(1)
	@DisplayName("Test ball move command ")
	void testExecute() {
		undoPosition = ball.getPosition();
		undoPreviousPosition = ball.getPreviousPosition();
		undoVelocity = ball.getVelocity();
		undoMoveDirection = ball.getMoveDirection();
		ballMoveCommand.execute();
		Assertions.assertNotEquals(undoPosition, ball.getPosition());
		Assertions.assertNotEquals(undoPreviousPosition, ball.getPreviousPosition());
		Assertions.assertNotEquals(undoVelocity, ball.getVelocity());
		Assertions.assertEquals(undoMoveDirection, ball.getMoveDirection());
		redoPosition = ball.getPosition();
		redoPreviousPosition = ball.getPreviousPosition();
		redoVelocity = ball.getVelocity();
		redoMoveDirection = ball.getMoveDirection();
	}

	@Test
	@Order(2)
	@DisplayName("Test undo of ball move command")
	void testUndo() {
		ballMoveCommand.undo();
		Assertions.assertEquals(undoPosition, ball.getPosition());
		Assertions.assertEquals(undoPreviousPosition, ball.getPreviousPosition());
		Assertions.assertEquals(undoVelocity, ball.getVelocity());
		Assertions.assertEquals(undoMoveDirection, ball.getMoveDirection());
	}

	@Test
	@Order(3)
	@DisplayName("Test redo of ball move command")
	void testRedo() {
		ballMoveCommand.redo();
		Assertions.assertEquals(redoPosition, ball.getPosition());
		Assertions.assertEquals(undoPosition, ball.getPreviousPosition());
		Assertions.assertEquals(redoVelocity, ball.getVelocity());
		Assertions.assertEquals(redoMoveDirection, ball.getMoveDirection());
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(ballMoveCommand.save(true));
		String jsonStr = ballMoveCommand.save(true);
		JsonObject jsonBallMoveCommand = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(ballMoveCommand.getBallId(), BreakoutJsonUtility.Deserializer.deserializeInt("ballId", jsonBallMoveCommand));
	}

	@Test
	void testLoad() {
		JsonObject jsonBallMoveCommand = JsonParser.parseString(ballMoveCommand.save(true)).getAsJsonObject();
		BallMoveCommand newBallMoveCommand = new BallMoveCommand();
		newBallMoveCommand.load(jsonBallMoveCommand);
		Assertions.assertEquals(newBallMoveCommand.getBallId(), ballMoveCommand.getBallId());
	}

	@Test
	void testAddSavable() {
		try {
			ballMoveCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			ballMoveCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
