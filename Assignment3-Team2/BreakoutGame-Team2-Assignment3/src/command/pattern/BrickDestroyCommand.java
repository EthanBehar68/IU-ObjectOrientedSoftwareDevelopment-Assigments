/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import com.google.gson.JsonObject;

import breakout.Brick;
import composite.pattern.BreakoutJsonUtility;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class BrickDestroyCommand extends Command {

	private Brick brick;
	// For deserialization
	private int brickId;

	private Point2D undoPosition;
	private Point2D undoDimensions;
	private Color undoColor;

	private Point2D redoPosition;
	private Point2D redoDimensions;
	private Color redoColor;

	public BrickDestroyCommand() {
	}

	public BrickDestroyCommand(Brick brick) {
		this.brick = brick;
	}

	public void setBrick(Brick brick) {
		this.brick = brick;
	}

	public int getBrickId() {
		return brickId;
	}

	/************************************
	 * Command Implementations
	 ************************************/
	@Override
	public void execute() {
		undoPosition = brick.getPosition();
		undoDimensions = brick.getDimensions();
		undoColor = brick.getColor();

		brick.setColor(Color.TRANSPARENT);
		brick.setPosition(new Point2D(-1, -1));
		brick.setDimensions(new Point2D(0, 0));

		redoPosition = brick.getPosition();
		redoDimensions = brick.getDimensions();
		redoColor = brick.getColor();
	}

	@Override
	public void undo() {
		brick.setPosition(undoPosition);
		brick.setDimensions(undoDimensions);
		brick.setColor(undoColor);
	}

	@Override
	public void redo() {
		brick.setPosition(redoPosition);
		brick.setDimensions(redoDimensions);
		brick.setColor(redoColor);
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
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("brickId", brick.getId()));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoPosition", undoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("undoDimensions", undoDimensions));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("undoColor", undoColor.toString()));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoPosition", redoPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("redoDimensions", redoDimensions));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("redoColor", redoColor.toString()));

		if (encloseMyself) {
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		brickId = BreakoutJsonUtility.Deserializer.deserializeInt("brickId", jsonObject);

		undoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoPosition", jsonObject);
		undoDimensions = BreakoutJsonUtility.Deserializer.deserializePoint2D("undoDimensions", jsonObject);
		undoColor = BreakoutJsonUtility.Deserializer.deserializeColor("undoColor", jsonObject);

		redoPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoPosition", jsonObject);
		redoDimensions = BreakoutJsonUtility.Deserializer.deserializePoint2D("redoDimensions", jsonObject);
		redoColor = BreakoutJsonUtility.Deserializer.deserializeColor("redoColor", jsonObject);
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
