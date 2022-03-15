package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class ReverseXVelocityStrategy implements CollisionStrategy, GeneralStrategy {
	/**
	 * A static strategy that can be used instead of the constructor.
	 */
	public static final ReverseXVelocityStrategy instance = new ReverseXVelocityStrategy();

	private ReverseXVelocityStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		target.setVelocityX(target.getVelocityX() * -Constants.ONE);
	}

	@Override
	public String toString() {
		return "Reverse X Velocity";
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
