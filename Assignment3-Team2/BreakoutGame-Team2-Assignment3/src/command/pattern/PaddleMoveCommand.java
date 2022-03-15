/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import com.google.gson.JsonObject;

import breakout.Paddle;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;

public class PaddleMoveCommand extends Command {

	private Paddle paddle;
	private double timeDelta;
	// For deserialization
	private int paddleId;

	private Point2D undoPosition;
	private Point2D undoPreviousPosition;
	private Point2D undoVelocity;
	private Point2D undoMoveDirection;

	private Point2D redoPosition;
	private Point2D redoPreviousPosition;
	private Point2D redoVelocity;
	private Point2D redoMoveDirection;

	public PaddleMoveCommand() {
	}

	public PaddleMoveCommand(Paddle paddle, double timeDelta) {
		this.paddle = paddle;
		this.timeDelta = timeDelta;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public int getPaddleId() {
		return paddleId;
	}

	/************************************
	 * Command Implementations
	 ************************************/
	@Override
	public void execute() {
		undoPosition = paddle.getPosition();
		undoPreviousPosition = paddle.getPreviousPosition();
		undoVelocity = paddle.getVelocity();
		undoMoveDirection = paddle.getMoveDirection();

		paddle.captureMoveDirection();
		paddle.setVelocity(paddle.getMoveBehaviour().move(timeDelta, paddle.getMoveDirection(), paddle.getSpeed()));
		paddle.setPreviousPosition(paddle.getPosition());
		paddle.setPosition(paddle.getPosition().add(paddle.getVelocity()));

		redoPosition = paddle.getPosition();
		redoPreviousPosition = paddle.getPreviousPosition();
		redoVelocity = paddle.getVelocity();
		redoMoveDirection = paddle.getMoveDirection();
	}

	@Override
	public void undo() {
		paddle.setPosition(undoPosition);
		paddle.setPreviousPosition(undoPreviousPosition);
		paddle.setVelocity(undoVelocity);
		paddle.setMoveDirection(undoMoveDirection);
	}

	@Override
	public void redo() {
		paddle.setPosition(redoPosition);
		paddle.setPreviousPosition(redoPreviousPosition);
		paddle.setVelocity(redoVelocity);
		paddle.setMoveDirection(redoMoveDirection);
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
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("paddleId", paddle.getId()));
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

		if (encloseMyself) {
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		paddleId = BreakoutJsonUtility.Deserializer.deserializeInt("paddleId", jsonObject);
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
