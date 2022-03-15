package p532.gamemaker.utility;

import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.CollisionType;

import java.util.Iterator;
import java.util.List;

public class CollisionDetectionUtility
{
    /**
     * Determines the collision type by checking where the collision happened.
     * No collision occurs if either object is marked as destroyed.
     * @param impactee the object that collider might be hitting/touching since collider object is moving
     * @return any one of the possible CollisionTypes.
     * Ex: Return LeftImpact if the collider hits the impactee's left side.
     */
    public static CollisionType checkForCollision(Sprite collider, Sprite impactee)
    {
        //If either object is destroyed, there is no collision
        if (collider.isDestroyed() || impactee.isDestroyed())
            return CollisionType.NoCollision;

        //Define some abbreviations
        double colliderX = collider.getX();
        double colliderY = collider.getY();
        double impacteeX = impactee.getX();
        double impacteeY = impactee.getY();
        double colliderVelX = collider.getVelocityX();
        double colliderVelY = collider.getVelocityY();
        //double colliderWidth = collider.getView().getWidth();
        //double colliderHeight = collider.getView().getHeight();
        double impacteeWidth = impactee.getView().getSpriteWidth();
        double impacteeHeight = impactee.getView().getSpriteHeight();

        //If the collider's y-velocity is so fast that it will speed right through the impactee...
        if (Math.abs(colliderVelY) > impacteeHeight)
        {
            if (colliderX >= impacteeX && colliderX <= impacteeX + impacteeWidth) {
                if (colliderY < impacteeY && colliderY + colliderVelY >= impacteeY)
                    return CollisionType.TopImpact;
                else if (colliderY > impacteeY && colliderY + colliderVelY <= impacteeY)
                    return CollisionType.BottomImpact;
            }
        }
        //If the collider's x-velocity is so fast that it will speed right through the impactee...
        else if (Math.abs(colliderVelX) > impacteeWidth)
        {
            if (colliderY >= impacteeY && colliderY <= impacteeY + impacteeHeight) {
                if (colliderX < impacteeX && colliderX + colliderVelX >= impacteeX)
                    return CollisionType.RightImpact;
                else if (colliderX > impacteeX && colliderX + colliderVelX <= impacteeX)
                    return CollisionType.LeftImpact;
            }
        }

        //Check if the collider is moving more vertically than horizontally. If so, it may be a vertical impact.
        if (Math.abs(colliderVelY) - Math.abs(colliderVelX) > 0)
        {
            //Return a left or right collision if it is occurring.
            CollisionType output = checkForVerticalCollision(collider, impactee);
            if (output != CollisionType.NoCollision)
                return output;
                //Collision could instead be horizontal contrary to the prediction
            else
                return checkForHorizontalCollision(collider, impactee);
        }
        //Else if the collider has more x-velocity than y-velocity, it may be a horizontal impact
        else
        {
            //Return a top or bottom collision if it is occurring.
            CollisionType output = checkForHorizontalCollision(collider, impactee);
            if (output != CollisionType.NoCollision)
                return output;
                //Collision could instead be vertical contrary to the prediction
            else
                return checkForVerticalCollision(collider, impactee);
        }
    }


    private static CollisionType checkForVerticalCollision(Sprite collider, Sprite impactee)
    {
        //Define some abbreviations
        double colliderX = collider.getX();
        double colliderY = collider.getY();
        double impacteeX = impactee.getX();
        double impacteeY = impactee.getY();
        double colliderWidth = collider.getView().getSpriteWidth(); //TODO this was unused previously; check if produces correct results
        double colliderHeight = collider.getView().getSpriteHeight();
        double impacteeWidth = impactee.getView().getSpriteWidth();
        double impacteeHeight = impactee.getView().getSpriteHeight();

        //Check if the x-coordinate of the collider is inside the impactee
        if (colliderX >= impacteeX - colliderWidth && colliderX <= impacteeX + impacteeWidth)
        {
            //Check if collider touches the TOP half of the impactee (top collision)
            if (colliderY + colliderHeight >= impacteeY && colliderY + colliderHeight <= impacteeY + (impacteeHeight / 2))
                return CollisionType.TopImpact;
                //Check if collider touches the BOTTOM half of the impactee (bottom collision)
            else if (colliderY >= impacteeY + (impacteeHeight / 2) && colliderY <= impacteeY + impacteeHeight)
                return CollisionType.BottomImpact;
        }

        //Default: The objects aren't touching
        return CollisionType.NoCollision;
    }


    private static CollisionType checkForHorizontalCollision(Sprite collider, Sprite impactee)
    {
        //Define some abbreviations
        double colliderX = collider.getX();
        double colliderY = collider.getY();
        double impacteeX = impactee.getX();
        double impacteeY = impactee.getY();
        double colliderWidth = collider.getView().getSpriteWidth();
        double colliderHeight = collider.getView().getSpriteHeight();
        double impacteeWidth = impactee.getView().getSpriteWidth();
        double impacteeHeight = impactee.getView().getSpriteHeight();

        //Check if the y-coordinate of the collider is inside the impactee
        if (colliderY >= impacteeY - colliderHeight && colliderY <= impacteeY + impacteeHeight)
        {
            //Check if collider touches the LEFT half of the impactee (left collision)
            if (colliderX + colliderWidth >= impacteeX && colliderX + colliderWidth <= impacteeX + (impacteeWidth / 2))
                return CollisionType.LeftImpact;
                //Check if collider touches the RIGHT half of the impactee (right collision)
            else if (colliderX >= impacteeX + (impacteeWidth / 2) && colliderX <= impacteeX + impacteeWidth)
                return CollisionType.RightImpact;
        }

        //Default: The objects aren't touching
        return CollisionType.NoCollision;
    }


    /**
     * Checks each Sprite in the list to see if it collides with (is touching)
     * any other Sprite in the list. If it is, fires the object's collision behavior methods.
     * @param allSprites a list of all Sprites in the game
     *                           that should have their collisions checked
     */
    public static void checkForCollisions(List<Sprite> allSprites)
    {
        /*
        Check for collisions...
        Define two iterators to iterate through allSprites.
        colliderIterator checks if its object is hitting something else.
        impacteeIterator checks if its object is being hit.
         */
        Iterator<Sprite> colliderIterator = allSprites.listIterator();
        int i = 0; //index of collider
        int j = 0; //index of impactee
        while (colliderIterator.hasNext())
        {
            //Pull one object from the iterator
            Sprite collider = colliderIterator.next();

            Iterator<Sprite> impacteeIterator = allSprites.listIterator();
            while (impacteeIterator.hasNext())
            {
                Sprite impactee = impacteeIterator.next();
                //Make sure collider != impactee
                if (i != j)
                {
                    //Check for the collision
                    CollisionType collisionType = CollisionDetectionUtility.checkForCollision(collider, impactee);
                    if (collisionType != CollisionType.NoCollision) {
                        //Fire the collision events
                        collider.onHitSomething(impactee, collisionType);
                        impactee.onGetHit(collider, collisionType);
                    }
                }
                j++; //Update impactee index
            }
            i++; //Update collider index
        }
    }
    
    
    public static CollisionType getcheckForHorizontalCollision(Sprite collider, Sprite impactee)
    {
    	return checkForHorizontalCollision(collider,impactee);
    }
    
    public static CollisionType getcheckForVerticalCollision(Sprite collider, Sprite impactee)
    {
    	return checkForVerticalCollision(collider,impactee);
    }
}

