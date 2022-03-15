/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Abhishek Tiwari, Ethan Taylor Behar
 * @References:
 * https://stackoverflow.com/questions/4904308/axis-aligned-bounding-boxes-collision-what-sides-are-colliding
 **/
package collision.detection;

import java.util.ArrayList;
import java.util.Hashtable;
import breakout.Brick;
import game.engine.GameObject;
import javafx.geometry.Point2D;

/**
 * Note 1:
 * Towards the bottom we have methods like kissObjects.
 * This simply means correct their positions so they are just barely touching.
 * I.e. this will push two objects against their bounds instead of having them intersecting each other.
 **/
public class CollisionHandler2D {
	    
    private ArrayList<GameObject> gameObjects;
    private Hashtable<GameObject, GameObject> collidedObjects = new Hashtable<GameObject, GameObject>();
    private double gameSceneWidth;
    private double gameSceneHeight;
	private final static int ZERO_BOUND = 0;
	private final static int ONE = 1;
	private final static int FLIP_DIRECTION = -1;
	private final static String LEFT = "LEFT";
	private final static String RIGHT = "RIGHT";
	private final static String TOP = "TOP";
	private final static String BOTTOM = "BOTTOM";
	
    public CollisionHandler2D() {
    	gameObjects = new ArrayList<GameObject>();
    	collidedObjects = new Hashtable<GameObject, GameObject>();
    }
    
	public void setGameSceneDimensions(double width, double height) {
		gameSceneWidth = width;
		gameSceneHeight = height;
	}
	
	public void restartFromLoadFile() {
		dumpLists();
	}
    
    public void reset() {
    	dumpLists();
    }
    
    private void dumpLists() {
        // Dump gameObjects for a fresh start
    	gameObjects.clear();
    	gameObjects = null;
    	gameObjects = new ArrayList<GameObject>();
    	
    	// Dump any tracked collided objects for a fresh start
    	collidedObjects.clear();
    	collidedObjects = null;
    	collidedObjects = new Hashtable<GameObject, GameObject>();
    }
	
    public void addGameObject(GameObject object) {
		// Prevent double registration
		if(!gameObjects.contains(object)) {
			gameObjects.add(object);
		}
    }
    
    public void removeGameObject(GameObject object) {
		// Ensure GameObject is already registered
		int objectIndex = gameObjects.indexOf(object);
		// Then remove it
		if (objectIndex >= 0) {
			gameObjects.remove(object);
		}
    }
    
    public void processCollisions() {
		for (GameObject object1 : gameObjects) {
			for (GameObject object2 : gameObjects) {
				// Skip processing the same two objects
				// Skip if both are bricks 
				// TODO how to make Brick check cleaner?
				if(object1 == object2 || 
						(object1 instanceof Brick && object2 instanceof Brick))
				{
					continue;
				}
				// See class level comment.
				// commandListener.receiveCommand(new CollisionObjectCheckCommand(this, object1, object2));
				checkObjectCollision(object1, object2);
			}
			// See class level comment.
			// commandListener.receiveCommand(new CollisionScreenCheckCommand(this, object1));
			checkScreenCollision(object1);
		}
		// See class level comment.
		// Reset collided tracker per tick
		// commandListener.receiveCommand(new CollisionClearCollidedDictionaryCommand(this));
		collidedObjects.clear();
    }
    
    public void clearCollidedObjects() {
		collidedObjects.clear();
    }

	public void checkObjectCollision(GameObject object1, GameObject object2) {
		// Obtain object positions
		Point2D object1Position = object1.getPosition();
		Point2D object2Position = object2.getPosition();
		
		// Check if positions are overlapping
		if(overlapCheck(object1, object2, object1Position, object2Position)) 
		{
        	handleObjectCollision(object1, object2);                
		}
	}

	private boolean overlapCheck(GameObject object1, GameObject object2, 
			Point2D object1Position, Point2D object2Position) 
	{
		return object1.getUpperRight(object1Position).getX() >= object2.getUpperLeft(object2Position).getX() &&
				object1.getUpperLeft(object1Position).getX() <= object2.getUpperRight(object2Position).getX() &&
				object1.getLowerLeft(object1Position).getY() >= object2.getUpperLeft(object2Position).getY() &&
				object1.getUpperLeft(object1Position).getY() <= object2.getLowerLeft(object2Position).getY();
	}
    
    public void handleObjectCollision(GameObject object1, GameObject object2) {
    	// Due to the nature of the double loop it is possible to detect a collision twice in one tick()
    	// Break if we already dealt with this collision
    	if((collidedObjects.containsKey(object1) && collidedObjects.get(object1) == object2) ||
    			(collidedObjects.containsKey(object2) && collidedObjects.get(object2) == object1))
    	{
    		//System.out.println("skipping already processed collision.");
    		return;
    	} else {
    		// Track that these two objects collided
        	collidedObjects.put(object1, object2);
        	
        	// Determine collision direction
        	String collisionDirection = determineCollisionDirection(object1, object2);
        	if (collisionDirection == "") {
        		System.out.println("ERRROOOOORRRRRRRRRRRRRRRRRRRRRRRRR");
        	}
        	
        	// Tell objects they collided
        	kissObjects(object1, object2, collisionDirection);
    	}
    }
    
    private String determineCollisionDirection(GameObject object1, GameObject object2) {
    	double leftPenetration = 9999;
    	double rightPenetration = 9999;
    	double topPenetration = 9999;
    	double bottomPenetration = 9999;
    	String direction = "";
    	    	
    	// Object1 hit from the left
    	if(object1.getPosition().getX() < object2.getPosition().getX() 
    			&& object2.getPosition().getX() < object1.getUpperRight(object1.getPosition()).getX()) {   
    	    leftPenetration = object1.getUpperRight(object1.getPosition()).getX() - object2.getPosition().getX();
    	    // System.out.println("LeftPen: " + leftPenetration);
    	}

    	// Object1 hit from the right
    	if(object2.getPosition().getX() < object1.getPosition().getX() 
    			&& object1.getPosition().getX() < object2.getUpperRight(object2.getPosition()).getX()) {
    	    rightPenetration = object1.getPosition().getX() - object2.getUpperRight(object2.getPosition()).getX();
    	    // System.out.println("RightPen: " + rightPenetration);
    	}

    	// Object1 hit from the top
    	if(object1.getPosition().getY() < object2.getPosition().getY() 
    			&& object2.getPosition().getY() < object1.getLowerLeft(object1.getPosition()).getY()) {
    		topPenetration = object2.getPosition().getY() - object1.getLowerLeft(object1.getPosition()).getY();
    	    // System.out.println("TopPen: " + topPenetration);
    	}

    	// Object1 hit from the bottom
    	if(object2.getPosition().getY() < object1.getPosition().getY() 
    			&& object1.getPosition().getY() < object2.getLowerLeft(object2.getPosition()).getY()) {
    		bottomPenetration = object1.getPosition().getY() - object2.getLowerLeft(object2.getPosition()).getY();
    	    // System.out.println("BottomPen: " + bottomPenetration);
    	}
    	
    	leftPenetration = Math.abs(leftPenetration);
    	rightPenetration = Math.abs(rightPenetration);
    	topPenetration = Math.abs(topPenetration);
    	bottomPenetration = Math.abs(bottomPenetration);
    	
    	if (leftPenetration < rightPenetration && leftPenetration < topPenetration && leftPenetration < bottomPenetration) {
    		direction = LEFT;
    	} else if (rightPenetration < leftPenetration && rightPenetration < topPenetration && rightPenetration < bottomPenetration) {
    		direction = RIGHT;
    	} else if (topPenetration < leftPenetration && topPenetration < rightPenetration && topPenetration < bottomPenetration) {
    		direction = TOP;
    	} else if (bottomPenetration < leftPenetration && bottomPenetration < rightPenetration && bottomPenetration < topPenetration) {
    		direction = BOTTOM;
    	} else {
    		if (leftPenetration == topPenetration) {
    			direction = "TOPLEFT";
    		}
    		if (rightPenetration == topPenetration) {
    			direction = "TOPRIGHT";
    		}
    		if (leftPenetration == bottomPenetration) {
    			direction = "BOTTOMLEFT";
    		}
    		if (rightPenetration == bottomPenetration) {
    			direction = "BOTTOMRIGHT";
    		}
    	}

    	return direction;
    }
    
    private void kissObjects(GameObject object1, GameObject object2, String collisionDirection) {
		// Grab necessary info
    	double object1XMoveDirection = object1.getMoveDirection().getX();
		double object1YMoveDirection = object1.getMoveDirection().getY();
		double object1XPosition = object1.getPosition().getX();
		double object1YPosition = object1.getPosition().getY();
		
		double object2XMoveDirection = object2.getMoveDirection().getX();
		double object2YMoveDirection = object2.getMoveDirection().getY();
		double object2XPosition = object2.getPosition().getX();
		double object2YPosition = object2.getPosition().getY();
		
		// Correct and update position/moveDirection based on
		// collision from the top.
		if (collisionDirection.contains("TOP")) {
			object1YMoveDirection *= FLIP_DIRECTION;
			object2YMoveDirection *= FLIP_DIRECTION;

			object1YPosition = object2.getPosition().getY() - object1.getDimensions().getY() - ONE;
			object2YPosition = object1.getPosition().getY() + object1.getDimensions().getY() + ONE;
		}
		// Correct and update position/moveDirection based on
		// collision from the bottom.
		if (collisionDirection.contains("BOTTOM")) {
			object1YMoveDirection *= FLIP_DIRECTION;
			object2YMoveDirection *= FLIP_DIRECTION;
			
			object1YPosition = object2.getPosition().getY() + object2.getDimensions().getY() + ONE;
			object2YPosition = object1.getPosition().getY() - object2.getDimensions().getY() - ONE;
		}
		// Correct and update position/moveDirection based on
		// collision from the right.
		if (collisionDirection.contains("RIGHT")) {
			object1XMoveDirection *= FLIP_DIRECTION;
			object2XMoveDirection *= FLIP_DIRECTION;
			
			object1XPosition = object2.getPosition().getX() + object2.getDimensions().getX() + ONE;
			object2XPosition = object1.getPosition().getX() - object2.getDimensions().getX() - ONE;
		}
		// Correct and update position/moveDirection based on
		// collision from the left.
		if(collisionDirection.contains("LEFT")) {
			object1XMoveDirection *= FLIP_DIRECTION;
			object2XMoveDirection *= FLIP_DIRECTION;
			
			object1XPosition = object2.getPosition().getX() - object1.getDimensions().getX() - ONE;
			object2XPosition = object1.getPosition().getX() + object1.getDimensions().getX() + ONE;
		}

        object1.handleObjectCollision(object2, 
                new Point2D(object1XPosition, object1YPosition), 
                new Point2D(object1XMoveDirection, object1YMoveDirection));
        object2.handleObjectCollision(object1, 
                new Point2D(object2XPosition, object2YPosition), 
                new Point2D(object2XMoveDirection, object2YMoveDirection));
    }

    public void checkScreenCollision(GameObject object) {
    	Point2D originalMoveDirection = object.getMoveDirection();
    	// If object hit wall - kiss it to wall
    	Point2D newPosition = kissObjectToWall(object);
    	// If hit wall make sure it bounces off wall
    	// It's up to the object to decided if it bounces or not.
    	// This class just tells object the new move direction.
    	Point2D newMoveDirection = wallBounceCheck(object, newPosition);

    	// If the original and new aren't the same we know a screen collision happened so make the call
    	// Since position using floats we avoid checking those due to percision issues.
    	if(originalMoveDirection.getX() != newMoveDirection.getX() || originalMoveDirection.getY() != newMoveDirection.getY())
    	{
    		object.handleScreenCollision(newPosition, newMoveDirection);
    	}
    }
    
    private Point2D kissObjectToWall(GameObject object) {
    	Point2D position = object.getPosition();
    	Point2D dimensions = object.getDimensions();
		double positionX = position.getX();
		double positionY = position.getY();
		Point2D velocity = object.getVelocity();
		
		// Horizontal Scene Check
		// if moving left and beyond pixel 0 - force inside bounds
		if (velocity.getX() < 0 && position.getX() < ZERO_BOUND) {
			positionX = ZERO_BOUND;
		}
		// if moving right and beyond scene width - force inside bounds
		else if (velocity.getX() > 0 && (position.getX() + dimensions.getX()) >= gameSceneWidth) { 
			positionX = gameSceneWidth - dimensions.getX();
		} 
		
		// Vertical Scene Check
		// if moving up and beyound pixel 0 - force inside bounds
		if (velocity.getY() < 0 && position.getY() < ZERO_BOUND) {
			positionY = ZERO_BOUND;
		}
		// if moving down and beyond scene height - force inside bounds
		else if (velocity.getY() > 0 && (position.getY() + dimensions.getY()) >= gameSceneHeight) { 
			positionY = gameSceneHeight - dimensions.getY();
		} 
		
		return new Point2D(positionX, positionY);
    }
    
	public Point2D wallBounceCheck(GameObject object, Point2D newPosition) {
		double horizontalDirection = object.getMoveDirection().getX();
		double verticalDirection = object.getMoveDirection().getY();
		
		// If touching left or right bound flip horizontal move direction
		if (newPosition.getX() == ZERO_BOUND || 
				newPosition.getX() == (gameSceneWidth - object.getDimensions().getX())) 
		{
			horizontalDirection *= FLIP_DIRECTION;
		}

		// If touching top or bottom bound flip vertical move direction
        if (newPosition.getY() == ZERO_BOUND || 
        		newPosition.getY() == (gameSceneHeight - object.getDimensions().getY())) 
        {
			verticalDirection *= FLIP_DIRECTION;
		}
		
		// TODO proper game over
		if (newPosition.getY() == (gameSceneHeight - object.getDimensions().getY())) {
			System.out.println("Game Over here...");
		}
	
		return new Point2D(horizontalDirection, verticalDirection);
	}
}
