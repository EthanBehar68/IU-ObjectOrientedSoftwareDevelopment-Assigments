/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 15, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.strategies.movement;

import java.io.File;

import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;

public class SetYVelocityPositiveStrategy implements GeneralStrategy {
	/**
	 * A static strategy that can be used instead of the constructor.
	 */
	public static final SetYVelocityPositiveStrategy instance = new SetYVelocityPositiveStrategy();

	private SetYVelocityPositiveStrategy() {
	}

	@Override
	public void execute(Sprite target, File soundFx) {
		if (target.getVelocityY() > 0) {
			MoveStrategyHelper.getInstance().reverseYVelocity(target);
		} else {
			return;
		}
	}

	@Override
	public String toString() {
		return "Set Y Velocity +";
	}
}