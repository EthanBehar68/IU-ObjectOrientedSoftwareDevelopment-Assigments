/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/
package composite.pattern;

import com.google.gson.JsonObject;

public interface Savable {
	public String save(boolean encloseMyself);
	public void load(JsonObject jsonObject);
	public void addSavable();
	public void removeSavable();
}
