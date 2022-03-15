package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.collision.EndGameStrategy;
import p532.gamemaker.strategies.collision.SwitchLevelStrategy;

import java.io.IOException;

public class SwitchLevelStrategyJsonDeserializer extends StdDeserializer<SwitchLevelStrategy>
{
    public SwitchLevelStrategyJsonDeserializer() {
        super(SwitchLevelStrategy.class);
    }

    @Override
    public SwitchLevelStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return SwitchLevelStrategy.instance;
    }
}