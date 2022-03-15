package p532.gamemaker.junit.tests;


import org.junit.Test;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.DestroySelfStrategy;

public class DestroySelfStrategyTest {

	@Test
	public void testDoCollisionBehavior() {
		Sprite sprite = GameCircle.defaultSprite();
		DestroySelfStrategy.instance.doCollisionBehavior(sprite, null, CollisionType.NO_COLLISION, null);
		assert(sprite.isDestroyed());
	}

}
