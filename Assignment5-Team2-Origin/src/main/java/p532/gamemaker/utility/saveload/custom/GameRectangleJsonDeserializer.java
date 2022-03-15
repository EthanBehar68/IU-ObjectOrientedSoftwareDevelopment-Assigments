package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.SpriteView;

import java.io.IOException;

public class GameRectangleJsonDeserializer extends StdDeserializer<GameRectangle>
{
    public GameRectangleJsonDeserializer() {
        super(SpriteView.class);
    }

    @Override
    public GameRectangle deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        //Get the GameCircle object
        JsonNode node = jp.getCodec().readTree(jp);

        //Create a blank GameRectangle object so that the loaded fields can be added to it
        GameRectangle gameRectangle = new GameRectangle();

        //Add the fields to the POJO using the JSON
        SpriteViewJsonDeserializerUtility.addFields(node, gameRectangle);

        return gameRectangle;
    }
}