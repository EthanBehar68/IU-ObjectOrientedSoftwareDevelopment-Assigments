package p532.gamemaker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.strategies.*;
import p532.gamemaker.strategies.advanced.FireDownStrategy;
import p532.gamemaker.strategies.advanced.FireUpStrategy;
import p532.gamemaker.strategies.advanced.RandomEnemyFireDownStrategy;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.*;
import p532.gamemaker.strategies.movement.*;

public class Constants {

	private Constants() {
	}

	public static final Random RANDOM = new Random();

	public static final long FRAME_DURATION = 33; // 30 FPS
	public static final long MILLISECOND_FACTOR = 1000;
	public static final int CONTROL_SPRITE_SPEED = 20; // how fast a Sprite moves from a key event
	public static final int INITIAL_SCREEN_WIDTH = 1340;
	public static final int INITIAL_SCREEN_HEIGHT = 690;
	public static final int INT_PARSE_FAILURE_CODE = -99;
	public static final int BULLET_SPEED = 2;
	public static final int BULLET_HEIGHT = 10;
	public static final int BULLET_WIDTH = 5;
	public static final int OFF_SCREEN_POSITION = -9999;
	public static final int ONE = 1;
	public static final int ZERO = 0;
	public static final int RANDOM_ENEMY_LOW_CHANCE = ONE;
	public static final int RANDOM_ENEMY_HIGH_CHANCE = 35;
	public static final int DUPLICATE_SPRITE_PADDING = 40;

	public static final String ENEMY_STRING = "Enemy";
	public static final String PLAYER_STRING = "Player";
	public static final String RESET_WALL_STRING = "ResetWall";
	public static final String BULLET_STRING = "Bullet";
	public static final String PLAYER_BULLET_STRING = "Player Bullet";
	public static final String DEFAULT_SAVE_FILE_PATH = "saved-game-design.txt";
	public static final String RIBBON_FXML_PATH = "ribbon-buttons.fxml";
	public static final String COLOR_PROPERTY_FXML_PATH = "color-property-component.fxml";
	public static final String CONDITIONAL_COMPONENT_FXML_PATH = "conditional-component.fxml";
	public static final String KEY_EVENT_FXML_PATH = "key-event-component.fxml";
	public static final String TIME_EVENT_FXML_PATH = "time-event-component.fxml";
	public static final String PROPERTIES_PANEL_FXML_PATH = "properties-panel.fxml";
	public static final String EMPTY_STRING = "";
	public static final String TIME_FORMAT = "h:mm:ss a";

	public static final Color BULLET_COLOR = Color.ORANGE;
	public static final Color SELECTED_STROKE_COLOR = Color.BLACK;
	public static final Color SELECTED_LABEL_COLOR = Color.MAGENTA;
	public static final Color UNSELECTED_STROKE_COLOR = Color.TRANSPARENT;

	public enum PropertyType {
		STANDARD_PROPERTY_COMPONENT, ON_KEY_PRESS_CONDITION_COMPONENT, ON_TIME_CONDITION_COMPONENT,
		ON_HIT_SOMETHING_CONDITION_COMPONENT, ON_GET_HIT_CONDITION_COMPONENT, MOUSE_EVENT_COMPONENT
	}

	public enum CollisionType {
		NO_COLLISION, TOP_IMPACT, BOTTOM_IMPACT, LEFT_IMPACT, RIGHT_IMPACT
	}

	protected static final List<CollisionStrategy> ALL_COLLISION_STRATEGIES = List.of(DoNothingStrategy.instance,
			ReflectStrategy.instance, DestroySelfStrategy.instance, EndGameStrategy.instance, VictoryStrategy.instance,
			SwitchLevelStrategy.instance, KissObjectStrategy.instance, new RandomizeMovementStrategy(), new TeleportToSpawnStrategy(), ReverseYVelocityStrategy.instance);

	protected static final List<GeneralStrategy> ALL_GENERAL_STRATEGIES = List.of(DoNothingStrategy.instance,
			MoveLeftStrategy.instance, MoveRightStrategy.instance, MoveUpStrategy.instance, MoveDownStrategy.instance,
			DestroySelfStrategy.instance, EndGameStrategy.instance, VictoryStrategy.instance,
			MoveVelocityStrategy.instance, ReverseXVelocityStrategy.instance, ReverseYVelocityStrategy.instance,
			SwitchLevelStrategy.instance, FireUpStrategy.instance, FireDownStrategy.instance,
			new RandomEnemyFireDownStrategy(), SwapVelocityToYStrategy.instance, SwapVelocityToXStrategy.instance,
			SetXVelocityPositiveStrategy.instance, SetXVelocityNegativeStrategy.instance, SetYVelocityPositiveStrategy.instance,
			SetYVelocityNegativeStrategy.instance, TurnOnAutomoveStrategy.instance, new TeleportToSpawnStrategy());

	protected static final List<GeneralStrategy> ON_TICK_STRATEGIES = List.of(DoNothingStrategy.instance,
			SetClockTextStrategy.instance, SetStopwatchTextStrategy.instance, MoveVelocityStrategy.instance);

	protected static final List<String> AUDIO_TYPES = new ArrayList<>(Arrays.asList("*.wav", "*.mp3"));

	protected static final List<String> DEFAULT_USER_DEFINED_TYPES = List.of(BULLET_STRING, RESET_WALL_STRING,
			PLAYER_STRING, ENEMY_STRING, PLAYER_BULLET_STRING);

	public static List<String> getAudioTypes() {
		return AUDIO_TYPES;
	}

	public static List<CollisionStrategy> getAllCollisionStrategies() {
		return ALL_COLLISION_STRATEGIES;
	}

	public static List<GeneralStrategy> getAllGeneralStrategies() {
		return ALL_GENERAL_STRATEGIES;
	}

	public static List<GeneralStrategy> getOnTickStrategies() {
		return ON_TICK_STRATEGIES;
	}

	public static List<String> getDefaultUserDefinedTypes() {
		return DEFAULT_USER_DEFINED_TYPES;
	}

	private static final ClassLoader CLASS_LOADER = ParentWindow.class.getClassLoader();
	public static final File ALIEN_DEATH_SOUND = new File(
			CLASS_LOADER.getResource("SpaceInvaders-AlienDeath.wav").getFile());
	public static final File PLAYER_DEATH_SOUND = new File(
			CLASS_LOADER.getResource("SpaceInvaders-PlayerDeath.wav").getFile());
	public static final File PLAYER_SHOOT_SOUND = new File(
			CLASS_LOADER.getResource("SpaceInvaders-PlayerShoot.wav").getFile());

}
