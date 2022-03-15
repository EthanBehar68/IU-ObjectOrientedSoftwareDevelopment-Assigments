package p532.gamemaker.views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.design.GetKeyInputButtonStrategy;
import p532.gamemaker.utility.FileManager;

import java.io.IOException;

/**
 * When this component is created, you must call setup().
 */
public class KeyEventConditionComponent extends EventConditionComponent {

	private Button buttonChangeKeyCode;

	public KeyEventConditionComponent() {
	}

	private void reset() {
		try {
			// 1. Create the properties panel
			parent = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.KEY_EVENT_FXML_PATH));

			// 2. Select the UI elements in this component
			buttonChangeKeyCode = (Button) parent.lookup("#btnChangeKeyCode");
			comboChangeBehavior = (ComboBox<GeneralStrategy>) parent.lookup("#comboChangeBehavior");
			selectSoundButton = (Button) parent.lookup("#selectSoundButton");

			// 4. Add onChange events to each ComboBox (NOT MVC)
			updateOnChangeBehavior();
		
		} catch (IOException e) {
		}
	}


	public void setup(KeyEventCondition condition, ObservableList<GeneralStrategy> strategyOptions) {
		reset();

		// Set what data should be displayed in the UI
		setCondition(condition);
		setStrategyOptions(strategyOptions);

		// Update UI...
		// Set button text
		buttonChangeKeyCode.setText(condition.getKeyCode().toString());

		// Set combo box items
		comboChangeBehavior.setItems(strategyOptions);
		comboChangeBehavior.setValue(condition.getStrategy());

		// Add key event listener to the button
		buttonChangeKeyCode.setOnAction(event -> {
			// Capture one key code
			GetKeyInputButtonStrategy.execute(buttonChangeKeyCode, condition);
		});

		// Add sound
		selectSoundButton.setOnAction(event -> {
			boolean success = FileManager.getInstance().getAudioFile(ParentWindow.getGameDesignScene().getWindow());
			if (success) {
				condition.setSoundFxFile(FileManager.getInstance().getSelectedFile(), true);
			}
		});
	}
}
