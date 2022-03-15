/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package movement.behaviours;

import game.engine.Movable;
import javafx.geometry.Point2D;

public class NoMovement implements Movable {

	@Override
	public Point2D move(double timeDelta, Point2D moveDirection, double speed) {
		return new Point2D(0, 0);
	}
}
