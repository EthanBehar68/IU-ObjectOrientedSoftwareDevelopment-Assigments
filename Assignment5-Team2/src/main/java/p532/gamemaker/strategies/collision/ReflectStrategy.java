package p532.gamemaker.strategies.collision;


import java.io.File;

import p532.gamemaker.Constants;
import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.CollisionStrategy;

public class ReflectStrategy implements CollisionStrategy
{
    private final static int MAX_SPEED_INCREASE = 2; //exclusive
    /**
     * A static ReflectStrategy that can be used instead of the constructor.
     */
    public static final ReflectStrategy instance = new ReflectStrategy();

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx)
    {
        //Define some abbreviations
        double colliderVelX = colliderOrImpactee.getVelocityX();
        double colliderVelY = colliderOrImpactee.getVelocityY();

        //Check where the collider hit the impactee
        if (collisionType == Constants.CollisionType.TOP_IMPACT || collisionType == Constants.CollisionType.BOTTOM_IMPACT)
        {
            //ReflectStrategy the collider in the opposite direction
            double yVelocityAdjustment = -2 * colliderVelY; //TODO
            colliderOrImpactee.setVelocityY(colliderVelY + yVelocityAdjustment);
            /*
             Angle adjustment:
             The x-velocity can increase in speed by as much as MAX_SPEED_INCREASE.
             */
            int reflectAngle = (int)(Constants.RANDOM.nextFloat() * MAX_SPEED_INCREASE);
            if (colliderVelX < 0)
                reflectAngle *= -1; //makes negative x-velocities speed up
            colliderOrImpactee.setVelocityX(colliderVelX + reflectAngle);

            //Track this reflection as part of the frame history
            //by saving a reference to the velocity adjustments and the BreakOutObject changed
            //recordCommand(colliderOrImpactee, reflectAngle, yVelocityAdjustment);
            //recordCommand(colliderOrImpactee, colliderVelX, colliderVelY);
        }
        else if (collisionType == Constants.CollisionType.LEFT_IMPACT || collisionType == Constants.CollisionType.RIGHT_IMPACT)
        {
            //ReflectStrategy the collider in the opposite direction
            double xVelocityAdjustment = -2 * colliderVelX;
            colliderOrImpactee.setVelocityX(colliderVelX + xVelocityAdjustment);
            /*
             Angle adjustment:
             The y-velocity can increase in speed by as much as MAX_SPEED_INCREASE.
             */
            int reflectAngle = (int)(Constants.RANDOM.nextFloat() * MAX_SPEED_INCREASE);
            if (colliderVelY < 0)
                reflectAngle *= -1; //makes negative y-velocities speed up
            colliderOrImpactee.setVelocityY(colliderVelY + reflectAngle);

            //Track this reflection as part of the frame history
            //by saving a reference to the velocity adjustments and the BreakOutObject changed
            //recordCommand(colliderOrImpactee, xVelocityAdjustment, reflectAngle);
            //recordCommand(colliderOrImpactee, colliderVelX, colliderVelY);
        }
        else
            throw new IllegalArgumentException("CollisionType unknown or invalid.");
        
        if(soundFx != null) {
        	SoundEngine.getInstance().playAudio(soundFx);
        }
    }

    @Override
    public String toString() {
        return "Reflect";
    }

	@Override
	public void setup(Sprite sprite) {
		return;
	}
}
