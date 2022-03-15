package p532.gamemaker.junit.tests;

import java.io.File;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.TimeEventCondition;


public class TimeEventConditionTest {
 
	@Test
	public void test() {
		Sprite sprite = GameCircle.defaultSprite();
		TimeEventCondition eventCondition = new TimeEventCondition();
		File sound = Constants.ALIEN_DEATH_SOUND;
		eventCondition.setSoundFxFile(sound, true);
		eventCondition.attemptBehavior(sprite, 0);
		eventCondition.setInterval(10);
		assert(10 == eventCondition.getInterval());
		
	}

}
