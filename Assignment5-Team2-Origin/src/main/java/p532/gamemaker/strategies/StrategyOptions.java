package p532.gamemaker.strategies;

import p532.gamemaker.strategies.collision.*;
import p532.gamemaker.strategies.display.SetClockTextStrategy;
import p532.gamemaker.strategies.display.SetStopwatchTextStrategy;
import p532.gamemaker.strategies.movement.MoveDownStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;
import p532.gamemaker.strategies.movement.MoveRightStrategy;
import p532.gamemaker.strategies.movement.MoveUpStrategy;

import java.util.List;

/**
 * Holds lists of strategies that can be chosen by the user,
 * e.g. in a ComboBox.
 */
public class StrategyOptions
{
    private StrategyOptions() {
    }

    public static final List<CollisionStrategy> ALL_COLLISION_STRATEGIES = List.of(
            DoNothingStrategy.instance,
            ReflectStrategy.instance,
            DestroySelfStrategy.instance,
            EndGameStrategy.instance,
            VictoryStrategy.instance,
            SwitchLevelStrategy.instance
    );

    public static final List<GeneralStrategy> ALL_KEY_EVENT_STRATEGIES = List.of(
            DoNothingStrategy.instance,
            MoveLeftStrategy.instance,
            MoveRightStrategy.instance,
            MoveUpStrategy.instance,
            MoveDownStrategy.instance,
            DestroySelfStrategy.instance,
            EndGameStrategy.instance,
            VictoryStrategy.instance,
            SwitchLevelStrategy.instance
    );

    public static final List<GeneralStrategy> ALL_MOUSE_EVENT_STRATEGIES = List.of(
            DoNothingStrategy.instance,
            DestroySelfStrategy.instance,
            EndGameStrategy.instance,
            VictoryStrategy.instance,
            SwitchLevelStrategy.instance
    );

    public static final List<GeneralStrategy> GAMELABEL_ON_TICK_STRATEGIES = List.of(
            DoNothingStrategy.instance,
            SetClockTextStrategy.instance,
            SetStopwatchTextStrategy.instance
    );
}
