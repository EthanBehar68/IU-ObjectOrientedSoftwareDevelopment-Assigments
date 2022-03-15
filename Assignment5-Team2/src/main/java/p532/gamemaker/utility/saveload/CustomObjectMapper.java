package p532.gamemaker.utility.saveload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.scene.paint.Color;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.SpriteView;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.advanced.*;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.*;
import p532.gamemaker.strategies.movement.*;
import p532.gamemaker.utility.saveload.custom.*;

public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -9138862939250304355L;

	public CustomObjectMapper() {
		super();

		// Customize the mapper to allow custom de/serialize
		SimpleModule module = new SimpleModule("JavaFX Module");

		// Colors
		module.addSerializer(Color.class, new ColorJsonSerializer(Color.class));
		module.addDeserializer(Color.class, new ColorJsonDeserializer(Color.class));

		// SpriteViews
		module.addSerializer(SpriteView.class, new SpriteViewJsonSerializer(SpriteView.class));
		module.addDeserializer(GameCircle.class, new GameCircleJsonDeserializer());
		module.addDeserializer(GameRectangle.class, new GameRectangleJsonDeserializer());
		module.addDeserializer(GameLabel.class, new GameLabelJsonDeserializer());

		// Strategies
		module.addDeserializer(DoNothingStrategy.class, new DoNothingCollisionStrategyJsonDeserializer());
		module.addDeserializer(ReflectStrategy.class, new ReflectStrategyJsonDeserializer());
		module.addDeserializer(KissObjectStrategy.class, new KissObjectStrategyJsonDeserializer());
		module.addDeserializer(RandomizeMovementStrategy.class, new RandomizeMovementStrategyJsonDeserializer());

		module.addDeserializer(EndGameStrategy.class, new EndGameStrategyJsonDeserializer());
		module.addDeserializer(DestroySelfStrategy.class, new DestroySelfStrategyJsonDeserializer());
		module.addDeserializer(SwitchLevelStrategy.class, new SwitchLevelStrategyJsonDeserializer());
		module.addDeserializer(VictoryStrategy.class, new VictoryStrategyJsonDeserializer());
		module.addDeserializer(SetClockTextStrategy.class, new SetClockTextStrategyJsonDeserializer());
		module.addDeserializer(SetStopwatchTextStrategy.class, new SetStopwatchTextStrategyJsonDeserializer());

		// Advanced
		module.addDeserializer(FireUpStrategy.class, new FireUpStrategyJsonDeserializer());
		module.addDeserializer(FireDownStrategy.class, new FireDownStrategyJsonDeserializer());
		module.addDeserializer(RandomEnemyFireDownStrategy.class, new RandomEnemyFireDownStrategyJsonDeserializer());
		module.addDeserializer(ReloadStrategy.class, new ReloadStrategyJsonDeserializer());

		// Move Strategies
		module.addDeserializer(MoveLeftStrategy.class, new MoveLeftStrategyJsonDeserializer());
		module.addDeserializer(MoveRightStrategy.class, new MoveRightStrategyJsonDeserializer());
		module.addDeserializer(MoveUpStrategy.class, new MoveUpStrategyJsonDeserializer());
		module.addDeserializer(MoveDownStrategy.class, new MoveDownStrategyJsonDeserializer());

		module.addDeserializer(MoveVelocityStrategy.class, new MoveVelocityStrategyJsonDeserializer());
		
		module.addDeserializer(ReverseXVelocityStrategy.class, new ReverseXVelocityStrategyJsonDeserializer());
		module.addDeserializer(ReverseYVelocityStrategy.class, new ReverseYVelocityStrategyJsonDeserializer());
		
		module.addDeserializer(SwapVelocityToYStrategy.class, new SwapVelocityToYStrategyJsonDeserializer());
		module.addDeserializer(SwapVelocityToXStrategy.class, new SwapVelocityToXStrategyJsonDeserializer());
		
		module.addDeserializer(SetYVelocityPositiveStrategy.class, new SetYVelocityPositiveStrategyJsonDeserializer());
		module.addDeserializer(SetYVelocityNegativeStrategy.class, new SetYVelocityNegativeStrategyJsonDeserializer());
		module.addDeserializer(SetXVelocityPositiveStrategy.class, new SetXVelocityPositiveStrategyJsonDeserializer());
		module.addDeserializer(SetXVelocityNegativeStrategy.class, new SetXVelocityNegativeStrategyJsonDeserializer());

		module.addDeserializer(TurnOnAutomoveStrategy.class, new TurnOnAutomoveStrategyJsonDeserializer());
		module.addDeserializer(TeleportToSpawnStrategy.class, new TeleportToSpawnStrategyJsonDeserializer());

		
		this.registerModule(module);
	}
}
