package p532.gamemaker.strategies;


import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.CollisionStrategy;
import p532.gamemaker.strategies.collision.CollisionType;

public class DoNothingStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static DoNothingStrategy that can be used instead of the constructor.
     */
    public static final DoNothingStrategy instance = new DoNothingStrategy();

    @Override
    public void doCollisionBehavior(Sprite collider, CollisionType collisionType) {
        //Do nothing
    }

    @Override
    public void execute(Sprite target) {
        //Do nothing
    }

    @Override
    public String toString() {
        return "Do Nothing";
    }

}
