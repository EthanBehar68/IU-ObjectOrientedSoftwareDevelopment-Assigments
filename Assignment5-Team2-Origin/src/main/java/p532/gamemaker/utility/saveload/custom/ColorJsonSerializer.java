package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ColorJsonSerializer extends StdSerializer<Color>
{
    public ColorJsonSerializer(Class<Color> t) {
        super(t);
    }

    @Override
    public void serialize(Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();

        //Write the RGB fields of the Color as doubles

        jsonGenerator.writeFieldName("red");
        jsonGenerator.writeNumber(color.getRed());
        jsonGenerator.writeFieldName("green");
        jsonGenerator.writeNumber(color.getGreen());
        jsonGenerator.writeFieldName("blue");
        jsonGenerator.writeNumber(color.getBlue());
        jsonGenerator.writeFieldName("opacity");
        jsonGenerator.writeNumber(color.getOpacity());
        jsonGenerator.writeFieldName("saturation");
        jsonGenerator.writeNumber(color.getSaturation());
        jsonGenerator.writeFieldName("brightness");
        jsonGenerator.writeNumber(color.getBrightness());
        jsonGenerator.writeFieldName("hue");
        jsonGenerator.writeNumber(color.getHue());
        

        jsonGenerator.writeBooleanField("opaque",color.isOpaque());

        jsonGenerator.writeEndObject();
    }
}