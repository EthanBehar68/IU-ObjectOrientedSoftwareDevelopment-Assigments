package p532.gamemaker.strategies.movement;

import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.collision.CollisionStrategy;
import p532.gamemaker.strategies.collision.CollisionType;

public class MoveLeftStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static strategy that can be used instead of the constructor.
     */
    public static final MoveLeftStrategy instance = new MoveLeftStrategy();

    private MoveLeftStrategy() {
    }

    @Override
    public void execute(Sprite target)
    {
        target.setX(target.getX() - Constants.CONTROL_SPRITE_SPEED);
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType)
    {
        //Reuse above method
        execute(colliderOrImpactee);
    }

    @Override
    public String toString() {
        return "Move Left";
    }
}
