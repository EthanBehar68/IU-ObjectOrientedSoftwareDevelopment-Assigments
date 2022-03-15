/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 15, 2021
 * @editors:
 **/
package game.engine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public abstract class TimelineGameLoop {

	private Timeline gameLoop;
	private KeyFrame keyFrame;
	private double totalTime = 0;
	private double previousTotalTime = 0;
    private final static double START_MILLI_TIME = System.currentTimeMillis();
    // private final static float FRAMES_PER_SECOND = 0.00825f; // Target 120 FPS - game starts to break down at this point
    private final static float FRAMES_PER_SECOND = 0.0165f; // Target 60 FPS
    // private final static float FRAMES_PER_SECOND = 0.033f; // Target 30 FPS

    private final static double MILLISECOND_CONVERSION = 1000;
		
	private boolean isPaused = false;
	private boolean isPlaying = false;
	
	private boolean isPauseScheduled = false;
	private boolean isPlayScheduled = false;

    public TimelineGameLoop() {  
		// Set up timeline as a 60 fps ticker
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		EventHandler<ActionEvent> tickEvent = new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent actionEvent) {
	        	previousTotalTime = totalTime;
				totalTime = (System.currentTimeMillis() - START_MILLI_TIME) / MILLISECOND_CONVERSION; 
				
		        if (isPauseScheduled) {
		            isPaused = true;
		            isPauseScheduled = false;
		        }

		        if (isPlayScheduled) {
		        	previousTotalTime = totalTime;
		            isPaused = false;
		            isPlayScheduled = false;
		        }
		        
		        if (!isPaused) {
		            update(totalTime - previousTotalTime);
		        }
			}
		};
		keyFrame = new KeyFrame(Duration.seconds(FRAMES_PER_SECOND), tickEvent);
		gameLoop.getKeyFrames().add(keyFrame);
    }
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void pause() {
		if (!isPaused) { 
			isPauseScheduled = true;
		}
	}
	
	public void play() {
		if (isPaused) { 
			isPlayScheduled = true;
		}
	}
	
    public void start() {
    	gameLoop.play();
        isPlaying = true;
        isPlayScheduled = true;
    }

    public void stop() {
    	gameLoop.stop();
        isPaused = false;
        isPlaying = false;
        isPauseScheduled = false;
        isPlayScheduled = false;
    }
    
    public abstract void update(double timeDelta);
}

