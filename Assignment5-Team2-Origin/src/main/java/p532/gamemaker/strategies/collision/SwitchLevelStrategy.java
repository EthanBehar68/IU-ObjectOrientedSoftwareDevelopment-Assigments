package p532.gamemaker.strategies.collision;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.design.BeginGamePlayStrategy;

public class SwitchLevelStrategy  implements CollisionStrategy, GeneralStrategy {
    /**
     * A static SwitchLevelStrategy that can be used instead of the constructor.
     */
    public static final SwitchLevelStrategy instance = new SwitchLevelStrategy();

    private SwitchLevelStrategy() {
    }

    @Override
    public void execute(Sprite target) {
        //Pause the game
        ParentWindow.getGamePlayScene().getTickObservable().stopGameLoop();
        System.out.println("Switching Level...");

        //TODO
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType) {
        //Reuse above method
        execute(colliderOrImpactee);
    }

    @Override
    public String toString() {
        return "Switch Level";
    }
}