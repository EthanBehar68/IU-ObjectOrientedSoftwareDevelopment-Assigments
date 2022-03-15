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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import breakout.Ball;
import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviours.SimpleMovement;
import rendering.DrawCircle;

@TestMethodOrder(OrderAnnotation.class)
class BallObjectCollisionCommandTest {

	private static Ball ball;
	private static BallObjectCollisionCommand ballObjectCollisionCommand;
	private static Point2D undoPosition;
	private static Point2D undoPreviousPosition;
	private static Point2D undoMoveDirection;
	private static Point2D redoPosition;
	private static Point2D redoPreviousPosition;
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

		Point2D newPosition = new Point2D(400, 350);
		Point2D newMoveDirection = new Point2D(1, 1);
		ballObjectCollisionCommand = new BallObjectCollisionCommand(ball, newPosition, newMoveDirection);
	}

	@Test
	@Order(1)
	@DisplayName("Test ball collision command ")
	void testExecute() {
		undoPosition = ball.getPosition();
		undoPreviousPosition = ball.getPreviousPosition();
		undoMoveDirection = ball.getMoveDirection();

		ballObjectCollisionCommand.execute();
		Assertions.assertNotEquals(undoPosition, ball.getPosition());
		Assertions.assertNotEquals(undoPreviousPosition, ball.getPreviousPosition());
		Assertions.assertNotEquals(undoMoveDirection, ball.getMoveDirection());
		redoPosition = ball.getPosition();
		redoPreviousPosition = ball.getPreviousPosition();
		redoMoveDirection = ball.getMoveDirection();
	}

	@Test
	@Order(2)
	@DisplayName("Test undo of ball collison command")
	void testUndo() {
		ballObjectCollisionCommand.undo();
		Assertions.assertEquals(undoPosition, ball.getPosition());
		Assertions.assertEquals(undoPreviousPosition, ball.getPreviousPosition());
		Assertions.assertEquals(undoMoveDirection, ball.getMoveDirection());
	}

	@Test
	@Order(3)
	@DisplayName("Test redo of ball collision command")
	void testRedo() {
		ballObjectCollisionCommand.redo();
		Assertions.assertEquals(redoPosition, ball.getPosition());
		Assertions.assertEquals(redoPreviousPosition, ball.getPreviousPosition());
		Assertions.assertEquals(redoMoveDirection, ball.getMoveDirection());
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(ballObjectCollisionCommand.save(true));
		String jsonStr = ballObjectCollisionCommand.save(true);
		JsonObject jsonBallObjectCollisionCommand = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(ballObjectCollisionCommand.getBallId(), BreakoutJsonUtility.Deserializer.deserializeInt("ballId", jsonBallObjectCollisionCommand));
	}

	@Test
	void testLoad() {
		JsonObject jsonBallObjectCollisionCommand = JsonParser.parseString(ballObjectCollisionCommand.save(true)).getAsJsonObject();
		BallObjectCollisionCommand newBallObjectCollisionCommand = new BallObjectCollisionCommand();
		newBallObjectCollisionCommand.load(jsonBallObjectCollisionCommand);
		Assertions.assertEquals(newBallObjectCollisionCommand.getBallId(), ballObjectCollisionCommand.getBallId());
	}

	@Test
	void testAddSavable() {
		try {
			ballObjectCollisionCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			ballObjectCollisionCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

}
