/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/package command.pattern;

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
class BrickChangeColorCommandTest {

	private static Brick brick;
	private static BrickChangeColorCommand brickChangeColorCommand;

	private static Color undoColor;
	private static Color redoColor;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ObjectPooler objectPooler = new ObjectPooler();
		brick = (Brick) objectPooler.createObject(Brick.class);
		brick.setDrawBehaviour(new DrawRectangle());
		brick.setOriginalColor(Color.BROWN);
		brick.setOriginalPosition(new Point2D(400, 395));
		brick.setOriginalDimensions(new Point2D(40, 10));
		brick.setOriginalSpeed(0);

		brickChangeColorCommand = new BrickChangeColorCommand();
	}

	@Test
	@Order(0)
	@DisplayName("Test execute of brick change color command")
	void testExecute() {
		undoColor = brick.getColor();
		brickChangeColorCommand.execute();
		Assertions.assertNotEquals(undoColor, brick.getColor());
		redoColor = brick.getColor();
	}

	@Test
	@Order(1)
	@DisplayName("Test undo of brick change color command")
	void testUndo() {
		brickChangeColorCommand.undo();
		Assertions.assertEquals(undoColor, brick.getColor());
	}

	@Test
	@Order(2)
	@DisplayName("Test redo of brick change color command")
	void testRedo() {
		brickChangeColorCommand.redo();
		Assertions.assertEquals(redoColor, brick.getColor());
	}


	@Test
	void testSave() {
		Assertions.assertNotNull(brickChangeColorCommand.save(true));
		String jsonStr = brickChangeColorCommand.save(true);
		JsonObject jsonBrickChangeColorCommand = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(brickChangeColorCommand.getBrickId(), BreakoutJsonUtility.Deserializer.deserializeInt("brickId", jsonBrickChangeColorCommand));
	}

	@Test
	void testLoad() {
		JsonObject jsonBrickChangeColorCommand = JsonParser.parseString(brickChangeColorCommand.save(true)).getAsJsonObject();
		BrickChangeColorCommand newBrickChangeColorCommand = new BrickChangeColorCommand();
		newBrickChangeColorCommand.load(jsonBrickChangeColorCommand);
		Assertions.assertEquals(newBrickChangeColorCommand.getBrickId(), brickChangeColorCommand.getBrickId());
	}

	@Test
	void testAddSavable() {
		try {
			brickChangeColorCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			brickChangeColorCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
