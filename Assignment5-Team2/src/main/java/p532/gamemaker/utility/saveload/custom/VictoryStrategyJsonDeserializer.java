package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.VictoryStrategy;

import java.io.IOException;

public class VictoryStrategyJsonDeserializer extends StdDeserializer<VictoryStrategy> {

	private static final long serialVersionUID = 456169800619069043L;

	public VictoryStrategyJsonDeserializer() {
		super(VictoryStrategy.class);
	}

	@Override
	public VictoryStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return VictoryStrategy.instance;
	}
}