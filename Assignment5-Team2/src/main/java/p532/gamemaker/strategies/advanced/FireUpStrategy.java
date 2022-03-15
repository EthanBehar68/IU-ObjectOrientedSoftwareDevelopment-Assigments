package p532.gamemaker.strategies.advanced;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;

public class FireUpStrategy implements GeneralStrategy {

	private Sprite bulletSprite = GameRectangle.defaultSprite();

	private boolean addToGame = true;
	private boolean bulletFired = false;

	public static final FireUpStrategy instance = new FireUpStrategy();

	private FireUpStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		if (addToGame) {
			bulletSprite = FireStrategyHelper.getInstance().setupBullet(bulletSprite, Constants.PLAYER_BULLET_STRING, -Constants.BULLET_SPEED);
			addToGame = false;
			bulletSprite = FireStrategyHelper.getInstance().buildCollisionEvents(this, bulletSprite, Constants.RESET_WALL_STRING, Constants.ENEMY_STRING);
		}

		if (!bulletFired) {
			bulletSprite = FireStrategyHelper.getInstance().fire(bulletSprite, target, soundFx);
			bulletFired = true;
		} else {
			if (target.getUserDefinedType().compareToIgnoreCase(Constants.PLAYER_BULLET_STRING) == 0) {
				bulletSprite = FireStrategyHelper.getInstance().reload(bulletSprite);
				bulletFired = false;
			}
		}
	}

	@Override
	public String toString() {
		return "Fire Up";
	}
}
