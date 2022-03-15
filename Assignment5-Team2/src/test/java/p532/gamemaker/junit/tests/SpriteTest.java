package p532.gamemaker.junit.tests;

//import static org.junit.Assert.*;

import org.junit.Test;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;

public class SpriteTest {
	
	Sprite newSprite = GameCircle.defaultSprite();
	
	@Test
	public void testSetupSpriteForGamePlay() {
		Sprite otherSprite = GameRectangle.defaultSprite();
		newSprite.setupSpriteForGamePlay();
		newSprite.onGetHit(otherSprite, CollisionType.NO_COLLISION);
	}

	@Test
	public void testSetVelocityX() {
		double velocityX = 20.0;
		newSprite.setVelocityX(velocityX);
		assert(newSprite.getVelocityX() == velocityX);
	}

	@Test
	public void testSetVelocityY() {
		double velocityY = 10.0;
		newSprite.setVelocityY(velocityY);
		assert(newSprite.getVelocityY() == velocityY);
	}

	@Test
	public void testSetDestroyed() {
		newSprite.setDestroyed(true);
		assert(newSprite.isDestroyed());
	}

	@Test
	public void testSetX() {
		double newXPosition = 150.0;
		newSprite.setX(newXPosition);
		assert(newSprite.getX() == newXPosition);
	}

	@Test
	public void testSetY() {
		double newYPosition = 150.0;
		newSprite.setY(newYPosition);
		assert(newSprite.getY() == newYPosition);
	}

	@Test
	public void testOnTick() {
		double increaseXVelocity = 20.0;
		double increaseYVelocity = 30.0;
		newSprite.setVelocityX(increaseXVelocity);
		newSprite.setVelocityY(increaseYVelocity);
		// Not expect to change the position
		newSprite.setUseAutomove(false);
		double originalXPosition = newSprite.getX();
		double originalYPosition = newSprite.getY();
		
		newSprite.onTick(1, 1);
		
		assert(newSprite.getX() != originalXPosition + increaseXVelocity);
		assert(newSprite.getY() != originalYPosition + increaseYVelocity);
		// change the position of sprite
		newSprite.setUseAutomove(true);
		
		newSprite.onTick(1, 1);
		
		assert(newSprite.getX() == originalXPosition + increaseXVelocity);
		assert(newSprite.getY() == originalYPosition + increaseYVelocity);
	}

}
