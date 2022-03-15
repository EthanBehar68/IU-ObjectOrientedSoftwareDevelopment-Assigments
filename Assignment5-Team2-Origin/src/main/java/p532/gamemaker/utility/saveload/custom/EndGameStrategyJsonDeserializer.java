package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.EndGameStrategy;

import java.io.IOException;

public class EndGameStrategyJsonDeserializer extends StdDeserializer<EndGameStrategy>
{
    public EndGameStrategyJsonDeserializer() {
        super(EndGameStrategy.class);
    }

    @Override
    public EndGameStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return EndGameStrategy.instance;
    }
}