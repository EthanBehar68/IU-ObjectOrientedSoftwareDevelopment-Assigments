package p532.gamemaker.sprite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import p532.gamemaker.views.PropertiesPanel;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameRectangle.class, name = "GameRectangle"),
        @JsonSubTypes.Type(value = GameCircle.class, name = "GameCircle"),
        @JsonSubTypes.Type(value = GameLabel.class, name = "GameLabel") }
)
public interface SpriteView
{
    double getSpriteWidth();
    void setSpriteWidth(double width);

    double getSpriteHeight();
    void setSpriteHeight(double height);

    Paint getFill();
    void setFill(Paint value);

    /**
     * Since one cannot extend JavaFX Node directly but one can
     * extend Node subclasses, this returns
     * `this`, i.e. the subclass object
     * @return this
     */
    Node getNode();

    void setLayoutX(double value);
    double getLayoutX();

    void setLayoutY(double value);
    double getLayoutY();

    void addPropertyFields(Sprite parentSprite, PropertiesPanel propertiesBox);
}
