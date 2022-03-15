/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import breakout.Ball;
import javafx.geometry.Point2D;

public class BallMoveCommand implements Command {

	private Ball ball;
	private double timeDelta;
	private Point2D undoPosition;
	private Point2D undoPreviousPosition;
	private Point2D undoVelocity;
	private Point2D undoMoveDirection;
	private Point2D redoPosition;
	private Point2D redoPreviousPosition;
	private Point2D redoVelocity;
	private Point2D redoMoveDirection;
	
	public BallMoveCommand(Ball ball, double timeDelta) {
		this.ball = ball;
		this.timeDelta = timeDelta;
	}
	
	@Override
	public void execute() {
		undoPosition = ball.getPosition();
		undoPreviousPosition = ball.getPreviousPosition();
		undoVelocity = ball.getVelocity();
		undoMoveDirection = ball.getMoveDirection();
		
		ball.performMove(timeDelta);
		
		redoPosition = ball.getPosition();
		redoPreviousPosition = ball.getPreviousPosition();
		redoVelocity = ball.getVelocity();
		redoMoveDirection = ball.getMoveDirection();
	}

	@Override
	public void undo() {
		ball.setPosition(undoPosition);
		ball.setPreviousPosition(undoPreviousPosition);
		ball.setVelocity(undoVelocity);
		ball.setMoveDirection(undoMoveDirection);
	}
	
	@Override 
	public void redo() {
		ball.setPosition(redoPosition);
		ball.setPreviousPosition(redoPreviousPosition);
		ball.setVelocity(redoVelocity);
		ball.setMoveDirection(redoMoveDirection);
	}

	@Override
	public void store() {
		// Something like...
		// Save out vars
	}

	@Override
	public void load() {
		// Something like...
		// Create ball(?)
		// Load with variables
	}
}
