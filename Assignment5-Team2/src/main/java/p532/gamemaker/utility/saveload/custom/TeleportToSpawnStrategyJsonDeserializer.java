package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import p532.gamemaker.strategies.movement.TeleportToSpawnStrategy;

import java.io.IOException;

public class TeleportToSpawnStrategyJsonDeserializer extends StdDeserializer<TeleportToSpawnStrategy> {

	private static final long serialVersionUID = -2668961276837195251L;

	public TeleportToSpawnStrategyJsonDeserializer() {
		super(TeleportToSpawnStrategy.class);
	}

	@Override
	public TeleportToSpawnStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return new TeleportToSpawnStrategy();
	}
}