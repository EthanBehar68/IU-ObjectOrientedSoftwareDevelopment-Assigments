package p532.gamemaker.junit.tests;


import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.ReverseXVelocityStrategy;

public class ReverseXVelocityStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteXVelocity = newSprite.getVelocityX();
		ReverseXVelocityStrategy.instance.execute(newSprite, null);
		assert(newSprite.getVelocityX() == newSpriteXVelocity * -Constants.ONE);
	}

	@Test
	public void testToString() {
		assert("Reverse X Velocity".equals(ReverseXVelocityStrategy.instance.toString()));
	}
}
