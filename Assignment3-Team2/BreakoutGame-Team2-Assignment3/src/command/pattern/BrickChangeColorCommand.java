/**
 * @author: Snehal Patare
 * @CreationDate: Sep 12, 2021
 * @editors: Ethan Behar
 **/

package command.pattern;

import com.google.gson.JsonObject;
import breakout.Brick;
import composite.pattern.BreakoutJsonUtility;
import javafx.scene.paint.Color;

public class BrickChangeColorCommand extends Command {

	private Brick brick;
	private Color newColor;
	// For deserialization
	private int brickId;

	private Color undoColor;

	private Color redoColor;

	public BrickChangeColorCommand() {
	}

	public BrickChangeColorCommand(Brick brick, Color newColor) {
		this.brick = brick;
		this.newColor = newColor;
	}

	/*
	 * Should be used by the Deserializor only!
	 */
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
		undoColor = brick.getColor();

		brick.setColor(newColor);

		redoColor = brick.getColor();
	}

	@Override
	public void undo() {
		brick.setColor(undoColor);
	}

	@Override
	public void redo() {
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
		sb.append(BreakoutJsonUtility.Serializer.serializeString("newColor", newColor.toString()));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializeString("undoColor", undoColor.toString()));
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
		newColor = BreakoutJsonUtility.Deserializer.deserializeColor("newColor", jsonObject);

		undoColor = BreakoutJsonUtility.Deserializer.deserializeColor("undoColor", jsonObject);

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
