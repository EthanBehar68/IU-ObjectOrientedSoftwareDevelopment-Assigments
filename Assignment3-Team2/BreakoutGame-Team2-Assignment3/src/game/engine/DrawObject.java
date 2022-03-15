/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Snehal Patare, Aditi Pednekar, Ethan Behar
 **/
package game.engine;

import com.google.gson.JsonObject;

import rendering.DrawCircle;
import rendering.DrawRectangle;
import rendering.DrawText;
import command.pattern.CommandListener;
import composite.pattern.BreakoutJsonUtility;
import composite.pattern.Savable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import observer.pattern.Observer;

/**
 * Use the original setters the first time you set a variable. This will become
 * the original value and calling restart will set the varaible to the original
 * variable.
 **/
public abstract class DrawObject implements Observer, Savable {

	protected CommandListener commandListener;
	protected int id;
	protected Drawable drawBehaviour;
	protected Color originalColor;
	protected Color color;
	protected Point2D originalPosition;
	protected Point2D position;
	protected Point2D originalDimension;
	protected Point2D dimension;

	public DrawObject() {
		id = -1;
		originalColor = Color.MAGENTA;
		color = originalColor;
		originalPosition = new Point2D(0, 0);
		position = originalPosition;
		originalDimension = new Point2D(0, 0);
		dimension = originalDimension;
	}

	public DrawObject(int id, Drawable drawBehaviour, Color originalColor, Color color, Point2D originalPosition,
			Point2D position, Point2D originalDimension, Point2D dimension) 
	{
		this.id = id;
		this.drawBehaviour = drawBehaviour;
		this.originalPosition = originalPosition;
		this.position = position;
		this.originalDimension = originalDimension;
		this.dimension = dimension;
		this.originalColor = originalColor;
		this.color = color;
	}

	public CommandListener getCommandListener() {
		return commandListener;
	}

	public void setCommandListener(CommandListener listener) {
		commandListener = listener;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drawable getDrawBehaviour() {
		return drawBehaviour;
	}

	public void setDrawBehaviour(Drawable drawBehaviour) {
		this.drawBehaviour = drawBehaviour;
	}

	public void performDraw(GraphicsContext context) {
		drawBehaviour.draw(this, context);
	}

	public Color getOriginalColor() {
		return originalColor;
	}

	public void setOriginalColor(Color color) {
		this.originalColor = color;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point2D getOriginalPosition() {
		return originalPosition;
	}

	public void setOriginalPosition(Point2D position) {
		this.originalPosition = position;
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getOriginalDimensions() {
		return originalDimension;
	}

	public void setOriginalDimensions(Point2D dimensions) {
		this.originalDimension = dimensions;
		this.dimension = dimensions;
	}

	public Point2D getDimensions() {
		return dimension;
	}

	public void setDimensions(Point2D dimensions) {
		this.dimension = dimensions;
	}

	public void reset() {
		setPosition(originalPosition);
		setDimensions(originalDimension);
		setColor(originalColor);
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
		
		sb.append(BreakoutJsonUtility.Serializer.serializeInt("id", id));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("drawBehaviour", drawBehaviour.getClass().getTypeName()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("originalColor", originalColor.toString()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializeString("color", color.toString()));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("originalPosition", originalPosition));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("position", position));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("originalDimension", originalDimension));
		sb.append(",");
		sb.append(BreakoutJsonUtility.Serializer.serializePoint2D("dimension", dimension));

		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}
	
	@Override
	public void load(JsonObject jsonObject) {
		id = BreakoutJsonUtility.Deserializer.deserializeInt("id", jsonObject);
		drawBehaviour = getDrawBehaviourFromString(BreakoutJsonUtility.Deserializer.deserializeString("drawBehaviour", jsonObject));
		originalColor = BreakoutJsonUtility.Deserializer.deserializeColor("originalColor", jsonObject);
		color = BreakoutJsonUtility.Deserializer.deserializeColor("color", jsonObject);
		originalPosition = BreakoutJsonUtility.Deserializer.deserializePoint2D("originalPosition", jsonObject);
		position = BreakoutJsonUtility.Deserializer.deserializePoint2D("position", jsonObject);
		originalDimension = BreakoutJsonUtility.Deserializer.deserializePoint2D("originalDimension", jsonObject);
		dimension = BreakoutJsonUtility.Deserializer.deserializePoint2D("dimension", jsonObject);
	}

	private Drawable getDrawBehaviourFromString(String typeOfString) {
		if (typeOfString.compareToIgnoreCase("rendering.DrawCircle") == 0) {
			return new DrawCircle();
		} else if (typeOfString.compareToIgnoreCase("rendering.DrawRectangle") == 0) {
			return new DrawRectangle();
		} else if (typeOfString.compareToIgnoreCase("rendering.DrawText") == 0) {
			return new DrawText();
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
