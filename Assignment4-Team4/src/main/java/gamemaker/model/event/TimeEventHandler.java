/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Sep 30, 2021
 * @Editors:
 * @EditDate:
 **/
package gamemaker.model.event;

import gamemaker.Constants;
import gamemaker.GameMakerApplication;
import gamemaker.model.actions.Action;
import gamemaker.model.interfaces.Dumpable;
import gamemaker.observer.pattern.Observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class TimeEventHandler implements Observer, Dumpable {

	// Must be Integer b/c HashMap doesn't work with primitives
	private HashMap<Integer, LinkedList<Action>> intervalToActionMap;
	private int secsElapsed = 0;

	public TimeEventHandler() {
		intervalToActionMap = new HashMap<Integer, LinkedList<Action>>();
	}

	public TimeEventHandler(Iterator<Integer> keys, HashMap<Integer, LinkedList<TimeEvent>> spriteIdToTimeEventsMap) {
		intervalToActionMap = new HashMap<Integer, LinkedList<Action>>();
		buildHashMap(keys, spriteIdToTimeEventsMap);
	}

	/************************************
	 *
	 * Time Event Invoking
	 *
	 ************************************/

	private void buildHashMap(Iterator<Integer> keys, HashMap<Integer, LinkedList<TimeEvent>> spriteIdToTimeEventsMap) {
		while (keys.hasNext()) {
			Integer key = keys.next();

			Iterator<TimeEvent> aSpritesEvents = spriteIdToTimeEventsMap.get(key).iterator();
			while (aSpritesEvents.hasNext()) {
				TimeEvent currentEvent = aSpritesEvents.next();

				// Interval is already a key
				if (intervalToActionMap.containsKey(currentEvent.getInterval())) {
					LinkedList<Action> actionList = intervalToActionMap.get(currentEvent.getInterval());
					GameMakerApplication.logger.info("intervalToActionMap interval " + currentEvent.getInterval()
							+ "'s action list length pre add: " + actionList.size());
					actionList.add(currentEvent.getAction());
					GameMakerApplication.logger.info("intervalToActionMap interval " + currentEvent.getInterval()
							+ "'s action list length post add: " + actionList.size());
				}
				// Interval is not a key yet
				else {
					LinkedList<Action> actionList = new LinkedList<Action>();
					actionList.add(currentEvent.getAction());
					intervalToActionMap.put(currentEvent.getInterval(), actionList);
				}
			}
		}
	}

	/************************************
	 *
	 * Observer Implementations
	 *
	 ************************************/

	@Override
	public void update(double totalTime, double timeDelta) {
		// Get Key Iterator
		Iterator<Integer> keyIterator = intervalToActionMap.keySet().iterator();
		HashMap<String, Object> params = new HashMap<String, Object>();

		while (keyIterator.hasNext()) {

			params.put(Constants.TIME_ELAPSED_KEY, totalTime);
			params.put(Constants.TIME_DELTA_KEY, timeDelta);
			int interval = (int) keyIterator.next();

			// Signifies every tick!
			if (interval == -1) {
				Iterator<Action> actionIterator = intervalToActionMap.get(interval).iterator();
				while (actionIterator.hasNext()) {

					Action action = actionIterator.next();
					action.execute(params);
				}
			}

			// Signifies every X seconds

			else if (Math.floor(totalTime) == secsElapsed + 1) {
				secsElapsed++;
				if ((Math.floor(totalTime) % interval) == 0) {

					Iterator<Action> actionIterator = intervalToActionMap.get(interval).iterator();
					while (actionIterator.hasNext()) {
						Action action = actionIterator.next();
						action.execute(params);
					}
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
		// Clear time event info
		intervalToActionMap.clear();
		intervalToActionMap = null;
	}
}
