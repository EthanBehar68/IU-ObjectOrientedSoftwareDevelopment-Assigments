package p532.gamemaker.utility.saveload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.scene.paint.Color;
import p532.gamemaker.sprite.GameCircle;
import p532.gamemaker.sprite.GameLabel;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.SpriteView;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.strategies.display.SetStopwatchTextStrategy;
import p532.gamemaker.strategies.movement.MoveDownStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;
import p532.gamemaker.strategies.movement.MoveRightStrategy;
import p532.gamemaker.strategies.movement.MoveUpStrategy;
import p532.gamemaker.utility.saveload.custom.*;

public class CustomObjectMapper extends ObjectMapper
{
    public CustomObjectMapper()
    {
        super();

        //Customize the mapper to allow custom de/serialize
        SimpleModule module = new SimpleModule("JavaFX Module");

        //Colors
        module.addSerializer(Color.class, new ColorJsonSerializer(Color.class));
        module.addDeserializer(Color.class, new ColorJsonDeserializer(Color.class));

        //SpriteViews
        module.addSerializer(SpriteView.class, new SpriteViewJsonSerializer(SpriteView.class));
        module.addDeserializer(GameCircle.class, new GameCircleJsonDeserializer());
        module.addDeserializer(GameRectangle.class, new GameRectangleJsonDeserializer());
        module.addDeserializer(GameLabel.class, new GameLabelJsonDeserializer());

        //Strategies
        module.addDeserializer(DoNothingStrategy.class, new DoNothingCollisionStrategyJsonDeserializer());
        module.addDeserializer(ReflectStrategy.class, new ReflectStrategyJsonDeserializer());
        module.addDeserializer(EndGameStrategy.class, new EndGameStrategyJsonDeserializer());
        module.addDeserializer(DestroySelfStrategy.class, new DestroySelfStrategyJsonDeserializer());
        module.addDeserializer(SwitchLevelStrategy.class, new SwitchLevelStrategyJsonDeserializer());
        module.addDeserializer(VictoryStrategy.class, new VictoryStrategyJsonDeserializer());
        module.addDeserializer(SetClockTextStrategy.class, new SetClockTextStrategyJsonDeserializer());
        module.addDeserializer(SetStopwatchTextStrategy.class, new SetStopwatchTextStrategyJsonDeserializer());
        //Move Strategies
        module.addDeserializer(MoveLeftStrategy.class, new MoveLeftStrategyJsonDeserializer());
        module.addDeserializer(MoveRightStrategy.class, new MoveRightStrategyJsonDeserializer());
        module.addDeserializer(MoveUpStrategy.class, new MoveUpStrategyJsonDeserializer());
        module.addDeserializer(MoveDownStrategy.class, new MoveDownStrategyJsonDeserializer());

        this.registerModule(module);
    }
}
