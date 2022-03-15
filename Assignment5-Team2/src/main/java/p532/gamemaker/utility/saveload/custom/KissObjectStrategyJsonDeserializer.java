package p532.gamemaker.utility.saveload.custom;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import p532.gamemaker.strategies.collision.KissObjectStrategy;

public class KissObjectStrategyJsonDeserializer extends StdDeserializer<KissObjectStrategy>  {
	
	private static final long serialVersionUID = -3876176423479352276L;

	public KissObjectStrategyJsonDeserializer() {
		super(KissObjectStrategy.class);
	}

	@Override
	public KissObjectStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return KissObjectStrategy.instance;
	}

}

