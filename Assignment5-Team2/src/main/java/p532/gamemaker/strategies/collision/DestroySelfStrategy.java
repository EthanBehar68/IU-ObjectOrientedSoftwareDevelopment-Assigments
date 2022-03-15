package p532.gamemaker.strategies.collision;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class DestroySelfStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static DestroySelfStrategy that can be used instead of the constructor.
     */
    public static final DestroySelfStrategy instance = new DestroySelfStrategy();

    private DestroySelfStrategy() {
    }

    @Override
    public void execute(Sprite target, File soundFx)
    {
        target.getView().setSpriteHeight(Constants.ZERO);
        target.getView().setSpriteWidth(Constants.ZERO);
        target.setDestroyed(true);
        if(soundFx != null) {
        	SoundEngine.getInstance().playAudio(soundFx);
        }
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx)
    {
        //Reuse above method
    	execute(colliderOrImpactee, soundFx);
    }

    @Override
    public String toString() {
        return "Destroy Self";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
