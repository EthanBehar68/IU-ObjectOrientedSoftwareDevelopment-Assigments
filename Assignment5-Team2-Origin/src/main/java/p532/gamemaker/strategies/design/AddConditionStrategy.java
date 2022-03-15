package p532.gamemaker.strategies.design;

import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.PropertyType;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.sprite.conditions.KeyEventCondition;

import static p532.gamemaker.sprite.PropertyType.*;

public class AddConditionStrategy
{
    public static void execute(PropertyType propertyType)
    {
        //Get the currently selected sprite
        Sprite sprite = ParentWindow.getGameDesignScene().getSpritePresenter().getSelectedSprite();

        if (propertyType == OnHitSomethingConditionComponent)
        {
            //Add a Condition object to the selected Sprite.
            CollisionCondition condition = new CollisionCondition();
            sprite.getOnHitSomethingConditionList().add(condition);
        }
        else if (propertyType == OnGetHitConditionComponent)
        {
            //Add a Condition object to the selected Sprite.
            CollisionCondition condition = new CollisionCondition();
            sprite.getOnGetHitConditionList().add(condition);
        }
        else if (propertyType == OnKeyPressConditionComponent)
        {
            //Add a Condition object to the selected Sprite.
            KeyEventCondition condition = new KeyEventCondition();
            sprite.getOnKeyPressConditionList().add(condition);
        }
        else
            throw new IllegalArgumentException("There is no implementation for that propertyType in AddConditionStrategy");

        //Update the UI (Observer Pattern could be used, but I'm lazy)
        SelectSpriteStrategy.execute(sprite);
    }
}
