package p532.gamemaker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Level;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.design.*;
import p532.gamemaker.utility.saveload.SaveUtility;
import p532.gamemkaer.game.SetUpGameUI;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds and manages all visible editor buttons for the game maker. This
 * forwards click events to the appropriate utility classes.
 */
public class EditorButtonsController {
	private Parent ribbon; // the view
	private Scene scene;

	/**
	 * In addition to creating a EditorButtonsController, this constructor creates a
	 * ribbon view from ribbon-buttons.fxml.
	 * 
	 * @param scene this should contain a VBox with ID vboxPreview so that the
	 *              buttons ribbon/container can be added there.
	 */
	public EditorButtonsController(Scene scene) {
		this.scene = scene;
		try {
			// 1. Create the ribbon
			ribbon = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.RIBBON_FXML_PATH));
			// 2. Select the properties VBox from the scene
			VBox vboxPreview = (VBox) scene.lookup("#vboxPreview");
			// 3. Add the ribbon to the editor scene
			vboxPreview.getChildren().add(0, ribbon);
		} catch (IOException e) {
		}
	}

	/**
	 * Required constructor for when the XML attaches itself to this
	 */
	public EditorButtonsController() {
	}

	public void onClickPlayGame() {
		BeginGamePlayStrategy.execute(); // this isn't really the strategy pattern, so we can redesign when needed
	}

	public void onClickLoad(ActionEvent actionEvent) {
		LoadGameDesignStrategy.execute();
	}

	public void onClickSave(ActionEvent actionEvent) {
		Level level = ParentWindow.getGameDesignScene().getSpritePresenter().getLevel();
		List<Level> levelList = new LinkedList<>();
		levelList.add(level); // TEMP: we can add a LevelController to give us a list of levels later
		SaveUtility.saveEntireGameDesign(levelList);
	}

	public void onClickNewLevel(ActionEvent actionEvent) {
		SetUpGameUI setupgameui = new SetUpGameUI(scene);
		String levelname = setupgameui.setLevel();
		System.out.println(levelname);
		SetGameLevelStrategy.execute(levelname);
	}

	public void onClickNewGame(ActionEvent actionEvent) {
		SetUpGameUI setupgameui = new SetUpGameUI(scene);
		String gameName = setupgameui.setGameName();
		System.out.println(gameName);
		SetNewGameStrategy.execute(gameName);

	}

	public void onSelectNewLevel(ActionEvent actionEvent) {
		// TODO
	}

	public void onClickNewRectangle(ActionEvent actionEvent) {
		AddNewSpriteStrategy.createRectangle();
	}

	public void onClickNewDisplay(ActionEvent actionEvent) {
		AddNewSpriteStrategy.createLabel();
	}

	public void onClickNewCircle(ActionEvent actionEvent) {
		AddNewSpriteStrategy.createCircle();
	}

	public void onClickDuplicateSprite(ActionEvent actionEvent) {
		Sprite selectedSprite = ParentWindow.getGameDesignScene().getSpritePresenter().getSelectedSprite();
		AddNewSpriteStrategy.duplicateSprite(selectedSprite);
	}

}
