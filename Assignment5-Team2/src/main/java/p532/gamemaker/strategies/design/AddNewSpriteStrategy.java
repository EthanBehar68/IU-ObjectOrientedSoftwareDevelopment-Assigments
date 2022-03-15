package p532.gamemaker.strategies.design;

import java.util.ArrayList;
import java.util.Iterator;

import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.views.SpritePresenter;
import p532.gamemaker.sprite.*;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.sprite.conditions.TimeEventCondition;
import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.advanced.FireDownStrategy;
import p532.gamemaker.strategies.advanced.FireUpStrategy;
import p532.gamemaker.strategies.advanced.RandomEnemyFireDownStrategy;
// We use all of them so wildcard is acceptable.
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.movement.*;

public class AddNewSpriteStrategy {
	private AddNewSpriteStrategy() {
	}

	private static void setupSprite(Sprite defaultSprite) {
		SpritePresenter spriteController = ParentWindow.getGameDesignScene().getSpritePresenter();

		// Add the default Sprite to the SpritePresenter
		spriteController.addSprite(defaultSprite);

		// Add the sprite to the level
		Level level = spriteController.getLevel();
		level.addSprite(defaultSprite);
	}

	public static void setupSpriteInGamePlay(Sprite defaultSprite) {
		SpritePresenter spriteController = ParentWindow.getGamePlayScene().getSpritePresenter();

		// Add the default Sprite to the SpritePresenter
		spriteController.addSprite(defaultSprite);

		// Add the sprite to the level
		Level level = spriteController.getLevel();
		level.addSprite(defaultSprite);
	}

	public static Sprite createRectangle() {
		// Create the Sprite
		Sprite defaultSprite = GameRectangle.defaultSprite();

		// Set it up for use in the editor
		setupSprite(defaultSprite);
		return defaultSprite;
	}

	public static Sprite createCircle() {
		// Create the Sprite
		Sprite defaultSprite = GameCircle.defaultSprite();

		// Set it up for use in the editor
		setupSprite(defaultSprite);
		return defaultSprite;
	}

	public static Sprite createLabel() {
		// Create the Sprite
		Sprite defaultSprite = GameLabel.defaultSprite();

		// Set it up for use in the editor
		setupSprite(defaultSprite);
		return defaultSprite;
	}

	public static void duplicateSprite(Sprite originalSprite) {
		if (originalSprite == null) {
			return;
		}

		// Create the Sprite
		Sprite duplicateSprite = newDuplicateSpriteFrom(originalSprite);

		// Set it up for use in the editor
		setupSprite(duplicateSprite);
	}

	public static Sprite newDuplicateSpriteFrom(Sprite copySprite) {
		// Make a new sprite
		Sprite newSprite = createNewSpriteFromCopy(copySprite);
		// Set it up for use in the editor
		newSprite.setupSpriteForEditor();

		// Basic sprite info
		copySpriteViewInfo(copySprite, newSprite);
		copySpriteInfo(copySprite, newSprite);

		// Event conditions
		copyKeyEvents(copySprite, newSprite);
		copyTimeEvents(copySprite, newSprite);
		copyGetHitEvents(copySprite, newSprite);
		copyHitSomethingEvents(copySprite, newSprite);

		return newSprite;
	}

	public static Sprite createNewSpriteFromCopy(Sprite copySprite) {
		Sprite newSprite = new Sprite();

		String copyClass = copySprite.getView().getClass().toString();

		if (copyClass.equals("class p532.gamemaker.sprite.GameCircle")) {
			newSprite.setView(new GameCircle());
		} else if (copyClass.equals("class p532.gamemaker.sprite.GameRectangle")) {
			newSprite.setView(new GameRectangle());
		} else {
			newSprite.setView(new GameLabel());
		}

		return newSprite;
	}

	public static void copySpriteViewInfo(Sprite copySprite, Sprite newSprite) {
		// Doesnt matter is Rect/Circle/Label setWidth/Height will know what to do
		newSprite.getView().setSpriteWidth(copySprite.getView().getSpriteWidth());
		newSprite.getView().setSpriteHeight(copySprite.getView().getSpriteHeight());
		newSprite.getView().setLayoutX(copySprite.getView().getLayoutX() + Constants.DUPLICATE_SPRITE_PADDING);
		newSprite.getView().setLayoutY(copySprite.getView().getLayoutY());
		newSprite.getView().setFill(copySprite.getView().getFill());
	}

	public static void copySpriteInfo(Sprite copySprite, Sprite newSprite) {
		newSprite.setUserDefinedType(copySprite.getUserDefinedType());
		newSprite.setUserDefinedName(copySprite.getUserDefinedName());
		newSprite.setVelocityX(copySprite.getVelocityX());
		newSprite.setVelocityY(copySprite.getVelocityY());
		newSprite.setDestroyed(copySprite.isDestroyed());
		newSprite.setUseAutomove(newSprite.getUseAutomove());
	}

	public static void copyKeyEvents(Sprite copySprite, Sprite newSprite) {
		ArrayList<KeyEventCondition> newConditions = new ArrayList<>();

		Iterator<KeyEventCondition> eventIterator = copySprite.getOnKeyPressConditionList().iterator();

		while (eventIterator.hasNext()) {
			KeyEventCondition copyCondition = eventIterator.next();

			KeyEventCondition newCondition = new KeyEventCondition();
			newCondition.setKeyCode(copyCondition.getKeyCode());
			newCondition.setSoundFxFile(copyCondition.getSoundFxFile(), false);

			GeneralStrategy newStrategy = getCopyOfGeneralStrategy(copyCondition.getStrategy());
			newCondition.setStrategy(newStrategy);
			newConditions.add(newCondition);
		}
		newSprite.setOnKeyPressConditionList(newConditions);
	}

	public static void copyTimeEvents(Sprite copySprite, Sprite newSprite) {
		ArrayList<TimeEventCondition> newConditions = new ArrayList<>();

		Iterator<TimeEventCondition> eventIterator = copySprite.getOnTimeConditionList().iterator();

		while (eventIterator.hasNext()) {
			TimeEventCondition copyCondition = eventIterator.next();

			TimeEventCondition newCondition = new TimeEventCondition();
			newCondition.setInterval(copyCondition.getInterval());
			newCondition.setSoundFxFile(copyCondition.getSoundFxFile(), false);

			GeneralStrategy newStrategy = getCopyOfGeneralStrategy(copyCondition.getStrategy());
			newCondition.setStrategy(newStrategy);
			newConditions.add(newCondition);
		}
		newSprite.setOnTimeConditionList(newConditions);
	}

	public static void copyGetHitEvents(Sprite copySprite, Sprite newSprite) {
		ArrayList<CollisionCondition> newConditions = new ArrayList<>();

		Iterator<CollisionCondition> eventIterator = copySprite.getOnGetHitConditionList().iterator();

		while (eventIterator.hasNext()) {
			CollisionCondition copyCondition = eventIterator.next();

			CollisionCondition newCondition = new CollisionCondition();
			newCondition.setOtherSpriteType(copyCondition.getOtherSpriteType());
			newCondition.setSoundFxFile(copyCondition.getSoundFxFile());

			CollisionStrategy newStrategy = getCopyOfCollisionStrategy(copyCondition.getStrategy());
			newCondition.setStrategy(newStrategy);
			newConditions.add(newCondition);
		}
		newSprite.setOnGetHitConditionList(newConditions);
	}

	public static void copyHitSomethingEvents(Sprite copySprite, Sprite newSprite) {
		ArrayList<CollisionCondition> newConditions = new ArrayList<>();

		Iterator<CollisionCondition> eventIterator = copySprite.getOnHitSomethingConditionList().iterator();

		while (eventIterator.hasNext()) {
			CollisionCondition copyCondition = eventIterator.next();

			CollisionCondition newCondition = new CollisionCondition();
			newCondition.setOtherSpriteType(copyCondition.getOtherSpriteType());
			newCondition.setSoundFxFile(copyCondition.getSoundFxFile());

			CollisionStrategy newStrategy = getCopyOfCollisionStrategy(copyCondition.getStrategy());
			newCondition.setStrategy(newStrategy);
			newConditions.add(newCondition);
		}
		newSprite.setOnHitSomethingConditionList(newConditions);
	}

	// We only need strategies that can be added to a sprite.
	// Because the UI doesn't support parameters this is fairly simple.
	// Once parameters are incorporated this will become more complicated.
	public static GeneralStrategy getCopyOfGeneralStrategy(GeneralStrategy copyStrategy) {
		String copyClass = copyStrategy.getClass().toString().toLowerCase();

		switch (copyClass) {
		case "class p532.gamemaker.strategies.advanced.firedownstrategy":
			return FireDownStrategy.instance;
		case "class p532.gamemaker.strategies.advanced.fireupstrategy":
			return FireUpStrategy.instance;
		case "class p532.gamemaker.strategies.advanced.randomenemyfiredownstrategy":
			return new RandomEnemyFireDownStrategy();
		case "class p532.gamemaker.strategies.movement.movedownstrategy":
			return MoveDownStrategy.instance;
		case "class p532.gamemaker.strategies.movement.moveupstrategy":
			return MoveUpStrategy.instance;
		case "class p532.gamemaker.strategies.movement.moveleftstrategy":
			return MoveLeftStrategy.instance;
		case "class p532.gamemaker.strategies.movement.moverightstrategy":
			return MoveRightStrategy.instance;
		case "class p532.gamemaker.strategies.movement.movevelocitystrategy":
			return MoveVelocityStrategy.instance;
		case "class p532.gamemaker.strategies.movement.reversexvelocitystrategy":
			return ReverseXVelocityStrategy.instance;
		case "class p532.gamemaker.strategies.movement.reverseyvelocitystrategy":
			return ReverseYVelocityStrategy.instance;
		case "class p532.gamemaker.strategies.movement.setxvelocitynegativestrategy":
			return SetXVelocityNegativeStrategy.instance;
		case "class p532.gamemaker.strategies.movement.setxvelocitypositivestrategy":
			return SetXVelocityPositiveStrategy.instance;
		case "class p532.gamemaker.strategies.movement.setyvelocitynegativestrategy":
			return SetYVelocityNegativeStrategy.instance;
		case "class p532.gamemaker.strategies.movement.setyvelocitypositivestrategy":
			return SetYVelocityPositiveStrategy.instance;
		case "class p532.gamemaker.strategies.movement.swapvelocitytoxstrategy":
			return SwapVelocityToXStrategy.instance;
		case "class p532.gamemaker.strategies.movement.swapvelocitytoystrategy":
			return SwapVelocityToYStrategy.instance;
		case "class p532.gamemaker.strategies.movement.turnonautomovestrategy":
			return TurnOnAutomoveStrategy.instance;
		case "class p532.gamemaker.strategies.movement.teleporttospawnstrategy":
			return new TeleportToSpawnStrategy();
		default:
			throw new UnsupportedOperationException("Cannot copy " + copyClass + ". Is it accounted for?");
		}
	}

	public static CollisionStrategy getCopyOfCollisionStrategy(CollisionStrategy copyStrategy) {
		String copyClass = copyStrategy.getClass().toString().toLowerCase();

		switch (copyClass) {
		case "class p532.gamemaker.strategies.collision.destroyselfstrategy":
			return DestroySelfStrategy.instance;
		case "class p532.gamemaker.strategies.collision.endgamestrategy":
			return EndGameStrategy.instance;
		case "class p532.gamemaker.strategies.collision.reflectstrategy":
			return ReflectStrategy.instance;
		case "class p532.gamemaker.strategies.collision.switchlevelstrategy":
			return SwitchLevelStrategy.instance;
		case "class p532.gamemaker.strategies.collision.victorystrategy":
			return VictoryStrategy.instance;
		case "class p532.gamemaker.strategies.collision.kissobjectstrategy":
			return KissObjectStrategy.instance;
		case "class p532.gamemaker.strategies.collision.randomizemovementstrategy":
			return new RandomizeMovementStrategy();
		case "class p532.gamemaker.strategies.movement.teleporttospawnstrategy":
			return new TeleportToSpawnStrategy();
		case "class p532.gamemaker.strategies.movement.reverseyvelocitystrategy":
			return ReverseYVelocityStrategy.instance;
		default:
			throw new UnsupportedOperationException("Cannot copy " + copyClass + ". Is it accounted for?");
		}
	}
}
