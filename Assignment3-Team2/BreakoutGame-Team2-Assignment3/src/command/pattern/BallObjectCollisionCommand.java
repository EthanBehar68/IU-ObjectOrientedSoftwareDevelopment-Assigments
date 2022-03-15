/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 16, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import com.google.gson.JsonObject;

import breakout.Ball;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;

public class BallObjectCollisionCommand extends Command {

	private Ball ball;
	private Point2D newPosition;
	private Point2D newMoveDirection;
	// For deserialization
	private int ballId;

	private Point2D undoPosition;
	private Point2D undoPreviousPosition;
	private Point2D undoMoveDirection;

	private Point2D redoPosition;
	private Point2D redoPreviousPosition;
	private Point2D redoMoveDirection;

	public BallObjectCollisionCommand() {
	}

	public BallObjectCollisionCommand(Ball ball, Point2D newPosition, Point2D newMoveDirection) {
		this.ball = ball;
		this.newPosition = newPosition;
		this.newMoveDirection = newMoveDirection;
	}
	
	/*
	 * Should be used by the Deserializor only!
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public int getBallId() {
		return ballId;
	}

	/************************************
	 * Command Implementations
	 ************************************/
	@Override
	public void execute() {
		undoPosition = ball.getPosition();
		undoPreviousPosition = ball.getPreviousPosition();
		undoMoveDirection = ball.getMoveDirection();

		ball.setPreviousPosition(ball.getPosition());
		ball.setPosition(newPosition);
		ball.setMoveDirection(newMoveDirection);

		redoPosition = ball.getPosition();
		redoPreviousPosition = ball.getPreviousPosition();
		redoMoveDirection = ball.getMoveDirection();
	}

	@Override
	public void undo() {
		ball.setPosition(undoPosition);
		ball.setPreviousPosition(undoPreviousPosition);
		ball.setMoveDirection(undoMoveDirection);
	}

	@Override
	public void redo() {
		ball.setPosition(redoPosition);
		ball.setPreviousPosition(redoPreviousPosition);
		ball.setMoveDirection(redoMoveDirection);
	}

	/************************************
	 * Savable Implementations: Leaf Savable
	 ************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();

		if (encloseMyself) {
			sb.append("{");
		}

		sb.append(BreakoutJsonUtility.Serializer.serializeString("class", this.getClass().getTypeName()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("ballId", ball.getId()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("newPosition", newPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("newMoveDirection", newMoveDirection));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPosition", undoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPreviousPosition", undoPreviousPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoMoveDirection", undoMoveDirection));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPosition", redoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPreviousPosition", redoPreviousPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoMoveDirection", redoMoveDirection));

		if (encloseMyself) {
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		ballId = BreakoutJsonUtility.Deserializer.deserializeInt("ballId", jsonObject);
		newPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("newPosition", jsonObject);
		newMoveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("newMoveDirection", jsonObject);

		undoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPosition", jsonObject);
		undoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPreviousPosition", jsonObject);
		undoMoveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoMoveDirection", jsonObject);

		redoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPosition", jsonObject);
		redoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPreviousPosition", jsonObject);
		redoMoveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoMoveDirection", jsonObject);
	}

	@Override
	public void addSavable() {
		throw new UnsupportedOperationException("I am a leaf savable, I don't do that.");
	}

	@Override
	public void removeSavable() {
		throw new UnsupportedOperationException("I am a leaf savable, I don't do that.");
	}
}
