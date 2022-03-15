package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.DestroySelfStrategy;

import java.io.IOException;

public class DestroySelfStrategyJsonDeserializer extends StdDeserializer<DestroySelfStrategy> {

	private static final long serialVersionUID = -441596646611252003L;

	public DestroySelfStrategyJsonDeserializer() {
		super(DestroySelfStrategy.class);
	}

	@Override
	public DestroySelfStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return DestroySelfStrategy.instance;
	}
}