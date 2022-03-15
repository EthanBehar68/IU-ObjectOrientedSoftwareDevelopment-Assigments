package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.movement.MoveDownStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;

import java.io.IOException;

public class MoveDownStrategyJsonDeserializer extends StdDeserializer<MoveDownStrategy>
{
    public MoveDownStrategyJsonDeserializer() {
        super(MoveDownStrategy.class);
    }

    @Override
    public MoveDownStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return MoveDownStrategy.instance;
    }
}