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

public class KissObjectStrategy implements CollisionStrategy {
	/**
	 * A static KissObjectStrategy that can be used instead of the constructor.
	 */
	public static final KissObjectStrategy instance = new KissObjectStrategy();

	private KissObjectStrategy() {
	}

	@Override
	public void doCollisionBehavior(Sprite thisSprite, Sprite otherSprite, Constants.CollisionType collisionType,
			File soundFx) {
		if (collisionType == Constants.CollisionType.TOP_IMPACT) {
			thisSprite.setY(otherSprite.getY() - thisSprite.getView().getSpriteHeight() - Constants.ONE);
		} else if (collisionType == Constants.CollisionType.BOTTOM_IMPACT) {
			
			
			thisSprite.setY(otherSprite.getY() + otherSprite.getView().getSpriteHeight()
					+ Constants.ONE);
		}

		else if (collisionType == Constants.CollisionType.RIGHT_IMPACT) {
			thisSprite.setX(otherSprite.getX() + otherSprite.getView().getSpriteWidth() + Constants.ONE);

		}

		else if (collisionType == Constants.CollisionType.LEFT_IMPACT) {
			thisSprite.setX(otherSprite.getX() - thisSprite.getView().getSpriteWidth() - Constants.ONE);
		} else {
			return;
		}
	}
	
    @Override
    public String toString() {
        return "Kiss Objects";
    }

	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
