package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.scene.paint.Color;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.SpriteView;

import java.io.IOException;

/**
 * This class overrides the serialization behavior for SpriteView.
 */
public class SpriteViewJsonSerializer extends StdSerializer<SpriteView>
{
    public SpriteViewJsonSerializer(Class<SpriteView> t) {
        super(t);
    }

    /**
     * Writes the properties of a SpriteView to the JsonGenerator's
     * JSON object.
     */
    @Override
    public void serialize(SpriteView spriteView, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        //Write the fields of the SpriteView as JSON properties
        jsonGenerator.writeFieldName("width");
        jsonGenerator.writeNumber(spriteView.getSpriteWidth());
        jsonGenerator.writeFieldName("height");
        jsonGenerator.writeNumber(spriteView.getSpriteHeight());
        jsonGenerator.writeFieldName("layoutX");
        jsonGenerator.writeNumber(spriteView.getLayoutX());
        jsonGenerator.writeFieldName("layoutY");
        jsonGenerator.writeNumber(spriteView.getLayoutY());
        jsonGenerator.writeFieldName("color");
        jsonGenerator.writeObject((Color)spriteView.getFill());

        //Write fields specific to SpriteView implementors
        if (spriteView instanceof GameLabel)
        {
            //GameLabel fields
            GameLabel gameLabel = (GameLabel) spriteView;
            jsonGenerator.writeFieldName("text");
            jsonGenerator.writeObject(gameLabel.getText());
        }
    }

    /**
     * Writes the @type prefix to the JSON object before it is serialized
     * so that the interface implementor (concrete class) can be identified.
     * Source: https://stackoverflow.com/a/27893673/16519580
     */
    @Override
    public void serializeWithType(SpriteView value, JsonGenerator gen, SerializerProvider serializerProvider, TypeSerializer typeSer) throws IOException {
        //Write the type ID for this object since SpriteView is an interface
        typeSer.writeTypePrefixForObject(value, gen);
        serialize(value, gen, serializerProvider); //Call the above customized serialize method
        typeSer.writeTypeSuffixForObject(value, gen);
    }
}