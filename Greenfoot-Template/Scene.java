import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Scene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scene extends World
{
    static public final int WIDTH = 600, HEIGHT = 800;
    private int gamestate = 1; // 1: Intro screen, 2: Game screen, 3: End screen
    public final MouseInfo mi = Greenfoot.getMouseInfo();
    private static boolean worldCreated = false;
    private final Intro introScreen;
    private final Game gameScreen;
    
    /**
     * Constructor for objects of class Scene.
     * 
     */
    public Scene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
            super(WIDTH, HEIGHT, 1);
        
            introScreen = new Intro(this);
            gameScreen = new Game(this);
            
            nextScene();
    }
    
    private void nextScene()
    {
        switch (gamestate)
        {
            case 1:
                introScreen.setScene();
                break;
            case 2:
                gameScreen.setScene();
                break;
            case 3:
                break;
        }
    }
    
    private void resetScene() {
        List objects = getObjects(null);
        removeObjects(objects);
        getBackground().fill();
        
        nextScene();
    }
    
    public void act() {
        System.out.println(gamestate);
        switch (gamestate)
        {
            case 1: // Intro screen
                introScreen.act();
                
                if (introScreen.getReadyForNextScene()) {
                    gamestate = 2;
                    resetScene();
                }
                break;
            case 2: // Game screen
                gameScreen.act();
                break;
            case 3: // End screen
                break;
        }
    }
}

class Intro
{
    private Scene scene;
    private StartBtn startBtn;
    private boolean readyForNextScene = false; 
    
    Intro(Scene scene)
    {
        this.scene = scene;
    }
    
    public boolean getReadyForNextScene()
    {
        return readyForNextScene;
    }
    
    public void setScene() {
        startBtn = new StartBtn(this.scene.mi);
    
        this.scene.addObject(startBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
        
        this.scene.getBackground().setColor(new Color(100, 100, 100));
        this.scene.getBackground().fill();
    }
    
    public void act() {
        if (startBtn.getClicked())
            readyForNextScene = true;
    }
}

class Game
{
    private Scene scene;
    private boolean readyForNextScene = false; 
    
    Game(Scene scene)
    {
        this.scene = scene;
    }
    
    public boolean getReadyForNextScene()
    {
        return readyForNextScene;
    }
    
    public void setScene() {
        this.scene.getBackground().setColor(new Color(200, 0, 100));
        this.scene.getBackground().fill();
    }
    
    public void act() {

    }
}
