package p532.gamemaker.views;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import p532.gamemaker.controllers.DragController;
import p532.gamemaker.sprite.Level;
import p532.gamemaker.sprite.Sprite;

/**
 * Holds a reference to the Level being displayed.
 * When the Level changes, replaces all Sprites on the display
 * with the new Level's list of Sprites.
 */
public class SpritePresenter
{
    private Scene scene;
    private Level level; //holds all the sprites for one level
    private Sprite selectedSprite = null;
    private Sprite previousSelectedSprite = null;
    private DragController dragController;

    public SpritePresenter(Scene scene) {
        this.scene = scene;
        this.level = new Level();
        this.dragController = new DragController();
    }

    public Scene getScene() {
        return scene;
    }

    public Sprite getSelectedSprite() {
        return selectedSprite;
    }

    public void setSelectedSprite(Sprite newSelectedSprite) {
    	this.previousSelectedSprite = this.selectedSprite;
    	if(previousSelectedSprite != null) {
    		this.previousSelectedSprite.getView().setSelected(false);
    	}
        this.selectedSprite = newSelectedSprite;
        this.selectedSprite.getView().setSelected(true);
        dragController.setTarget(this.selectedSprite.getView());
    }

    public Level getLevel() {
        return level;
    }

    /**
     * Changes the active Level, removes all current Sprites from the display,
     * and adds all Sprites from the new level to the display.
     * @param level a non-null Level that contains a collection
     *              of Sprites.
     */
    public void setLevel(Level level)
    {
        this.level = level;

        //Clear all existing sprites from the display
        AnchorPane paneGameRoot = findGameRoot();
        paneGameRoot.getChildren().clear();
        //Add all sprites from the level to the display
        for (Sprite sprite : level.getAllSprites())
        {
            paneGameRoot.getChildren().add(sprite.getView().getNode());
        }
    }





    private AnchorPane findGameRoot()
    {
        //Select the AnchorPane that contains all the Sprites from the scene
        return (AnchorPane) scene.lookup("#paneGameRoot");
    }

    /**
     * Places a Sprite onto the AnchorPane, making
     * it visible.
     */
    public void addSprite(Sprite sprite)
    {
        //Put the Sprite onto the display
        AnchorPane paneGameRoot = findGameRoot();
        paneGameRoot.getChildren().add(sprite.getView().getNode());
    }

    /**
     * Removes the Sprite from both the active Level
     * and the display.
     * @param sprite the Sprite to remove
     */
    public void removeSprite(Sprite sprite)
    {
        //Remove the Sprite from the display
        AnchorPane paneGameRoot = findGameRoot();
        Platform.runLater(() -> {
            paneGameRoot.getChildren().remove(sprite.getView().getNode());
        });

        //Remove the Sprite from the level
        this.level.getAllSprites().remove(sprite);
    }
}
