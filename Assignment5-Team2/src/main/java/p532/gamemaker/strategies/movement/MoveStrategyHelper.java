/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 14, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.movement;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.Sprite;

public class MoveStrategyHelper {

	private static MoveStrategyHelper instance;

	private MoveStrategyHelper() {
	}

	public static MoveStrategyHelper getInstance() {
		if (instance == null) {
			instance = new MoveStrategyHelper();
		}
		return instance;
	}

	public void reverseXVelocity(Sprite sprite) {
		sprite.setVelocityX(sprite.getVelocityX() * -Constants.ONE);
	}

	public void reverseYVelocity(Sprite sprite) {
		sprite.setVelocityY(sprite.getVelocityY() * -Constants.ONE);
	}

	public void swapVelocities(Sprite sprite) {
		double currentX = sprite.getVelocityX();
		double currentY = sprite.getVelocityY();
		sprite.setVelocityY(currentX);
		sprite.setVelocityX(currentY);
	}

	/*
	 * Magic # Explanation (b/c putting these in constants is silly?)
	 * RandomDirection: We need a random number between 1-3 to decide our new direction.
	 * We are already moving in 1 particular direction so a 4th number isn't needed.
	 * 1 = Reverse the current velocity. So if moving up we'll move down.
	 * 2 = Swap current velocities. So if moving up we'll move right now.
	 * 3 = Swap current velocities and reverse them. So if moving up we'll move left now.
	 */
	public int randomizeMovement(Sprite sprite, CollisionType direction, int previousDirection) {
		int randomDirection = Constants.RANDOM.nextInt(4 - 1) + 1;
		
		if(previousDirection == randomDirection) {
			randomDirection = forceDifferentDirection(previousDirection);
		}
		
		switch (direction) {
		case TOP_IMPACT:
			if (randomDirection == 1) {
				reverseYVelocity(sprite);
			} else if (randomDirection == 2) {
				swapVelocities(sprite);
			} else {
				swapVelocities(sprite);
				reverseXVelocity(sprite);
			}
			break;
		case BOTTOM_IMPACT:
			if (randomDirection == 1) {
				reverseYVelocity(sprite);
			} else if (randomDirection == 2) {
				swapVelocities(sprite);
			} else {
				swapVelocities(sprite);
				reverseXVelocity(sprite);
			}
			break;
		case LEFT_IMPACT:
			if (randomDirection == 1) {
				reverseXVelocity(sprite);
			} else if (randomDirection == 2) {
				swapVelocities(sprite);
			} else {
				swapVelocities(sprite);
				reverseYVelocity(sprite);
			}
			break;
		case RIGHT_IMPACT:
			if (randomDirection == 1) {
				reverseXVelocity(sprite);
			} else if (randomDirection == 2) {
				swapVelocities(sprite);
			} else {
				swapVelocities(sprite);
				reverseYVelocity(sprite);
			}
			break;
		default:
			break;
		}
		return randomDirection;
	}
	
	private int forceDifferentDirection(int previousDirection) {
		if(previousDirection == 1) {
			return 3;
		} else if (previousDirection == 2){
			return 2;
		} else {
			return 3;
		}
	}
}
