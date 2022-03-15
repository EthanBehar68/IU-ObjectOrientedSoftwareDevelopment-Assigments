/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 16, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class TeleportToSpawnStrategy implements GeneralStrategy, CollisionStrategy {

	private double spawnX = 0;
	private double spawnY = 0;
	
	public TeleportToSpawnStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		target.setX(spawnX);
		target.setY(spawnY);
	}

	@Override
	public String toString() {
		return "Teleport to Spawn";
	}
	
	@Override
	public void setup(Sprite sprite) {
		spawnX = sprite.getX();
		spawnY = sprite.getY();
	}

	@Override
	public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, CollisionType collisionType,
			File soundFx) {
		execute(colliderOrImpactee, soundFx);
	}
}