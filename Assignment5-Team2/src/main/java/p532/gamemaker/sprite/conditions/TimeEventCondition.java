package p532.gamemaker.sprite.conditions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import p532.gamemaker.sprite.Sprite;

@JsonInclude(Include.NON_NULL)
public class TimeEventCondition extends EventCondition {

	private int interval = 0;

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void attemptBehavior(Sprite thisSprite, double totalTime) {
		strategy.execute(thisSprite, soundFxFile);
	}
}
