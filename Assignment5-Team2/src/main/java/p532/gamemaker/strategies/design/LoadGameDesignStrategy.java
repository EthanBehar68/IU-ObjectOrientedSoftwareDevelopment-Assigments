package p532.gamemaker.strategies.design;

import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Level;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.saveload.LoadUtility;
import p532.gamemaker.views.GameDesignScene;

import java.util.List;

public class LoadGameDesignStrategy {
	// Prevent instantiation
	private LoadGameDesignStrategy() {
	}

	public static List<Level> execute() {
		try {
			// Load data from the save file
			List<Level> levelList = LoadUtility.loadEntireGameDesign();

			// If loading successful...
			if (levelList != null && !levelList.isEmpty()) {
				GameDesignScene gameDesignScene = ParentWindow.getGameDesignScene();
				// Add the 1st level to the game play (TEMP)
				Level level = levelList.get(0);

				// Make each sprite editor-ready (i.e. attach drag-and-drop events, etc.)
				for (Sprite sprite : level.getAllSprites()) {
					sprite.setupSpriteForEditor();
				}

				// Display the loaded level
				gameDesignScene.getSpritePresenter().setLevel(level);
			}
			return levelList;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
