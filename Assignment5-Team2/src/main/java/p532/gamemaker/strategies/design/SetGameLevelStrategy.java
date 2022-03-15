package p532.gamemaker.strategies.design;

import p532.gamemaker.controllers.GameDesignController;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemkaer.game.Game;
import p532.gamemkaer.game.GameLevel;

public class SetGameLevelStrategy {
	public static void execute(String levelname) {
		GameDesignController gameDesignController = ParentWindow.getGameDesignScene().getGameDesignController();

		Game currentGame = gameDesignController.getCurrentGame();

		GameLevel level = new GameLevel(levelname);
		currentGame.addLevel(level);
	}

}
