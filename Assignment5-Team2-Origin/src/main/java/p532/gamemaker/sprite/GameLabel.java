package p532.gamemaker.sprite;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.StrategyOptions;
import p532.gamemaker.views.PropertiesPanel;

import java.util.LinkedList;

import static p532.gamemaker.sprite.PropertyType.MouseEventComponent;
import static p532.gamemaker.sprite.PropertyType.StandardPropertyComponent;

/**
 * A SpriteView that has a text property.
 * Since it is not a shape, its width/height setters do nothing
 * and its get/setFill methods get/set font color.
 */
public class GameLabel extends Label implements SpriteView
{
    public static final String PLACEHOLDER_TEXT = "Hello";
    public static final Font DEFAULT_FONT = new Font(16);

    public GameLabel() {
    }

    @Override
    public double getSpriteWidth() {
        return this.getWidth();
    }

    @Override
    public void setSpriteWidth(double width) {
        //Do nothing -- setWidth() is handled by JavaFX
    }

    @Override
    public double getSpriteHeight() {
        return this.getHeight();
    }

    @Override
    public void setSpriteHeight(double height) {
        //Do nothing -- setHeight() is handled by JavaFX
    }

    /**
     * Get the font color
     */
    @Override
    public Paint getFill() {
        return this.getTextFill();
    }

    /**
     * Change the font color
     */
    @Override
    public void setFill(Paint value) {
        this.setTextFill(value);
    }

    @Override
    public Node getNode() {
        return this;

        /*Label testLabel = new Label();
        testLabel.setText("This is a test. TEMP");
        return testLabel;*/
    }

    /////////////////////////////////////////////////////////

    /**
     * A "constructor" that creates a new Sprite
     * with default properties.
     * @return a new Sprite with a GameLabel view
     */
    public static Sprite defaultSprite()
    {
        //Create the Sprite and GameLabel
        Sprite sprite = new Sprite();
        GameLabel spriteView = new GameLabel();
        sprite.setView(spriteView);

        //Customize the view
        spriteView.setLayoutX(200);
        spriteView.setLayoutY(200);
        spriteView.setFill(Color.BLACK);
        spriteView.setText(PLACEHOLDER_TEXT);
        spriteView.setFont(DEFAULT_FONT);

        //Add listeners
        sprite.setupSpriteForEditor();

        return sprite;
    }

    /**
     * Adds property fields specific to GameLabel instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    public void addPropertyFields(Sprite parentSprite, PropertiesPanel propertiesBox)
    {
        //Add a property component for customizable parts of the Sprite...

        //Text
        propertiesBox.addStringPropertyComponent("Initial Text", getText(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateStringField(String newValue) {
                super.updateStringField(newValue);
                setText(newValue);
            }
        });

        //onTick
        propertiesBox.addComboBoxPropertyComponent(
                "Should the Text Change?",
                parentSprite.getOnTickStrategy(),
                StandardPropertyComponent,
                new UpdateSpriteAdapter() {
                    @Override
                    public void updateStrategy(GeneralStrategy newValue) {
                        super.updateStrategy(newValue);
                        parentSprite.setOnTickStrategy(newValue);
                    }
                },
                StrategyOptions.GAMELABEL_ON_TICK_STRATEGIES
        );
    }
}
