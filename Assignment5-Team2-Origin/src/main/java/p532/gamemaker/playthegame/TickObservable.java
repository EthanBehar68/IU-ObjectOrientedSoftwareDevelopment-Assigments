package p532.gamemaker.playthegame;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.CollisionDetectionUtility;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * When the user desires to play the game, this manages
 * what happens during each tick (game loop).
 */
public class TickObservable
{
    //All game objects
    private final LinkedList<Sprite> allSprites = new LinkedList<Sprite>();
    private final LinkedList<TickObserver> otherObservers = new LinkedList<>(); //these do not experience collisions

    //Game loop scheduler
    private ScheduledExecutorService executorService = null;

    //Logical control for pause behavior
    private boolean paused = false;

    public TickObservable() {
    }

    /**
     * Adds the input Sprite as an observer of ticks.
     * The Sprite will have its onTick() method called, and it
     * will be checked for collisions.
     * @param newSprite the Sprite to register
     */
    public void registerSprite(Sprite newSprite)
    {
        allSprites.add(newSprite);
    }

    /**
     * Adds the input object as an observer of ticks.
     * It will have its onTick() method called for every tick, but
     * it will NOT be checked for collisions.
     * If the observer, is a Sprite, please see registerSprite(...).
     * @param observer object to register
     */
    public void registerObserver(TickObserver observer)
    {
        otherObservers.add(observer);
    }

    public void unregisterAll()
    {
        allSprites.clear();
        otherObservers.clear();
    }

    public boolean isPaused()
    {
        return this.paused;
    }

    public void pause()
    {
        this.paused = true;
    }

    public void unpause()
    {
        this.paused = false;
    }

    public void initiateGameLoop()
    {
        //Schedule the game logic task to occur every frame
        executorService = Executors.newSingleThreadScheduledExecutor();
        final long INITIAL_DELAY = 500;
        executorService.scheduleAtFixedRate(this::doFrameLogic, INITIAL_DELAY, Constants.FRAME_DURATION, TimeUnit.MILLISECONDS);
        paused = false;
    }


    private void doFrameLogic()
    {
        //While paused, do the pause behavior instead.
        if (isPaused())
        {
            doPauseBehavior();
            return;
        }
        //While not paused, do the below code.

        //Trigger collisions
        CollisionDetectionUtility.checkForCollisions(allSprites);

        //Notify sprites of ticks so that they move or do whatever they need to do.
        for (Sprite sprite : allSprites)
        {
            sprite.onTick();
        }
        //Notify all non-sprites of ticks
        for (TickObserver observer : otherObservers)
        {
            observer.onTick();
        }
    }

    private static void doPauseBehavior()
    {
        /*/Draw a paused message
        final int FONT_SIZE = 36;
        GraphicsContext gc = GameWindow.getGraphicsContext();
        gc.setFont(new Font(FONT_SIZE)); //set font size
        int x = GameWindow.getWindowWidth()/2;
        int y = GameWindow.getWindowHeight()/2;
        gc.fillText("Paused", x - FONT_SIZE, y);*/
    }

    public void stopGameLoop()
    {
        //Terminate game loop runner
        if (executorService != null)
            executorService.shutdown();
        paused = true;
    }
}
