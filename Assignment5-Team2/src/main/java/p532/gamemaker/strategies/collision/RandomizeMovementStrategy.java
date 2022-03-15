/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 15, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.collision;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.movement.MoveStrategyHelper;

public class RandomizeMovementStrategy implements CollisionStrategy {
	
	private int previousDirection = 0;
	
	public RandomizeMovementStrategy() {
	}

	@Override
	public void doCollisionBehavior(Sprite thisSprite, Sprite otherSprite, Constants.CollisionType collisionType,
			File soundFx) {
		previousDirection = MoveStrategyHelper.getInstance().randomizeMovement(thisSprite, collisionType, previousDirection);
	}

	@Override
	public String toString() {
		return "Randomize Movement";
	}
	


	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
