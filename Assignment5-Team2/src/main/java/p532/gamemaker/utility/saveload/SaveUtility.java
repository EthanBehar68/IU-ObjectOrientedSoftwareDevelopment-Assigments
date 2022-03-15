package p532.gamemaker.utility.saveload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Level;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Serializes the frame history and saves it to a file. Source:
 * https://www.tutorialspoint.com/java/java_serialization.htm
 */
public class SaveUtility {

	public static void saveEntireGameDesign(List<Level> levelList) {
		CustomObjectMapper mapper = new CustomObjectMapper();
		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // allow saving of Strategy objects as {}
		try {
			// Convert the levelList to an array
			Level[] levelArray = new Level[levelList.size()];
			int i = 0;
			for (Level level : levelList) {
				levelArray[i++] = (level);
			}
			// Convert the level array to JSON
			String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(levelArray);

			// Open a file to write the JSON to
			try (FileOutputStream outFile = new FileOutputStream(Constants.DEFAULT_SAVE_FILE_PATH)) {
				try (ObjectOutputStream out = new ObjectOutputStream(outFile)) {

					// Write the JSON to the file
					out.writeObject(jsonResult);
				}
			}
		}
		// Handle JsonProcessingException, FileNotFoundException, and others
		catch (IOException e) {
		}
	}

	/**
	 * Serializes the levelList into JSON.
	 * 
	 * @param levelList all Levels for a game
	 * @return a complete JSON array of JSON objects or an empty string if there was
	 *         a problem.
	 */
	public static String serializeGameToJson(List<Level> levelList) {
		// Prepare an ObjectMapper that will produce the JSON
		CustomObjectMapper mapper = new CustomObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// Convert the levelList to an array
		Level[] levelArray = new Level[levelList.size()];
		int i = 0;
		for (Level level : levelList) {
			levelArray[i++] = (level);
		}
		try {
			// Convert the level array to a string
			String jsonResult = mapper.writeValueAsString(levelArray);
			return jsonResult;
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
