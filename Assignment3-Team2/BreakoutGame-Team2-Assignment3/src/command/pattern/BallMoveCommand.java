/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import com.google.gson.JsonObject;

import breakout.Ball;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;

public class BallMoveCommand extends Command {

	private Ball ball;
	private double timeDelta;
	// For deserialization
	private int ballId;

	private Point2D undoPosition = new Point2D(0, 0);
	private Point2D undoPreviousPosition = new Point2D(0, 0);
	private Point2D undoVelocity = new Point2D(0, 0);
	private Point2D undoMoveDirection = new Point2D(0, 0);

	private Point2D redoPosition = new Point2D(0, 0);
	private Point2D redoPreviousPosition = new Point2D(0, 0);
	private Point2D redoVelocity = new Point2D(0, 0);
	private Point2D redoMoveDirection = new Point2D(0, 0);

	public BallMoveCommand() {
	}

	public BallMoveCommand(Ball ball, double timeDelta) {
		this.ball = ball;
		this.timeDelta = timeDelta;
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
		undoVelocity = ball.getVelocity();
		undoMoveDirection = ball.getMoveDirection();

		ball.setVelocity(ball.getMoveBehaviour().move(timeDelta, ball.getMoveDirection(), ball.getSpeed()));
		ball.setPreviousPosition(ball.getPosition());
		ball.setPosition(ball.getPosition().add(ball.getVelocity()));

		redoPosition = ball.getPosition();
		redoPreviousPosition = ball.getPreviousPosition();
		redoVelocity = ball.getVelocity();
		redoMoveDirection = ball.getMoveDirection();
	}

	@Override
	public void undo() {
		ball.setPosition(undoPosition);
		ball.setPreviousPosition(undoPreviousPosition);
		ball.setVelocity(undoVelocity);
		ball.setMoveDirection(undoMoveDirection);
	}

	@Override
	public void redo() {
		ball.setPosition(redoPosition);
		ball.setPreviousPosition(redoPreviousPosition);
		ball.setVelocity(redoVelocity);
		ball.setMoveDirection(redoMoveDirection);
	}

	/************************************
	 * Savable Implementations: Leaf Savable
	 ************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();
		
		if(encloseMyself) {
			sb.append("{");
		}
		
		sb.append(BreakoutJsonUtility.Serializer.serializeString("class", this.getClass().getTypeName()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("ballId", ball.getId()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeDouble("timeDelta", timeDelta));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPosition", undoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPreviousPosition", undoPreviousPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoVelocity", undoVelocity));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoMoveDirection", undoMoveDirection));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPosition", redoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPreviousPosition", redoPreviousPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoVelocity", redoVelocity));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoMoveDirection", redoMoveDirection));

		if(encloseMyself) {
			sb.append("}");
		}		
				
		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		ballId = BreakoutJsonUtility.Deserializer.deserializeInt("ballId", jsonObject);
		timeDelta = BreakoutJsonUtility.Deserializer.deserializeDouble("timeDelta", jsonObject);

		undoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPosition", jsonObject);
		undoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPreviousPosition", jsonObject);
		undoVelocity = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoVelocity", jsonObject);
		undoMoveDirection = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoMoveDirection", jsonObject);

		redoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPosition", jsonObject);
		redoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPreviousPosition", jsonObject);
		redoVelocity = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoVelocity", jsonObject);
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
