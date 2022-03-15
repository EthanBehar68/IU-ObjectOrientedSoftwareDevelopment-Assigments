package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.SetXVelocityPositiveStrategy;

public class SetXVelocityPositiveStrategyTest {

	@Test
	public void testExecute() {
		Sprite target = GameCircle.defaultSprite();
		target.setVelocityX(10);
		SetXVelocityPositiveStrategy.instance.execute(target, Constants.PLAYER_DEATH_SOUND);
		assert(target.getVelocityX() == -10);
	}

	@Test
	public void testToString() {
		assert("Set X Velocity +".equals( SetXVelocityPositiveStrategy.instance.toString()));
	}

}

