package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.VictoryStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;

import java.io.IOException;

public class MoveLeftStrategyJsonDeserializer extends StdDeserializer<MoveLeftStrategy>
{
    public MoveLeftStrategyJsonDeserializer() {
        super(MoveLeftStrategy.class);
    }

    @Override
    public MoveLeftStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return MoveLeftStrategy.instance;
    }
}