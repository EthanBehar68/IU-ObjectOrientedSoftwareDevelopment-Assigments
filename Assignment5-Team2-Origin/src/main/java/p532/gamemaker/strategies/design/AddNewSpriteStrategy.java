package p532.gamemaker.strategies.design;

import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.views.SpritePresenter;
import p532.gamemaker.sprite.*;

public class AddNewSpriteStrategy
{
    private AddNewSpriteStrategy() {
    }
    
    private static void setupSprite(Sprite defaultSprite)
    {
        SpritePresenter spriteController = ParentWindow.getGameDesignScene().getSpritePresenter();

        //Add the default Sprite to the SpritePresenter
        spriteController.addSprite(defaultSprite);

        //Add the sprite to the level
        Level level = spriteController.getLevel();
        level.addSprite(defaultSprite);
    }
    
    public static Sprite createRectangle()
    {
        //Create the Sprite
        Sprite defaultSprite = GameRectangle.defaultSprite();
        
        //Set it up for use in the editor
        setupSprite(defaultSprite);
        return defaultSprite;
    }

    public static Sprite createCircle()
    {
        //Create the Sprite
        Sprite defaultSprite = GameCircle.defaultSprite();
        
        //Set it up for use in the editor
        setupSprite(defaultSprite);
        return defaultSprite;
    }

    public static Sprite createLabel()
    {
        //Create the Sprite
        Sprite defaultSprite = GameLabel.defaultSprite();

        //Set it up for use in the editor
        setupSprite(defaultSprite);
        return defaultSprite;
    }
}
