/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 10, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.advanced;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class ReloadStrategy  implements CollisionStrategy, GeneralStrategy {

	public ReloadStrategy() {
    }
    
    private GeneralStrategy fireStrategy;

    public GeneralStrategy getFireStrategy() {
		return fireStrategy;
	}

	public void setFireStrategy(GeneralStrategy fireStrategy) {
		this.fireStrategy = fireStrategy;
	}

    @Override
    public void execute(Sprite target, File soundFx) {
    	fireStrategy.execute(target, soundFx);
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx) {
    	execute(colliderOrImpactee, soundFx);
    }

    @Override
    public String toString() {
        return "Reload";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}