package p532.gamemaker.strategies.collision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReflectStrategy.class, name = "ReflectStrategy"),
        @JsonSubTypes.Type(value = DoNothingStrategy.class, name = "DoNothingStrategy"),
        @JsonSubTypes.Type(value = DestroySelfStrategy.class, name = "DestroySelfStrategy"),
        @JsonSubTypes.Type(value = EndGameStrategy.class, name = "EndGameStrategy"),
        @JsonSubTypes.Type(value = SwitchLevelStrategy.class, name = "SwitchLevelStrategy"),
        @JsonSubTypes.Type(value = VictoryStrategy.class, name = "VictoryStrategy"), }
)
public interface CollisionStrategy
{
    void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType);
}