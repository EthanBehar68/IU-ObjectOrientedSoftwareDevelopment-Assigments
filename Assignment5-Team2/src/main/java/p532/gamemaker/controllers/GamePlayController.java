package p532.gamemaker.controllers;

import javafx.event.ActionEvent;

public class GamePlayController
{

    public void onClickReturnToEditor(ActionEvent actionEvent)
    {
        //Stop game loop
        ParentWindow.getGamePlayScene().getTickObservable().stopGameLoop();
        //Show editor
        ParentWindow.switchToGameDesignScene();
    }
}
