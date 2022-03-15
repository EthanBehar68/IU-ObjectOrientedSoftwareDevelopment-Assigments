/**
 * @author: Ethan Taylor Behar, Christian Dummer, Saurabh Gulati
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

import java.util.ArrayList;

import composite.pattern.Layouts.LayoutType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class LayablePane extends Pane implements Layable {

	public ArrayList<Layable> layables = new ArrayList<Layable>();

	public LayablePane() {
		super();
	}

	public void AddChild(Node whatIsNode) {
		getChildren().add(whatIsNode);
		addLayable((Layable) whatIsNode);
	}

	@Override
	public void changeLayout(LayoutType layout, int parentX, int parentY, int index) {
		switch (layout) {
		case LEFT:
			setLayoutX(Layouts.LeftLayout.controlPaneX);
			setLayoutY(Layouts.LeftLayout.controlPaneY);
			setMinWidth(Layouts.LeftLayout.controlPaneWidth);
			setMinHeight(Layouts.LeftLayout.controlPaneHeight);
			setWidth(Layouts.LeftLayout.controlPaneWidth);
			setHeight(Layouts.LeftLayout.controlPaneHeight);
			setMaxWidth(Layouts.LeftLayout.controlPaneWidth);
			setMaxHeight(Layouts.LeftLayout.controlPaneHeight);
			break;
		case RIGHT:
			setLayoutX(Layouts.RightLayout.controlPaneX);
			setLayoutY(Layouts.RightLayout.controlPaneY);
			setMinWidth(Layouts.RightLayout.controlPaneWidth);
			setMinHeight(Layouts.RightLayout.controlPaneHeight);
			setWidth(Layouts.RightLayout.controlPaneWidth);
			setHeight(Layouts.RightLayout.controlPaneHeight);
			setMaxWidth(Layouts.RightLayout.controlPaneWidth);
			setMaxHeight(Layouts.RightLayout.controlPaneHeight);
			break;
		case TOP:
			setLayoutX(Layouts.TopLayout.controlPaneX);
			setLayoutY(Layouts.TopLayout.controlPaneY);
			setMinWidth(Layouts.TopLayout.controlPaneWidth);
			setMinHeight(Layouts.TopLayout.controlPaneHeight);
			setWidth(Layouts.TopLayout.controlPaneWidth);
			setHeight(Layouts.TopLayout.controlPaneHeight);
			setMaxWidth(Layouts.TopLayout.controlPaneWidth);
			setMaxHeight(Layouts.TopLayout.controlPaneHeight);
			break;
		case BOTTOM:
			setLayoutX(Layouts.BottomLayout.controlPaneX);
			setLayoutY(Layouts.BottomLayout.controlPaneY);
			setMinWidth(Layouts.BottomLayout.controlPaneWidth);
			setMinHeight(Layouts.BottomLayout.controlPaneHeight);
			setWidth(Layouts.BottomLayout.controlPaneWidth);
			setHeight(Layouts.BottomLayout.controlPaneHeight);
			setMaxWidth(Layouts.BottomLayout.controlPaneWidth);
			setMaxHeight(Layouts.BottomLayout.controlPaneHeight);
			break;
		}

		requestLayout();

		for (Layable lay : layables) {
			if ((layout == LayoutType.TOP || layout == LayoutType.BOTTOM) && index >= 6) {
				index = 0;
				parentX = 0;
				parentY = 75;
			}
			lay.changeLayout(layout, parentX, parentY, index);
			index++;
		}
		layoutChildren();
	}

	@Override
	public void addLayable(Layable layable) {
		layables.add(layable);
	}

	@Override
	public void removeLayable(Layable layable) {
		layables.remove(layable);
	}

}
