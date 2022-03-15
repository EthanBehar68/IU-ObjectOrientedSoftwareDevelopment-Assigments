package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ColorJsonDeserializer extends StdDeserializer<Color> {
	private static final long serialVersionUID = 2712010168890734889L;

	public ColorJsonDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Color deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// Get the color object
		JsonNode node = jp.getCodec().readTree(jp);

		return deserializeColorNode(node);
	}

	/**
	 * Reads the properties of the colorNode and uses them to create and return a
	 * new Color POJO.
	 * 
	 * @param colorNode a JSON object containing properties "red", "green", and
	 *                  "blue" at the top level.
	 * @return a new Color object created using the properties read from the
	 *         colorNode
	 */
	public static Color deserializeColorNode(JsonNode colorNode) {
		// Parse the RGB fields from the color json
		double red = (Double) ((DoubleNode) colorNode.get("red")).numberValue();
		double green = (Double) ((DoubleNode) colorNode.get("green")).numberValue();
		double blue = (Double) ((DoubleNode) colorNode.get("blue")).numberValue();

		// Create return a new Color object using the loaded properties
		Color color = new Color(red, green, blue, 1);
		return color;
	}
}