package p532.gamemaker.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import p532.gamemaker.sprite.PropertyType;
import p532.gamemaker.strategies.design.AddConditionStrategy;

public class PropertiesPanelController
{
    /*
    The following event handlers add new Conditions to Sprites
    when they are fired. Then, they update the UI.
     */

    public static final EventHandler<ActionEvent> addOnKeyPressBehavior = new EventHandler() {
        @Override
        public void handle(Event event)
        {
            /*/Create (load in) a ConditionComponent
            ConditionComponent conditionComponent = new ConditionComponent();
            //Add the ConditionComponent to the appropriate VBox group
            VBox vBox = ParentWindow.getGameDesignScene().propertiesPanel.getVboxOnKeyPressConditions();
            vBox.getChildren().add(conditionComponent.getView());*/

            AddConditionStrategy.execute(PropertyType.OnKeyPressConditionComponent);
        }
    };

    public static final EventHandler<ActionEvent> addOnHitSomethingBehavior = new EventHandler() {
        @Override
        public void handle(Event event)
        {
            /*//Get the currently selected sprite
            Sprite sprite = ParentWindow.getGameDesignScene().spriteController.getSelectedSprite();
            //Add a Condition object to the selected Sprite.
            CollisionCondition collisionCondition = new CollisionCondition();
            sprite.getOnHitSomethingConditionList().add(collisionCondition);
            //Update the UI (Observer Pattern could be used, but I'm lazy)
            SelectSpriteStrategy.execute(sprite);*/
            AddConditionStrategy.execute(PropertyType.OnHitSomethingConditionComponent);
        }
    };

    public static final EventHandler<ActionEvent> addOnGetHitBehavior = new EventHandler() {
        @Override
        public void handle(Event event)
        {
            /*/Get the currently selected sprite
            Sprite sprite = ParentWindow.getGameDesignScene().spriteController.getSelectedSprite();
            //Add a Condition object to the selected Sprite.
            CollisionCondition collisionCondition = new CollisionCondition();
            sprite.getOnGetHitConditionList().add(collisionCondition);
            //Update the UI (Observer Pattern could be used, but I'm lazy)
            SelectSpriteStrategy.execute(sprite);*/
            AddConditionStrategy.execute(PropertyType.OnGetHitConditionComponent);
        }
    };
}
