package p532.gamemaker.sprite.conditions;

import javafx.scene.input.KeyCode;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.DoNothingStrategy;

public class KeyEventCondition
{
    private KeyCode keyCode = KeyCode.SPACE;
    private GeneralStrategy strategy = DoNothingStrategy.instance;

    public KeyCode getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public GeneralStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GeneralStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean attemptBehavior(Sprite thisSprite, KeyCode keyCode)
    {
        //If the input keyCode matches the condition's keyCode, perform the behavior
        if (this.keyCode.equals(keyCode))
        {
            //Perform the collision
            strategy.execute(thisSprite);
            return true;
        }
        return false;
    }
}
