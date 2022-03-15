package p532.gamemaker.strategies.design;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import p532.gamemaker.sprite.conditions.KeyEventCondition;


public class GetKeyInputButtonStrategy
{
    public static void execute(Button whereTheInputShouldBeRecorded, KeyEventCondition condition)
    {
        //Focus on the Node
        whereTheInputShouldBeRecorded.requestFocus();

        //Handle the press of one key
        whereTheInputShouldBeRecorded.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                KeyCode keyCode = event.getCode();

                //Re-focus on the Node so that pressing an arrow key doesn't move the focus away
                whereTheInputShouldBeRecorded.requestFocus();

                //Remove key events from the Node
                whereTheInputShouldBeRecorded.setOnKeyPressed(event1 -> {
                    //Do nothing
                });

                //Display the key code on this button
                whereTheInputShouldBeRecorded.setText(keyCode.toString());

                //Record the KeyCode so that it is associated with a Sprite
                condition.setKeyCode(keyCode);
            }
        });
    }
}
