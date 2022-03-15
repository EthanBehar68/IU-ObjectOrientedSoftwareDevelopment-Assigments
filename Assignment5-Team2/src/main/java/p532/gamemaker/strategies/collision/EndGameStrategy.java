package p532.gamemaker.strategies.collision;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.design.BeginGamePlayStrategy;

public class EndGameStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static EndGameStrategy that can be used instead of the constructor.
     */
    public static final EndGameStrategy instance = new EndGameStrategy();

    private EndGameStrategy() {
    }

    @Override
    public void execute(Sprite target, File soundFx)
    {
        if(soundFx != null) {
        	SoundEngine.getInstance().playAudio(soundFx);
        }
        
        //Pause the game
        ParentWindow.getGamePlayScene().getTickObservable().stopGameLoop();

        //Show dialog box
        Alert alert = new Alert(Alert.AlertType.WARNING, "Game Over.", ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            //Restart game
            BeginGamePlayStrategy.execute();
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
        return "End the Game";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
