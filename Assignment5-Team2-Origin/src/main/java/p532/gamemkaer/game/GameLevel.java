package p532.gamemkaer.game;

import java.util.LinkedList;
import java.util.List;

import p532.gamemaker.sprite.Sprite;

public class GameLevel {
	
	private String levelName;
	private List<Sprite> Sprites ;
	 
	//default Constructor 
	 
	public GameLevel()
	{
		this.levelName="Level 1"; // default value 
		Sprites = new LinkedList<>();	
	}
	
	public GameLevel(String levelName)
	{
		this.levelName=levelName;
		Sprites = new LinkedList<>();
	}
	
	
	public void setLevelName(String levelName)
	{
		this.levelName=levelName;
	}
	
	
	public List<Sprite>  getSprites()
	{
		return Sprites;
	}
	
	
	public void addSprite(int level, Sprite sprite)
	{
		Sprites.add(sprite);
	}
	
	public void setAllSpritesToLevel(List<Sprite> sprites)
	{
		this.Sprites=Sprites;
	}

}
