package p532.gamemaker.junit.tests;

import org.junit.Test;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.MoveUpStrategy;

public class MoveUpStrategyTest {

	@Test
	public void testExecute() {
		Sprite newSprite = GameRectangle.defaultSprite();
		double newSpriteOriginalYPosition = newSprite.getY();
		// Sprite move up on a Tick
		MoveUpStrategy.instance.execute(newSprite, null);
		assert (newSprite.getY() == newSpriteOriginalYPosition - Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testDoCollisionBehavior() {
		Sprite collider = GameCircle.defaultSprite();
		double colliderOriginalYPosition = collider.getY();
		MoveUpStrategy.instance.doCollisionBehavior(collider, null, Constants.CollisionType.TOP_IMPACT, null);
		assert (collider.getY() == colliderOriginalYPosition - Constants.CONTROL_SPRITE_SPEED);
	}

	@Test
	public void testToString() {
		assert ("Move Up".equals(MoveUpStrategy.instance.toString()));
	}

}
