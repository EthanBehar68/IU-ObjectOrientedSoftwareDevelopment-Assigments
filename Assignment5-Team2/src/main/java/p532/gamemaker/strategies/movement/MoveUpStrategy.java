package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class MoveUpStrategy implements CollisionStrategy, GeneralStrategy {
	/**
	 * A static strategy that can be used instead of the constructor.
	 */
	public static final MoveUpStrategy instance = new MoveUpStrategy();

	private MoveUpStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		SoundEngine.getInstance().playAudio(soundFx);
		target.setY(target.getY() - Constants.CONTROL_SPRITE_SPEED);
	}

	@Override
	public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite,
			Constants.CollisionType collisionType, File soundFx) {
		// Reuse above method
		execute(colliderOrImpactee, soundFx);
	}

	@Override
	public String toString() {
		return "Move Up";
	}

	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
