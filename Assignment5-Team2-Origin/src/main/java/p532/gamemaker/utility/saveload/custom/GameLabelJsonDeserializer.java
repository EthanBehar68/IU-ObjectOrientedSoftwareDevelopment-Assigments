package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.SpriteView;

import java.io.IOException;

public class GameLabelJsonDeserializer extends StdDeserializer<GameLabel>
{
    public GameLabelJsonDeserializer() {
        super(SpriteView.class);
    }

    @Override
    public GameLabel deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        //Get the GameCircle object
        JsonNode node = jp.getCodec().readTree(jp);

        //Create a blank GameCircle object so that the loaded fields can be added to it
        GameLabel gameLabel = new GameLabel();

        //Add the fields to the POJO using the JSON
        SpriteViewJsonDeserializerUtility.addFields(node, gameLabel);

        //Add GameLabel-specific fields
        String text = node.get("text").asText();
        gameLabel.setText(text);
        gameLabel.setFont(GameLabel.DEFAULT_FONT);

        return gameLabel;
    }
}