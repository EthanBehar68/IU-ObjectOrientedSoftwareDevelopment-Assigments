/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import com.google.gson.JsonObject;

import composite.pattern.BreakoutJsonUtility;
import composite.pattern.Savable;

public abstract class Command implements Savable {
	
	private boolean wasExecuted = false;
	
	public boolean getWasExecuted() {
		return wasExecuted;
	}

	public void setWasExecuted(boolean wasExecuted) {
		this.wasExecuted = wasExecuted;
	}
	
	public void execute() {
		throw new UnsupportedOperationException();
	}
	
	public void undo() {
		throw new UnsupportedOperationException();
	}
	
	public void redo() {
		throw new UnsupportedOperationException();
	}
	
	/************************************
	* Savable Implementations
	************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();
		
		if(encloseMyself) {
			sb.append("{");
		}
		
		sb.append(BreakoutJsonUtility.Serializer.serializeBoolean("wasExecuted", wasExecuted));

		if(encloseMyself) {
			sb.append("}");
		}		
		
		return sb.toString();
	}
	
	@Override
	public void load(JsonObject jsonObject) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addSavable() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeSavable() {
		throw new UnsupportedOperationException();
	}


}
