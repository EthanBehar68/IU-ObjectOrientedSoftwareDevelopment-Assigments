package p532.gamemaker.command;

import java.util.LinkedList;
import java.util.Queue;

public class MoveCommands {

private Queue<Command> commands;
	
	public MoveCommands() {
		commands = new LinkedList<Command>();
	}
	
	public MoveCommands(LinkedList<Command> commands) {
		this.commands = commands;
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	public void execute() {
		for(Command command: commands) {
			command.execute();
		}
	}
}
