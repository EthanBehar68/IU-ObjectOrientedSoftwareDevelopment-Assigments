package p532.gamemaker.strategies.collision;

import javafx.application.Platform;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.DoNothingStrategy;
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
    public void execute(Sprite target)
    {
        //Remove the Sprite from the game play
        ParentWindow.getGamePlayScene().getSpritePresenter().removeSprite(target);
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType)
    {
        //Reuse above method
    	execute(colliderOrImpactee);
    }

    @Override
    public String toString() {
        return "Destroy Self";
    }
}
