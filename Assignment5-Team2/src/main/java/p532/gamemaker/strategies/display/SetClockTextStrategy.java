package p532.gamemaker.strategies.display;

import javafx.application.Platform;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetClockTextStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static strategy that can be used instead of the constructor.
     */
    public static final SetClockTextStrategy instance = new SetClockTextStrategy();

    private SetClockTextStrategy() {
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
            String formattedTime = new SimpleDateFormat(Constants.TIME_FORMAT).format(new Date());

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
        return "Clock";
    }
    
	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
