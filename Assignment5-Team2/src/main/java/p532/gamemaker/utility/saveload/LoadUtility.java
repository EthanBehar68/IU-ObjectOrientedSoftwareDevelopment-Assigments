package p532.gamemaker.utility.saveload;

import p532.gamemaker.Constants;
import p532.gamemaker.sprite.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LoadUtility {
	/**
	 * Loads an array of Levels from the "save-data.txt" JSON file. This has to
	 * parse each Level then push all Levels in the array into a LinkedList.
	 * 
	 * @return a LinkedList<Level> containing all the Levels needed to be inserted
	 *         into GameManager.
	 * @throws ClassNotFoundException when there is a problem reading the JSON data.
	 */
	public static List<Level> loadEntireGameDesign() throws ClassNotFoundException {

		List<Level> newLevelList = new ArrayList<>();
		// Open the save file
		try (FileInputStream fileIn = new FileInputStream(Constants.DEFAULT_SAVE_FILE_PATH)) {
			try (ObjectInputStream in = new ObjectInputStream(fileIn)) {

				// Read the entire save file
				String saveData = (String) in.readObject();

				// Read an array of level objects from the file
				CustomObjectMapper mapper = new CustomObjectMapper();
				Level[] ctrlArr = mapper.readValue(saveData, Level[].class);

				// Convert level array to level list
				for (int i = 0; i < ctrlArr.length; i++) {
					Level level = ctrlArr[i];
					if (level != null) {
						newLevelList.add(level);
					}
				}
			}
		} catch (IOException i) {
		}

		return newLevelList;
	}

	/**
	 * Deserializes a game (list of levels) from the input JSON
	 * 
	 * @param gameJson a JSON array of JSON objects
	 * @return an ArrayList of Levels loaded in from the JSON or an empty list if
	 *         there was a problem.
	 */
	public static List<Level> deserializeGameFromJson(String gameJson) {
		List<Level> newLevelList = new ArrayList<>();
		try {
			// Read an array of level objects from the string
			CustomObjectMapper mapper = new CustomObjectMapper();
			Level[] ctrlArr = mapper.readValue(gameJson, Level[].class);

			// Convert level array to level list
			for (int i = 0; i < ctrlArr.length; i++) {
				Level level = ctrlArr[i];
				if (level != null) {
					newLevelList.add(level);
				}
			}
		} catch (IOException i) {
		}

		return newLevelList;
	}
}
