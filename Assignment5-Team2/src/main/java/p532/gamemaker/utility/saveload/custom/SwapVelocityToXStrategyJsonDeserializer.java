package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.movement.SwapVelocityToXStrategy;

import java.io.IOException;

public class SwapVelocityToXStrategyJsonDeserializer extends StdDeserializer<SwapVelocityToXStrategy> {

	private static final long serialVersionUID = 7027582530981707765L;

	public SwapVelocityToXStrategyJsonDeserializer() {
		super(SwapVelocityToXStrategy.class);
	}

	@Override
	public SwapVelocityToXStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return SwapVelocityToXStrategy.instance;
	}
}