/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Sep 26, 2021
 * @Editors:
 * @EditDate:
 **/
package gamemaker.model.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import gamemaker.Constants;
import gamemaker.Constants.CollisionSide;
import gamemaker.Constants.CollisionType;
import gamemaker.model.interfaces.Dumpable;
import gamemaker.model.sprite.Sprite;
import gamemaker.observer.pattern.Observer;
import javafx.geometry.Point2D;

public class CollisionEventHandler implements Observer, Dumpable {

	private HashMap<CollisionType, LinkedList<CollisionEvent>> collisionToActionMap;

	public CollisionEventHandler(Iterator<Integer> collisionKeys,
			HashMap<Integer, LinkedList<CollisionEvent>> spriteIdToCollisionEventsMap) {

		collisionToActionMap = new HashMap<CollisionType, LinkedList<CollisionEvent>>();
		buildCollisionHashMap(collisionKeys, spriteIdToCollisionEventsMap);
	}

	// LATE NIGHT NOTES
	// THINK WE NEED OBJECT VS GETHITOBJECT COLLISION TYPE!
	private void buildCollisionHashMap(Iterator<Integer> collisionKeys,
			HashMap<Integer, LinkedList<CollisionEvent>> spriteIdToCollisionEventsMap) {
		LinkedList<CollisionEvent> objectCollisionActions = new LinkedList<CollisionEvent>();
		LinkedList<CollisionEvent> screenCollisionActions = new LinkedList<CollisionEvent>();

		while (collisionKeys.hasNext()) {
			Integer key = collisionKeys.next();

			Iterator<CollisionEvent> aSpritesEvents = spriteIdToCollisionEventsMap.get(key).iterator();

			while (aSpritesEvents.hasNext()) {
				CollisionEvent event = aSpritesEvents.next();

				if (event.getCollisionType() == CollisionType.SCREEN) {
					screenCollisionActions.add(event);
				} else {
					objectCollisionActions.add(event);
				}
			}
		}
		collisionToActionMap.put(CollisionType.SCREEN, screenCollisionActions);
		collisionToActionMap.put(CollisionType.OBJECT, objectCollisionActions);
	}

	private void processCollisions() {
		// TODO rest of collision
		checkScreenBoundsCollision();
	}

	private void checkScreenBoundsCollision() {
		Iterator<CollisionEvent> screenCollisionActions = collisionToActionMap.get(CollisionType.SCREEN).iterator();

		while (screenCollisionActions.hasNext()) {
			CollisionEvent event = screenCollisionActions.next();
			Sprite sprite = event.getAction().getSprite();
			
			// Horizontal screen bound check
			// if moving left and beyond pixel 0 - force inside bounds
			if (sprite.getVelocity().getX() < Constants.ZERO && sprite.getPosition().getX() < Constants.ZERO) {
				HashMap<String, Object> actionParams = new HashMap<String, Object>();
				actionParams.put(Constants.KISS_POSITION_KEY, new Point2D(Constants.ZERO, sprite.getPosition().getY()));
				actionParams.put(Constants.COLLISION_SIDE_KEY, CollisionSide.LEFT);
				event.action.execute(actionParams);
			}
			// if moving right and beyond scene width - force inside bounds
			else if (sprite.getVelocity().getX() > 0
					&& (sprite.getPosition().getX() + sprite.getDimensions().getX()) >= Constants.GAME_CANVAS_WIDTH) {
				HashMap<String, Object> actionParams = new HashMap<String, Object>();
				actionParams.put(Constants.KISS_POSITION_KEY, new Point2D(
						(Constants.GAME_CANVAS_WIDTH - sprite.getDimensions().getX()), sprite.getPosition().getY()));
				actionParams.put(Constants.COLLISION_SIDE_KEY, CollisionSide.RIGHT);
				event.action.execute(actionParams);
			}

			// Vertical Scene Check
			// if moving up and beyound pixel 0 - force inside bounds
			if (sprite.getVelocity().getY() < 0 && sprite.getPosition().getY() < Constants.ZERO) {
				HashMap<String, Object> actionParams = new HashMap<String, Object>();
				actionParams.put(Constants.KISS_POSITION_KEY, new Point2D(sprite.getPosition().getX(), Constants.ZERO));
				actionParams.put(Constants.COLLISION_SIDE_KEY, CollisionSide.TOP);
				event.action.execute(actionParams);
			}

			// if moving down and beyond scene height - force inside bounds
			else if (sprite.getVelocity().getY() > 0
					&& (sprite.getPosition().getY() + sprite.getDimensions().getY()) >= Constants.GAME_CANVAS_HEIGHT) {
				HashMap<String, Object> actionParams = new HashMap<String, Object>();
				actionParams.put(Constants.KISS_POSITION_KEY, new Point2D(sprite.getPosition().getX(),
						Constants.GAME_CANVAS_HEIGHT - sprite.getDimensions().getY()));
				actionParams.put(Constants.COLLISION_SIDE_KEY, CollisionSide.BOTTOM);
				event.action.execute(actionParams);
			}
		}
	}

	@Override
	public void dump() {
		collisionToActionMap.clear();
		collisionToActionMap = null;
	}

	@Override
	public void update(double totalTime, double timeDelta) {
		processCollisions();
	}

}
