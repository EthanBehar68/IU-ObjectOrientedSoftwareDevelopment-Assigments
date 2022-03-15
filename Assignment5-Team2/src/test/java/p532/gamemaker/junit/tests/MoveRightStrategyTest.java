package p532.gamemaker.junit.tests;


import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.MoveRightStrategy;

public class MoveRightStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteOriginalXPosition = newSprite.getX();
		// Sprite move left on a event
		MoveRightStrategy.instance.execute(newSprite, null);
		assert(newSprite.getX() == newSpriteOriginalXPosition + Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testDoCollisionBehavior() {
		Sprite collider = GameCircle.defaultSprite();
		double newSpriteOriginalXPosition = collider.getX();
		MoveRightStrategy.instance.doCollisionBehavior(collider, null, Constants.CollisionType.TOP_IMPACT, null);
		assert(collider.getX() == newSpriteOriginalXPosition + Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testToString() {
		assert("Move Right".equals(MoveRightStrategy.instance.toString()));
	}

}
