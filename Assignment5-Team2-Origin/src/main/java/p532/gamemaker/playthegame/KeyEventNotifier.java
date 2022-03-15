package p532.gamemaker.playthegame;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import p532.gamemaker.sprite.Sprite;

import java.util.LinkedList;

public class KeyEventNotifier
{
    //All game objects
    private final LinkedList<KeyEventObserver> observers = new LinkedList<>();

    public KeyEventNotifier(Scene scene) {
        setup(scene);
    }

    public void registerSprite(Sprite newSprite)
    {
        observers.add(newSprite);
    }

    private void setup(Scene scene)
    {
        //Add key event listener to the (game play) scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                //Notify all observers that a key event occurred
                for (KeyEventObserver observer : observers)
                {
                    observer.notifyOfKeyPressed(event.getCode());
                }
            }
        });
    }
}