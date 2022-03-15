/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 18, 2021
 * @editors:
 **/
package composite.pattern;

import java.util.Iterator;

import com.google.gson.JsonObject;

import breakout.Ball;
import breakout.Brick;
import breakout.DigitalTimer;
import breakout.Paddle;
import command.pattern.BallMoveCommand;
import command.pattern.BallObjectCollisionCommand;
import command.pattern.BallScreenCollisionCommand;
import command.pattern.BrickChangeColorCommand;
import command.pattern.BrickDestroyCommand;
import command.pattern.DigitalTimerUpdateCommand;
import command.pattern.PaddleMoveCommand;
import command.pattern.PaddleScreenCollisionCommand;
import command.pattern.Command;
import command.pattern.CommandInvoker;
import command.pattern.MacroCommand;
import game.engine.ObjectPooler;

public class RootSavable implements Savable {

	protected boolean successfulLoad;
	protected CommandInvoker commandInvoker;
	protected ObjectPooler objectPooler;

	public RootSavable() {
		commandInvoker = new CommandInvoker();
		objectPooler = new ObjectPooler();
	}

	/*
	 * Use when load fails! So we can gracefully handle the failure! Like if they
	 * failed to grab a file. Not if the JsonString failed to load.
	 */
	public RootSavable(boolean successfulLoad) {
		this.successfulLoad = successfulLoad;
	}

	/*
	 * Use for saving!
	 */
	public RootSavable(CommandInvoker commandInvoker, ObjectPooler objectPooler) {
		this.successfulLoad = false;
		this.commandInvoker = commandInvoker;
		this.objectPooler = objectPooler;
	}

	public void setSuccesfulLoad(boolean successfulLoad) {
		this.successfulLoad = successfulLoad;
	}
	
	public boolean getSuccesfulLoad() {
		return successfulLoad;
	}

	public CommandInvoker getCommandInvoker() {
		return commandInvoker;
	}

	public ObjectPooler getObjectPooler() {
		return objectPooler;
	}

	public void rebuildReferences() {
		Iterator<MacroCommand> undoIterator = commandInvoker.getUndoListIterator();
		rebuildMacroCommandLists(undoIterator);
		Iterator<MacroCommand> redoIterator = commandInvoker.getRedoListIterator();
		rebuildMacroCommandLists(redoIterator);
	}

	public void rebuildMacroCommandLists(Iterator<MacroCommand> iterator) {
		while (iterator.hasNext()) {
			Iterator<Command> commandIterator = iterator.next().getCommandIterator();
			rebuildCommands(commandIterator);
		}
	}

	public void rebuildCommands(Iterator<Command> commandIterator) {
		while (commandIterator.hasNext()) {
			Command next = commandIterator.next();
			if (next instanceof BallMoveCommand) {
				BallMoveCommand command = (BallMoveCommand) next;
				command.setBall((Ball) objectPooler.getObjectById(command.getBallId()));
			} else if (next instanceof BallObjectCollisionCommand) {
				BallObjectCollisionCommand command = (BallObjectCollisionCommand) next;
				command.setBall((Ball) objectPooler.getObjectById(command.getBallId()));
			} else if (next instanceof BallScreenCollisionCommand) {
				BallScreenCollisionCommand command = (BallScreenCollisionCommand) next;
				command.setBall((Ball) objectPooler.getObjectById(command.getBallId()));
			} else if (next instanceof BrickChangeColorCommand) {
				BrickChangeColorCommand command = (BrickChangeColorCommand) next;
				command.setBrick((Brick) objectPooler.getObjectById(command.getBrickId()));
			} else if (next instanceof BrickDestroyCommand) {
				BrickDestroyCommand command = (BrickDestroyCommand) next;
				command.setBrick((Brick) objectPooler.getObjectById(command.getBrickId()));			
			} else if (next instanceof DigitalTimerUpdateCommand) {
				DigitalTimerUpdateCommand command = (DigitalTimerUpdateCommand) next;
				command.setDigitalTimer((DigitalTimer) objectPooler.getObjectById(command.getDigitalTimerId()));
			} else if (next instanceof PaddleMoveCommand) {
				PaddleMoveCommand command = (PaddleMoveCommand) next;
				command.setPaddle((Paddle) objectPooler.getObjectById(command.getPaddleId()));
			} else if (next instanceof PaddleScreenCollisionCommand) {
				PaddleScreenCollisionCommand command = (PaddleScreenCollisionCommand) next;
				command.setPaddle((Paddle) objectPooler.getObjectById(command.getPaddleId()));
			}
		}
	}

	/************************************
	 * Savable Implementations: Composite Savable No need for Add/Remove b/c
	 * children savables are added from elsewhere
	 ************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();

		if (encloseMyself) {
			sb.append('{');
		}
		sb.append(commandInvoker.save(false));
		sb.append(',');
		sb.append(objectPooler.save(false));

		if (encloseMyself) {
			sb.append('}');
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		commandInvoker.load(jsonObject.get("commandInvoker").getAsJsonObject());
		objectPooler.load(jsonObject.get("objectPooler").getAsJsonObject());
	}
	
	@Override
	public void addSavable() {
		throw new UnsupportedOperationException("This action is completed by the constructor. Please avoid using it.");
	}

	@Override
	public void removeSavable() {
		throw new UnsupportedOperationException("This action is completed by the constructor. Please avoid using it.");
	}
}
