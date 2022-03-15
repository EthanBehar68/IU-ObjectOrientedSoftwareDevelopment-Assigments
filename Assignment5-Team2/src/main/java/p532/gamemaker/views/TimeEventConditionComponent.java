package p532.gamemaker.views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.conditions.TimeEventCondition;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.utility.FileManager;
import p532.gamemaker.utility.ParseNumberUtility;

import java.io.IOException;

/**
 * When this component is created, you must call setup().
 */
public class TimeEventConditionComponent extends EventConditionComponent {

	private TextField textFieldInterval;

	public TimeEventConditionComponent() {
	}

	public void setInterval(int interval) {
		// A cheeky hack to convert int to string
		textFieldInterval.textProperty().set(Constants.EMPTY_STRING + interval);
	}

	private void reset() {
		try {
			// 1. Create the properties panel
			parent = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.TIME_EVENT_FXML_PATH));

			// 2. Select the UI elements in this component
			textFieldInterval = (TextField) parent.lookup("#textFieldInterval");
			comboChangeBehavior = (ComboBox<GeneralStrategy>) parent.lookup("#comboChangeBehavior");
			selectSoundButton = (Button) parent.lookup("#selectSoundButton");

			// 4. Add onChange events to each ComboBox (NOT MVC)
			updateOnChangeBehavior();
		} catch (IOException e) {
		}
	}

	public void setup(TimeEventCondition condition, ObservableList<GeneralStrategy> strategyOptions) {
		reset();

		// Set what data should be displayed in the UI
		setCondition(condition);
		setStrategyOptions(strategyOptions);

		// Set combo box items
		comboChangeBehavior.setItems(strategyOptions);
		comboChangeBehavior.setValue(condition.getStrategy());

		// Set up interval TextField
		textFieldInterval.textProperty().addListener((observable, oldValue, newValue) -> {
			int parsedValue = ParseNumberUtility.parseIntOrReturnNull(newValue);
			if (parsedValue != Constants.INT_PARSE_FAILURE_CODE)
				condition.setInterval(parsedValue);
		});
		setInterval(condition.getInterval());

		// Add key event listener to the sound button
		selectSoundButton.setOnAction(event -> {
			boolean success = FileManager.getInstance().getAudioFile(ParentWindow.getGameDesignScene().getWindow());
			if (success) {
				condition.setSoundFxFile(FileManager.getInstance().getSelectedFile(), true);
			}
		});
	}
}
