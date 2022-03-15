package p532.gamemaker.strategies.display;

import java.io.File;

import javafx.application.Platform;
import p532.gamemaker.Constants;
import p532.gamemaker.playthegame.Stopwatch;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class SetStopwatchTextStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static strategy that can be used instead of the constructor.
     */
    public static final SetStopwatchTextStrategy instance = new SetStopwatchTextStrategy();

    private SetStopwatchTextStrategy() {
    }

    /**
     * Sets the target's text to the current time.
     * Only compatible with Sprites that have GameLabel views.
     * @param target
     */
    @Override
    public void execute(Sprite target, File soundFx)
    {
        //Only compatible with Sprites that have GameLabel views.
        if (target.getView() instanceof GameLabel)
        {
            GameLabel gameLabel = (GameLabel) target.getView();

            //Get current time
            long stopwatchTime = Stopwatch.instance.getMsElapsed();
            String formattedTime = "Time: " + String.valueOf(stopwatchTime / 1000.0);

            //Delay the UI update so that it is on the FX thread
            Platform.runLater(() -> {
                //Set the text of the label
                gameLabel.setText(formattedTime);
            });
        }
        else
        {
            throw new IllegalArgumentException("The Sprite's view must be a GameLabel");
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
        return "Stopwatch";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
