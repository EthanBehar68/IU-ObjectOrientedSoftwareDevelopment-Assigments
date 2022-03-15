package p532.gamemaker.strategies;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.advanced.ReloadStrategy;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.movement.ReverseYVelocityStrategy;
import p532.gamemaker.strategies.movement.TeleportToSpawnStrategy;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = ReloadStrategy.class, name = "ReloadStrategy"),
		@JsonSubTypes.Type(value = ReflectStrategy.class, name = "ReflectStrategy"),
		@JsonSubTypes.Type(value = KissObjectStrategy.class, name = "KissObjectStrategy"),
		@JsonSubTypes.Type(value = RandomizeMovementStrategy.class, name = "RandomizeMovementStrategy"),
		@JsonSubTypes.Type(value = TeleportToSpawnStrategy.class, name = "TeleportToSpawnStrategy"),
		@JsonSubTypes.Type(value = ReverseYVelocityStrategy.class, name = "ReverseYVelocityStrategy"),
		@JsonSubTypes.Type(value = DoNothingStrategy.class, name = "DoNothingStrategy"),
		@JsonSubTypes.Type(value = DestroySelfStrategy.class, name = "DestroySelfStrategy"),
		@JsonSubTypes.Type(value = EndGameStrategy.class, name = "EndGameStrategy"),
		@JsonSubTypes.Type(value = SwitchLevelStrategy.class, name = "SwitchLevelStrategy"),
		@JsonSubTypes.Type(value = VictoryStrategy.class, name = "VictoryStrategy"), })
public interface CollisionStrategy {
	void doCollisionBehavior(Sprite colliderOrImpactee, Sprite otherSprite, Constants.CollisionType collisionType, File soundFx);
	void setup(Sprite sprite);
}