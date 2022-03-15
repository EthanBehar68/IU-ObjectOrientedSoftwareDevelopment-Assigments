/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Behar
 **/
package command.pattern;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MacroCommand extends Command {
	
	private Queue<Command> commands;
	
	public MacroCommand() {
		commands = new LinkedList<Command>();
	}
	
	public MacroCommand(LinkedList<Command> commands) {
		this.commands = commands;
	}
	
	public Queue<Command> getCommandForSerialization() {
		return commands;
	}
	
	public Iterator<Command> getCommandIterator() {
		return commands.iterator();
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	/************************************
	* Command Implementations
	************************************/
	@Override
	public void execute() {
		for(Command command: commands) {
			if(!command.getWasExecuted()) {
				command.execute();
				command.setWasExecuted(true);
			}
		}
	}
	
	@Override
	public void undo() {
		for(Command command: commands) {
			command.undo();
		}
	}
	
	@Override
	public void redo() {
		for(Command command: commands) {
			command.redo();
		}
	}
	
	/************************************
	* Savable Implementations
	* A Composite Savable
	* No need for Add/Remove b/c
	* children savables are added from elsewhere
	************************************/
	@Override 
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();
		
		if(encloseMyself) {
			sb.append("{");
		}
		
		sb.append("\"commands\":[");
		Iterator<Command> commandIterator = commands.iterator();
		while(commandIterator.hasNext()) {
			sb.append(commandIterator.next().save(true));
			if(commandIterator.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("]");
		
		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}
	
	@Override 
	public void load(JsonObject jsonObject) {
		JsonArray commandListJson = jsonObject.get("commands").getAsJsonArray();
		for(JsonElement jsonElement: commandListJson) {
			JsonObject commandJson = jsonElement.getAsJsonObject();

			String typeAsString = commandJson.get("class").getAsString();
			
			Command command = deserializeCommand(typeAsString, commandJson);
			commands.add(command);
		}
	}
	
	public static Command deserializeCommand(String typeAsString, JsonObject jsonObject) {
		if (typeAsString.compareToIgnoreCase("command.pattern.BallMoveCommand") == 0) {
			BallMoveCommand ballMoveCommand = new BallMoveCommand();
			ballMoveCommand.load(jsonObject);
			ballMoveCommand.setWasExecuted(true);
			return ballMoveCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.BallObjectCollisionCommand") == 0) {
			BallObjectCollisionCommand ballObjectCollisionCommand = new BallObjectCollisionCommand();
			ballObjectCollisionCommand.load(jsonObject);
			ballObjectCollisionCommand.setWasExecuted(true);
			return ballObjectCollisionCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.BallScreenCollisionCommand") == 0) {
			BallScreenCollisionCommand ballScreenCollisionCommand = new BallScreenCollisionCommand();
			ballScreenCollisionCommand.load(jsonObject);
			ballScreenCollisionCommand.setWasExecuted(true);
			return ballScreenCollisionCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.BrickChangeColorCommand") == 0) {
			BrickChangeColorCommand brickChangeColorCommand = new BrickChangeColorCommand();
			brickChangeColorCommand.load(jsonObject);
			brickChangeColorCommand.setWasExecuted(true);
			return brickChangeColorCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.BrickDestroyCommand") == 0) {
			BrickDestroyCommand brickDestroyCommand = new BrickDestroyCommand();
			brickDestroyCommand.load(jsonObject);
			brickDestroyCommand.setWasExecuted(true);
			return brickDestroyCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.DigitalTimerUpdateCommand") == 0) {
			DigitalTimerUpdateCommand digitalTimerUpdateCommand = new DigitalTimerUpdateCommand();
			digitalTimerUpdateCommand.load(jsonObject);
			digitalTimerUpdateCommand.setWasExecuted(true);
			return digitalTimerUpdateCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.PaddleMoveCommand") == 0) {
			PaddleMoveCommand paddleMoveCommand = new PaddleMoveCommand();
			paddleMoveCommand.load(jsonObject);
			paddleMoveCommand.setWasExecuted(true);
			return paddleMoveCommand;
		} else if (typeAsString.compareToIgnoreCase("command.pattern.PaddleScreenCollisionCommand") == 0) {
			PaddleScreenCollisionCommand paddleScreenCollisionCommand = new PaddleScreenCollisionCommand();
			paddleScreenCollisionCommand.load(jsonObject);
			paddleScreenCollisionCommand.setWasExecuted(true);
			return paddleScreenCollisionCommand;
		} else {
			throw new UnsupportedOperationException("Implement deserializer for command: " + typeAsString);
		}
	}
	
	@Override 
	public void addSavable() {
		throw new UnsupportedOperationException("This action is completed by other MacroCommand tasks. Please avoid using it.");
	}
	
	@Override 
	public void removeSavable() {
		throw new UnsupportedOperationException("This action is completed by other MacroCommand tasks. Please avoid using it.");
	}
}
