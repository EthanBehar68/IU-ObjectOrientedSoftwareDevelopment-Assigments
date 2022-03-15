package p532.gamemaker.junit.tests;
import org.junit.Test;

import p532.gamemaker.playthegame.Stopwatch;

public class StopWatchTest {

	double resetTime = 0.0;
	@Test
	public void testGetMsElapsed() {
		assert(Stopwatch.instance.getMsElapsed() == resetTime);
	}

	@Test
	public void testOnTick() {
		double totalTime = 10.0;
		long longTime = 10;
		Stopwatch.instance.onTick(longTime,totalTime);
		assert(Stopwatch.instance.getMsElapsed() == totalTime);
	}

	@Test
	public void testReset() {
		Stopwatch.instance.reset();
		assert(Stopwatch.instance.getMsElapsed() == resetTime);
	}

}
