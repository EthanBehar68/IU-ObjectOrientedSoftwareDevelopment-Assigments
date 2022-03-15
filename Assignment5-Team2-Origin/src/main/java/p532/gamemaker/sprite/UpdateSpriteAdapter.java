package p532.gamemaker.sprite;

import javafx.scene.paint.Color;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.collision.CollisionStrategy;

/**
 * A class that should be overridden using the anonymous objects.
 * This allows property components to have their on-change behavior
 * defined by anonymous UpdateSpriteAdapter objects.
 */
public abstract class UpdateSpriteAdapter
{
    public void updateDoubleField(Double newValue) {};

    public void updateStringField(String newValue) {};

    public void updateStrategy(CollisionStrategy newValue) {};

    public void updateStrategy(GeneralStrategy newValue) {};

    public void updateColor(Color color) {}
}
