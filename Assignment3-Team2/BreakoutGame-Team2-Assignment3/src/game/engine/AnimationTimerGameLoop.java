/**
 * @author: Ed Eden-Rump
 * @CreationDate: Sep 1, 2021
 * @editors: Ethan Taylor Behar
 * @Info: Be warned Ed's code was bugged and needed to be fixed.
 * @References: 
 * https://github.com/edencoding/javafx-game-dev/blob/master/SpaceShooter/src/main/java/com/edencoding/animation/GameLoopTimer.java
 * https://github.com/edencoding/javafx-animation/blob/master/animation-timer-pause/src/main/java/com/edencoding/animation/PausableAnimationTimer.java
 * https://edencoding.com/animation-timer-pausing/
 **/

package game.engine;

import javafx.animation.AnimationTimer;

public abstract class AnimationTimerGameLoop extends AnimationTimer {

	private long lastFrameTimeNanos = 0;
	private boolean isPaused = false;
	private boolean isPauseScheduled = false;
	private boolean isPlayScheduled = false;
	
	public boolean isPaused() {
		return isPaused;
	}

	public void pause() {
		if (!isPaused) { 
	        isPlayScheduled = false;
	    	isPauseScheduled = true;
		}
	}
	
	public void play() {
		if (isPaused) { 
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
    }

    @Override
    public void stop() {
        super.stop();
        isPaused = false;
        isPauseScheduled = false;
        isPlayScheduled = false;
    }

    @Override
    public void handle(long now) {
        if (isPauseScheduled) {
            isPaused = true;
            isPauseScheduled = false;
        }

        if (isPlayScheduled) {
    		lastFrameTimeNanos = now;
            isPaused = false;
            isPlayScheduled = false;
        }
        
        if (!isPaused) {
        	float secondsSincePreviousFrame = (float) ((now - lastFrameTimeNanos) / 1e9);
            lastFrameTimeNanos = now;
            update(secondsSincePreviousFrame);
        }
    }

    public abstract void update(float secondsSinceLastFrame);
}
