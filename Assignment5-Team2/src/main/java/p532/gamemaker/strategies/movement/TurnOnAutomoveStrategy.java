/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 16, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;

public class TurnOnAutomoveStrategy implements GeneralStrategy {

	public static final TurnOnAutomoveStrategy instance = new TurnOnAutomoveStrategy();

	private TurnOnAutomoveStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		target.setUseAutomove(true);
	}

	@Override
	public String toString() {
		return "Turn Automove On";
	}
}