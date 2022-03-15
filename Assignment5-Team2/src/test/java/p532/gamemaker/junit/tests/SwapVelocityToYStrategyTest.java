package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.SwapVelocityToYStrategy;

public class SwapVelocityToYStrategyTest {

	@Test
	public void testExecute() {
		Sprite target = GameCircle.defaultSprite();
		target.setVelocityY(0);
		SwapVelocityToYStrategy.instance.execute(target, Constants.PLAYER_DEATH_SOUND);
		assert(target.getVelocityX() == 0);
	}

	@Test
	public void testToString() {
		assert("Swap To Y Velocity".equals( SwapVelocityToYStrategy.instance.toString()));
	}

}
