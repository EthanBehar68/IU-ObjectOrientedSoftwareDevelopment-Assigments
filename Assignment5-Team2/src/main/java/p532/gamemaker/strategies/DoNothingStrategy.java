package p532.gamemaker.strategies;


import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;

public class DoNothingStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static DoNothingStrategy that can be used instead of the constructor.
     */
    public static final DoNothingStrategy instance = new DoNothingStrategy();

    @Override
    public void doCollisionBehavior(Sprite collider, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx) {
    	SoundEngine.getInstance().playAudio(soundFx);
    	return;
    }

    @Override
    public void execute(Sprite target, File soundFx) {
    	SoundEngine.getInstance().playAudio(soundFx);
        return;
    }

    @Override
    public String toString() {
        return "Do Nothing";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
