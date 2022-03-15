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

import breakout.Paddle;
import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviours.SimpleMovement;
import rendering.DrawCircle;
import rendering.DrawRectangle;

@TestMethodOrder(OrderAnnotation.class)
class PaddleScreenCollisionCommandTest {

	private static Paddle paddle;
	private static PaddleScreenCollisionCommand paddleScreenCollisionCommand;
	private static Point2D undoPosition;
	private static Point2D undoPreviousPosition;
	private static Point2D undoMoveDirection;
	private static Point2D redoPosition;
	private static Point2D redoPreviousPosition;
	private static Point2D redoMoveDirection;

	@BeforeAll
	static void setUpBeforeClass() {
		ObjectPooler objectPooler = new ObjectPooler();
		paddle = (Paddle) objectPooler.createObject(Paddle.class);
		paddle.setDrawBehaviour(new DrawRectangle());
		paddle.setOriginalColor(Color.BLACK);
		paddle.setOriginalPosition(new Point2D(350, 550));
		paddle.setOriginalDimensions(new Point2D(100, 10));
		paddle.setMoveBehaviour(new SimpleMovement());
		paddle.setOriginalSpeed(550);
		Point2D newPosition = new Point2D(300, 550);
		paddleScreenCollisionCommand = new PaddleScreenCollisionCommand(paddle, newPosition);
	}
	@Test
	@Order(1)
	@DisplayName("Test paddle screen collision command ")
	void testExecute() {
		undoPosition = paddle.getPosition();
		undoPreviousPosition = paddle.getPreviousPosition();
		undoMoveDirection = paddle.getMoveDirection();

		paddleScreenCollisionCommand.execute();
		Assertions.assertNotEquals(undoPosition, paddle.getPosition());
		Assertions.assertNotEquals(undoPreviousPosition, paddle.getPreviousPosition());
		Assertions.assertEquals(undoMoveDirection, paddle.getMoveDirection());
		redoPosition = paddle.getPosition();
		redoPreviousPosition = paddle.getPreviousPosition();
		redoMoveDirection = paddle.getMoveDirection();
	}

	@Test
	@Order(2)
	@DisplayName("Test undo of paddle screen collison command")
	void testUndo() {
		paddleScreenCollisionCommand.undo();
		Assertions.assertEquals(undoPosition, paddle.getPosition());
		Assertions.assertEquals(undoPreviousPosition, paddle.getPreviousPosition());
		Assertions.assertEquals(undoMoveDirection, paddle.getMoveDirection());
	}

	@Test
	@Order(3)
	@DisplayName("Test redo of paddle screen collision command")
	void testRedo() {
		paddleScreenCollisionCommand.redo();
		Assertions.assertEquals(redoPosition, paddle.getPosition());
		Assertions.assertEquals(redoMoveDirection, paddle.getMoveDirection());
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(paddleScreenCollisionCommand.save(true));
		String jsonStr = paddleScreenCollisionCommand.save(true);
		JsonObject jsonPaddleScreenCollisionCommand = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(paddleScreenCollisionCommand.getPaddleId(), BreakoutJsonUtility.Deserializer.deserializeInt("paddleId", jsonPaddleScreenCollisionCommand));
	}

	@Test
	void testLoad() {
		JsonObject jsonPaddleScreenCollisionCommand = JsonParser.parseString(paddleScreenCollisionCommand.save(true)).getAsJsonObject();
		PaddleScreenCollisionCommand newPaddleScreenCollisionCommand = new PaddleScreenCollisionCommand();
		newPaddleScreenCollisionCommand.load(jsonPaddleScreenCollisionCommand);
		Assertions.assertEquals(newPaddleScreenCollisionCommand.getPaddleId(), paddleScreenCollisionCommand.getPaddleId());
	}

	@Test
	void testAddSavable() {
		try {
			paddleScreenCollisionCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			paddleScreenCollisionCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}

