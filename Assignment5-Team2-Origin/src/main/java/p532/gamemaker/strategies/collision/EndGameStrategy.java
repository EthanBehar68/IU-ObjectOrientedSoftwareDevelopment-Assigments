package p532.gamemaker.strategies.collision;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.design.BeginGamePlayStrategy;

public class EndGameStrategy implements CollisionStrategy, GeneralStrategy
{
    /**
     * A static EndGameStrategy that can be used instead of the constructor.
     */
    public static final EndGameStrategy instance = new EndGameStrategy();

    private EndGameStrategy() {
    }

    @Override
    public void execute(Sprite target)
    {
        //Pause the game
        ParentWindow.getGamePlayScene().getTickObservable().stopGameLoop();
        System.out.println("Game Over.");

        //Show dialog box
        Alert alert = new Alert(Alert.AlertType.WARNING, "Game Over.", ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            //Restart game
            BeginGamePlayStrategy.execute();
        }
    }

    @Override
    public void doCollisionBehavior(Sprite colliderOrImpactee, CollisionType collisionType)
    {
        //Reuse above method
        execute(colliderOrImpactee);
    }

    @Override
    public String toString() {
        return "End the Game";
    }
}
