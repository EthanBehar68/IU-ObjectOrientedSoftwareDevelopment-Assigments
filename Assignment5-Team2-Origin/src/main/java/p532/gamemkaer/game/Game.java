package p532.gamemkaer.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import p532.gamemaker.sprite.Sprite;


/**
 * 
 * @author ramya
 * 
 *
 */
public class Game 
{
	private String GameName;
	private List<GameLevel> levels ;

	//default Constructor
	
	public Game()
	{
		this.GameName="Sample Game";
		levels =  new LinkedList<>();
	}
	
	
	public Game(String GameName)
	{
		this.GameName=GameName;
		levels =  new LinkedList<>();
	}
	
	
	// set a name to the game we create 
	
	public void setGameName(String GameName)
	{
		this.GameName=GameName;

	}
	
	//get the game name
	
	public String getGameName()
	{
		return this.GameName;
	}
	
	// get the no of Levels in a game 
	
	public int getLevelCount()
	{
		return (levels.size());
	}
	
	// Add a new level to the Game Object 
	
	public void addLevel( GameLevel gamelevel)
	{
		levels.add(gamelevel);
	}
	
	// return the specified game level 
	
	public List<GameLevel> getLevel()
	{
		return levels;
	}
	
	

}
