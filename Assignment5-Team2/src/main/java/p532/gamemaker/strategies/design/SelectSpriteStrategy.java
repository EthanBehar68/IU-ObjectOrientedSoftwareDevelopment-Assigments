package p532.gamemaker.strategies.design;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.sprite.conditions.TimeEventCondition;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.views.ColorPickerComponentLoader;
import p532.gamemaker.views.ConditionComponent;
import p532.gamemaker.views.KeyEventConditionComponent;
import p532.gamemaker.views.PropertiesPanel;
import p532.gamemaker.views.TimeEventConditionComponent;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.UpdateSpriteAdapter;

/**
 * This is a kind of controller or presenter for the PropertiesPanel view. It
 * determines what components should be placed onto the view and how the
 * selected Sprite should be updated as the user interacts with the UI.
 */
public class SelectSpriteStrategy {
	private SelectSpriteStrategy() {
	}

	public static void execute(Sprite sprite) {
		// Tell SpritePresenter to keep a reference to this Sprite
		ParentWindow.getGameDesignScene().getSpritePresenter().setSelectedSprite(sprite);

		// Select the properties component within game editor scene
		PropertiesPanel propertiesBox = ParentWindow.getGameDesignScene().getPropertiesPanel();

		// Remove all current property components
		propertiesBox.clear();

		// Add the property components that are common among all Sprites
		addBasicSpriteViewFields(propertiesBox, sprite);

		// Add property components specific to the SpriteView type
		sprite.getView().addPropertyFields(sprite, propertiesBox);

		// Add condition components
		addOnHitSomethingConditions(sprite);
		addOnGetHitSomethingConditions(sprite);
		addOnKeyPressConditions(sprite);
		addOnTimeConditions(sprite);
	}

	public static void addBasicSpriteViewFields(PropertiesPanel propertiesBox, Sprite sprite) {
		// Add a property component for customizable parts of the Sprite...

		// User-defined Type
		propertiesBox.addStringPropertyComponent("Type", sprite.getUserDefinedType(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStringField(String newValue) {
						super.updateStringField(newValue);
						sprite.setUserDefinedType(newValue);
					}
				});
		
		// User-defined Name
		propertiesBox.addStringPropertyComponent("Name", sprite.getUserDefinedName(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStringField(String newValue) {
						super.updateStringField(newValue);
						sprite.setUserDefinedName(newValue);
					}
				});

		// x
		propertiesBox.addDoublePropertyComponent("X Position", sprite.getX(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateDoubleField(Double newValue) {
						super.updateDoubleField(newValue);
						sprite.getView().setLayoutX(newValue);
					}
				});

		// y
		propertiesBox.addDoublePropertyComponent("Y Position", sprite.getY(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateDoubleField(Double newValue) {
						super.updateDoubleField(newValue);
						sprite.getView().setLayoutY(newValue);
					}
				});

		// x-velocity
		propertiesBox.addDoublePropertyComponent("X Velocity", sprite.getVelocityX(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateDoubleField(Double newValue) {
						super.updateDoubleField(newValue);
						sprite.setVelocityX(newValue);
					}
				});

		// y-velocity
		propertiesBox.addDoublePropertyComponent("Y Velocity", sprite.getVelocityY(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateDoubleField(Double newValue) {
						super.updateDoubleField(newValue);
						sprite.setVelocityY(newValue);
					}
				});

		// onMouseClick
		propertiesBox.addComboBoxPropertyComponent("onMouseClick", sprite.getMouseClickStrategy(),
				Constants.PropertyType.MOUSE_EVENT_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStrategy(GeneralStrategy newValue) {
						super.updateStrategy(newValue);
						sprite.setMouseClickStrategy(newValue);
					}
				}, Constants.getAllGeneralStrategies());

		// onMouseEnter
		propertiesBox.addComboBoxPropertyComponent("onMouseEnter", sprite.getMouseEnterStrategy(),
				Constants.PropertyType.MOUSE_EVENT_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStrategy(GeneralStrategy newValue) {
						super.updateStrategy(newValue);
						sprite.setMouseEnterStrategy(newValue);
					}
				}, Constants.getAllGeneralStrategies());

		// onMouseExit
		propertiesBox.addComboBoxPropertyComponent("onMouseExit", sprite.getMouseExitStrategy(),
				Constants.PropertyType.MOUSE_EVENT_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStrategy(GeneralStrategy newValue) {
						super.updateStrategy(newValue);
						sprite.setMouseExitStrategy(newValue);
					}
				}, Constants.getAllGeneralStrategies());

		// Colors -- Create a ColorPickerComponent first
		Node colorPickerComponent = ColorPickerComponentLoader.create((Color) sprite.getView().getFill(),
				new UpdateSpriteAdapter() {
					@Override
					public void updateColor(Color color) {
						super.updateColor(color);
						sprite.getView().setFill(color);
					}
				});
		
		// onTick
		propertiesBox.addComboBoxPropertyComponent("onTick Condition", sprite.getOnTickStrategy(),
				Constants.PropertyType.STANDARD_PROPERTY_COMPONENT, new UpdateSpriteAdapter() {
					@Override
					public void updateStrategy(GeneralStrategy newValue) {
						super.updateStrategy(newValue);
						sprite.setOnTickStrategy(newValue);
					}
				}, Constants.getOnTickStrategies());
		
		// Then add it to the panel
		propertiesBox.addNode(colorPickerComponent, Constants.PropertyType.STANDARD_PROPERTY_COMPONENT);
	}

	private static void addOnHitSomethingConditions(Sprite sprite) {
		// Select the VBox that the conditions will be placed into
		VBox vBox = ParentWindow.getGameDesignScene().getPropertiesPanel().getVboxOnHitSomethingConditions();

		// Display each condition from the sprite as a component
		for (CollisionCondition condition : sprite.getOnHitSomethingConditionList()) {
			// Create (load in) a ConditionComponent
			ConditionComponent conditionComponent = new ConditionComponent();
			conditionComponent.setup(condition, FXCollections.observableArrayList(Constants.getAllCollisionStrategies()));

			// Add the ConditionComponent to the appropriate VBox group
			vBox.getChildren().add(conditionComponent.getView());
		}
	}

	private static void addOnGetHitSomethingConditions(Sprite sprite) {
		// Select the VBox that the conditions will be placed into
		VBox vBox = ParentWindow.getGameDesignScene().getPropertiesPanel().getVboxOnGetHitConditions();

		// Display each condition from the sprite as a component
		for (CollisionCondition condition : sprite.getOnGetHitConditionList()) {
			// Create (load in) a ConditionComponent
			ConditionComponent conditionComponent = new ConditionComponent();
			conditionComponent.setup(condition, FXCollections.observableArrayList(Constants.getAllCollisionStrategies()));

			// Add the ConditionComponent to the appropriate VBox group
			vBox.getChildren().add(conditionComponent.getView());
		}
	}

	private static void addOnKeyPressConditions(Sprite sprite) {
		// Select the VBox that the conditions will be placed into
		VBox vBox = ParentWindow.getGameDesignScene().getPropertiesPanel().getVboxOnKeyPressConditions();

		// Display each condition from the sprite as a component
		for (KeyEventCondition condition : sprite.getOnKeyPressConditionList()) {
			// Create (load in) a ConditionComponent
			KeyEventConditionComponent conditionComponent = new KeyEventConditionComponent();
			conditionComponent.setup(condition, FXCollections.observableArrayList(Constants.getAllGeneralStrategies()));

			// Add the ConditionComponent to the appropriate VBox group
			vBox.getChildren().add(conditionComponent.getView());
		}
	}

	private static void addOnTimeConditions(Sprite sprite) {
		// Select the VBox that the conditions will be placed into
		VBox vBox = ParentWindow.getGameDesignScene().getPropertiesPanel().getVboxOnTimeConditions();

		// Display each condition from the sprite as a component
		for (TimeEventCondition condition : sprite.getOnTimeConditionList()) {
			// Create (load in) a ConditionComponent
			TimeEventConditionComponent conditionComponent = new TimeEventConditionComponent();
			conditionComponent.setup(condition, FXCollections.observableArrayList(Constants.getAllGeneralStrategies()));
			
			// Add the ConditionComponent to the appropriate VBox group
			vBox.getChildren().add(conditionComponent.getView());
		}
	}
}
