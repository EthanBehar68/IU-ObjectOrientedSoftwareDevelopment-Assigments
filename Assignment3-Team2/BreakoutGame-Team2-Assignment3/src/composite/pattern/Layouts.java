/**
 * @author: Ethan Taylor Behar, Christian Dummer, Saurabh Gulati
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

public class Layouts {
	
	public static enum LayoutType {
	    BOTTOM,
	    RIGHT,
	    TOP,
	    LEFT
	}
	
	public static class RightLayout {
		public static int rootPaneWidth = 1100;
		public static int rootPaneHeight = 628;
		public static int gameCanvasWidth = 900;
		public static int gameCanvasHeight = 600;
		public static int gameCanvasX = 0;
		public static int gameCanvasY = 0;
		public static int controlPaneWidth = 200;
		public static int controlPaneHeight = 600;
		public static int controlPaneX = 900;
		public static int controlPaneY = 0;
	}
	
	public static class LeftLayout {
		public static int rootPaneWidth = 1100;
		public static int rootPaneHeight = 628;
		public static int gameCanvasWidth = 900;
		public static int gameCanvasHeight = 600;
		public static int gameCanvasX = 200;
		public static int gameCanvasY = 0;
		public static int controlPaneWidth = 200;
		public static int controlPaneHeight = 600;
		public static int controlPaneX = 0;
		public static int controlPaneY = 0;

	}
	
	public static class TopLayout {
		public static int rootPaneWidth = 900;
		public static int rootPaneHeight = 800;
		public static int gameCanvasWidth = 900;
		public static int gameCanvasHeight = 600;
		public static int gameCanvasX = 0;
		public static int gameCanvasY = 174;
		public static int controlPaneWidth = 900;
		public static int controlPaneHeight = 174;
		public static int controlPaneX = 0;
		public static int controlPaneY = 0;
		
	}
	
	public static class BottomLayout {
		public static int rootPaneWidth = 900;
		public static int rootPaneHeight = 800;
		public static int gameCanvasWidth = 900;
		public static int gameCanvasHeight = 600;
		public static int gameCanvasX = 0;
		public static int gameCanvasY = 0;
		public static int controlPaneWidth = 900;
		public static int controlPaneHeight = 200;
		public static int controlPaneX = 0;
		public static int controlPaneY = 600;
	}
}
