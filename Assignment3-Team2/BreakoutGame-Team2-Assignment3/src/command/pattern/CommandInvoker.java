/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Aditi Shivaji Pednekar,  Ethan Behar, Raghunadham G
 **/
package command.pattern;

import java.util.Iterator;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import composite.pattern.Savable;

public class CommandInvoker implements CommandListener, Savable {
	
	// Each MacroCommand contains all commands for a given tick/update in the game engine
	private MacroCommand currentTickCommandList;
	private Stack<MacroCommand> undoCommandList;
	private Stack<MacroCommand> redoCommandList;
	
	public CommandInvoker() {
		currentTickCommandList = new MacroCommand();
		undoCommandList = new Stack<MacroCommand>();
		redoCommandList = new Stack<MacroCommand>();
	}
	
	public CommandInvoker(Stack<MacroCommand> undoCommandList, Stack<MacroCommand> redoCommandList) {
		currentTickCommandList = new MacroCommand();
		this.undoCommandList = undoCommandList;
		this.redoCommandList = redoCommandList;
	}
	
	public Stack<MacroCommand> getUndoListForSerialization() {
		return undoCommandList;
	}
	
	public Iterator<MacroCommand> getUndoListIterator() {
		return undoCommandList.iterator();
	}

	public Stack<MacroCommand> getRedoListForSerialization() {
		return redoCommandList;
	}

	public MacroCommand getCurrentTickCommandList() {
		return currentTickCommandList;
	}

	public Iterator<MacroCommand> getRedoListIterator() {
		return redoCommandList.iterator();
	}
	
	public void restartFromLoadFile() {
		dumpLists();
	}
	
	public void reset() {
		dumpLists();
	}
	
	public void dumpLists() {
		currentTickCommandList = new MacroCommand();
		undoCommandList.clear();
		undoCommandList = null;
		undoCommandList = new Stack<MacroCommand>();
		redoCommandList.clear();
		redoCommandList = null;
		redoCommandList = new Stack<MacroCommand>();
	}
	
	@Override
	public void receiveCommand(Command command) {
		currentTickCommandList.addCommand(command);
	}
	
	/*
	 * Normal game engine execution
	 */
	public void executeCurrentTickCommands(boolean pushToUndo) {
		currentTickCommandList.execute();
		if(pushToUndo) {
			undoCommandList.push(currentTickCommandList);
			currentTickCommandList = new MacroCommand();
		}
	}
	
	/*
	 * When undoing commands
	 * Either undo step or rewind
	 */
	public boolean undosAvailable() {
		return !undoCommandList.isEmpty();
	}
	
	public void undoCurrentTickCommands() {
		MacroCommand undoMacroCommand = undoCommandList.pop();
		redoCommandList.push(undoMacroCommand);
		undoMacroCommand.undo();
	}
	
	/*
	 * When redoing commands
	 * Either redo step, fast forward, or replay
	 */
	public boolean redosAvailable() {
		return !redoCommandList.isEmpty();
	}
	
	public void prepareForReplay(){
		while(undosAvailable()) {
			redoCommandList.push(undoCommandList.pop());
		}
	}
	
	public void redoCurrentTickCommands() {
		MacroCommand redoMacroCommand = redoCommandList.pop();
		undoCommandList.push(redoMacroCommand);
		redoMacroCommand.redo();
	}
	
	/************************************
	* Savable Implementations: Composite Savable
	* No need for Add/Remove b/c
	* children savables are added from elsewhere
	************************************/
	@Override 
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();
		
		if(encloseMyself) {
			sb.append("{");
		}

		sb.append("\"commandInvoker\":{");
		sb.append("\"undoCommandList\":[");
		Iterator<MacroCommand> undoIterator = undoCommandList.iterator(); 
		while(undoIterator.hasNext()) {
			sb.append(undoIterator.next().save(true));
			if(undoIterator.hasNext()) {
				sb.append(",");
			}
		}		
		sb.append("],");
		
		sb.append("\"redoCommandList\":[");
		Iterator<MacroCommand> redoIterator = redoCommandList.iterator(); 
		while(redoIterator.hasNext()) {
			sb.append(redoIterator.next().save(true));
			if(redoIterator.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("]}");
		
		if(encloseMyself) {
			sb.append("}");
		}
		
		return sb.toString();
	}
	
	@Override 
	public void load(JsonObject jsonObject) {
		JsonArray undoCommandListJson = jsonObject.get("undoCommandList").getAsJsonArray();
		for(JsonElement jsonElement: undoCommandListJson) {
			MacroCommand macroCommand = new MacroCommand();
			macroCommand.load(jsonElement.getAsJsonObject());
			undoCommandList.add(macroCommand);
		}
		
		JsonArray redoCommandListJson = jsonObject.get("redoCommandList").getAsJsonArray();
		for(JsonElement jsonElement: redoCommandListJson) {
			MacroCommand macroCommand = new MacroCommand();
			macroCommand.load(jsonElement.getAsJsonObject());
			redoCommandList.add(macroCommand);
		}
	}
	
	@Override 
	public void addSavable() {
		throw new UnsupportedOperationException("This action is completed by other CommandInvoker tasks. Please avoid using it.");
	}
	
	@Override 
	public void removeSavable() {
		throw new UnsupportedOperationException("This action is completed by other CommandInvoker tasks. Please avoid using it.");
	}
}