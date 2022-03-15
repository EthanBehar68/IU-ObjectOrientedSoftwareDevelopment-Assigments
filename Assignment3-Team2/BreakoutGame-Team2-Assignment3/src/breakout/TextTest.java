/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import composite.pattern.BreakoutJsonUtility;
import game.engine.ObjectPooler;

class TextTest {

	private static Text text;

	@Disabled
	@BeforeAll
	static void setUpBeforeClass() {
		ObjectPooler objectPooler = new ObjectPooler();
		text = (Text) objectPooler.createObject(Text.class);
		text.setOriginalFont("Verdana", 14);
		text.setOriginalLabel("");
		text.setFont("Verdana", 14);
		text.setLabel("");
	}

	@Disabled
	void testSave() {
		Assertions.assertNotNull(text.save(true));
		String jsonStr = text.save(true);
		JsonObject jsonText = JsonParser.parseString(jsonStr).getAsJsonObject();
		Assertions.assertEquals(text.getId(), BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonText));
	}

	@Disabled
	void testLoad() {
		JsonObject jsonText = JsonParser.parseString(text.save(true)).getAsJsonObject();
		Text newText = new Text();
		newText.load(jsonText);
		Assertions.assertEquals(newText.getId(), text.getId());
	}

	@Test
	void testAddSavable() {
		try {
			text.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			text.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
