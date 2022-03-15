package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.CollisionDetectionUtility;

public class CollisionDetectionUtilityTest {

	@Test
	public void testCheckForCollision() {
		Sprite collider = GameCircle.defaultSprite();
		Sprite impactee = GameRectangle.defaultSprite();
		
		CollisionType result = CollisionType.LEFT_IMPACT;

		assert(result.toString().equals(CollisionDetectionUtility.checkForCollision(collider, impactee).toString()));
		
		//move the collider to some different position
		collider.setX(170);
		collider.setY(180);
		assert("NO_COLLISION".equals(CollisionDetectionUtility.checkForCollision(collider, impactee).toString()));
		
		
		collider.setDestroyed(true);
		assert("NO_COLLISION".equals(CollisionDetectionUtility.checkForCollision(collider, impactee).toString()));
		
		
	}

}
