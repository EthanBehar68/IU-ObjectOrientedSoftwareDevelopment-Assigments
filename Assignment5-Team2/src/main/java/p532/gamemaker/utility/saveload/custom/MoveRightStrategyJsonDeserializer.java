package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.movement.MoveRightStrategy;

import java.io.IOException;

public class MoveRightStrategyJsonDeserializer extends StdDeserializer<MoveRightStrategy> {

	private static final long serialVersionUID = 8156668360894846400L;

	public MoveRightStrategyJsonDeserializer() {
		super(MoveRightStrategy.class);
	}

	@Override
	public MoveRightStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return MoveRightStrategy.instance;
	}
}