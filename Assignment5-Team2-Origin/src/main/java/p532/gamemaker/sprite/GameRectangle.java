package p532.gamemaker.sprite;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import p532.gamemaker.views.PropertiesPanel;

import static p532.gamemaker.sprite.PropertyType.StandardPropertyComponent;

public class GameRectangle extends Rectangle implements SpriteView
{
    public GameRectangle() {
    }

    @Override
    public double getSpriteWidth() {
        return this.getWidth();
    }

    @Override
    public void setSpriteWidth(double width) {
        this.setWidth(width);
    }

    @Override
    public double getSpriteHeight() {
        return this.getHeight();
    }

    @Override
    public void setSpriteHeight(double height) {
        this.setHeight(height);
    }

    @Override
    public Node getNode() {
        return this;
    }

    /////////////////////////////////////////////////////////

    /**
     * A "constructor" that creates a new Sprite
     * with default properties.
     * @return a new Sprite with a GameRectangle view
     */
    public static Sprite defaultSprite()
    {
        //Create the Sprite and GameRectangle
        Sprite sprite = new Sprite();
        GameRectangle spriteView = new GameRectangle();
        sprite.setView(spriteView);

        //Customize the view
        spriteView.setWidth(30);
        spriteView.setHeight(30);
        spriteView.setLayoutX(100);
        spriteView.setLayoutY(100);
        spriteView.setFill(Color.BLUE);

        //Add listeners
        sprite.setupSpriteForEditor();

        return sprite;
    }

    /**
     * Adds property fields specific to GameRectangle instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    @Override
    public void addPropertyFields(Sprite parentSprite, PropertiesPanel propertiesBox)
    {
        //Width
        propertiesBox.addDoublePropertyComponent("Width", getSpriteWidth(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateDoubleField(Double newValue) {
                super.updateDoubleField(newValue);
                setSpriteWidth(newValue);
            }
        });

        //Height
        propertiesBox.addDoublePropertyComponent("Height", getSpriteHeight(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateDoubleField(Double newValue) {
                super.updateDoubleField(newValue);
                setSpriteHeight(newValue);
            }
        });
    }

}
