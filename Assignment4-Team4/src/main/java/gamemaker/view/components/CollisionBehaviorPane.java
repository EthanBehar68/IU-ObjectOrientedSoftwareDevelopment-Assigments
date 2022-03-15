/**
 * @Author: Maazin Jawad
 * @CreationDate: 9/25/2021
 * @Editors:
 * @EditedDate:
 **/
package gamemaker.view.components;

import gamemaker.Constants;
import gamemaker.GameMakerApplication;
import gamemaker.model.event.CollisionEvent;
import gamemaker.model.event.TimeEvent;
import gamemaker.utilities.FileManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class CollisionBehaviorPane extends VBox {

	MenuButton collisionAction;
	MenuButton collisionCondition;
	HBox input;
	private Label path;

	public CollisionBehaviorPane() {
		super();
		this.setLayoutX(Constants.BEHAVIOR_PANE_MARGIN);
		this.setPrefWidth(Constants.BEHAVIOR_PANE_WIDTH);
		this.setPrefHeight(Constants.BEHAVIOR_PANE_HEIGHT);

		collisionCondition = new MenuButton("Choose Collision Condition");
		configureCollisionConditionDropDown();

		collisionAction = new MenuButton("Choose Action");
		configureActionChoices();

		this.getChildren().addAll(collisionCondition, collisionAction);

		this.setSpacing(15);

	}

	private void configureCollisionConditionDropDown() {
		MenuItem choice1 = new MenuItem("On Hit Other Sprite");
		MenuItem choice2 = new MenuItem("On Get Hit");
		MenuItem choice3 = new MenuItem("On Hit Gameplay Bounds");

		collisionCondition.getItems().addAll(choice1, choice2, choice3);
		collisionCondition.getItems()
				.forEach(choice -> choice.setOnAction(e -> collisionCondition.setText(choice.getText())));
	}

	private void configureActionChoices() {
		MenuItem choice1 = new MenuItem("Set Property");
		MenuItem choice2 = new MenuItem("Translate Property");
		MenuItem choice3 = new MenuItem("Reflect");
		MenuItem choice4 = new MenuItem("De-Spawn");
		MenuItem choice5 = new MenuItem("End Game");

		HBox propSelectorAndInput = new HBox();
		propSelectorAndInput.setSpacing(10);
		input = new HBox();

		choice1.setOnAction(e -> {
			propSelectorAndInput.getChildren().clear();
			input.getChildren().clear();
			collisionAction.setText(choice1.getText());
			// this.getChildren().add(new PropertySelector(hbox));
			SettablePropertySelector settablePropertySelector = new SettablePropertySelector(input);
			propSelectorAndInput.getChildren().addAll(settablePropertySelector, input);
			try {
				this.getChildren().add(propSelectorAndInput);
			} catch (IllegalArgumentException ignored) {

			}
		});

		choice2.setOnAction(e -> {
			propSelectorAndInput.getChildren().clear();
			input.getChildren().clear();
			collisionAction.setText(choice2.getText());
			// this.getChildren().add(new PropertySelector(hbox));
			TranslateAblePropertySelector settablePropertySelector = new TranslateAblePropertySelector(input);
			propSelectorAndInput.getChildren().addAll(settablePropertySelector, input);
			try {
				this.getChildren().add(propSelectorAndInput);
			} catch (IllegalArgumentException ignored) {

			}
		});

		choice3.setOnAction(e -> {
			propSelectorAndInput.getChildren().clear();
			input.getChildren().clear();
			collisionAction.setText(choice3.getText());
			try {
				this.getChildren().add(getSoundSelectionPane());
			} catch (IllegalArgumentException ignored) {

			}
		});

		choice4.setOnAction(e -> {
			propSelectorAndInput.getChildren().clear();
			input.getChildren().clear();
			collisionAction.setText(choice4.getText());
			try {
				this.getChildren().add(getSoundSelectionPane());
			} catch (IllegalArgumentException ignored) {

			}
		});

		choice5.setOnAction(e -> {
			propSelectorAndInput.getChildren().clear();
			input.getChildren().clear();
			collisionAction.setText(choice5.getText());
			try {
				this.getChildren().add(getSoundSelectionPane());
			} catch (IllegalArgumentException ignored) {

			}
		});

		collisionAction.getItems().addAll( choice3);

	}

	private VBox getSoundSelectionPane() {

		Label playSound = new Label("Sound FX: ");
		Button chooseFile = new Button("Choose File...");

		HBox soundSelectionPane = new HBox();
		soundSelectionPane.setSpacing(25);
		soundSelectionPane.getChildren().addAll(playSound, chooseFile);
		path = new Label(Constants.NO_AUDIO_FILE_SELECTED);
		path.setWrapText(true);
		path.setPrefWidth(300);
		VBox box = new VBox(soundSelectionPane, path);
		box.setPrefWidth(300);
		box.setSpacing(15);
		chooseFile.setOnAction(e -> {
			FileChooser.ExtensionFilter audioFilter = new FileChooser.ExtensionFilter("Audio Files",
					Constants.AUDIO_TYPES);

			boolean successful = FileManager.getInstance().getLoadFile(this.getScene().getWindow(), audioFilter);
			if (successful) {
				path.setText(FileManager.getInstance().getSelectedFile().getAbsolutePath());
			}
		});

		soundSelectionPane.setAlignment(Pos.CENTER_LEFT);

		return box;
	}

	public void setPath(Label path) {
		this.path = path;
	}

	public String rebuildFrom(CollisionEvent collisionEvent) {

		String actionUIName = collisionEvent.getAction().getUIInfo();
		String[] components = actionUIName.split("-");

		collisionCondition.getItems().get(collisionEvent.getCollisionType().ordinal()).fire();
		String collisionBehavior = components[0];
		if (collisionBehavior.startsWith("Translate") || collisionBehavior.startsWith("Set")) {
			String selectedProperty = components[1];
			if (selectedProperty.equals("Velocity") || selectedProperty.equals("Position")) {
				String x = components[2];
				String y = components[3];

				for (MenuItem menuItem : this.collisionCondition.getItems()) {
					if (menuItem.getText().startsWith(collisionBehavior)) {
						menuItem.fire();
						break;
					}
				}

				//MenuButton mb = (MenuButton) propSelectorAndInput.getChildren().get(0);
				//for(MenuItem menuItem: mb.getItems()){
				//	if (menuItem.getText().equals(selectedProperty)) {
				//		menuItem.fire();
				//		break;
				//	}
				//}

				HBox inputContents = (HBox) input.getChildren().get(0);
				TextField fieldX = (TextField) inputContents.getChildren().get(0);
				TextField fieldY = (TextField) inputContents.getChildren().get(1);

				fieldX.setText(x);
				fieldY.setText(y);

				if(collisionEvent.getAction().getSoundFXFile() != null){
					path.setText(collisionEvent.getAction().getSoundFXFile().getAbsolutePath());
				}

			}

		}
		else{
			GameMakerApplication.logger.info("REBUILDING");
			for(MenuItem menuItem: collisionAction.getItems()){
				if(menuItem.getText().equals(collisionBehavior)){
					menuItem.fire();
					break;
				}
			}
			if(collisionEvent.getAction().getSoundFXFile() != null){
				path.setText(collisionEvent.getAction().getSoundFXFile().getAbsolutePath());
			}
		}

		return collisionBehavior;
	}
	public CollisionBehaviorConfigObject export() {
		return new CollisionBehaviorConfigObject(collisionCondition.getText(), collisionAction.getText(), input,
				path.getText());
	}
}
