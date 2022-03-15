package p532.gamemaker.junit.tests;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;
//import org.w3c.dom.css.Rect;
import p532.gamemaker.sprite.GameRectangle;
import p532.gamemaker.sprite.Sprite;
import p532.gamemaker.utility.saveload.SaveUtility;

//import java.awt.*;
import java.io.IOException;

public class SaveLoadSpriteTest
{
    @Test
    public void saveRectangleTest() throws ClassNotFoundException, IOException
    {
        //Sprite defaultSprite = Sprite.defaultSprite();

        //Rectangle rectangle = new Rectangle();
        GameRectangle gameRectangle = new GameRectangle();

        //SaveUtility.saveEntireGameDesign();

        /*Ball ball = new StandardBall(new Position(TestData.ballPosX, TestData.ballPosY));
        BallController bcl=new BallController(ball);

        List <Controller> controller=new LinkedList<>();
        controller.add(bcl);
        SaveUtility.saveEntireGame(TestData.SAVE_FILE_PATH,controller);

        FileInputStream fileIn = new FileInputStream(TestData.SAVE_FILE_PATH);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        //Read the entire save file
        String saveData = (String) in.readObject();
        System.out.println(saveData);
        System.out.println(TestData.expected);

        assertTrue(saveData.equals(TestData.expected));*/
    }
}
