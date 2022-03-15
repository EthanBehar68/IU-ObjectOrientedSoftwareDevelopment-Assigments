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
import rendering.DrawText;

class DigitalTimerTest {

	private static DigitalTimer clock;

	@BeforeAll
	static void setUpBeforeClass() {

		ObjectPooler objectPooler = new ObjectPooler();
		clock = (DigitalTimer) objectPooler.createObject(DigitalTimer.class);
		clock.setDrawBehaviour(new DrawText());
		clock.setOriginalColor(Color.BLACK);
		clock.setOriginalPosition(new Point2D(1, 30));
		clock.setOriginalDimensions(new Point2D(0, 0));
		clock.setOriginalFont("Verdana", 30);
		clock.setOriginalLabel("Time: 00:00");
	}

	@Test
	void testReset() {
		clock.reset();
		Assertions.assertEquals(clock.getLabel(), clock.getOriginalLabel());
		Assertions.assertEquals(clock.getFontSize(), clock.getOriginalFontSize());
		Assertions.assertEquals(clock.getFontName(), clock.getOriginalFontName());
	}

	@Test
	void testSave() {
		Assertions.assertNotNull(clock.save(true));
		String jsonStr = clock.save(true);
		JsonObject jsonClock = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(clock.getId(), BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonClock));
	}

	@Test
	void testLoad() {
		JsonObject jsonClock = JsonParser.parseString(clock.save(true)).getAsJsonObject();
		DigitalTimer newClock = new DigitalTimer();
		newClock.load(jsonClock);
		Assertions.assertEquals(newClock.getId(), clock.getId());
	}

	@Test
	void testAddSavable() {
		try {
			clock.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			clock.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
