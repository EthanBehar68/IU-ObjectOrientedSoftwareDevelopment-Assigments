package p532.gamemaker.utility.saveload.custom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import javafx.scene.paint.Color;
import p532.gamemaker.sprite.SpriteView;

/**
 * This class is capable of reading all properties needed for a SpriteView
 * and adding those to a newly-created SpriteView concrete instance.
 */
public class SpriteViewJsonDeserializerUtility
{
    private SpriteViewJsonDeserializerUtility() {
    }

    /**
     * Reads all properties needed for a SpriteView
     * and adds those properties to the input spriteView instance.
     * @param node a JSON object containing a serialized SpriteView
     * @param spriteView the POJO that the properties from 'node' need to be
     *                   copied to
     */
    public static void addFields(JsonNode node, SpriteView spriteView)
    {
        //Parse the fields from the JSON and add them to the POJO
        double width = (Double) ((DoubleNode) node.get("width")).numberValue();
        spriteView.setSpriteWidth(width);
        double height = (Double) ((DoubleNode) node.get("height")).numberValue();
        spriteView.setSpriteHeight(height);
        double layoutX = (Double) ((DoubleNode) node.get("layoutX")).numberValue();
        spriteView.setLayoutX(layoutX);
        double layoutY = (Double) ((DoubleNode) node.get("layoutY")).numberValue();
        spriteView.setLayoutY(layoutY);

        //Add the color composite object
        JsonNode colorNode = node.get("color");
        Color color = ColorJsonDeserializer.deserializeColorNode(colorNode);
        spriteView.setFill(color);
    }
}