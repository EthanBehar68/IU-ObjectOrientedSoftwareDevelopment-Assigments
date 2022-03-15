/**
 * @author: Pratyush Duklan
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar, Snehal Patare, Aditi Pednekar
 **/
package breakout;

import com.google.gson.JsonObject;

import command.pattern.DigitalTimerUpdateCommand;
import composite.pattern.BreakoutJsonUtility;
import game.engine.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class DigitalTimer extends Text {

	private float finalTime = 0;

	public DigitalTimer() {
		super();
	}

	public DigitalTimer(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension, String originalFontName, String fontName,
			int originalFontSize, int fontSize, String originalLabel, String label, float finalTime) {
		super(id, drawBehaviour, originalColor, color, originalPosition, position, originalDimension, dimension,
				originalFontName, fontName, originalFontSize, fontSize, originalLabel, label);
		this.finalTime = finalTime;
	}

	public void performTimeUpdate(double timeDelta) {
		finalTime += timeDelta;
		int finalMins = (int) (finalTime / 60);
		int finalSecs = (int) (finalTime % 60);

		label = String.format("Time: %02d:%02d", (finalMins), (finalSecs));
	}

	public float getFinalTime() {
		return finalTime;
	}

	@Override
	public void reset() {
		super.reset();
	}

	/************************************
	 * Observable Implementations
	 ************************************/
	@Override
	public void update(double timeDelta) {
		commandListener.receiveCommand(new DigitalTimerUpdateCommand(this, timeDelta));
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
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeFloat("finalTime", finalTime));

		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		super.load(jsonObject);
		finalTime = BreakoutJsonUtility.Deserializer.deserializeFloat("finalTime", jsonObject);
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
