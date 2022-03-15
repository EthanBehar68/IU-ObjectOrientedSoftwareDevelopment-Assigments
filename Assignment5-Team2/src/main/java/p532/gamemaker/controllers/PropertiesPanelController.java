package p532.gamemaker.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import p532.gamemaker.Constants;
import p532.gamemaker.strategies.design.AddConditionStrategy;

public class PropertiesPanelController
{
    /*
    The following event handlers add new Conditions to Sprites
    when they are fired. Then, they update the UI.
     */

    public static final EventHandler<ActionEvent> addOnKeyPressBehavior = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            AddConditionStrategy.execute(Constants.PropertyType.ON_KEY_PRESS_CONDITION_COMPONENT);
        }
    };
    
    public static final EventHandler<ActionEvent> addOnTimeBehavior = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            AddConditionStrategy.execute(Constants.PropertyType.ON_TIME_CONDITION_COMPONENT);
        }
    };

    public static final EventHandler<ActionEvent> addOnHitSomethingBehavior = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            AddConditionStrategy.execute(Constants.PropertyType.ON_HIT_SOMETHING_CONDITION_COMPONENT);
        }
    };

    public static final EventHandler<ActionEvent> addOnGetHitBehavior = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            AddConditionStrategy.execute(Constants.PropertyType.ON_GET_HIT_CONDITION_COMPONENT);
        }
    };
}
