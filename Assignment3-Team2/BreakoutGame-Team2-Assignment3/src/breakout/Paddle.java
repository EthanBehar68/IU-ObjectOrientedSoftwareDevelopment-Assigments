/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package breakout;

import com.google.gson.JsonObject;

import command.pattern.PaddleMoveCommand;
import command.pattern.PaddleScreenCollisionCommand;
import composite.pattern.BreakoutJsonUtility;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import input.KeyPolling;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Paddle extends GameObject {

	public Paddle() {
		super();
	}

	public Paddle(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension, Movable moveBehaviour,
			Point2D previousPosition, Point2D velocity, double originalSpeed, double speed,
			Point2D originalMoveDirection, Point2D moveDirection) 
	{
		super(id, drawBehaviour, originalColor, color, originalPosition, position, originalDimension, dimension,
				moveBehaviour, previousPosition, velocity, originalSpeed, speed, originalMoveDirection, moveDirection);
	}

	// TODO get keypolling instance out of here
	// TODO Additionally this should happen before observers are updated.
	public void captureMoveDirection() {
		if (KeyPolling.getInstance().isDown(KeyCode.RIGHT)) {
			moveDirection = new Point2D(1, 0);
		} else if (KeyPolling.getInstance().isDown(KeyCode.LEFT)) {
			moveDirection = new Point2D(-1, 0);
		} else {
			// Not moving
			moveDirection = new Point2D(0, 0);
		}
	}

	/************************************
	 * Observable Implementations
	 ************************************/
	@Override
	public void update(double timeDelta) {
		commandListener.receiveCommand(new PaddleMoveCommand(this, timeDelta));
	}

	/************************************
	 * Collision Implementations
	 ************************************/
	@Override
	public void handleScreenCollision(Point2D newPosition, Point2D newMoveDirection) {
		commandListener.receiveCommand(new PaddleScreenCollisionCommand(this, newPosition));
	}

	@Override
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
		// nothing
	}

	/************************************
	 * Savable Implementations
	 * A leaf Savable
	 ************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();
		
		if(encloseMyself) {
			sb.append("{");
		}
		
		sb.append(BreakoutJsonUtility.Serializer.serializeString("class", this.getClass().getTypeName()));
		sb.append(",");
		sb.append(super.save(false));
		
		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		super.load(jsonObject);
	}

	@Override
	public void addSavable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeSavable() {
		throw new UnsupportedOperationException();
	}
}
