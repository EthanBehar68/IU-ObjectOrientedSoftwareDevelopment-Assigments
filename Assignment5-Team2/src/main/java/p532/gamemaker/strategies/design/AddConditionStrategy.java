package p532.gamemaker.strategies.design;

import p532.gamemaker.Constants;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.sprite.conditions.TimeEventCondition;

public class AddConditionStrategy
{
    public static void execute(Constants.PropertyType propertyType)
    {
        //Get the currently selected sprite
        Sprite sprite = ParentWindow.getGameDesignScene().getSpritePresenter().getSelectedSprite();

        if (propertyType == Constants.PropertyType.ON_HIT_SOMETHING_CONDITION_COMPONENT)
        {
            //Add a Condition object to the selected Sprite.
            CollisionCondition condition = new CollisionCondition();
            sprite.getOnHitSomethingConditionList().add(condition);
        }
        else if (propertyType == Constants.PropertyType.ON_GET_HIT_CONDITION_COMPONENT)
        {
            //Add a Condition object to the selected Sprite.
            CollisionCondition condition = new CollisionCondition();
            sprite.getOnGetHitConditionList().add(condition);
        }
        else if (propertyType == Constants.PropertyType.ON_KEY_PRESS_CONDITION_COMPONENT)
        {
            //Add a Condition object to the selected Sprite.
            KeyEventCondition condition = new KeyEventCondition();
            sprite.getOnKeyPressConditionList().add(condition);
        }
        else if (propertyType == Constants.PropertyType.ON_TIME_CONDITION_COMPONENT)
        {
            //Add a Condition object to the selected Sprite.
            TimeEventCondition condition = new TimeEventCondition();
            sprite.getOnTimeConditionList().add(condition);
        }
        else
            throw new IllegalArgumentException("There is no implementation for " + propertyType + " in AddConditionStrategy");

        //Update the UI (Observer Pattern could be used, but I'm lazy)
        SelectSpriteStrategy.execute(sprite);
    }
}
