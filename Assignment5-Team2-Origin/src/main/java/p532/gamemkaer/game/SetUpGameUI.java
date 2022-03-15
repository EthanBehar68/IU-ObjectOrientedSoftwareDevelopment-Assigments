package p532.gamemkaer.game;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import p532.gamemaker.sprite.Sprite;


public class SetUpGameUI {
	
    private Label label=new Label();
    private String  gameName="",levelname="";
    private Scene scene;
    
    public SetUpGameUI(Scene scene)
    {
    	this.scene=scene;
        
    }
	public  String  setGameName()
	{
		TextInputDialog dialog = new TextInputDialog("");

    	dialog.setTitle("New Game");
    	dialog.setHeaderText("Give a name to your Game ");
    	dialog.setContentText("Name:");

    	Optional<String> result = dialog.showAndWait();
    	 if(result.isPresent())
    	 {
    		 gameName=result.get();
    	 }
    	 
    	 return gameName;
	}
	
	
	public  String  setLevel()
	{
		TextInputDialog dialog = new TextInputDialog("");

    	dialog.setTitle("New Level");
    	dialog.setHeaderText("Set up new Level ");
    	dialog.setContentText("Level Name:");

    	Optional<String> result = dialog.showAndWait();

    	if(result.isPresent())
   	 	{
    		levelname=result.get();
   	 	}
    	
    	return levelname;
	}
	

}
