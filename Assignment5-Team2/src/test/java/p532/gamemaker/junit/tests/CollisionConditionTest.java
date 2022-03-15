package p532.gamemaker.junit.tests;

import static org.junit.Assert.assertThrows;

import java.io.File;

import org.junit.Test;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.CollisionCondition;

public class CollisionConditionTest {

	private String nullExceptionMessage = "NullPointerException thrown!";

	@Test
	public void testAttemptCollisionBehavior() {
		Sprite sprite = GameRectangle.defaultSprite();
		Sprite otherSprite = GameCircle.defaultSprite();

		Exception exception = assertThrows(NullPointerException.class, () -> {
			otherSprite.setUserDefinedType(Constants.PLAYER_BULLET_STRING);
		});
		assert (exception.getClass() == NullPointerException.class);

		CollisionCondition condition = new CollisionCondition();
		condition.setOtherSpriteType(Constants.PLAYER_BULLET_STRING);
		CollisionType result = CollisionType.LEFT_IMPACT;
		File playerShootSound = Constants.ALIEN_DEATH_SOUND;
		condition.setSoundFxFile(playerShootSound);
		assert (condition.attemptCollisionBehavior(sprite, otherSprite, result) == true);
	}

	@Test
	public void testAttemptCollisionBehavior1() {
		Sprite sprite = GameRectangle.defaultSprite();
		Sprite otherSprite = GameCircle.defaultSprite();

		Exception exception = assertThrows(NullPointerException.class, () -> {
			otherSprite.setUserDefinedType(Constants.PLAYER_BULLET_STRING);
		});
		assert (exception.getClass() == NullPointerException.class);
		
		CollisionCondition condition = new CollisionCondition();
		condition.setOtherSpriteType(Constants.BULLET_STRING);
		CollisionType result = CollisionType.LEFT_IMPACT;
		File playerShootSound = Constants.ALIEN_DEATH_SOUND;
		condition.setSoundFxFile(playerShootSound);
		assert (condition.attemptCollisionBehavior(sprite, otherSprite, result) == false);
	}
}
