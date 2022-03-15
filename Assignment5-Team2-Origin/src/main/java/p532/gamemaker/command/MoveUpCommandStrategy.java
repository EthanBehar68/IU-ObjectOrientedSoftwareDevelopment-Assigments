package p532.gamemaker.command;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;

public class MoveUpCommandStrategy implements Command{

private Sprite sprite;
	
	public MoveUpCommandStrategy(Sprite sprite) {
		this.sprite = sprite;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		//move sprite up
		sprite.setVelocityY(-Constants.CONTROL_SPRITE_SPEED);
	}

}
