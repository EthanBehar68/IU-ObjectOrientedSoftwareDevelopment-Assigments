package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;
import p532.gamemaker.strategies.movement.MoveUpStrategy;

import java.io.IOException;

public class MoveUpStrategyJsonDeserializer extends StdDeserializer<MoveUpStrategy>
{
    public MoveUpStrategyJsonDeserializer() {
        super(MoveUpStrategy.class);
    }

    @Override
    public MoveUpStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return MoveUpStrategy.instance;
    }
}