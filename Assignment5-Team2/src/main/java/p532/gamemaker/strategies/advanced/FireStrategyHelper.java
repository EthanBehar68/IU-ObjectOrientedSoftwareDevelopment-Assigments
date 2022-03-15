/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 14, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.advanced;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.design.AddNewSpriteStrategy;

public class FireStrategyHelper {

	private static FireStrategyHelper instance;

	private FireStrategyHelper() {
	}

	public static FireStrategyHelper getInstance() {
		if (instance == null) {
			instance = new FireStrategyHelper();
		}
		return instance;
	}

	public Sprite setupBullet(Sprite bulletSprite, String bulletType, double bulletSpeed) {
		bulletSprite.setUserDefinedType(bulletType);
		bulletSprite.getView().setSpriteWidth(Constants.BULLET_WIDTH);
		bulletSprite.getView().setSpriteHeight(Constants.BULLET_HEIGHT);
		bulletSprite.getView().setFill(Constants.BULLET_COLOR);
		bulletSprite.setVelocityY(bulletSpeed);

		AddNewSpriteStrategy.setupSpriteInGamePlay(bulletSprite);
		ParentWindow.getGamePlayScene().getTickObservable().registerSprite(bulletSprite);

		return bulletSprite;
	}

	public Sprite buildCollisionEvents(GeneralStrategy fireStrategy, Sprite bulletSprite, String hitWallType,
			String hitEnemyType) {

		// When bullet hits a wall
		CollisionCondition hitWall = new CollisionCondition();
		hitWall.setOtherSpriteType(hitWallType);
		ReloadStrategy reload = new ReloadStrategy();
		reload.setFireStrategy(fireStrategy);
		hitWall.setStrategy(reload);

		// When bullet hits a enemy
		CollisionCondition hitEnemy = new CollisionCondition();
		hitEnemy.setOtherSpriteType(hitEnemyType);
		reload = new ReloadStrategy();
		reload.setFireStrategy(fireStrategy);
		hitEnemy.setStrategy(reload);

		bulletSprite.getOnHitSomethingConditionList().add(hitWall);
		bulletSprite.getOnHitSomethingConditionList().add(hitEnemy);

		return bulletSprite;
	}

	public Sprite fire(Sprite bulletSprite, Sprite target, File soundFx) {
		bulletSprite.setX(target.getX());
		bulletSprite.setY(target.getY());
		bulletSprite.setUseAutomove(true);

		SoundEngine.getInstance().playAudio(soundFx);
		
		return bulletSprite;
	}

	public Sprite reload(Sprite bulletSprite) {
		bulletSprite.setX(Constants.OFF_SCREEN_POSITION);
		bulletSprite.setY(Constants.OFF_SCREEN_POSITION);
		bulletSprite.setUseAutomove(false);

		return bulletSprite;
	}
}
