/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar
 **/
package rendering;

import java.util.ArrayList;
//import command.pattern.CommandListener;
//import command.pattern.RendererClearCanvasCommand;
//import command.pattern.RendererDrawObjectCommand;
import game.engine.DrawObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
        
    private GraphicsContext context;
    private ArrayList<DrawObject> drawables;
//    private CommandListener commandListener;
    private static final Color CLEAR_CANVAS_COLOR = Color.GREY;

    public Renderer() {
    	drawables = new ArrayList<DrawObject>();
    }
    
//    public Renderer(CommandListener commandListener) {
//    	drawables = new ArrayList<DrawObject>();
//    	this.commandListener = commandListener;
//    }
    
    public void restartFromLoadFile() {
    	dumpLists();
    }

    public void reset() {
    	dumpLists();
    }
    
    private void dumpLists() {
        // Dump drawables to start fresh
    	drawables.clear();
    	drawables = null;
    	drawables = new ArrayList<DrawObject>();
    }
    
    public void setGraphicsContext(GraphicsContext context) {
    	// Save graphics context
        this.context = context;
    }
    
    public void addDrawable(DrawObject object) {
		// Prevent double registration
		if(!drawables.contains(object)) {
			drawables.add(object);
		}
    }
    
    public void removeDrawable(DrawObject object) {
    	// Ensure DrawBehavior is already registered
		int objectIndex = drawables.indexOf(object);
		// Then remove it
		if (objectIndex >= 0) {
			drawables.remove(object);
		}
    }
    
    public void render() {
    	clearCanvas();
    	// Create command for clearing GraphicsContext
//    	 commandListener.receiveCommand(new RendererClearCanvasCommand(this));
    	
    	for (DrawObject object : drawables) { 
    		object.performDraw(context);
    	}
        // Create commands for drawing each object
//        for (DrawObject object : drawables) {
//        	commandListener.receiveCommand(new RendererDrawObjectCommand(object, context));
//        }
    }
    
    public void clearCanvas() {
    	context.setFill(CLEAR_CANVAS_COLOR);
        context.fillRect(0,0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }
}
