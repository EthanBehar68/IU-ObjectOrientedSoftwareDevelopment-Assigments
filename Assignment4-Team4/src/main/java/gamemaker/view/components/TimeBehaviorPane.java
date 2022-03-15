package gamemaker.view.components;

import gamemaker.Constants;
import gamemaker.model.event.TimeEvent;
import gamemaker.utilities.FileManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class TimeBehaviorPane extends VBox {

	private CheckBox continuous;
	Label every;
	TextField numSeconds;
	Label seconds;
	String selectedAction;
	String selectedProperty;
	Label path;

	public HBox getInput() {
		return input;
	}

	HBox input;

	public SettablePropertySelector getSettablePropertySelector() {
		return settablePropertySelector;
	}

	public TranslateAblePropertySelector getTranslateAblePropertySelector() {
		return translateAblePropertySelector;
	}

	SettablePropertySelector settablePropertySelector;
	TranslateAblePropertySelector translateAblePropertySelector;

	public Label getEvery() {
		return every;
	}

	public void setEvery(Label every) {
		this.every = every;
	}

	public TextField getNumSeconds() {
		return numSeconds;
	}

	public void setNumSeconds(TextField numSeconds) {
		this.numSeconds = numSeconds;
	}

	public Label getSeconds() {
		return seconds;
	}

	public void setSeconds(Label seconds) {
		this.seconds = seconds;
	}

	public MenuButton getTimeBehavior() {
		return timeBehavior;
	}

	public void setTimeBehavior(MenuButton timeBehavior) {
		this.timeBehavior = timeBehavior;
	}


	MenuButton timeBehavior;
	VBox soundSelectionPane;

	public TimeBehaviorPane() {
		super();
		this.setLayoutX(Constants.BEHAVIOR_PANE_MARGIN);
		this.setPrefWidth(Constants.BEHAVIOR_PANE_WIDTH);
		this.setPrefHeight(Constants.BEHAVIOR_PANE_HEIGHT);
		every = new Label("Every");
		numSeconds = new TextField();
		numSeconds.setPrefWidth(30);
		seconds = new Label("second(s): ");
		continuous = new CheckBox("Continuous");
		continuous
				.setTooltip(new Tooltip("Selected action will process every frame. Input for seconds will be ignored"));
		continuous.setLayoutX(60);

		timeBehavior = new MenuButton("Choose Action");
		configureActionChoices();

		HBox everyXseconds = new HBox(every, numSeconds, seconds, continuous);
		everyXseconds.setSpacing(10);

		this.setSpacing(15);
		this.getChildren().addAll(everyXseconds, timeBehavior);

		soundSelectionPane = getSoundSelectionPane();


	}

	public HBox propSelectorAndInput;
	MenuItem choice1 = new MenuItem("Set Property");
	MenuItem choice2 = new MenuItem("Translate Property");
	MenuItem choice3 = new MenuItem("Display Time");

	private void configureActionChoices() {
		propSelectorAndInput = new HBox();
		propSelectorAndInput.setSpacing(10);
		input = new HBox();

		choice1.setOnAction(this::choice1SetOnAction);
		choice2.setOnAction(this::choice2SetOnAction);
		choice3.setOnAction(this::choice3SetOnAction);

		timeBehavior.getItems().addAll(choice2, choice3);
	}

	public void choice1SetOnAction(ActionEvent value) {
		propSelectorAndInput.getChildren().clear();
		input.getChildren().clear();
		timeBehavior.setText(choice1.getText());
		settablePropertySelector = new SettablePropertySelector(input);
		selectedAction = settablePropertySelector.getText();
		propSelectorAndInput.getChildren().addAll(settablePropertySelector, input);
		try {

			this.getChildren().addAll(propSelectorAndInput, soundSelectionPane);

		} catch (IllegalArgumentException ignored) {

		}
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
				// FileManager.getInstance().getSelectedFile();
				path.setText(FileManager.getInstance().getSelectedFile().getAbsolutePath());
			}
		});

		soundSelectionPane.setAlignment(Pos.CENTER_LEFT);

		return box;
	}

	public void choice2SetOnAction(ActionEvent value) {
		propSelectorAndInput.getChildren().clear();
		input.getChildren().clear();
		timeBehavior.setText(choice2.getText());
		translateAblePropertySelector = new TranslateAblePropertySelector(input);
		selectedProperty = translateAblePropertySelector.getText();
		propSelectorAndInput.getChildren().addAll(translateAblePropertySelector, input);
		try {
			this.getChildren().addAll(propSelectorAndInput, soundSelectionPane);
		} catch (IllegalArgumentException ignored) {

		}
	}
	public void choice3SetOnAction(ActionEvent value) {
		timeBehavior.setText(choice3.getText());
		propSelectorAndInput.getChildren().clear();
		try {
			this.getChildren().add(soundSelectionPane);
		}catch(IllegalArgumentException duplicate){

		}
	}

	public void setPath(Label path) {
		this.path = path;
	}


	public TimeBehaviorConfigObject export() {
		if(translateAblePropertySelector != null){
			selectedProperty = translateAblePropertySelector.getText();
		}else if(settablePropertySelector != null){
			selectedProperty = settablePropertySelector.getText();
		}else{
			selectedProperty = Constants.EMPTY_STRING;
		}
		return new TimeBehaviorConfigObject(numSeconds.getText(), continuous.isSelected(), timeBehavior.getText(),
				selectedProperty, input, path.getText());
	}

	public String rebuildFrom(TimeEvent timeEvent) {
		String actionUIName = timeEvent.getAction().getUIInfo();
		String[] components = actionUIName.split("-");

		String interval = String.valueOf(timeEvent.getInterval());
		if (interval.equals("-1")) {
			continuous.setSelected(true);
		} else {
			numSeconds.setText(interval);
		}
		String timeBehavior = components[0];
		if (timeBehavior.startsWith("Translate") || timeBehavior.startsWith("Set")) {
			String selectedProperty = components[1];
			if (selectedProperty.equals("Velocity") || selectedProperty.equals("Position")) {
				String x = components[2];
				String y = components[3];

				for (MenuItem menuItem : this.timeBehavior.getItems()) {
					if (menuItem.getText().startsWith(timeBehavior)) {
						menuItem.fire();
						break;
					}
				}

				MenuButton mb = (MenuButton) propSelectorAndInput.getChildren().get(0);
				for(MenuItem menuItem: mb.getItems()){
					if (menuItem.getText().equals(selectedProperty)) {
						menuItem.fire();
						break;
					}
				}

				HBox inputContents = (HBox) input.getChildren().get(0);
				TextField fieldX = (TextField) inputContents.getChildren().get(0);
				TextField fieldY = (TextField) inputContents.getChildren().get(1);

				fieldX.setText(x);
				fieldY.setText(y);

				if(timeEvent.getAction().getSoundFXFile() != null){
					path.setText(timeEvent.getAction().getSoundFXFile().getAbsolutePath());
				}

			}

		}else{
			for(MenuItem menuItem: this.timeBehavior.getItems()){
				if(menuItem.getText().equals(timeBehavior)){
					menuItem.fire();
					break;
				}
			}
			if(timeEvent.getAction().getSoundFXFile() != null){
				path.setText(timeEvent.getAction().getSoundFXFile().getAbsolutePath());
			}
		}

		return timeBehavior;
	}
}
