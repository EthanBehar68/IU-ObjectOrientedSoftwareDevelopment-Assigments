package p532.gamemaker.command;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;

public class MoveDownCommmandStrategy implements Command {

private Sprite sprite;
	
	public MoveDownCommmandStrategy(Sprite sprite) {
		this.sprite = sprite;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		sprite.setVelocityY(Constants.CONTROL_SPRITE_SPEED);
	}

}
