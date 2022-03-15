/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 19, 2021
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

import breakout.Brick;
import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import rendering.DrawRectangle;

@TestMethodOrder(OrderAnnotation.class)
class BrickDestroyCommandTest {

	static Brick brick;
	static BrickDestroyCommand brickDestroyCommand;

	private static Point2D undoPosition;
	private static Point2D undoDimensions;
	private static Color undoColor;
	private static Point2D redoPosition;
	private static Point2D redoDimensions;
	private static Color redoColor;

	@BeforeAll
	static void setUpBeforeClass() {
		ObjectPooler objectPooler = new ObjectPooler();
		brick = (Brick) objectPooler.createObject(Brick.class);
		brick.setDrawBehaviour(new DrawRectangle());
		brick.setOriginalColor(Color.BROWN);
		brick.setOriginalPosition(new Point2D(400, 395));
		brick.setOriginalDimensions(new Point2D(40, 10));
		brick.setOriginalSpeed(0);
		brickDestroyCommand = new BrickDestroyCommand(brick);
	}

	@Test
	@Order(0)
	@DisplayName("Test execute of BrickDestroyCommand")
	void testExecute() {
		undoPosition = brick.getPosition();
		undoDimensions = brick.getDimensions();
		undoColor = brick.getColor();

		brickDestroyCommand.execute();

		Assertions.assertNotEquals(undoPosition, brick.getPosition());
		Assertions.assertNotEquals(undoDimensions, brick.getDimensions());
		Assertions.assertNotEquals(undoColor, brick.getColor());
		redoPosition = brick.getPosition();
		redoDimensions = brick.getDimensions();
		redoColor = brick.getColor();
	}

	@Test
	@Order(1)
	@DisplayName("Test undo of BrickDestroyCommand")
	void testUndo() {
		brickDestroyCommand.undo();
		Assertions.assertEquals(undoPosition, brick.getPosition());
		Assertions.assertEquals(undoDimensions, brick.getDimensions());
		Assertions.assertEquals(undoColor, brick.getColor());
	}

	@Test
	@Order(2)
	@DisplayName("Test redo of BrickDestroyCommand")
	void testRedo() {
		brickDestroyCommand.redo();
		Assertions.assertEquals(redoPosition, brick.getPosition());
		Assertions.assertEquals(redoDimensions, brick.getDimensions());
		Assertions.assertEquals(redoColor, brick.getColor());

	}


	@Test
	void testSave() {
		Assertions.assertNotNull(brickDestroyCommand.save(true));
		String jsonStr = brickDestroyCommand.save(true);
		JsonObject jsonBrickDestroyCommand = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(brickDestroyCommand.getBrickId(), BreakoutJsonUtility.Deserializer.deserializeInt("brickId", jsonBrickDestroyCommand));
	}

	@Test
	void testLoad() {
		JsonObject jsonBrickDestroyCommand = JsonParser.parseString(brickDestroyCommand.save(true)).getAsJsonObject();
		BrickDestroyCommand newBrickDestroyCommand = new BrickDestroyCommand();
		newBrickDestroyCommand.load(jsonBrickDestroyCommand);
		Assertions.assertEquals(newBrickDestroyCommand.getBrickId(), brickDestroyCommand.getBrickId());
	}

	@Test
	void testAddSavable() {
		try {
			brickDestroyCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			brickDestroyCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
