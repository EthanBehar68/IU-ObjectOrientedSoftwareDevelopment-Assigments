/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package breakout;

import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import command.pattern.CommandInvoker;
import game.engine.DrawObject;
import game.engine.ObjectPooler;

class GameManagerTest {

	private static ObjectPooler objectPooler;
	private static GameManager gameManager;


	@BeforeAll
	static void setUpBeforeClass() {
		objectPooler = new ObjectPooler();
        CommandInvoker commandInvoker = new CommandInvoker();
    	gameManager = new GameManager(objectPooler, commandInvoker);
	}

	@Test
	void testReset() {
		gameManager.reset();
		Iterator<DrawObject> drawObjectIterator = objectPooler.getDrawObjectIterator();
		while (drawObjectIterator.hasNext()) {
			DrawObject drawObject = drawObjectIterator.next();
			Assertions.assertEquals(drawObject.getPosition(), drawObject.getOriginalPosition());
		}
	}

}
