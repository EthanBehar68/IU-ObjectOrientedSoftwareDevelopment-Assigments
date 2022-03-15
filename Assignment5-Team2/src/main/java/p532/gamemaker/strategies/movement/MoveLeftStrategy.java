package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class MoveLeftStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static strategy that can be used instead of the constructor.
     */
    public static final MoveLeftStrategy instance = new MoveLeftStrategy();

    private MoveLeftStrategy() {
    }

    @Override
    public void execute(Sprite target, File soundFx)
    {
    	SoundEngine.getInstance().playAudio(soundFx);
        target.setX(target.getX() - Constants.CONTROL_SPRITE_SPEED);
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx)
    {
        //Reuse above method
        execute(colliderOrImpactee, soundFx);
    }

    @Override
    public String toString() {
        return "Move Left";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
