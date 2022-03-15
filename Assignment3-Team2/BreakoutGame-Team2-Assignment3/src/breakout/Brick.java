/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 * @References:
 * https://guides.codepath.com/android/Creating-Custom-Listeners
 **/
package breakout;

import com.google.gson.JsonObject;

import command.pattern.BrickDestroyCommand;
import composite.pattern.BreakoutJsonUtility;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Brick extends GameObject {

	public Brick() {
		super();
	}

	public Brick(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension, Movable moveBehaviour,
			Point2D previousPosition, Point2D velocity, double originalSpeed, double speed,
			Point2D originalMoveDirection, Point2D moveDirection) 
	{
		super(id, drawBehaviour, originalColor, color, originalPosition, position, originalDimension, dimension,
				moveBehaviour, previousPosition, velocity, originalSpeed, speed, originalMoveDirection, moveDirection);
	}

	/************************************
	 * Observable Implementations
	 ************************************/
	@Override
	public void update(double timeDelta) {
		// nothing
	}

	/************************************
	 * Collision Implementations
	 ************************************/
	@Override
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
		commandListener.receiveCommand(new BrickDestroyCommand(this));
	}

	@Override
	public void handleScreenCollision(Point2D newPosition, Point2D newMoveDirection) {
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
