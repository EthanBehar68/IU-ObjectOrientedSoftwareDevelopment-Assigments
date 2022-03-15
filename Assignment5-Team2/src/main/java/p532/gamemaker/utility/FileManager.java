/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 2, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.utility;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import p532.gamemaker.Constants;

public class FileManager {

	private File selectedFile;

	// Singleton Pattern
	private static FileManager instance;

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}
	
	public boolean getAudioFile(Window appWindow) {
		ExtensionFilter extensionFilter = new ExtensionFilter("Audio Files", Constants.getAudioTypes());
		return getLoadFile(appWindow, extensionFilter);
	}

	public boolean getLoadFile(Window appWindow, ExtensionFilter extensionFilter) {
		// Build FileChooser and ExtensionFilter
		FileChooser fileChooser = new FileChooser();

		// Apply the filter
		fileChooser.getExtensionFilters().add(extensionFilter);

		// Attempt to get a file from user.
		setSelectedFile(fileChooser.showOpenDialog(appWindow));

		// If nothing was selected return false
		// Otherwise we selected a file so save it and return true.
		if (getSelectedFile() == null) {
			return false;
		}

		return true;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public File getSelectedFile() {
		return selectedFile;
	}
}
