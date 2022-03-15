/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 19, 2021
 * @editors:
 **/
package composite.pattern;

import com.google.gson.JsonObject;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class BreakoutJsonUtility {

	public static class Serializer {

		public static String serializeString(String key, String value) {
			// Example:
			// "class": "command.pattern.BallMoveCommand"
			return ("\"" + key + "\":\"" + value + "\"");
		}

		public static String serializeInt(String key, int value) {
			// Example:
			// "ball": 63
			return ("\"" + key + "\":" + value);
		}

		public static String serializeDouble(String key, double value) {
			// Example:
			// "ball": 63.000
			return ("\"" + key + "\":" + value);
		}
		
		public static String serializeFloat(String key, float value) {
			// Example:
			// "ball": 63.000
			return ("\"" + key + "\":" + value);
		}
		
		public static String serializePoint2D(String key, Point2D value) {
			// Example:
			// "undoPositionX": 588.1476196751464,
            // "undoPositionY": 583.1476196751464
			return ("\"" + key + "X" + "\":" + value.getX() 
			+ ",\"" + key + "Y" + "\":" + value.getY());
		}
		
		public static String serializeBoolean(String key, boolean value) {
			// Example:
			// "wasExecuted": false
			return ("\"" + key + "X" + "\":" + value);
		}
	}

	public static class Deserializer {
		
		public static String deserializeString(String key, JsonObject json) {
			return json.get(key).getAsString();
		}

		public static int deserializeInt(String key, JsonObject json) {
			return json.get(key).getAsInt();
		}

		public static Double deserializeDouble(String key, JsonObject json) {
			return json.get(key).getAsDouble();
		}

		public static Float deserializeFloat(String key, JsonObject json) {
			return json.get(key).getAsFloat();
		}
		
		public static Point2D deserializePoint2D(String key, JsonObject json) {
			return new Point2D(json.get(key + 'X').getAsDouble(), json.get(key + 'Y').getAsDouble());
		}
		
		public static Boolean deserializeBoolean(String key, JsonObject json) {
			return json.get(key).getAsBoolean();
		}
		
		public static Color deserializeColor(String key, JsonObject json) {
			return Color.web(deserializeString(key, json));
		}
	}
}
