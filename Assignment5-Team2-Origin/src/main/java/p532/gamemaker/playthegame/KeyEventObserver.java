package p532.gamemaker.playthegame;

import javafx.scene.input.KeyCode;

public interface KeyEventObserver
{
    void notifyOfKeyPressed(KeyCode keyCode);
}