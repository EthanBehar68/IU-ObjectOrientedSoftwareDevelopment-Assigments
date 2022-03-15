package p532.gamemaker.strategies.design;

import p532.gamemaker.playthegame.KeyEventNotifier;
import p532.gamemaker.playthegame.Stopwatch;
import p532.gamemaker.playthegame.TickObservable;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Level;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.saveload.LoadUtility;
import p532.gamemaker.utility.saveload.SaveUtility;
import p532.gamemaker.views.GamePlayScene;
import p532.gamemaker.sound.SoundEngine;

import java.util.ArrayList;
import java.util.List;

public class BeginGamePlayStrategy //implements GeneralStrategy
{
    public static void execute()
    {
        //Change to the scene that involves playing the game
        GamePlayScene gamePlayScene = ParentWindow.switchToGamePlayScene();
        
        //Find the game loop manager
        TickObservable loopManager = gamePlayScene.getTickObservable();
        loopManager.unregisterAll(); //reset it
        loopManager.start();
      
        //Find the event notifiers
        KeyEventNotifier keyEventNotifier = gamePlayScene.getKeyEventNotifier();

        //TODO (Ramya) add support for multiple levels
        //Use the level that is currently being edited and make it into a list
        Level levelDesign = ParentWindow.getGameDesignScene().getSpritePresenter().getLevel();
        ArrayList<Level> levelList = new ArrayList<>();
        levelList.add(levelDesign);

        //Create a clone of the game
        List<Level> gameClone = cloneGame(levelList);
       
        //Use the clone's first level
        Level levelClone = gameClone.get(0);

        //Make the clone visible on the playing scene
        gamePlayScene.getSpritePresenter().setLevel(levelClone);

        //Register the Sprites with the game
        List<Sprite> spriteList = levelClone.getAllSprites();
        for (Sprite sprite : spriteList)
        {
            //Register the clone to listen to ticks
            loopManager.registerSprite(sprite);
           
            //Register the clone to listen to key events
            keyEventNotifier.registerSprite(sprite);
            
            //Add click events, etc to the clone
            sprite.setupSpriteForGamePlay();
        }

        // Start soundEngine if hasn't been
        SoundEngine.getInstance();
        
        //Start the Stopwatch
        Stopwatch stopwatch = Stopwatch.instance;
        stopwatch.reset();
        loopManager.registerObserver(stopwatch);

        //Start the game loop
        loopManager.play();
    }

    private static List<Level> cloneGame(List<Level> levelList)
    {
        //Write the current game to a string through JSON serialization.
        //The string will serve as the copy of the input game.
        String gameJson = SaveUtility.serializeGameToJson(levelList);
        
        //Deserialize the string into a game (level list)
        List<Level> loadedGame = LoadUtility.deserializeGameFromJson(gameJson);

        return loadedGame;
    }
}