package p532.gamemaker.command;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;

public class MoveLeftCommandStrategy implements Command {

	private Sprite sprite;
	
	public MoveLeftCommandStrategy(Sprite sprite) {
		this.sprite = sprite;
	}
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		//move sprite left
		sprite.setVelocityX(-Constants.CONTROL_SPRITE_SPEED);
	}

}
