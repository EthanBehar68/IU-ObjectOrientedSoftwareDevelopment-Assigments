/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 16, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import com.google.gson.JsonObject;

import breakout.Paddle;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;

public class PaddleScreenCollisionCommand extends Command {

	public Paddle paddle;
	public Point2D newPosition;
	// For deserialization
	public int paddleId;
		
	public Point2D undoPosition;
	public Point2D undoPreviousPosition;

	public Point2D redoPosition;
	public Point2D redoPreviousPosition;

	public PaddleScreenCollisionCommand() {
	}
	
	public PaddleScreenCollisionCommand(Paddle paddle, Point2D newPosition) {
		this.paddle = paddle;
		this.newPosition = newPosition;
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
		
		paddle.setPreviousPosition(paddle.getPosition());
		paddle.setPosition(newPosition);
		
		redoPosition = paddle.getPosition();
		redoPreviousPosition = paddle.getPreviousPosition();
	}

	@Override
	public void undo() {
		paddle.setPreviousPosition(undoPreviousPosition);
		paddle.setPosition(undoPosition);
	}
	
	@Override
	public void redo() {
		paddle.setPreviousPosition(redoPreviousPosition);
		paddle.setPosition(redoPosition);
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
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("paddleId", paddle.getId()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("newPosition", newPosition));
		sb.append(",");
		
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPosition", undoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPreviousPosition", undoPreviousPosition));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPosition", redoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPreviousPosition", redoPreviousPosition));
		
		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		paddleId = BreakoutJsonUtility.Deserializer.deserializeInt("paddleId", jsonObject);
		newPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("newPosition", jsonObject);

		undoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPosition", jsonObject);
		undoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPreviousPosition", jsonObject);

		redoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPosition", jsonObject);
		redoPreviousPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPreviousPosition", jsonObject);
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
