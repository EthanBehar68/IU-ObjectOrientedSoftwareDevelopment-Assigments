package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.SetYVelocityPositiveStrategy;

public class SetYVelocityPositiveStrategyTest {

	@Test
	public void testExecute() {
		Sprite target = GameCircle.defaultSprite();
		target.setVelocityY(10);
		SetYVelocityPositiveStrategy.instance.execute(target, Constants.PLAYER_DEATH_SOUND);
		assert(target.getVelocityY() == -10);
	}

	@Test
	public void testToString() {
		assert("Set Y Velocity +".equals( SetYVelocityPositiveStrategy.instance.toString()));
	}

}
