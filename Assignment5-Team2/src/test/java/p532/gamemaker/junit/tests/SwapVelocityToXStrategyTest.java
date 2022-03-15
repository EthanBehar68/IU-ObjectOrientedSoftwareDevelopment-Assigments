package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.SwapVelocityToXStrategy;

public class SwapVelocityToXStrategyTest {

	@Test
	public void testExecute() {
		Sprite target = GameCircle.defaultSprite();
		target.setVelocityX(0);
		SwapVelocityToXStrategy.instance.execute(target, Constants.PLAYER_DEATH_SOUND);
		assert(target.getVelocityY() == 0);
	}

	@Test
	public void testToString() {
		assert("Swap To X Velocity".equals( SwapVelocityToXStrategy.instance.toString()));
	}

}
