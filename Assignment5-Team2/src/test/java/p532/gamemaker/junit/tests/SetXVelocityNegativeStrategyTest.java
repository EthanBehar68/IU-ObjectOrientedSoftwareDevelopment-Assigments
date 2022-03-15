package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.SetXVelocityNegativeStrategy;

public class SetXVelocityNegativeStrategyTest {

	@Test
	public void testExecute() {
		Sprite target = GameCircle.defaultSprite();
		target.setVelocityX(-10);
		SetXVelocityNegativeStrategy.instance.execute(target, Constants.PLAYER_DEATH_SOUND);
		assert(target.getVelocityX() == 10);
	}

	@Test
	public void testToString() {
		assert("Set X Velocity -".equals( SetXVelocityNegativeStrategy.instance.toString()));
	}

}

