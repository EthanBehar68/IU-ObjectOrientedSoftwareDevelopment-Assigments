package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class ReverseYVelocityStrategy implements CollisionStrategy, GeneralStrategy {
	/**
	 * A static strategy that can be used instead of the constructor.
	 */
	public static final ReverseYVelocityStrategy instance = new ReverseYVelocityStrategy();

	private ReverseYVelocityStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		target.setVelocityY(target.getVelocityY() * -Constants.ONE);
	}

	@Override
	public String toString() {
		return "Reverse Y Velocity";
	}

	@Override
	public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, CollisionType collisionType, File soundFx) {
		execute(colliderOrImpactee, soundFx);
	}
	
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
