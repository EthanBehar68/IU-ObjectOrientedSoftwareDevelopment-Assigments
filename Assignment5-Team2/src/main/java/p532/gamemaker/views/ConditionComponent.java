package p532.gamemaker.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.utility.FileManager;

import java.io.IOException;
import java.util.List;

/**
 * When this component is created, you must call setup().
 */
public class ConditionComponent {
	// Things needed to set up the component
	private Parent parent;

	// The UI elements
	private ComboBox<String> comboSelectSpriteType;
	private ComboBox<CollisionStrategy> comboChangeBehavior;
	private Button selectSoundButton;

	// The data to be rendered
	private CollisionCondition condition; // which Condition is being displayed
	private List<String> allSpriteTypes;
	private ObservableList<CollisionStrategy> strategyOptions = FXCollections
			.observableArrayList(Constants.getAllCollisionStrategies());

	/**
	 * Required constructor for JavaFXML.
	 */
	public ConditionComponent() {
	}

	///////////////////////////////////////////////////////////

	public List<String> getAllSpriteTypes() {
		return allSpriteTypes;
	}

	public void setAllSpriteTypes(List<String> allSpriteTypes) {
		this.allSpriteTypes = allSpriteTypes;
	}

	public List<CollisionStrategy> getStrategyOptions() {
		return strategyOptions;
	}

	public void setStrategyOptions(ObservableList<CollisionStrategy> strategyOptions) {
		this.strategyOptions = strategyOptions;
	}

	public Parent getView() {
		return parent;
	}

	public CollisionCondition getCondition() {
		return condition;
	}

	/**
	 * Changes the active condition and updates the UI accordingly
	 */
	public void setCondition(CollisionCondition condition) {
		this.condition = condition;
	}

	///////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	private void reset() {
		try {
			// 1. Create the properties panel
			parent = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.CONDITIONAL_COMPONENT_FXML_PATH));

			// 2. Select the UI elements in this component
			comboSelectSpriteType = (ComboBox<String>) parent.lookup("#comboSelectSpriteType");
			comboChangeBehavior = (ComboBox<CollisionStrategy>) parent.lookup("#comboChangeBehavior");
			selectSoundButton = (Button) parent.lookup("#selectSoundButton");
			assert comboSelectSpriteType != null;
			assert comboChangeBehavior != null;
			assert selectSoundButton != null;

			// 3. Load in all possible sprite types into the ComboBox
			List<String> allSpriteTypes = ParentWindow.getGameDesignScene().getSpritePresenter().getLevel()
					.getAllUserDefinedTypes();
			comboSelectSpriteType.setItems(FXCollections.observableList(allSpriteTypes));

			// 4. Show the strategy options in the ComboBox
			comboChangeBehavior.setItems(strategyOptions);

			// 5. Add onChange events to each ComboBox (NOT MVC)
			comboSelectSpriteType.valueProperty().addListener((observable, oldValue, newValue) -> {
				condition.setOtherSpriteType(newValue);
			});
			comboChangeBehavior.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue instanceof CollisionStrategy)
					condition.setStrategy((CollisionStrategy) newValue);
				else
					System.err.println(
							"Error: Selected something that is not a CollisionStrategy from the properties combo box");
			});
		} catch (IOException e) {
		}
	}

	public void setup(CollisionCondition condition, ObservableList<CollisionStrategy> strategyOptions) {
		reset();

		// Set what data should be displayed in the UI
		setCondition(condition);
		setStrategyOptions(strategyOptions);

		// Update UI...
		comboSelectSpriteType.setValue(condition.getOtherSpriteType());
		comboChangeBehavior.setValue(condition.getStrategy());
		
		selectSoundButton.setOnAction(event -> {
			boolean success = FileManager.getInstance().getAudioFile(ParentWindow.getGameDesignScene().getWindow());
			if (success) {
				condition.setSoundFxFile(FileManager.getInstance().getSelectedFile());
			}
		});
	}
}
