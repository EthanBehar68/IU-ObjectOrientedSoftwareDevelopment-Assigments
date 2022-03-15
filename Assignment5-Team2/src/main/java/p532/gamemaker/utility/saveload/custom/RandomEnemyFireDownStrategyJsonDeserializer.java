package p532.gamemaker.utility.saveload.custom;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import p532.gamemaker.strategies.advanced.RandomEnemyFireDownStrategy;

public class RandomEnemyFireDownStrategyJsonDeserializer extends StdDeserializer<RandomEnemyFireDownStrategy>  {
	
	private static final long serialVersionUID = -3876176423479352276L;

	public RandomEnemyFireDownStrategyJsonDeserializer() {
		super(RandomEnemyFireDownStrategy.class);
	}

	@Override
	public RandomEnemyFireDownStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return new RandomEnemyFireDownStrategy();
	}

}

