package p532.gamemaker.playthegame;

import p532.gamemaker.Constants;

/**
 * A singleton Stopwatch that can return its current time when it is requested.
 * Tracks milliseconds when onTick() is called so that it pauses with the game loop.
 */
public class Stopwatch implements TickObserver
{
    private static long msElapsed = 0;

    /**
     * A static Stopwatch that can be used instead of the constructor.
     */
    public static final Stopwatch instance = new Stopwatch();

    private Stopwatch() {
    }

    public long getMsElapsed() {
        return msElapsed;
    }

    /**
     * Increases the amount of time elapsed since the start of the game.
     */
    @Override
    public void onTick() {
        msElapsed += Constants.FRAME_DURATION;
    }

    public void reset() {
        msElapsed = 0;
    }
}
