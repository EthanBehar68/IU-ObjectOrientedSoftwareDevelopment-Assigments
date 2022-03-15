/**
 * @author: Snehal Patare, Aditi Pednekar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/

package command.pattern;

import com.google.gson.JsonObject;

import breakout.DigitalTimer;
import composite.pattern.BreakoutJsonUtility;

public class DigitalTimerUpdateCommand extends Command {

	private DigitalTimer digitalTimer;
	private double timeDelta;
	// For deserialization
	private int digitalTimerId;

	private String undoLabel;

	private String redoLabel;

	public DigitalTimerUpdateCommand() {
	}

	public DigitalTimerUpdateCommand(DigitalTimer digitalTimer, double timeDelta) {
		this.digitalTimer = digitalTimer;
		this.timeDelta = timeDelta;
	}

	public void setDigitalTimer(DigitalTimer digitalTimer) {
		this.digitalTimer = digitalTimer;
	}

	public int getDigitalTimerId() {
		return digitalTimerId;
	}

	/************************************
	 * Command Implementations
	 ************************************/
	@Override
	public void execute() {
		undoLabel = digitalTimer.getLabel();

		digitalTimer.performTimeUpdate(timeDelta);

		redoLabel = digitalTimer.getLabel();
	}

	@Override
	public void undo() {
		digitalTimer.setLabel(undoLabel);
	}

	@Override
	public void redo() {
		digitalTimer.setLabel(redoLabel);
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
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("digitalTimerId", digitalTimer.getId()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeDouble("timeDelta", timeDelta));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializeString("undoLabel", undoLabel));
		sb.append(",");

		sb.append(BreakoutJsonUtility.Serializer.serializeString("redoLabel", redoLabel));

		if (encloseMyself) {
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		digitalTimerId = BreakoutJsonUtility.Deserializer.deserializeInt("digitalTimerId", jsonObject);

		undoLabel = BreakoutJsonUtility.Deserializer.deserializeString("undoLabel", jsonObject);

		redoLabel = BreakoutJsonUtility.Deserializer.deserializeString("redoLabel", jsonObject);
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
