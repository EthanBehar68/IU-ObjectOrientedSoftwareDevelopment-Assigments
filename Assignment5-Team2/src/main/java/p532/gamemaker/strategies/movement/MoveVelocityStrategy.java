package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class MoveVelocityStrategy implements CollisionStrategy, GeneralStrategy {
	/**
	 * A static strategy that can be used instead of the constructor.
	 */
	public static final MoveVelocityStrategy instance = new MoveVelocityStrategy();

	private MoveVelocityStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		target.setY(target.getY() - target.getVelocityY());
		target.setX(target.getX() - target.getVelocityX());
	}

	@Override
	public String toString() {
		return "Move Velocity";
	}

	@Override
	public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, CollisionType collisionType, File soundFx) {
		return;
	}
	
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
