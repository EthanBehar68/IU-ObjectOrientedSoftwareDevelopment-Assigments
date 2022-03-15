package p532.gamemaker.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import p532.gamemaker.controllers.EditorButtonsController;
import p532.gamemaker.controllers.GameDesignController;

/**
 * Holds PropertiesPanel, buttons, and the AnchorPane that holds Sprites. I'm
 * not quite sure if it's a view or a controller - Pete.
 */
public class GameDesignScene extends Scene {
	private final PropertiesPanel propertiesPanel;
	private final EditorButtonsController editorButtonsController;
	private final SpritePresenter spritePresenter;
	private final GameDesignController gameDesignController;

	/**
	 * Sets up the Controllers relevant to editing the game design.
	 * 
	 * @param parent                     the root window of the application.
	 * @param initialWidth/initialHeight the size of the window
	 */
	public GameDesignScene(Parent parent, int initialWidth, int initialHeight) {
		super(parent, initialWidth, initialHeight);

		// Set up the controllers relevant to editing the game
		propertiesPanel = new PropertiesPanel(this);
		editorButtonsController = new EditorButtonsController(this);
		gameDesignController = new GameDesignController(this);
		spritePresenter = new SpritePresenter(this);
	}

	public PropertiesPanel getPropertiesPanel() {
		return propertiesPanel;
	}

	public EditorButtonsController getEditorButtonsController() {
		return editorButtonsController;
	}

	public SpritePresenter getSpritePresenter() {
		return spritePresenter;
	}

	public GameDesignController getGameDesignController() {
		return gameDesignController;
	}
}
