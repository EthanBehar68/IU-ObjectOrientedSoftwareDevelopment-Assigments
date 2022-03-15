package p532.gamemaker.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.UpdateSpriteAdapter;

import java.io.IOException;

public class ColorPickerComponentLoader {

	private ColorPickerComponentLoader() {
	}

	/**
	 * Creates a VBox that holds a Label and a ColorPicker. When a value is selected
	 * in the ColorPicker, this passes the color value to the updateSpriteAdapter's
	 * updateColor(...) method.
	 * 
	 * @param initialValue
	 * @param updateSpriteAdapter an anonymous object that updates a Sprite. It
	 *                            should override the updateColor(...) method.
	 * @return a VBox holding a Label and a ColorPicker OR an empty VBOX if there
	 *         was a problem loading the FXML..
	 */
	public static VBox create(Color initialValue, UpdateSpriteAdapter updateSpriteAdapter) {
		try {
			// Create a Color picker from FXML
			ClassLoader classLoader = ColorPickerComponentLoader.class.getClassLoader();
			VBox colorPickerComponent = FXMLLoader.load(classLoader.getResource(Constants.COLOR_PROPERTY_FXML_PATH));
			ColorPicker picker = (ColorPicker) colorPickerComponent.lookup("#colorPicker");

			// Display the selected Sprite's current color in the picker
			picker.setValue(initialValue);

			// When the color picker's value changes, update the Sprite
			picker.valueProperty().addListener((observable, oldValue, newValue) -> {
				updateSpriteAdapter.updateColor(newValue);
			});
			return colorPickerComponent;
		} catch (IOException e) {
		}
		return new VBox();
	}
}