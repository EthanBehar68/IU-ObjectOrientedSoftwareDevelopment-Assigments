package gamemaker.view.components;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;

public class SettablePropertySelector extends MenuButton {

	private static List<String> properties = 
			Arrays.asList("Shape", "Position", "Velocity", "Dimensions", "Color", "Visible", "Text");

	public SettablePropertySelector(HBox container) {
		super("Choose Property");
		properties.forEach(property -> {
			MenuItem choice = new MenuItem(property);
			choice.setOnAction(e -> {
				container.getChildren().clear();
				this.setText(choice.getText());
				container.getChildren().add(processSelection(property));
			});
			this.getItems().add(choice);
		});
	}

	/**
	 * Based on the
	 * 
	 * @param choice
	 * @return
	 */
	private Node processSelection(String choice) {
		if (choice.equals("Label") || choice.equals("Text")) {
			return new TextField();
		} else if (choice.equals("Color")) {
			ColorPicker cp = new ColorPicker();
			cp.setPrefHeight(45);
			return cp;
		} else if (choice.equals("Shape")) {
			MenuButton shapeSelector = new MenuButton("Select Shape");
			for (String s : Arrays.asList("Rectangle", "Circle", "Text")) {
				MenuItem shape = new MenuItem(s);
				shape.setOnAction(e -> shapeSelector.setText(shape.getText()));
				shapeSelector.getItems().add(shape);
			}
			return shapeSelector;
		} else if (choice.equals("Visible")) {
			CheckBox cb = new CheckBox("Visible");
			cb.setSelected(true);
			return cb;
		} else {
			HBox dualInput = new HBox();
			dualInput.setSpacing(5);
			TextField inputX = new TextField();
			inputX.setTooltip(new Tooltip("Enter X"));
			inputX.setPrefWidth(150);
			TextField inputY = new TextField();
			inputY.setPrefWidth(150);
			inputY.setTooltip(new Tooltip("Enter Y"));
			dualInput.getChildren().addAll(inputX, inputY);
			return dualInput;
		}
	}

}
