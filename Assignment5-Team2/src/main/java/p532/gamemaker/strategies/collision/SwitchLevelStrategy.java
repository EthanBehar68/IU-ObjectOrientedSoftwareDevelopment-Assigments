package p532.gamemaker.strategies.collision;

import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class SwitchLevelStrategy implements CollisionStrategy, GeneralStrategy {
    /**
     * A static SwitchLevelStrategy that can be used instead of the constructor.
     */
    public static final SwitchLevelStrategy instance = new SwitchLevelStrategy();

    private SwitchLevelStrategy() {
    }

    @Override
    public void execute(Sprite target, File soundFx) {
        if(soundFx != null) {
        	SoundEngine.getInstance().playAudio(soundFx);
        }
        
        //Pause the game
        ParentWindow.getGamePlayScene().getTickObservable().stopGameLoop();

        //TODO
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx) {
        //Reuse above method
        execute(colliderOrImpactee, soundFx);
    }

    @Override
    public String toString() {
        return "Switch Level";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}