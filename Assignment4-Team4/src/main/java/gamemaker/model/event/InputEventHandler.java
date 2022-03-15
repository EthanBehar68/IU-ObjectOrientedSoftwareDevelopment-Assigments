/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Sep 29, 2021
 * @Editors:
 * @EditDate:
 **/
package gamemaker.model.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import gamemaker.Constants.MouseButtonCode;
import gamemaker.GameMakerApplication;
import gamemaker.model.actions.Action;
import gamemaker.model.interfaces.Dumpable;
import gamemaker.observer.pattern.Observer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class InputEventHandler implements Observer, Dumpable {

	// Key Listening
	private Pane gameCanvas;
	private HashSet<KeyCode> pressedKeys = new HashSet<>();

	// Mouse Listening
	private HashSet<MouseButtonCode> pressedMouseButtons = new HashSet<>();

	// Event Invoking
	private HashMap<KeyCode, LinkedList<Action>> keyToActionMap;
	private HashMap<MouseButtonCode, LinkedList<Action>> mouseToActionMap;

	public InputEventHandler() {
		keyToActionMap = new HashMap<KeyCode, LinkedList<Action>>();
		mouseToActionMap = new HashMap<MouseButtonCode, LinkedList<Action>>();
	}

	public InputEventHandler(Pane gameCanvas, Iterator<Integer> keyCodeKeys,
			HashMap<Integer, LinkedList<KeyCodeEvent>> spriteIdToKeyCodeEventsMap, Iterator<Integer> mouseCodeKeys,
			HashMap<Integer, LinkedList<MouseCodeEvent>> spriteIdToMouseCodeEventsMap) {

		this.gameCanvas = gameCanvas;
		gameCanvas.setOnKeyPressed(this::keyPressed);
		gameCanvas.setOnKeyReleased(this::keyReleased);
		gameCanvas.setOnMousePressed(this::mousePressed);
		gameCanvas.setOnMouseReleased(this::mouseReleased);

		keyToActionMap = new HashMap<KeyCode, LinkedList<Action>>();
		buildKeyCodeHashMap(keyCodeKeys, spriteIdToKeyCodeEventsMap);
		mouseToActionMap = new HashMap<MouseButtonCode, LinkedList<Action>>();
		buildMouseCodeHashMap(mouseCodeKeys, spriteIdToMouseCodeEventsMap);
	}

	/************************************
	 * 
	 * Key Listening/Recording
	 *
	 ************************************/

	public void keyPressed(KeyEvent event) {
		GameMakerApplication.logger.info("Key " + event.getCode() + " was pressed during the game.");
		pressedKeys.add(event.getCode());
	}

	public void keyReleased(KeyEvent event) {
		GameMakerApplication.logger.info("Key " + event.getCode() + " was released during the game.");
		pressedKeys.remove(event.getCode());
	}

	/************************************
	 * 
	 * Mouse Listening/Recording
	 *
	 ************************************/

	public void mousePressed(MouseEvent event) {
		MouseButtonCode code = getMouseCode(event);
		GameMakerApplication.logger.info("Mouse " + code + " was pressed during the game.");
		pressedMouseButtons.add(code);

	}

	public void mouseReleased(MouseEvent event) {
		MouseButtonCode code = getMouseCode(event);
		GameMakerApplication.logger.info("Mouse " + code + " was released during the game.");
		pressedMouseButtons.remove(code);
	}

	public MouseButtonCode getMouseCode(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			return MouseButtonCode.PRIMARY;
		} else if (event.isSecondaryButtonDown()) {
			return MouseButtonCode.SECONDARY;
		} else if (event.isMiddleButtonDown()) {
			return MouseButtonCode.MIDDLE;
		} else {
			return MouseButtonCode.IGNORE;
		}
	}

	/************************************
	 * 
	 * Input Event Invoking
	 *
	 ************************************/

	private void buildKeyCodeHashMap(Iterator<Integer> keyCodeKeys,
			HashMap<Integer, LinkedList<KeyCodeEvent>> spriteIdToKeyCodeEventsMap) {
		while (keyCodeKeys.hasNext()) {
			Integer key = keyCodeKeys.next();

			Iterator<KeyCodeEvent> aSpritesEvents = spriteIdToKeyCodeEventsMap.get(key).iterator();
			while (aSpritesEvents.hasNext()) {
				KeyCodeEvent currentEvent = aSpritesEvents.next();

				// KeyCode is already a key
				if (keyToActionMap.containsKey(currentEvent.getInputTrigger())) {
					LinkedList<Action> actionList = keyToActionMap.get(currentEvent.getInputTrigger());
					GameMakerApplication.logger.info("keyCodeToActionMap key code " + currentEvent.getInputTrigger()
							+ "'s length pre add: " + actionList.size());
					actionList.add(currentEvent.getAction());
					GameMakerApplication.logger.info("keyCodeToActionMap key code " + currentEvent.getInputTrigger()
							+ "'s action list length post add: " + actionList.size());
				}
				// KeyCode is not a key yet
				else {
					LinkedList<Action> actionList = new LinkedList<Action>();
					actionList.add(currentEvent.getAction());
					keyToActionMap.put(currentEvent.getInputTrigger(), actionList);
				}
			}
		}
	}

	private void buildMouseCodeHashMap(Iterator<Integer> mouseCodeKeys,
			HashMap<Integer, LinkedList<MouseCodeEvent>> spriteIdToMouseCodeEventsMap) {

		while (mouseCodeKeys.hasNext()) {
			Integer key = mouseCodeKeys.next();

			Iterator<MouseCodeEvent> aSpritesEvents = spriteIdToMouseCodeEventsMap.get(key).iterator();
			while (aSpritesEvents.hasNext()) {
				MouseCodeEvent currentEvent = aSpritesEvents.next();

				// MouseCode is already a key
				if (mouseToActionMap.containsKey(currentEvent.getInputTrigger())) {
					LinkedList<Action> actionList = mouseToActionMap.get(currentEvent.getInputTrigger());
					GameMakerApplication.logger.info("mouseCodeToActionMap mouse button "
							+ currentEvent.getInputTrigger() + "'s length pre add: " + actionList.size());
					actionList.add(currentEvent.getAction());
					GameMakerApplication.logger.info("mouseCodeToActionMap mouse button "
							+ currentEvent.getInputTrigger() + "'s action list length post add: " + actionList.size());
				}
				// MouseCode is not a key yet
				else {
					LinkedList<Action> actionList = new LinkedList<Action>();
					actionList.add(currentEvent.getAction());
					mouseToActionMap.put(currentEvent.getInputTrigger(), actionList);
				}
			}
		}
	}

	/************************************
	 * 
	 * Observer Implementation
	 *
	 ************************************/

	@Override
	public void update(double totalTime, double timeDelta) {
		processKeyEvents(totalTime, timeDelta);
		processMouseEvents(totalTime, timeDelta);
	}

	private void processKeyEvents(double totalTime, double timeDelta) {
		// Get Key Iterator
		Iterator<KeyCode> keyIterator = keyToActionMap.keySet().iterator();

		while (keyIterator.hasNext()) {
			KeyCode key = keyIterator.next();

			// Signifiyes every tick!
			if (pressedKeys.contains(key)) {
				Iterator<Action> actionIterator = keyToActionMap.get(key).iterator();
				while (actionIterator.hasNext()) {
					Action action = actionIterator.next();
					action.execute(null);
				}
			}
		}
	}

	private void processMouseEvents(double totalTime, double timeDelta) {
		// Get Key Iterator
		Iterator<MouseButtonCode> mouseCodeIterator = mouseToActionMap.keySet().iterator();

		while (mouseCodeIterator.hasNext()) {
			MouseButtonCode mouseButtonCoude = mouseCodeIterator.next();

			// Signifiyes every tick!
			if (pressedMouseButtons.contains(mouseButtonCoude)) {
				Iterator<Action> actionIterator = mouseToActionMap.get(mouseButtonCoude).iterator();
				while (actionIterator.hasNext()) {
					Action action = actionIterator.next();
					action.execute(null);
				}
			}
		}
	}

	/************************************
	 * 
	 * Dumpable Implementation
	 *
	 ************************************/

	@Override
	public void dump() {
		// Clear listeners
		// How do we properly remove these?
		gameCanvas.setOnKeyPressed(null);
		gameCanvas.setOnKeyReleased(null);
		gameCanvas.setOnMousePressed(null);
		gameCanvas.setOnMouseReleased(null);
		gameCanvas = null;

		// Clear all key info
		pressedKeys.clear();
		pressedKeys = null;
		keyToActionMap.clear();
		keyToActionMap = null;

		// Clear all mouse info
		pressedMouseButtons.clear();
		pressedMouseButtons = null;
		mouseToActionMap.clear();
		mouseToActionMap = null;
	}
}
