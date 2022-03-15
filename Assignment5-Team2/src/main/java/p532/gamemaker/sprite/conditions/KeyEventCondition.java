package p532.gamemaker.sprite.conditions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javafx.scene.input.KeyCode;
import p532.gamemaker.sprite.Sprite;

@JsonInclude(Include.NON_NULL)
public class KeyEventCondition extends EventCondition {
	
	private KeyCode keyCode = KeyCode.SPACE;

	public KeyCode getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public boolean attemptBehavior(Sprite thisSprite, KeyCode keyCode) {
		// If the input keyCode matches the condition's keyCode, perform the behavior
		if (this.keyCode.equals(keyCode)) {
			// Perform the collision
			strategy.execute(thisSprite, soundFxFile);
			return true;
		}
		return false;
	}
}
