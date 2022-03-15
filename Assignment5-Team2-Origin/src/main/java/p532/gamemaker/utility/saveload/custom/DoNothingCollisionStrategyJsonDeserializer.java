package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.DoNothingStrategy;

import java.io.IOException;

public class DoNothingCollisionStrategyJsonDeserializer extends StdDeserializer<DoNothingStrategy>
{
    public DoNothingCollisionStrategyJsonDeserializer() {
        super(DoNothingStrategy.class);
    }

    @Override
    public DoNothingStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return DoNothingStrategy.instance;
    }
}