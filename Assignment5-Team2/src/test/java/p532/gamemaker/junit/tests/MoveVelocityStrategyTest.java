package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.MoveVelocityStrategy;

public class MoveVelocityStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteX = newSprite.getX();
		double newSpriteY = newSprite.getY();
		double newSpriteXVelocity = newSprite.getVelocityX();
		double newSpriteYVelocity = newSprite.getVelocityX();
		MoveVelocityStrategy.instance.execute(newSprite, null);
		assert(newSprite.getX() == newSpriteX - newSpriteXVelocity);
		assert(newSprite.getY() == newSpriteY - newSpriteYVelocity);
	}

	@Test
	public void testToString() {
		assert("Move Velocity".equals(MoveVelocityStrategy.instance.toString()));
	}

}

