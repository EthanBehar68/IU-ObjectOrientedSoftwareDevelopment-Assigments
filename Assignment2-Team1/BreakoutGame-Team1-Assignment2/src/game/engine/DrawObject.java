/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Snehal Patare, Aditi Pednekar
 **/
package game.engine;

import command.pattern.CommandListener;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import observer.pattern.Observer;

public abstract class DrawObject implements Observer {
	
    protected CommandListener commandListener;
	protected Drawable drawBehaviour;
    protected Point2D position;
    protected Point2D dimensions;
    protected Color color;

	public DrawObject() {
		position = new Point2D(0, 0);
		dimensions = new Point2D(0, 0);
        color = Color.MAGENTA;
	}
	
	public DrawObject(Drawable drawBehavior, Color color, Point2D position, Point2D dimensions) {
		this.drawBehaviour = drawBehavior;
		this.position = position;
		this.dimensions = dimensions;
		this.color = color;
	}
		
	public void performDraw(GraphicsContext context) {
		drawBehaviour.draw(this, context);
	}
	
    public Point2D getPosition() {
        return position;
    }
    
    public void setPosition(float x, float y) {
        position = new Point2D(x, y);
    }
    
    public void setPosition(Point2D position) {
    	this.position = position;
    }
    
    public Point2D getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(int width, int height) {
        dimensions = new Point2D(width, height);
    }
    
    public void setDimensions(Point2D dimensions) {
    	this.dimensions = dimensions;
    }
    
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;		
	}
	
	public CommandListener getCommandListener() {
		return commandListener;
	}
	
	public void setCommandListener(CommandListener listener) {
		commandListener = listener;
	}
}
