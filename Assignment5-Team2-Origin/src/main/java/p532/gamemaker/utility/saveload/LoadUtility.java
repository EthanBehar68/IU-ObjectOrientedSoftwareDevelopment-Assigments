package p532.gamemaker.utility.saveload;

import p532.gamemaker.sprite.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LoadUtility
{
    /**
     * Loads an array of Levels from the "save-data.txt" JSON file.
     * This has to parse each Level then push all Levels in the array
     * into a LinkedList.
     * @return a LinkedList<Level> containing all the Levels needed to be inserted
     * into GameManager.
     * @throws ClassNotFoundException when there is a problem reading the JSON data.
     */
    public static ArrayList<Level> loadEntireGameDesign() throws ClassNotFoundException
    {
        final String SAVE_FILE_PATH = "saved-game-design.txt";
        return loadEntireGameDesign(SAVE_FILE_PATH);
    }
    
    public static ArrayList<Level> loadEntireGameDesign(String SAVE_FILE_PATH) throws ClassNotFoundException
    {

        ArrayList<Level> newLevelList = new ArrayList<>();
        try
        {
            //Open the save file
            FileInputStream fileIn = new FileInputStream(SAVE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            //Read the entire save file
            String saveData = (String) in.readObject();

            //Read an array of level objects from the file
            CustomObjectMapper mapper = new CustomObjectMapper();
            Level[] ctrlArr = mapper.readValue(saveData, Level[].class);

            //Convert level array to level list
            for (int i = 0; i < ctrlArr.length; i++)
            {
                Level level = ctrlArr[i];
                if (level != null)
                {
                    newLevelList.add(level);
                }
            }

            in.close();
            fileIn.close();
        }
        catch (IOException i)
        {
            System.out.println("There was a problem loading save data");
            i.printStackTrace();
        }

        return newLevelList;
    }

    /**
     * Deserializes a game (list of levels) from the input JSON
     * @param gameJson a JSON array of JSON objects
     * @return an ArrayList of Levels loaded in from the JSON or
     * an empty list if there was a problem.
     */
    public static ArrayList<Level> deserializeGameFromJson(String gameJson)
    {
        ArrayList<Level> newLevelList = new ArrayList<>();
        try
        {
            //Read an array of level objects from the string
            CustomObjectMapper mapper = new CustomObjectMapper();
            Level[] ctrlArr = mapper.readValue(gameJson, Level[].class);

            //Convert level array to level list
            for (int i = 0; i < ctrlArr.length; i++)
            {
                Level level = ctrlArr[i];
                if (level != null)
                {
                    newLevelList.add(level);
                }
            }
        }
        catch (IOException i)
        {
            System.out.println("There was a problem in deserializing the game data");
            i.printStackTrace();
        }

        return newLevelList;
    }
}
