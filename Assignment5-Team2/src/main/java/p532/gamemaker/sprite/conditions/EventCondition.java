/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 14, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.sprite.conditions;

import java.io.File;

import p532.gamemaker.sound.SoundEngine;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.GeneralStrategy;

public class EventCondition {

	protected GeneralStrategy strategy = DoNothingStrategy.instance;
	protected File soundFxFile;

	public File getSoundFxFile() {
		return soundFxFile;
	}

	public void setSoundFxFile(File soundFxFile, boolean testSound) {
		this.soundFxFile = soundFxFile;
		if (testSound && this.soundFxFile != null) {
			SoundEngine.getInstance().playAudio(this.soundFxFile);
		}
	}

	public GeneralStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(GeneralStrategy strategy) {
		this.strategy = strategy;
	}
}
