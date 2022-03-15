package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.strategies.CollisionStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.advanced.FireDownStrategy;
import p532.gamemaker.strategies.advanced.FireUpStrategy;
import p532.gamemaker.strategies.advanced.RandomEnemyFireDownStrategy;
import p532.gamemaker.strategies.collision.DestroySelfStrategy;
import p532.gamemaker.strategies.collision.EndGameStrategy;
import p532.gamemaker.strategies.collision.ReflectStrategy;
import p532.gamemaker.strategies.collision.SwitchLevelStrategy;
import p532.gamemaker.strategies.collision.VictoryStrategy;
import p532.gamemaker.strategies.design.AddNewSpriteStrategy;
import p532.gamemaker.strategies.movement.MoveDownStrategy;
import p532.gamemaker.strategies.movement.MoveLeftStrategy;
import p532.gamemaker.strategies.movement.MoveRightStrategy;
import p532.gamemaker.strategies.movement.MoveUpStrategy;
import p532.gamemaker.strategies.movement.MoveVelocityStrategy;
import p532.gamemaker.strategies.movement.ReverseXVelocityStrategy;
import p532.gamemaker.strategies.movement.ReverseYVelocityStrategy;

public class AddNewSpriteStrategyTest {

	@Test
	public void testGetCopyOfGeneralStrategy() {
		CollisionStrategy strategy1 =  DestroySelfStrategy.instance;
		assert("class p532.gamemaker.strategies.collision.destroyselfstrategy".equals(AddNewSpriteStrategy.getCopyOfCollisionStrategy(strategy1).getClass().toString().toLowerCase()));
		
		CollisionStrategy strategy2 =  EndGameStrategy.instance;
		assert("class p532.gamemaker.strategies.collision.endgamestrategy".equals(AddNewSpriteStrategy.getCopyOfCollisionStrategy(strategy2).getClass().toString().toLowerCase()));
		
		CollisionStrategy strategy3 =  ReflectStrategy.instance;
		assert("class p532.gamemaker.strategies.collision.reflectstrategy".equals(AddNewSpriteStrategy.getCopyOfCollisionStrategy(strategy3).getClass().toString().toLowerCase()));
		
		CollisionStrategy strategy4 =  SwitchLevelStrategy.instance;
		assert("class p532.gamemaker.strategies.collision.switchlevelstrategy".equals(AddNewSpriteStrategy.getCopyOfCollisionStrategy(strategy4).getClass().toString().toLowerCase()));
		
		CollisionStrategy strategy5 =  VictoryStrategy.instance;
		assert("class p532.gamemaker.strategies.collision.victorystrategy".equals(AddNewSpriteStrategy.getCopyOfCollisionStrategy(strategy5).getClass().toString().toLowerCase()));
		
		// TODO: Unknown case: Exception is raised
	}
	
	@Test
	public void testGetCopyOfCollisionStrategy() {
		GeneralStrategy strategy1 = FireDownStrategy.instance;
		assert("class p532.gamemaker.strategies.advanced.firedownstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy1).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy2 = FireUpStrategy.instance;
		assert("class p532.gamemaker.strategies.advanced.fireupstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy2).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy3 =  new RandomEnemyFireDownStrategy();
		assert("class p532.gamemaker.strategies.advanced.randomenemyfiredownstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy3).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy4 = MoveDownStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.movedownstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy4).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy5 = MoveUpStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.moveupstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy5).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy6 = MoveLeftStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.moveleftstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy6).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy7 = MoveRightStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.moverightstrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy7).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy8 = MoveVelocityStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.movevelocitystrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy8).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy9 = ReverseXVelocityStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.reversexvelocitystrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy9).getClass().toString().toLowerCase()));
		
		GeneralStrategy strategy10 = ReverseYVelocityStrategy.instance;
		assert("class p532.gamemaker.strategies.movement.reverseyvelocitystrategy".equals(AddNewSpriteStrategy.getCopyOfGeneralStrategy(strategy10).getClass().toString().toLowerCase()));
		
		// TODO: Unknown case: Exception is raised
	}

}
