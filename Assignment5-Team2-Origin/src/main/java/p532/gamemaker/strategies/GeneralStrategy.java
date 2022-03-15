package p532.gamemaker.strategies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.strategies.display.SetStopwatchTextStrategy;
import p532.gamemaker.strategies.movement.MoveDownStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;
import p532.gamemaker.strategies.movement.MoveRightStrategy;
import p532.gamemaker.strategies.movement.MoveUpStrategy;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReflectStrategy.class, name = "ReflectStrategy"),
        @JsonSubTypes.Type(value = DoNothingStrategy.class, name = "DoNothingStrategy"),
        @JsonSubTypes.Type(value = DestroySelfStrategy.class, name = "DestroySelfStrategy"),
        @JsonSubTypes.Type(value = EndGameStrategy.class, name = "EndGameStrategy"),
        @JsonSubTypes.Type(value = SwitchLevelStrategy.class, name = "SwitchLevelStrategy"),
        @JsonSubTypes.Type(value = VictoryStrategy.class, name = "VictoryStrategy"),

        @JsonSubTypes.Type(value = MoveLeftStrategy.class, name = "MoveLeftStrategy"),
        @JsonSubTypes.Type(value = MoveRightStrategy.class, name = "MoveRightStrategy"),
        @JsonSubTypes.Type(value = MoveUpStrategy.class, name = "MoveUpStrategy"),
        @JsonSubTypes.Type(value = MoveDownStrategy.class, name = "MoveDownStrategy"),

        @JsonSubTypes.Type(value = SetClockTextStrategy.class, name = "SetClockTextStrategy"),
        @JsonSubTypes.Type(value = SetStopwatchTextStrategy.class, name = "SetStopwatchTextStrategy")
})
public interface GeneralStrategy
{
    void execute(Sprite target);
}
