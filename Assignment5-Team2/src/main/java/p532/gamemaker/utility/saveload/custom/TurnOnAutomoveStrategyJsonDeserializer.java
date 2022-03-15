package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import p532.gamemaker.strategies.movement.TurnOnAutomoveStrategy;

import java.io.IOException;

public class TurnOnAutomoveStrategyJsonDeserializer extends StdDeserializer<TurnOnAutomoveStrategy> {

	private static final long serialVersionUID = -2336450914306968211L;

	public TurnOnAutomoveStrategyJsonDeserializer() {
		super(TurnOnAutomoveStrategy.class);
	}

	@Override
	public TurnOnAutomoveStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return TurnOnAutomoveStrategy.instance;
	}
}