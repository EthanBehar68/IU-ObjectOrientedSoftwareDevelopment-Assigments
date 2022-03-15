/**
 * @author: Ethan Taylor Behar, Christian Dummer, Saurabh Gulati
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

import composite.pattern.Layouts.LayoutType;

public interface Layable {
	public void changeLayout(LayoutType currentLayout, int parentX, int parentY, int index);
	public void addLayable(Layable layable);
	public void removeLayable(Layable layable);
}
