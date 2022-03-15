/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package breakout;

import com.google.gson.JsonObject;

import composite.pattern.BreakoutJsonUtility;
import game.engine.DrawObject;
import game.engine.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text extends DrawObject {

	protected String originalFontName;
	protected String fontName;
	protected int originalFontSize;
	protected int fontSize;
	protected String originalLabel;
	protected String label;
	// Ignore serializing Font font
	// We can easily rebuild with fontName and fontSize
	protected Font font;

	public Text() {
		super();
		originalFontName = "Verdana";
		fontName = originalFontName;
		originalFontSize = 14;
		fontSize = originalFontSize;
		originalLabel = "";
		label = originalLabel;
		font = new Font(fontName, fontSize);
	}

	public Text(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension, String originalFontName, String fontName,
			int originalFontSize, int fontSize, String originalLabel, String label) 
	{
		super(id, drawBehaviour, originalColor, color, originalPosition, position, originalDimension, dimension);
		this.originalFontName = originalFontName;
		this.fontName = fontName;
		this.originalFontSize = originalFontSize;
		this.fontSize = fontSize;
		this.originalLabel = originalLabel;
		this.label = label;
		this.font = new Font(this.fontName, this.fontSize);
	}

	public String getOriginalFontName() {
		return originalFontName;
	}

	public String getFontName() {
		return fontName;
	}

	public int getOriginalFontSize() {
		return originalFontSize;
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getOriginalLabel() {
		return originalLabel;
	}

	public void setOriginalLabel(String label) {
		this.originalLabel = label;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOriginalFont(String fontName, int fontSize) {
		this.fontName = fontName;
		this.originalFontName = fontName;
		this.fontSize = fontSize;
		this.originalFontSize = fontSize;
		font = new Font(this.fontName, this.fontSize);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(String fontName, int fontSize) {
		this.fontName = fontName;
		this.fontSize = fontSize;
		font = new Font(this.fontName, this.fontSize);
	}

	public void reset() {
		super.reset();
		setLabel(originalLabel);
		setFont(originalFontName, originalFontSize);
	}

	/************************************
	 * Observable Implementations
	 ************************************/
	@Override
	public void update(double timeDelta) {
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
			sb.append(",");
		}
		
		sb.append(super.save(false));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("originalFontName", originalFontName));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("fontName", fontName));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("originalFontSize", originalFontSize));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("fontSize", fontSize));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("originalLabel", originalLabel));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("label", label));

		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		super.load(jsonObject);
		originalFontName = BreakoutJsonUtility.Deserializer.deserializeString("originalFontName", jsonObject);
		fontName = BreakoutJsonUtility.Deserializer.deserializeString("fontName", jsonObject);
		originalFontSize = BreakoutJsonUtility.Deserializer.deserializeInt("originalFontSize", jsonObject);
		fontSize = BreakoutJsonUtility.Deserializer.deserializeInt("fontSize", jsonObject);
		originalLabel = BreakoutJsonUtility.Deserializer.deserializeString("originalLabel", jsonObject);
		label = BreakoutJsonUtility.Deserializer.deserializeString("label", jsonObject);
		
		setFont(fontName, fontSize);
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
