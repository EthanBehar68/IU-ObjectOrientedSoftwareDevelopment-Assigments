package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.sound.SoundEngine;

public class SoundEngineTest {

	@Test
	public void testSetVolumne() {
		SoundEngine.getInstance().setVolumne(10);
		assert(SoundEngine.getInstance().getVolumne() == 10);
	}

}
