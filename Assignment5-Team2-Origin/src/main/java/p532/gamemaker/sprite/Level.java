package p532.gamemaker.sprite;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * An aggregate class that holds all Sprites held in a level
 */
public class Level
{
    private List<Sprite> allSprites = new LinkedList<>();
    @JsonIgnore
    private final LinkedList<String> allUserDefinedTypes = new LinkedList<>();

    public Level() {
    }

    public List<Sprite> getAllSprites() {
        return allSprites;
    }

    public void setAllSprites(List<Sprite> allSprites) {
        this.allSprites = allSprites;
        updateUserDefinedTypesList();
    }

    public void addSprite(Sprite sprite)
    {
        //Add the sprite to the collection
        allSprites.add(sprite);
        updateUserDefinedTypesList();
    }

    public LinkedList<String> getAllUserDefinedTypes() {
        return allUserDefinedTypes;
    }

    /**
     * Updates allUserDefinedTypes with all the types
     * from each Sprite in the allSprites list.
     */
    public void updateUserDefinedTypesList()
    {
        HashSet<String> types = new HashSet<>(); //prevents duplicates

        //Re-define the list of all sprite types
        allUserDefinedTypes.clear();
        for (Sprite sprite : allSprites)
        {
            String spriteType = sprite.getUserDefinedType();
            //If the sprite type is not a duplicate, add it to the type list
            if (!types.contains(spriteType))
            {
                allUserDefinedTypes.add(spriteType);
                types.add(spriteType);
            }
        }
    }
}
