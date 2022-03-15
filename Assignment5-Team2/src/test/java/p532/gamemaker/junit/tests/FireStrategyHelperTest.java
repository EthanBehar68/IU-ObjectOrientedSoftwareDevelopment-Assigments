package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.advanced.FireDownStrategy;
import p532.gamemaker.strategies.advanced.FireStrategyHelper;

public class FireStrategyHelperTest {

	
	@Test
	public void testFire() {
		double targetYPostion = 50;
		double targetXPostion = 20;
		Sprite bulletSprite = GameRectangle.defaultSprite();
		Sprite target = GameCircle.defaultSprite();
		target.setX(targetXPostion);
		target.setY(targetYPostion);
		FireStrategyHelper.getInstance().fire(bulletSprite, target, Constants.PLAYER_SHOOT_SOUND);
		assert(bulletSprite.getX() == targetXPostion);
		assert(bulletSprite.getY() == targetYPostion);
	}

	@Test
	public void testReload() {
		Sprite bulletSprite = GameRectangle.defaultSprite();
		FireStrategyHelper.getInstance().reload(bulletSprite);
		assert(bulletSprite.getX() == Constants.OFF_SCREEN_POSITION);
		assert(bulletSprite.getY() == Constants.OFF_SCREEN_POSITION);
	}
	
	@Test
	public void testsetupBullet()  {
		Sprite bulletSprite = GameRectangle.defaultSprite();
		FireStrategyHelper.getInstance().buildCollisionEvents(FireDownStrategy.instance, bulletSprite, "wall", "enemy");
	}

}
