package p532.gamemaker.junit.tests;


import org.junit.Test;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.RandomizeMovementStrategy;
import p532.gamemaker.strategies.movement.MoveStrategyHelper;

public class MovementStrategyHelperTest {

	@Test
	public void testRandomizeMovement() {
		Sprite sprite = GameCircle.defaultSprite();
		Sprite sprite2 = GameCircle.defaultSprite();
		int output = 3;
		MoveStrategyHelper.getInstance().randomizeMovement(sprite, CollisionType.LEFT_IMPACT, 2);
		MoveStrategyHelper.getInstance().randomizeMovement(sprite, CollisionType.BOTTOM_IMPACT, 3);
		MoveStrategyHelper.getInstance().randomizeMovement(sprite, CollisionType.RIGHT_IMPACT, 3);
		RandomizeMovementStrategy movement = new RandomizeMovementStrategy();
		movement.doCollisionBehavior(sprite, sprite2, CollisionType.TOP_IMPACT, null);
		assert(output == MoveStrategyHelper.getInstance().randomizeMovement(sprite, CollisionType.TOP_IMPACT, 1));

	
	}

}
