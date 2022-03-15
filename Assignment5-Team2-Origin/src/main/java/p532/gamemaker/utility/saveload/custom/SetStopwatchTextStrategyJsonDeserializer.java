package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.strategies.display.SetStopwatchTextStrategy;

import java.io.IOException;

public class SetStopwatchTextStrategyJsonDeserializer extends StdDeserializer<SetStopwatchTextStrategy>
{
    public SetStopwatchTextStrategyJsonDeserializer() {
        super(SetStopwatchTextStrategy.class);
    }

    @Override
    public SetStopwatchTextStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        return SetStopwatchTextStrategy.instance;
    }
}