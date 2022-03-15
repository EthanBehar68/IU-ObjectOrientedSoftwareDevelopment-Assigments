package p532.gamemaker.strategies.advanced;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;

/**
 ** Every enemy uses the same one instance of this class because of the public
 * static final instance line... WHY PETE WHY!!!!
 **/
public class RandomEnemyFireDownStrategy implements GeneralStrategy {

	private Sprite bulletSprite = GameRectangle.defaultSprite();

	private boolean addToGame = true;
	private boolean bulletFired = false;

	// 1 in 10 to shoot a bullet
	private int low = Constants.RANDOM_ENEMY_LOW_CHANCE;
	private int high = Constants.RANDOM_ENEMY_HIGH_CHANCE;

	public RandomEnemyFireDownStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		if(target.isDestroyed()) return;
		
		if (!bulletFired) {
			int random = Constants.RANDOM.nextInt(high - low) + low;
			if (random == 1) {
				// Only make the bullet when we are about to
				// shoot for the first time. If not, first frame its on screen somwhere
				if (addToGame) {
					bulletSprite = FireStrategyHelper.getInstance().setupBullet(bulletSprite, Constants.BULLET_STRING, Constants.BULLET_SPEED);
					addToGame = false;
					bulletSprite = FireStrategyHelper.getInstance().buildCollisionEvents(this, bulletSprite, Constants.RESET_WALL_STRING, Constants.PLAYER_STRING);
				}
				bulletSprite = FireStrategyHelper.getInstance().fire(bulletSprite, target, soundFx);
				bulletFired = true;
			}
		} else {
			if (target.getUserDefinedType().compareToIgnoreCase(Constants.BULLET_STRING) == 0) {
				bulletSprite = FireStrategyHelper.getInstance().reload(bulletSprite);
				bulletFired = false;
			}
		}
	}

	@Override
	public String toString() {
		return "Enemy Fire Down";
	}
}
