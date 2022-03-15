/**
 * @author: Raghunadham Gattu
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package command.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import breakout.Brick;
import game.engine.ObjectPooler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import rendering.DrawRectangle;

@TestMethodOrder(OrderAnnotation.class)
class CommandInvokerTest {

	private static CommandInvoker commandInvoker;
	private static BrickDestroyCommand brickDestroyCommand;

	@BeforeAll
	static void setUpBeforeClass() {
		commandInvoker = new CommandInvoker();
	}

	@Test
	@Order(0)
	@DisplayName("Test for CommandInvoker receiving commands")
	void testReceiveCommand() {

		ObjectPooler objectPooler = new ObjectPooler();
		Brick brick = (Brick) objectPooler.createObject(Brick.class);
		brick.setDrawBehaviour(new DrawRectangle());
		brick.setOriginalColor(Color.BROWN);
		brick.setOriginalPosition(new Point2D(50, 80));
		brick.setOriginalDimensions(new Point2D(40, 10));
		brick.setOriginalSpeed(0);
		brickDestroyCommand = new BrickDestroyCommand(brick);
		Assertions.assertFalse(commandInvoker.getCurrentTickCommandList().getCommandForSerialization().contains(brickDestroyCommand));
		commandInvoker.receiveCommand(brickDestroyCommand);
		Assertions.assertTrue(commandInvoker.getCurrentTickCommandList().getCommandForSerialization().contains(brickDestroyCommand));
	}

	@Test
	@Order(1)
	@DisplayName("Test for executing tick commands added to a list and push the tick commands to undo stack")
	void testExecuteCurrentTickCommands() {
		commandInvoker.executeCurrentTickCommands(true);
		Assertions.assertTrue(commandInvoker.getCurrentTickCommandList().getCommandForSerialization().isEmpty());
		Assertions.assertTrue(commandInvoker.undosAvailable());
	}

	@Test
	@Order(2)
	@DisplayName("Test for undo of tick commands")
	void testUndoCurrentTickCommands() {
		MacroCommand undoMacroCommand = commandInvoker.getUndoListForSerialization().lastElement();
		Assertions.assertFalse(commandInvoker.getRedoListForSerialization().contains(undoMacroCommand));
		commandInvoker.undoCurrentTickCommands();
		Assertions.assertTrue(commandInvoker.getRedoListForSerialization().contains(undoMacroCommand));
	}

	@Test
	@Order(3)
	@DisplayName("Test for redo of tick commands")
	void testRedoCurrentTickCommands() {
		MacroCommand redoMacroCommand = commandInvoker.getRedoListForSerialization().lastElement();
		Assertions.assertFalse(commandInvoker.getUndoListForSerialization().contains(redoMacroCommand));
		commandInvoker.redoCurrentTickCommands();
		Assertions.assertTrue(commandInvoker.getUndoListForSerialization().contains(redoMacroCommand));
	}

	@Test
	@DisplayName("Test to check if any undo commands are available")
	void testUndosAvailable() {
		Assertions.assertEquals(commandInvoker.undosAvailable(), !commandInvoker.getUndoListForSerialization().empty());
	}

	@Test
	@DisplayName("Test to check if any redo commands are available")
	void testRedosAvailable() {
		Assertions.assertEquals(commandInvoker.redosAvailable(), !commandInvoker.getRedoListForSerialization().empty());
	}

	@Test
	@DisplayName("Test for restart of CommandInvoker")
	void testRestartFromLoadFile() {
		commandInvoker.restartFromLoadFile();
		Assertions.assertFalse(commandInvoker.undosAvailable());
		Assertions.assertFalse(commandInvoker.redosAvailable());
	}

	@Test
	void testReset() {
		commandInvoker.reset();
		Assertions.assertFalse(commandInvoker.undosAvailable());
		Assertions.assertFalse(commandInvoker.redosAvailable());
	}

	@Test
	void testDumpLists() {
		commandInvoker.dumpLists();
		Assertions.assertFalse(commandInvoker.undosAvailable());
		Assertions.assertFalse(commandInvoker.redosAvailable());
	}

	@Test
	void testPrepareForReplay() {
		commandInvoker.prepareForReplay();
		Assertions.assertFalse(commandInvoker.undosAvailable());
	}

	@Test
	void testAddSavable() {
		try {
			brickDestroyCommand.addSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}

	@Test
	void testRemoveSavable() {
		try {
			brickDestroyCommand.removeSavable();
		} catch(UnsupportedOperationException e) {
			// Expected behavior
			return;
		}
		fail("Unexpected behavior");
	}
}
