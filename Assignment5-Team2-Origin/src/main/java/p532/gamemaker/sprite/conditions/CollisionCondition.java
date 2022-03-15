package p532.gamemaker.sprite.conditions;

import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.CollisionStrategy;
import p532.gamemaker.strategies.collision.CollisionType;
import p532.gamemaker.strategies.DoNothingStrategy;

public class CollisionCondition
{
    private String expectedOtherSpriteType;
    private CollisionStrategy strategy = DoNothingStrategy.instance;

    public String getOtherSpriteType() {
        return expectedOtherSpriteType;
    }

    public void setOtherSpriteType(String otherSpriteType) {
        this.expectedOtherSpriteType = otherSpriteType;
    }

    public CollisionStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(CollisionStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean attemptCollisionBehavior(Sprite thisSprite, Sprite otherSprite, CollisionType collisionType)
    {
        //Check if the other Sprite's type is the one that the user specified as able to interact with the target sprite
        String actualOtherSpriteType = otherSprite.getUserDefinedType();
        if (expectedOtherSpriteType.equals(actualOtherSpriteType))
        {
            //Perform the collision
            strategy.doCollisionBehavior(thisSprite, collisionType);
            return true;
        }
        return false;
    }
}
