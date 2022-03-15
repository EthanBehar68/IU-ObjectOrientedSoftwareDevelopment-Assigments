package p532.gamemaker.junit.tests;

//import static org.junit.Assert.*;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.ReverseYVelocityStrategy;

public class ReverseYVelocityStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteYVelocity = newSprite.getVelocityY();
		ReverseYVelocityStrategy.instance.execute(newSprite, null);
		assert(newSprite.getVelocityY() == newSpriteYVelocity * -Constants.ONE);
	}

	@Test
	public void testToString() {
		assert("Reverse Y Velocity".equals(ReverseYVelocityStrategy.instance.toString()));
	}
}
