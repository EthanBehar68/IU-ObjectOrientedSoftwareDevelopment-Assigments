package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.ReflectStrategy;

import java.io.IOException;

public class ReflectStrategyJsonDeserializer extends StdDeserializer<ReflectStrategy>
{
    public ReflectStrategyJsonDeserializer() {
        super(ReflectStrategy.class);
    }

    @Override
    public ReflectStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return ReflectStrategy.instance;
    }
}