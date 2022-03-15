package p532.gamemaker.junit.tests;


import java.io.File;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.KeyEventCondition;

public class KeyEventConditionTest {

	@Test
	public void testAttemptBehavior() {
		Sprite sprite = GameCircle.defaultSprite();
		KeyEventCondition condition = new KeyEventCondition();
		File playerShootSound = Constants.ALIEN_DEATH_SOUND;
		condition.setSoundFxFile(playerShootSound, true);
		condition.setKeyCode(KeyCode.SPACE);
		assert(condition.attemptBehavior(sprite, KeyCode.SPACE));
	}

}
