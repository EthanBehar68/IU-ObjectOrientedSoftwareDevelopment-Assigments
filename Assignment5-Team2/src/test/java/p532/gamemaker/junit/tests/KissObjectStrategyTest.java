package p532.gamemaker.junit.tests;


import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.KissObjectStrategy;

public class KissObjectStrategyTest {

	@Test
	public void testDoCollisionBehavior() {
		Sprite sprite1 = GameCircle.defaultSprite();
		Sprite sprite2 = GameRectangle.defaultSprite();
		
		KissObjectStrategy.instance.doCollisionBehavior(sprite1, sprite2, CollisionType.TOP_IMPACT, Constants.PLAYER_DEATH_SOUND);
		assert(sprite1.getY() == sprite2.getY() - sprite1.getView().getSpriteHeight() - Constants.ONE);

		KissObjectStrategy.instance.doCollisionBehavior(sprite1, sprite2, CollisionType.BOTTOM_IMPACT, Constants.PLAYER_DEATH_SOUND);
		assert(sprite1.getY() == sprite2.getY() + sprite2.getView().getSpriteHeight() + Constants.ONE);
		
		KissObjectStrategy.instance.doCollisionBehavior(sprite1, sprite2, CollisionType.RIGHT_IMPACT, Constants.PLAYER_DEATH_SOUND);
		assert(sprite1.getX() == sprite2.getX() + sprite2.getView().getSpriteWidth() + Constants.ONE);
		
		KissObjectStrategy.instance.doCollisionBehavior(sprite1, sprite2, CollisionType.LEFT_IMPACT, Constants.PLAYER_DEATH_SOUND);
		assert(sprite1.getX() == sprite2.getX() - sprite1.getView().getSpriteWidth() - Constants.ONE);
		
		
	}

	@Test
	public void testToString() {
		assert(KissObjectStrategy.instance.toString().equals("Kiss Objects"));
	}

}
