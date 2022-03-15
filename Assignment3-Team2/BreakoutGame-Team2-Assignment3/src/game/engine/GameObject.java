/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package game.engine;

import com.google.gson.JsonObject;

import collision.detection.ObjectCollidable;
import collision.detection.ScreenCollidable;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviours.NoMovement;
import movement.behaviours.SimpleMovement;

/**
 * Use the original setters the first time you set a variable. This will become
 * the original value and calling restart will set the varaible to the original
 * variable.
 **/
public abstract class GameObject extends DrawObject implements ScreenCollidable, ObjectCollidable {

	protected Movable moveBehaviour;
	protected Point2D previousPosition;
	protected Point2D velocity;
	protected double originalSpeed;
	protected double speed;
	protected Point2D originalMoveDirection;
	protected Point2D moveDirection;

	public GameObject() {
		super();
		moveBehaviour = new NoMovement();
		previousPosition = new Point2D(0, 0);
		velocity = new Point2D(0, 0);
		originalSpeed = 0;
		speed = originalSpeed;
		originalMoveDirection = new Point2D(0, 0);
		moveDirection = originalMoveDirection;
	}

	public GameObject(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension, Movable moveBehaviour,
			Point2D previousPosition, Point2D velocity, double originalSpeed, double speed,
			Point2D originalMoveDirection, Point2D moveDirection) 
	{
		super(id, drawBehaviour, originalColor, color, originalPosition, position, originalDimension, dimension);
		this.moveBehaviour = moveBehaviour;
		this.previousPosition = previousPosition;
		this.velocity = velocity;
		this.originalSpeed = originalSpeed;
		this.speed = speed;
		this.originalMoveDirection = originalMoveDirection;
		this.moveDirection = moveDirection;
	}

	public Movable getMoveBehaviour() {
		return moveBehaviour;
	}

	public void setMoveBehaviour(Movable moveBehaviour) {
		this.moveBehaviour = moveBehaviour;
	}

	public Point2D getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Point2D previousPosition) {
		this.previousPosition = previousPosition;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public double getOriginalSpeed() {
		return originalSpeed;
	}

	public void setOriginalSpeed(double speed) {
		this.originalSpeed = speed;
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Point2D getOriginalMoveDirection() {
		return originalMoveDirection;
	}

	public void setOriginalMoveDirection(Point2D moveDirection) {
		this.originalMoveDirection = moveDirection;
		this.moveDirection = moveDirection;
	}

	public Point2D getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(Point2D moveDirection) {
		this.moveDirection = moveDirection;
	}

	public void reset() {
		super.reset();
		setSpeed(originalSpeed);
		setMoveDirection(originalMoveDirection);
	}

	/*
	 * The below methods take a Point2D position parameter. So we can send it
	 * Position or PreviousPosition. Thus, not have a
	 * getPositionCenter/getPreviousPositionCenter etc.
	 */
	public Point2D getCenter(Point2D position) {
		return new Point2D(position.getX() + dimension.getX() / 2, position.getY() + dimension.getY() / 2);
	}

	public Point2D getUpperLeft(Point2D position) {
		return new Point2D(getCenter(position).getX() - dimension.getX() / 2,
				getCenter(position).getY() - dimension.getY() / 2);
	}

	public Point2D getLowerLeft(Point2D position) {
		return new Point2D(getCenter(position).getX() - dimension.getX() / 2,
				getCenter(position).getY() + dimension.getY() / 2);
	}

	public Point2D getUpperRight(Point2D position) {
		return new Point2D(getCenter(position).getX() + dimension.getX() / 2,
				getCenter(position).getY() - dimension.getY() / 2);
	}

	public Point2D getLowerRight(Point2D position) {
		return new Point2D(getCenter(position).getX() + dimension.getX() / 2,
				getCenter(position).getY() + dimension.getY() / 2);
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
			sb.append(BreakoutJsonUtility.Serializer.serializeString("class", this.getClass().getTypeName()));
		}
		
		sb.append(super.save(false));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("moveBehaviour", moveBehaviour.getClass().getTypeName()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("previousPosition", previousPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("velocity", velocity));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeDouble("originalSpeed", originalSpeed));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeDouble("speed", speed));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("originalMoveDirection", originalMoveDirection));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("moveDirection", moveDirection));

		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}
	
	@Override
	public void load(JsonObject jsonObject) {
		super.load(jsonObject);
		moveBehaviour = getMoveBehaviourFromString(BreakoutJsonUtility.Deserializer.deserializeString("moveBehaviour", jsonObject));
		previousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("previousPosition", jsonObject);
		velocity = BreakoutJsonUtility.Deserializer.deserializePoint2D("velocity", jsonObject);
		originalSpeed = BreakoutJsonUtility.Deserializer.deserializeDouble("originalSpeed", jsonObject);
		speed = BreakoutJsonUtility.Deserializer.deserializeDouble("speed", jsonObject);
		originalMoveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("originalMoveDirection", jsonObject);
		moveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("moveDirection", jsonObject);
	}

	private Movable getMoveBehaviourFromString(String typeOfString) {
		if (typeOfString.compareToIgnoreCase("movement.behaviours.NoMovement") == 0) {
			return new NoMovement();
		} else if (typeOfString.compareToIgnoreCase("movement.behaviours.SimpleMovement") == 0) {
			return new SimpleMovement();
		} else {
			throw new UnsupportedOperationException("Cannot convert class " + typeOfString + " to a drawable.");
		}
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
