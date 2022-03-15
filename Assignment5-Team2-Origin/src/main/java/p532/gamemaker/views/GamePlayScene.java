package p532.gamemaker.views;


import javafx.scene.Parent;
import javafx.scene.Scene;
import p532.gamemaker.playthegame.KeyEventNotifier;
import p532.gamemaker.playthegame.TickObservable;


public class GamePlayScene extends Scene
{
    private TickObservable tickObservable;
    private KeyEventNotifier keyEventNotifier;
    private SpritePresenter spritePresenter;

    public GamePlayScene(Parent parent, int initialWidth, int initialHeight)  {
        super(parent, initialWidth, initialHeight);

        //Set up the game loop manager
        tickObservable = new TickObservable();

        //Set up event notifiers
        keyEventNotifier = new KeyEventNotifier(this);

        //Add user's game design to the scene
        spritePresenter = new SpritePresenter(this);
    }

    public TickObservable getTickObservable() {
        return tickObservable;
    }

    public void setTickObservable(TickObservable tickObservable) {
        this.tickObservable = tickObservable;
    }

    public KeyEventNotifier getKeyEventNotifier() {
        return keyEventNotifier;
    }

    public void setKeyEventNotifier(KeyEventNotifier keyEventNotifier) {
        this.keyEventNotifier = keyEventNotifier;
    }

    public SpritePresenter getSpritePresenter() {
        return spritePresenter;
    }

    public void setSpritePresenter(SpritePresenter spritePresenter) {
        this.spritePresenter = spritePresenter;
    }
}
