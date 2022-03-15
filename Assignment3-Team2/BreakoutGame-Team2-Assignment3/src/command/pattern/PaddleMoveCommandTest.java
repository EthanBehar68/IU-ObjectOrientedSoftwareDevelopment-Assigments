package command.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import rendering.DrawRectangle;

@TestMethodOrder(OrderAnnotation.class)
class PaddleMoveCommandTest {

	private static Paddle paddle;

	private static Point2D undoPosition;
	private static Point2D undoPreviousPosition;
	private static Point2D undoVelocity;
	private static Point2D undoMoveDirection;

	private static Point2D redoPosition;
	private static Point2D redoPreviousPosition;
	private static Point2D redoVelocity;
	private static Point2D redoMoveDirection;

	private static PaddleMoveCommand paddleMoveCommand;

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
		paddleMoveCommand = new PaddleMoveCommand(paddle, 0.5);

	}

	@Test
	@Order(0)
	void testExecute() {
		undoPosition = paddle.getPosition();
		undoPreviousPosition = paddle.getPreviousPosition();
		undoVelocity = paddle.getVelocity();
		undoMoveDirection = paddle.getMoveDirection();

		paddleMoveCommand.execute();

		Assertions.assertEquals(undoPosition, paddle.getPosition());
		Assertions.assertNotEquals(undoPreviousPosition, paddle.getPreviousPosition());
		Assertions.assertEquals(undoMoveDirection, paddle.getMoveDirection());

		redoPosition = paddle.getPosition();
		redoPreviousPosition = paddle.getPreviousPosition();
		redoVelocity = paddle.getVelocity();
		redoMoveDirection = paddle.getMoveDirection();
	}

	@Test
	@Order(1)
	void testUndo() {
		paddleMoveCommand.undo();
		Assertions.assertEquals(undoPosition, paddle.getPosition());
		Assertions.assertEquals(undoPreviousPosition, paddle.getPreviousPosition());
		Assertions.assertEquals(undoMoveDirection, paddle.getMoveDirection());

	}

	@Test
	@Order(2)
	void testRedo() {
		paddleMoveCommand.redo();
		Assertions.assertEquals(redoPosition, paddle.getPosition());
		Assertions.assertEquals(redoPreviousPosition, paddle.getPreviousPosition());
		Assertions.assertEquals(redoMoveDirection, paddle.getMoveDirection());
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(paddle.save(true));
		String jsonStr = paddle.save(true);
		JsonObject jsonPaddle = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(paddle.getId(), BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonPaddle));
	}

	@Test
	void testLoad() {
		JsonObject jsonPaddle = JsonParser.parseString(paddle.save(true)).getAsJsonObject();
		Paddle newPaddle = new Paddle();
		newPaddle.load(jsonPaddle);
		Assertions.assertEquals(newPaddle.getId(), paddle.getId());
	}

	@Test
	void testAddSavable() {
		try {
			paddle.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			paddle.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
