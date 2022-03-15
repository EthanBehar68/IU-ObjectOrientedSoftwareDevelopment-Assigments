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
import movement.behaviours.SimpleMovement;
import rendering.DrawRectangle;

class PaddleTest {

	private static Paddle paddle;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		ObjectPooler objectPooler = new ObjectPooler();
		paddle = (Paddle) objectPooler.createObject(Paddle.class);
		paddle.setDrawBehaviour(new DrawRectangle());
		paddle.setOriginalColor(Color.BLACK);
		paddle.setOriginalPosition(new Point2D(350, 550));
		paddle.setOriginalDimensions(new Point2D(100, 10));
		paddle.setMoveBehaviour(new SimpleMovement());
		paddle.setOriginalSpeed(550);
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
