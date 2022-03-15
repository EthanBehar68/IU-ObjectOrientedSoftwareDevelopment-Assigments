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
import rendering.DrawCircle;

class BallTest {

	private static ObjectPooler objectPooler;
	private static Ball ball;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        objectPooler = new ObjectPooler();
		ball = (Ball) objectPooler.createObject(Ball.class);
		ball.setDrawBehaviour(new DrawCircle());
		ball.setOriginalColor(Color.WHITE);
		ball.setOriginalPosition(new Point2D(400, 395));
		ball.setOriginalDimensions(new Point2D(10, 10));
		ball.setMoveBehaviour(new SimpleMovement());
		ball.setOriginalSpeed(200);
		ball.setOriginalMoveDirection(new Point2D(1, -1));
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(ball.save(true));
		String jsonStr = ball.save(true);
		JsonObject jsonBall = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(ball.getId(), BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonBall));
	}

	@Test
	void testLoad() {
		JsonObject jsonBall = JsonParser.parseString(ball.save(true)).getAsJsonObject();
		Ball newBall = new Ball();
		newBall.load(jsonBall);
		Assertions.assertEquals(newBall.getId(), ball.getId());
	}

	@Test
	void testAddSavable() {
		try {
			ball.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			ball.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

}
