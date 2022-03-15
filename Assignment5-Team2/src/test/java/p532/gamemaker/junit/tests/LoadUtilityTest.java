package p532.gamemaker.junit.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import p532.gamemaker.Constants.CollisionType;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.Level;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.design.AddNewSpriteStrategy;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.utility.saveload.LoadUtility;
import p532.gamemaker.utility.saveload.SaveUtility;

public class LoadUtilityTest {

	@Test
	public void test() throws ClassNotFoundException {
		ParentWindow.main(null);
		AddNewSpriteStrategy.createCircle();
		AddNewSpriteStrategy.createLabel();
		AddNewSpriteStrategy.createRectangle();
		
		Level levelDesign = ParentWindow.getGameDesignScene().getSpritePresenter().getLevel();
		ArrayList<Level> levelList = new ArrayList<>();
		levelList.add(levelDesign);
		SaveUtility.saveEntireGameDesign(levelList);
		String gameJson = SaveUtility.serializeGameToJson(levelList);
		List<Level> loadedGame = LoadUtility.deserializeGameFromJson(gameJson);
		assert(loadedGame.toString().equals(levelList.toString()));
		
		List<Level> loadedGam = LoadUtility.loadEntireGameDesign();
		assert(loadedGam.toString().equals(levelList.toString()));
		
		
		Sprite label = GameLabel.defaultSprite();
		
		SetClockTextStrategy.instance.doCollisionBehavior(label, label, CollisionType.NO_COLLISION, null);
	}

}
