package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;

import java.io.IOException;

public class SetClockTextStrategyJsonDeserializer extends StdDeserializer<SetClockTextStrategy>
{
    public SetClockTextStrategyJsonDeserializer() {
        super(SetClockTextStrategy.class);
    }

    @Override
    public SetClockTextStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return SetClockTextStrategy.instance;
    }
}