package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.MoveDownStrategy;

public class MoveDownStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteOriginalYPosition = newSprite.getY();
		// Sprite move down on a event
		MoveDownStrategy.instance.execute(newSprite, null);
		assert(newSprite.getY() == newSpriteOriginalYPosition + Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testDoCollisionBehavior() {
		Sprite collider = GameCircle.defaultSprite();
		double colliderOriginalYPosition = collider.getY();
		MoveDownStrategy.instance.doCollisionBehavior(collider, null, Constants.CollisionType.TOP_IMPACT, null);
		assert(collider.getY() == colliderOriginalYPosition + Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testToString() {
		assert("Move Down".equals(MoveDownStrategy.instance.toString()));
	}

}
