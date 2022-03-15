package p532.gamemaker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import p532.gamemaker.sprite.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;
import p532.gamemaker.views.GameDesignScene;
import p532.gamemaker.views.GamePlayScene;

import java.io.IOException;

/**
 * Holds all views for the game maker. The app starts from here.
 */
public class ParentWindow extends Application
{
    public static final int INITIAL_SCREEN_WIDTH = (int) (Screen.getPrimary().getBounds().getWidth());
    public static final int INITIAL_SCREEN_HEIGHT = (int) (Screen.getPrimary().getBounds().getHeight()*0.9);

    private static Group rootGroup;
    private static GameDesignScene gameDesignScene = null;
    private static GamePlayScene gamePlayScene = null;
    private static Stage stage = null;
    private static GraphicsContext graphicsContext = null;
    private static Canvas canvas = null;


    public static GameDesignScene getGameDesignScene() {
        return gameDesignScene;
    }

    public static void setGameDesignScene(GameDesignScene gameDesignScene) {
        ParentWindow.gameDesignScene = gameDesignScene;
    }

    public static GamePlayScene getGamePlayScene() {
        return gamePlayScene;
    }

    public static void setGamePlayScene(GamePlayScene gamePlayScene) {
        ParentWindow.gamePlayScene = gamePlayScene;
    }






    /**
     * Configures the window by loading in the design & play scenes
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException
    {
        //Load in the XML layout of the editor window
        Parent editorRoot = FXMLLoader.load(getClass().getClassLoader().getResource("parent-editor-window.fxml"));

        //Load in the XML of the testing window
        Parent playRoot = FXMLLoader.load(getClass().getClassLoader().getResource("parent-play-window.fxml"));

        //Set up the scenes
        gameDesignScene = new GameDesignScene(editorRoot, INITIAL_SCREEN_WIDTH, INITIAL_SCREEN_HEIGHT);
        gamePlayScene = new GamePlayScene(playRoot, INITIAL_SCREEN_WIDTH, INITIAL_SCREEN_HEIGHT);
        primaryStage.setScene(gameDesignScene);
        primaryStage.show();
        
        //Change background colors
        editorRoot.setStyle("-fx-background-color: #AAAAAA");
        playRoot.setStyle("-fx-background-color: #AAAAAA");

        //Set up the window
        primaryStage.setTitle("Game Maker");
        primaryStage.show();
        this.stage = primaryStage;
    }

    /**
     * End the game when the window closes
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        getGamePlayScene().getTickObservable().stopGameLoop();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public static GameDesignScene switchToGameDesignScene()
    {
        stage.setScene(gameDesignScene);
        return gameDesignScene;
    }

    public static GamePlayScene switchToGamePlayScene()
    {
        stage.setScene(gamePlayScene);
        return gamePlayScene;
    }
}
