/**
 * @author: Ethan Taylor Behar, Christian Dummer, Saurabh Gulati
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

import composite.pattern.Layouts.LayoutType;
import javafx.scene.canvas.Canvas;

public class LayableCanvas extends Canvas implements Layable {

	public LayableCanvas(int width, int height) {
		super(width, height);
	}

	@Override
	public void changeLayout(LayoutType layout, int parentX, int parentY, int index) {
		switch (layout) {
		case LEFT:
			setLayoutX(Layouts.LeftLayout.gameCanvasX);
			setLayoutY(Layouts.LeftLayout.gameCanvasY);
			break;
		case RIGHT:
			setLayoutX(Layouts.RightLayout.gameCanvasX);
			setLayoutY(Layouts.RightLayout.gameCanvasY);
			break;
		case TOP:
			setLayoutX(Layouts.TopLayout.gameCanvasX);
			setLayoutY(Layouts.TopLayout.gameCanvasY);
			break;
		case BOTTOM:
			setLayoutX(Layouts.BottomLayout.gameCanvasX);
			setLayoutY(Layouts.BottomLayout.gameCanvasY);
			break;
		}
	}

	@Override
	public void addLayable(Layable layable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLayable(Layable layable) {
		// TODO Auto-generated method stub

	}

}
