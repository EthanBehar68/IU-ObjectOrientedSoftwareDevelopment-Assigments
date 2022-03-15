package p532.gamemaker.junit.tests;

import org.junit.Before;
import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.movement.TeleportToSpawnStrategy;

public class TeleportToSpawnStrategyTest {
	
	TeleportToSpawnStrategy strategy;
	Sprite sprite;
	Sprite target;

	@Before
	public void setup() {
		strategy = new TeleportToSpawnStrategy();
		sprite = GameCircle.defaultSprite();
		target = GameRectangle.defaultSprite();
		sprite.setX(20);
		sprite.setY(50);
		strategy.setup(sprite);
	}
	
	@Test
	public void testToString() {
		assert("Teleport to Spawn".equals(strategy.toString()));
	}

//	@Test
//	public void testSetup() {
//	
//	}

	@Test
	public void testDoCollisionBehavior() {
		strategy.doCollisionBehavior(target, sprite, CollisionType.NO_COLLISION, Constants.ALIEN_DEATH_SOUND);
		assert(target.getX() == 20);
		assert(target.getY() == 50);
	}

}
