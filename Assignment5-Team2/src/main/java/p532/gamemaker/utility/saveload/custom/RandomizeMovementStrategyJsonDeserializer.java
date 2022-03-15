package p532.gamemaker.utility.saveload.custom;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import p532.gamemaker.strategies.collision.RandomizeMovementStrategy;


public class RandomizeMovementStrategyJsonDeserializer extends StdDeserializer<RandomizeMovementStrategy>  {

	private static final long serialVersionUID = -1713910524536136224L;

	public RandomizeMovementStrategyJsonDeserializer() {
		super(RandomizeMovementStrategy.class);
	}

	@Override
	public RandomizeMovementStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return new RandomizeMovementStrategy();
	}
}

