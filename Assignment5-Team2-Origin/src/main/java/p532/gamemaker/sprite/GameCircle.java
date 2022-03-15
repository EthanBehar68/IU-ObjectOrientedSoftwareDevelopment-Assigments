package p532.gamemaker.sprite;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import p532.gamemaker.views.PropertiesPanel;

import static p532.gamemaker.sprite.PropertyType.StandardPropertyComponent;

public class GameCircle extends Circle implements SpriteView
{
    public GameCircle() {
    }

    @Override
    public double getSpriteWidth() {
        return this.getRadius();
    }

    @Override
    public void setSpriteWidth(double width) {
        setRadius(width);
    }

    @Override
    public double getSpriteHeight() {
        return this.getRadius();
    }

    @Override
    public void setSpriteHeight(double height) {
        setRadius(height);
    }

    @Override
    public Node getNode() {
        return this;
    }

    /////////////////////////////////////////////////////////

    /**
     * A "constructor" that creates a new Sprite
     * with default properties.
     * @return a new Sprite with a GameCircle view
     */
    public static Sprite defaultSprite()
    {
        Sprite defaultSprite = Sprite.defaultSprite();

        //defaultSprite already has a GameCircle; no action is needed

        return defaultSprite;
    }

    /**
     * Adds property fields specific to GameCircle instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    @Override
    public void addPropertyFields(Sprite parentSprite, PropertiesPanel propertiesBox)
    {
        //Radius
        propertiesBox.addDoublePropertyComponent("Radius", getRadius(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateDoubleField(Double newValue) {
                super.updateDoubleField(newValue);
                setRadius(newValue);
            }
        });
    }

}
