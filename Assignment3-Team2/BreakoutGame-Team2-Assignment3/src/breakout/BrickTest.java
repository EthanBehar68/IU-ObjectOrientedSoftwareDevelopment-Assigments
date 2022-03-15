/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import rendering.DrawRectangle;

class BrickTest {

	private static ObjectPooler objectPooler;
	private static Brick brick;

	@BeforeAll
	static void setUpBeforeClass() {
		 objectPooler = new ObjectPooler();
			brick = (Brick) objectPooler.createObject(Brick.class);
			brick.setDrawBehaviour(new DrawRectangle());
			brick.setOriginalColor(Color.BROWN);
			brick.setOriginalPosition(new Point2D(400, 395));
			brick.setOriginalDimensions(new Point2D(40, 10));
			brick.setOriginalSpeed(0);
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(brick.save(true));
		String jsonStr = brick.save(true);
		JsonObject jsonBrick = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(brick.getId(), BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonBrick));
	}

	@Test
	void testLoad() {
		JsonObject jsonBrick = JsonParser.parseString(brick.save(true)).getAsJsonObject();
		Brick newBrick = new Brick();
		newBrick.load(jsonBrick);
		Assertions.assertEquals(newBrick.getId(), brick.getId());
	}

	@Test
	void testAddSavable() {
		try {
			brick.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			brick.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
