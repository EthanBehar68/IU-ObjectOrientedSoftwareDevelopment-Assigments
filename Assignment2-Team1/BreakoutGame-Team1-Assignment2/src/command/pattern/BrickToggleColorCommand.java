/**
 * @author: Snehal Patare
 * @CreationDate: Sep 12, 2021
 * @editors: Ethan Behar
 **/

package command.pattern;

import breakout.Brick;
import javafx.scene.paint.Color;

public class BrickToggleColorCommand implements Command {
	
	private Brick brick;
	private Color newColor;
	private Color undoColor;
	private Color redoColor;
	
	public BrickToggleColorCommand (Brick brick, Color newColor) { 
		this.brick = brick;
		this.newColor = newColor;
	}

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

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
	

}
