/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Sep 25, 2021
 * @Editors:
 **/
package gamemaker.controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import gamemaker.Constants;
import gamemaker.GameMakerApplication;
import gamemaker.controller.command.*;
import gamemaker.model.Model;
import gamemaker.model.actions.DisplayTimeAction;
import gamemaker.model.actions.ReflectAction;
import gamemaker.model.actions.TranslateVelocityAction;

import gamemaker.model.event.CollisionEvent;
import gamemaker.model.event.KeyCodeEvent;
import gamemaker.model.event.MouseCodeEvent;
import gamemaker.model.event.TimeEvent;
import gamemaker.model.sprite.Sprite;
import gamemaker.view.CanvasInputHandler;
import gamemaker.view.View;
import gamemaker.view.components.CollisionBehaviorConfigObject;
import gamemaker.view.components.KeyInputBehaviorConfigObject;
import gamemaker.view.components.MouseInputBehaviorConfigObject;
import gamemaker.view.components.TimeBehaviorConfigObject;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class Controller {

	private Model model;
	private View view;
	private CommandInvoker commandInvoker;

	public Controller() {
		model = new Model();
		commandInvoker = new CommandInvoker();
	}

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.commandInvoker = new CommandInvoker();
	}

	private void sendCommandToCommandInvoker(Command command, boolean enableUndoButton) {
		if (enableUndoButton) {
			view.enableButton(view.undoBtn);
		}

		// Call the command
		commandInvoker.executeCurrentCommand(command);
	}

	/************************************
	 * 
	 * Sprite Related
	 * 
	 ************************************/

	public void issueCreateSpriteCommand(Pane gameCanvas) {
		// Build command
		CreateSpriteCommand createSpriteCommand = new CreateSpriteCommand(model);

		sendCommandToCommandInvoker(createSpriteCommand, false);
	}

	public void issueUpdateSelectedSpritePropretiesCommand(HashMap<String, String> propertyToInputValue) {
		// Build command
		UpdateSelectedSpritePropertiesCommand updateSelectedSpritePropertiesCommand = new UpdateSelectedSpritePropertiesCommand(
				model, propertyToInputValue);

		sendCommandToCommandInvoker(updateSelectedSpritePropertiesCommand, false);
	}

	public void issueCreatEventsCommand(List<TimeBehaviorConfigObject> timeBehaviorConfigObjects,
			List<KeyInputBehaviorConfigObject> keyBehaviorConfigObjects,
			List<MouseInputBehaviorConfigObject> mouseBehaviorConfigObjects,
			List<CollisionBehaviorConfigObject> collisionBehaviorConfigObjects) {

		Sprite target = model.getCurrentSelectedSprite();

		LinkedList<TimeEvent> timeEvents = buildTimeEvents(target, timeBehaviorConfigObjects);
		LinkedList<KeyCodeEvent> keyEvents = buildKeyCodeEvents(target, keyBehaviorConfigObjects);
		LinkedList<MouseCodeEvent> mouseEvents = buildMouseCodeEvents(target, mouseBehaviorConfigObjects);
		LinkedList<CollisionEvent> collisionEvents = buildCollisionEvents(target, collisionBehaviorConfigObjects);

		UpdateEventsCommand updateEventsCommand = new UpdateEventsCommand(model, timeEvents, keyEvents, mouseEvents,
				collisionEvents);

		sendCommandToCommandInvoker(updateEventsCommand, false);
	}

	public LinkedList<TimeEvent> buildTimeEvents(Sprite target,
			List<TimeBehaviorConfigObject> timeBehaviorConfigObjects) {
		LinkedList<TimeEvent> timeEvents = new LinkedList<TimeEvent>();

		for (TimeBehaviorConfigObject timeBehavior : timeBehaviorConfigObjects) {
			timeEvents.add(buildTimeEvent(target, timeBehavior));
		}

		return timeEvents;
	}

	private TimeEvent buildTimeEvent(Sprite target, TimeBehaviorConfigObject timeEventParams) {
		TimeEvent timeEvent;

		String interval = timeEventParams.isContinuous() ? "-1" : timeEventParams.getInterval();
		String action = timeEventParams.getAction();
		String selectedProperty = timeEventParams.getSelectedProperty();
		File soundFxFile;
		if (timeEventParams.getPath().compareToIgnoreCase(Constants.NO_AUDIO_FILE_SELECTED) != 0) {
			soundFxFile = new File(timeEventParams.getPath());
		} else {
			soundFxFile = null;
		}

		HBox input = timeEventParams.getInput();

		if (action.equals("Translate Property")) {
			if (selectedProperty.equals("Velocity")) {
				HBox entryFields = (HBox) (input.getChildren().get(0));
				TextField translateXInput = (TextField) (entryFields.getChildren().get(0));
				TextField translateYInput = (TextField) (entryFields.getChildren().get(1));

				double translateX = Double.parseDouble(translateXInput.getText());
				double translateY = Double.parseDouble(translateYInput.getText());

				TranslateVelocityAction newAction = new TranslateVelocityAction(target, soundFxFile, translateX,
						translateY);

				timeEvent = new TimeEvent(target.getId(), Integer.parseInt(interval), newAction);
				return timeEvent;
			}
		} else if (action.compareToIgnoreCase("Display Time") == 0) {
			DisplayTimeAction newAction = new DisplayTimeAction(target, soundFxFile);

			timeEvent = new TimeEvent(target.getId(), Integer.parseInt(interval), newAction);
			return timeEvent;
		}
		GameMakerApplication.logger.fatal("Do not know how to build a " + action + " action with " + selectedProperty);
		throw new UnsupportedOperationException(
				"Do not know how to build a " + action + " action with " + selectedProperty);
	}

	public LinkedList<KeyCodeEvent> buildKeyCodeEvents(Sprite target,
			List<KeyInputBehaviorConfigObject> keyBehaviorConfigObjects) {
		LinkedList<KeyCodeEvent> keyEvents = new LinkedList<KeyCodeEvent>();

		for (KeyInputBehaviorConfigObject keyBehavior : keyBehaviorConfigObjects) {
			keyEvents.add(buildKeyEvent(target, keyBehavior));
		}

		return keyEvents;
	}

	private KeyCodeEvent buildKeyEvent(Sprite target, KeyInputBehaviorConfigObject keyEventParams) {
		GameMakerApplication.logger.fatal("Do not know how to build an key event");
		throw new UnsupportedOperationException("Do not know how to build an key event");
	}

	public LinkedList<MouseCodeEvent> buildMouseCodeEvents(Sprite target,
			List<MouseInputBehaviorConfigObject> mouseBehaviorConfigObjects) {
		LinkedList<MouseCodeEvent> mouseEvents = new LinkedList<MouseCodeEvent>();

		for (MouseInputBehaviorConfigObject mouseBehavior : mouseBehaviorConfigObjects) {
			mouseEvents.add(buildMouseEvent(target, mouseBehavior));
		}

		return mouseEvents;
	}

	private MouseCodeEvent buildMouseEvent(Sprite target, MouseInputBehaviorConfigObject mouseEventParams) {
		GameMakerApplication.logger.fatal("Do not know how to build an mouse event");
		throw new UnsupportedOperationException("Do not know how to build an mouse event");
	}

	public LinkedList<CollisionEvent> buildCollisionEvents(Sprite target,
			List<CollisionBehaviorConfigObject> collisionBehaviorConfigObjects) {
		LinkedList<CollisionEvent> collisionEvents = new LinkedList<CollisionEvent>();

		for (CollisionBehaviorConfigObject collisionBehavior : collisionBehaviorConfigObjects) {
			collisionEvents.add(buildCollisionEvent(target, collisionBehavior));
		}

		return collisionEvents;
	}

	private CollisionEvent buildCollisionEvent(Sprite target, CollisionBehaviorConfigObject collisionEventParams) {
		CollisionEvent collisionEvent;

		String condition = collisionEventParams.getCondition();
		String action = collisionEventParams.getAction();
		File soundFxFile;
		if (collisionEventParams.getPath().compareToIgnoreCase(Constants.NO_AUDIO_FILE_SELECTED) != 0) {
			soundFxFile = new File(collisionEventParams.getPath());
		} else {
			soundFxFile = null;
		}

		if (condition.compareToIgnoreCase("on hit gameplay bounds") == 0) {
			if (action.compareToIgnoreCase("reflect") == 0) {

				ReflectAction reflectAction = new ReflectAction(target, soundFxFile);

				collisionEvent = new CollisionEvent(Constants.CollisionType.SCREEN, reflectAction);
				return collisionEvent;
			}
		}
		GameMakerApplication.logger.fatal("Do not know how to build " + condition + " condition with action " + action);
		throw new UnsupportedOperationException(
				"Do not know how to build " + condition + " condition with action " + action);
	}

	/************************************
	 *
	 * Background
	 *
	 ************************************/

	public void issueUpdateBackgroundProperties(HashMap<String, String> propToInput) {
		// Unbox input for command
		Color color = Color.web(propToInput.get(Constants.COLOR_KEY));
		String backgroundTrackPath = propToInput.get(Constants.AUDIO_PATH_KEY);
		File backgroundTrack;
		if (backgroundTrackPath.compareToIgnoreCase(Constants.EMPTY_STRING) == Constants.ZERO) {
			backgroundTrack = null;
		} else {
			backgroundTrack = new File(backgroundTrackPath);
		}

		UpdateBackgroundPropertiesCommand updateBackgroundProperties = new UpdateBackgroundPropertiesCommand(model,
				color, backgroundTrack);

		sendCommandToCommandInvoker(updateBackgroundProperties, false);
	}

	/************************************
	 *
	 * Audio Selecting
	 *
	 ************************************/

	public void issueGetBackgroundTrackCommand(Window appWindow) {
		SelectBackgroundTrackCommand selectBackgroundTrackCommand = new SelectBackgroundTrackCommand(view, appWindow);

		sendCommandToCommandInvoker(selectBackgroundTrackCommand, false);
	}

	/************************************
	 *
	 * Right Side Buttons
	 *
	 ************************************/

	public void issuePlayGameCommand(CanvasInputHandler canvasInputHandler) {
		// Build command
		PlayGameCommand playGameCommand = new PlayGameCommand(model, canvasInputHandler);

		sendCommandToCommandInvoker(playGameCommand, false);
	}

	public void issueStopGameCommand(CanvasInputHandler canvasInputHandler) {
		// Build command
		StopGameCommand stopGameCommand = new StopGameCommand(model, canvasInputHandler);

		sendCommandToCommandInvoker(stopGameCommand, false);
	}

	public void issueSaveCommand() {
		SaveCommand saveCommand = new SaveCommand(model);

		sendCommandToCommandInvoker(saveCommand, false);
	}

	public void issueSaveAsCommand(Window appWindow) {
		SaveAsCommand saveAsCommand = new SaveAsCommand(model, view, appWindow);

		sendCommandToCommandInvoker(saveAsCommand, false);
	}

	public void issueLoadCommand(Window appWindow) {
		LoadCommand loadCommand = new LoadCommand(model, view, appWindow);

		sendCommandToCommandInvoker(loadCommand, false);
	}

	public void undoLastCommand() {
		if (commandInvoker.undosAvailable()) {
			commandInvoker.undoCommand();
			view.enableButton(view.redoBtn);
			if (!commandInvoker.undosAvailable()) {
				view.disableButton(view.undoBtn);
			}
		} else {
			view.disableButton(view.undoBtn);
		}
	}

	public void redoLastCommand() {
		if (commandInvoker.redosAvailable()) {
			commandInvoker.redoCommand();
			view.enableButton(view.undoBtn);
			if (!commandInvoker.redosAvailable()) {
				view.disableButton(view.redoBtn);
			}
		} else {
			view.disableButton(view.redoBtn);
		}
	}

	/************************************
	 * 
	 * Non-Button Invoked i.e. Selecting/Dragging a shape
	 * 
	 ************************************/

	public void issueSpriteSelectedCommand(Node target) {
		// Each shape's id is set to the sprite id
		String id = target.getId();
		int spriteId = Integer.parseInt(id);

		// Get sprite based on id
		Sprite newSelectedSprite = model.getSpriteById(spriteId);

		// Null check in case
		if (newSelectedSprite == null) {
			GameMakerApplication.logger.error("The selected node cannot be cast to sprite!");
			return;
		}

		// Ignore request if sprite is already selected
		if (model.isSpriteTheSelectedSprite(newSelectedSprite)) {
			GameMakerApplication.logger
					.info("CommandInvoker denying SpriteSelectedCommand b/c selected sprite is already the target.");
			return;
		}

		// Build Command
		SpriteSelectedCommand shapeSelectedCommand = new SpriteSelectedCommand(model, newSelectedSprite);

		sendCommandToCommandInvoker(shapeSelectedCommand, false);
	}

	public void issueSpriteDragCommand(double translateX, double translateY) {
		// Build Command
		SpriteDraggedCommand spriteDraggedCommand = new SpriteDraggedCommand(model, translateX, translateY);

		sendCommandToCommandInvoker(spriteDraggedCommand, false);
	}

	public void issueSpriteReleasedCommand(double layoutX, double layoutY, double initialX, double initialY) {
		// Build Command
		SpriteReleasedCommand spriteReleasedCommand = new SpriteReleasedCommand(model, layoutX, layoutY);
		// , initialX, initialY);

		sendCommandToCommandInvoker(spriteReleasedCommand, false);
	}
}
