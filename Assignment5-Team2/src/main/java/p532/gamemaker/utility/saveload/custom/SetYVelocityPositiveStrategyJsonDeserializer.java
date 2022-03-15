package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.movement.SetYVelocityPositiveStrategy;

import java.io.IOException;

public class SetYVelocityPositiveStrategyJsonDeserializer extends StdDeserializer<SetYVelocityPositiveStrategy> {

	private static final long serialVersionUID = 7027582530981707765L;

	public SetYVelocityPositiveStrategyJsonDeserializer() {
		super(SetYVelocityPositiveStrategy.class);
	}

	@Override
	public SetYVelocityPositiveStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return SetYVelocityPositiveStrategy.instance;
	}
}