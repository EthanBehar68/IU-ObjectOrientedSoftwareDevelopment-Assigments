package p532.gamemaker.strategies.design;

import p532.gamemaker.controllers.GameDesignController;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemkaer.game.*;

public class SetNewGameStrategy {

	public static void execute(String gameName) {
		GameDesignController gameObjectsController = ParentWindow.getGameDesignScene().getGameDesignController();
		Game game = new Game(gameName);
		gameObjectsController.addGameObjects(game);
	}

}
