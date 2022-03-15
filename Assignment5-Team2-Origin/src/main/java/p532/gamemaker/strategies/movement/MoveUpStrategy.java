package p532.gamemaker.strategies.movement;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.collision.CollisionStrategy;
import p532.gamemaker.strategies.collision.CollisionType;

public class MoveUpStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static strategy that can be used instead of the constructor.
     */
    public static final MoveUpStrategy instance = new MoveUpStrategy();

    private MoveUpStrategy() {
    }

    @Override
    public void execute(Sprite target)
    {
        target.setY(target.getY() - Constants.CONTROL_SPRITE_SPEED);
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType)
    {
        //Reuse above method
        execute(colliderOrImpactee);
    }

    @Override
    public String toString() {
        return "Move Up";
    }
}
