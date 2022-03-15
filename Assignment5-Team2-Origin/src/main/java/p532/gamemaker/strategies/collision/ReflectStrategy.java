package p532.gamemaker.strategies.collision;


import p532.gamemaker.sprite.Sprite;

public class ReflectStrategy implements CollisionStrategy
{
    private final static int MAX_SPEED_INCREASE = 2; //exclusive
    /**
     * A static ReflectStrategy that can be used instead of the constructor.
     */
    public static final ReflectStrategy instance = new ReflectStrategy();

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType)
    {
        //Define some abbreviations
        double colliderVelX = colliderOrImpactee.getVelocityX();
        double colliderVelY = colliderOrImpactee.getVelocityY();

        //Check where the collider hit the impactee
        if (collisionType == CollisionType.TopImpact || collisionType == CollisionType.BottomImpact)
        {
            //ReflectStrategy the collider in the opposite direction
            double yVelocityAdjustment = -2 * colliderVelY; //TODO
            colliderOrImpactee.setVelocityY(colliderVelY + yVelocityAdjustment);
            /*
             Angle adjustment:
             The x-velocity can increase in speed by as much as MAX_REFLECT_ANGLE_RANGE.
             */
            int reflectAngle = (int)(Math.random() * MAX_SPEED_INCREASE);
            if (colliderVelX < 0)
                reflectAngle *= -1; //makes negative x-velocities speed up
            colliderOrImpactee.setVelocityX(colliderVelX + reflectAngle);

            //Track this reflection as part of the frame history
            //by saving a reference to the velocity adjustments and the BreakOutObject changed
            //recordCommand(colliderOrImpactee, reflectAngle, yVelocityAdjustment);
            //recordCommand(colliderOrImpactee, colliderVelX, colliderVelY);
        }
        else if (collisionType == CollisionType.LeftImpact || collisionType == CollisionType.RightImpact)
        {
            //ReflectStrategy the collider in the opposite direction
            double xVelocityAdjustment = -2 * colliderVelX;
            colliderOrImpactee.setVelocityX(colliderVelX + xVelocityAdjustment);
            /*
             Angle adjustment:
             The y-velocity can increase in speed by as much as MAX_REFLECT_ANGLE_RANGE.
             */
            int reflectAngle = (int)(Math.random() * MAX_SPEED_INCREASE);
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
    }

    /*private void recordCommand(BreakOutObject collider, double previousVelX, double previousVelY)
    {
        //Create a Command to record the result of the reflection
        Command commandExecuted = new ReflectCommand(collider, previousVelX, previousVelY);
        //Save the command to the frame that this reflection happened in.
        commandExecuted.saveToFrame();
    }*/

    @Override
    public String toString() {
        return "Reflect";
    }
}
