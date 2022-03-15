package p532.gamemaker.strategies.design;

import p532.gamemaker.sprite.*;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.StrategyOptions;
import p532.gamemaker.views.PropertiesPanel;

import static p532.gamemaker.sprite.PropertyType.MouseEventComponent;
import static p532.gamemaker.sprite.PropertyType.StandardPropertyComponent;

public class SpecificPropertyFieldsPresenter
{
    /**
     * Adds property fields specific to GameLabel instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    public static void addPropertyFields(Sprite sprite, GameLabel view, PropertiesPanel propertiesBox)
    {
        //Add a property component for customizable parts of the Sprite...

        //Text
        propertiesBox.addStringPropertyComponent("Initial Text", view.getText(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateStringField(String newValue) {
                super.updateStringField(newValue);
                view.setText(newValue);
            }
        });

        //onTick
        propertiesBox.addComboBoxPropertyComponent(
                "Should the Text Change Over Time?",
                DoNothingStrategy.instance,
                MouseEventComponent,
                new UpdateSpriteAdapter() {
                    @Override
                    public void updateStrategy(GeneralStrategy newValue) {
                        super.updateStrategy(newValue);
                        sprite.setMouseClickStrategy(newValue);
                    }
                },
                StrategyOptions.ALL_MOUSE_EVENT_STRATEGIES
        );
    }



    /**
     * Adds property fields specific to GameCircle instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    public static void addPropertyFields(Sprite sprite, GameCircle view, PropertiesPanel propertiesBox)
    {
        /*/Radius
        propertiesBox.addDoublePropertyComponent("Radius", getRadius(), StandardPropertyComponent, new UpdateSpriteAdapter() {
            @Override
            public void updateDoubleField(Double newValue) {
                super.updateDoubleField(newValue);
                setRadius(newValue);
            }
        });*/
    }


    /**
     * Adds property fields specific to GameRectangle instances to the propertiesBox.
     * @param propertiesBox where the property components will be added
     */
    public static void addPropertyFields(Sprite sprite, GameRectangle view, PropertiesPanel propertiesBox)
    {
        /*/Width
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
        });*/
    }
}
