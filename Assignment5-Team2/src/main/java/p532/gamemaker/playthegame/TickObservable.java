/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 10, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.playthegame;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.CollisionDetectionUtility;

public class TickObservable extends AnimationTimer {
	// All game objects
	private final ArrayList<Sprite> allSprites = new ArrayList<Sprite>();
	// these do not experience collisions
	private final ArrayList<TickObserver> otherObservers = new ArrayList<TickObserver>();

	// Total time count
	private double totalTime = 0;
	private long lastFrameTimeNanos = 0;
	private double secondsSincePreviousFrame = 0; // AKA TimeDelta

	// Logical control for pause behavior
	private boolean paused = false;
	private boolean isPauseScheduled = false;
	private boolean isPlayScheduled = false;

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		if (!paused) {
			isPlayScheduled = false;
			isPauseScheduled = true;
		}
	}

	public void unpause() {
		this.paused = false;
	}

	public void play() {
		if (paused) {
			super.start();
			isPlayScheduled = true;
			isPauseScheduled = false;
		}
	}

	@Override
	public void start() {
		super.start();
		isPlayScheduled = false;
		isPauseScheduled = true;
		paused = true;
	}

	public void stopGameLoop() {
		this.stop();
	}

	@Override
	public void stop() {
		// Stop the game loop
		super.stop();

		totalTime = 0;

		// Restart pause/play flags
		paused = false;
		isPauseScheduled = false;
		isPlayScheduled = false;
	}

	@Override
	public void handle(long now) {
		if (isPauseScheduled) {
			paused = true;
			isPauseScheduled = false;
			secondsSincePreviousFrame = (now - lastFrameTimeNanos) / 1e9;
		}

		if (isPlayScheduled) {
			lastFrameTimeNanos = now;
			paused = false;
			isPlayScheduled = false;
		}

		if (!paused) {
			secondsSincePreviousFrame = (now - lastFrameTimeNanos) / 1e9;
			totalTime += secondsSincePreviousFrame;
			lastFrameTimeNanos = now;
			doFrameLogic();
		}
	}

	private void doFrameLogic() {

		// While paused, do the pause behavior instead.
		if (isPaused()) {
			return;
		}
		// While not paused, do the below code.

		// Trigger collisions
		CollisionDetectionUtility.checkForCollisions(allSprites);

		// Notify sprites of ticks so that they move or do whatever they need to do.
		// Use this type of loop so we can add to Sprites without causing concurrent modification exceptions
		int size = allSprites.size();
		for (int index = 0; index < size; index++) {
			allSprites.get(index).onTick(secondsSincePreviousFrame, totalTime);
		}

		// Notify all non-sprites of ticks
		for (TickObserver observer : otherObservers) {
			observer.onTick(secondsSincePreviousFrame, totalTime);
		}

		// Add frame duration to total time
		totalTime += secondsSincePreviousFrame;
	}

	public TickObservable() {
	}

	/**
	 * Adds the input Sprite as an observer of ticks. The Sprite will have its
	 * onTick() method called, and it will be checked for collisions.
	 * 
	 * @param newSprite the Sprite to register
	 */
	public void registerSprite(Sprite newSprite) {
		allSprites.add(newSprite);
	}

	/**
	 * Adds the input object as an observer of ticks. It will have its onTick()
	 * method called for every tick, but it will NOT be checked for collisions. If
	 * the observer, is a Sprite, please see registerSprite(...).
	 * 
	 * @param observer object to register
	 */
	public void registerObserver(TickObserver observer) {
		otherObservers.add(observer);
	}

	public void unregisterAll() {
		allSprites.clear();
		otherObservers.clear();
	}
}
