/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package application;

import java.io.File;
import java.nio.file.Files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import composite.pattern.RootSavable;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SaveLoadManager {

	private File saveFile;

	public SaveLoadManager() {
	}

	public void save(RootSavable saveMe, Stage gameStage) throws Exception {
		if (!getSaveLocation(gameStage)) {
			// Meaning user failed to pick a file.
			// Quit the save process;
			return;
		}

		// Build json string from SavableContainer
		String saveJson = saveMe.save(true);

		// Save string to file
		Files.write(saveFile.toPath(), saveJson.getBytes());
	}

	public RootSavable load(Stage gameStage) throws Exception {
		if (!getLoadLocation(gameStage)) {
			// Meaning user failed to pick a file.
			// Quit the save process;
			return new RootSavable(false);
		}

		// Read in bytes and convert to string
		byte[] jsonBytes = Files.readAllBytes(saveFile.toPath());
		String jsonString = new String(jsonBytes);

		// Convert String to Json
		JsonObject jsonRootSavable = JsonParser.parseString(jsonString).getAsJsonObject();

		// Create RootSavable and load via JsonObject
		RootSavable rootSavable = new RootSavable();
		rootSavable.load(jsonRootSavable);

		// Rebuild the references of the commands
		rootSavable.rebuildReferences();
		
		// Successful!
		rootSavable.setSuccesfulLoad(true);

		return rootSavable;
	}

	private boolean getSaveLocation(Stage gameStage) {
		// Build FileChooser and ExtensionFilter
		FileChooser fileChooser = new FileChooser();
		// Makes it so only our file types are selectable
		ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Breakout Application Saved Data", "*.basd");

		// Apply the filter
		fileChooser.getExtensionFilters().add(extensionFilter);

		// Attempt to get a file from user.
		File selectedFile = fileChooser.showSaveDialog(gameStage);

		// If nothing was selected return false
		// Otherwise we selected a file so save it and return true.
		if (selectedFile == null) {
			System.out.println("No file was selected.");
			return false;
		} else {
			System.out.println(selectedFile.getAbsolutePath());
			saveFile = selectedFile;
			return true;
		}
	}

	private boolean getLoadLocation(Stage gameStage) {
		// Build FileChooser and ExtensionFilter
		FileChooser fileChooser = new FileChooser();
		// Makes it so only our file types are selectable
		ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Breakout Application Saved Data", "*.basd");

		// Apply the filter
		fileChooser.getExtensionFilters().add(extensionFilter);

		// Attempt to get a file from user.
		File selectedFile = fileChooser.showOpenDialog(gameStage);

		// If nothing was selected return false
		// Otherwise we selected a file so save it and return true.
		if (selectedFile == null) {
			System.out.println("No file was selected.");
			return false;
		} else {
			System.out.println(selectedFile.getAbsolutePath());
			saveFile = selectedFile;
			return true;
		}
	}
}
