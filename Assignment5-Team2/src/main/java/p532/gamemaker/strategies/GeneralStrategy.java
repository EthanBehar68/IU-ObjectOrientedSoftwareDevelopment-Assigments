package p532.gamemaker.strategies;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.advanced.FireDownStrategy;
import p532.gamemaker.strategies.advanced.FireUpStrategy;
import p532.gamemaker.strategies.advanced.RandomEnemyFireDownStrategy;
import p532.gamemaker.strategies.advanced.ReloadStrategy;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.*; 
import p532.gamemaker.strategies.movement.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = ReflectStrategy.class, name = "ReflectStrategy"),
		@JsonSubTypes.Type(value = DoNothingStrategy.class, name = "DoNothingStrategy"),
		@JsonSubTypes.Type(value = DestroySelfStrategy.class, name = "DestroySelfStrategy"),
		@JsonSubTypes.Type(value = EndGameStrategy.class, name = "EndGameStrategy"),
		@JsonSubTypes.Type(value = SwitchLevelStrategy.class, name = "SwitchLevelStrategy"),
		@JsonSubTypes.Type(value = VictoryStrategy.class, name = "VictoryStrategy"),

		@JsonSubTypes.Type(value = MoveLeftStrategy.class, name = "MoveLeftStrategy"),
		@JsonSubTypes.Type(value = MoveRightStrategy.class, name = "MoveRightStrategy"),
		@JsonSubTypes.Type(value = MoveUpStrategy.class, name = "MoveUpStrategy"),
		@JsonSubTypes.Type(value = MoveDownStrategy.class, name = "MoveDownStrategy"),

		@JsonSubTypes.Type(value = MoveVelocityStrategy.class, name = "MoveVelocityStrategy"),
		@JsonSubTypes.Type(value = ReverseXVelocityStrategy.class, name = "ReverseXVelocityStrategy"),
		@JsonSubTypes.Type(value = ReverseYVelocityStrategy.class, name = "ReverseYVelocityStrategy"),
		@JsonSubTypes.Type(value = SwapVelocityToYStrategy.class, name = "SwapVelocityToYStrategy"),
		@JsonSubTypes.Type(value = SwapVelocityToXStrategy.class, name = "SwapVelocityToXStrategy"),
		@JsonSubTypes.Type(value = SetXVelocityPositiveStrategy.class, name = "SetXVelocityPositiveStrategy"),
		@JsonSubTypes.Type(value = SetXVelocityNegativeStrategy.class, name = "SetXVelocityNegativeStrategy"),
		@JsonSubTypes.Type(value = SetYVelocityPositiveStrategy.class, name = "SetYVelocityPositiveStrategy"),
		@JsonSubTypes.Type(value = SetYVelocityNegativeStrategy.class, name = "SetYVelocityNegativeStrategy"),
		@JsonSubTypes.Type(value = TurnOnAutomoveStrategy.class, name = "TurnOnAutomoveStrategy"),
		@JsonSubTypes.Type(value = TeleportToSpawnStrategy.class, name = "TeleportToSpawnStrategy"),

		@JsonSubTypes.Type(value = SetClockTextStrategy.class, name = "SetClockTextStrategy"),
		@JsonSubTypes.Type(value = SetStopwatchTextStrategy.class, name = "SetStopwatchTextStrategy"),

		@JsonSubTypes.Type(value = FireUpStrategy.class, name = "FireUpStrategy"),
		@JsonSubTypes.Type(value = FireDownStrategy.class, name = "FireDownStrategy"),
		@JsonSubTypes.Type(value = RandomEnemyFireDownStrategy.class, name = "RandomEnemyFireDownStrategy"),
		@JsonSubTypes.Type(value = ReloadStrategy.class, name = "ReloadStrategy") })
public interface GeneralStrategy {
	void execute(Sprite target, File soundFx);
}
