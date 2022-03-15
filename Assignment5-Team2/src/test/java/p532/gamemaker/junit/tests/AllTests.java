package p532.gamemaker.junit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SoundEngineTest.class, 
				MoveUpStrategyTest.class, 
				MoveDownStrategyTest.class, 
				MoveLeftStrategyTest.class, 
				MoveRightStrategyTest.class, 
				SpriteTest.class, 
				StopWatchTest.class,
				ReverseXVelocityStrategyTest.class,
				ReverseYVelocityStrategyTest.class,
				MoveVelocityStrategyTest.class,
				SetNewGameStrategyTest.class,
				DragControllerTest.class,
				AddNewSpriteStrategyTest.class,
				ParseNumberUtiliyyTest.class,
				CollisionDetectionUtilityTest.class,
				CustomObjectMapperTest.class,
				DestroySelfStrategyTest.class,
				TimeEventConditionTest.class,
				KeyEventConditionTest.class,
				KissObjectStrategyTest.class,
				MovementStrategyHelperTest.class,
				SwapVelocityToYStrategyTest.class,
				SwapVelocityToXStrategyTest.class,
				SetYVelocityNegativeStrategyTest.class,
				SetYVelocityPositiveStrategyTest.class,
				SetXVelocityPositiveStrategyTest.class,
				SetXVelocityNegativeStrategyTest.class,
				TeleportToSpawnStrategyTest.class,
				LoadUtilityTest.class,
				FireStrategyHelperTest.class,
				CollisionConditionTest.class
				})
public class AllTests {
}
