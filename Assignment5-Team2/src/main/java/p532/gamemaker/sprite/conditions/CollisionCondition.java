package p532.gamemaker.sprite.conditions;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.DoNothingStrategy;

@JsonInclude(Include.NON_NULL)
public class CollisionCondition
{
    private String expectedOtherSpriteType;
    private CollisionStrategy strategy = DoNothingStrategy.instance;
    private File soundFxFile;
    
    public File getSoundFxFile() {
		return soundFxFile;
	}

	public void setSoundFxFile(File soundFxFile) {
		this.soundFxFile = soundFxFile;
	}

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

    public boolean attemptCollisionBehavior(Sprite thisSprite, Sprite otherSprite, Constants.CollisionType collisionType)
    {
        //Check if the other Sprite's type is the one that the user specified as able to interact with the target sprite
        String actualOtherSpriteType = otherSprite.getUserDefinedType();
        if (expectedOtherSpriteType.equals(actualOtherSpriteType))
        {
            //Perform the collision
            strategy.doCollisionBehavior(thisSprite, otherSprite, collisionType, soundFxFile);
    		return true;
        }
        return false;
    }
}
