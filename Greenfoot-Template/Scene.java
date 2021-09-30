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
    static public final int WIDTH = 700, HEIGHT = 900;
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
            super(WIDTH, HEIGHT, 1, false);
        
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
        this.scene.getBackground().setColor(new Color(200, 200, 200));
        this.scene.getBackground().fill();
        
        startBtn = new StartBtn(this.scene.mi);
    
        this.scene.addObject(startBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
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
    private final int PLAYER_START_X, PLAYER_START_Y;
    private ArrayList<Background> backgrounds = new ArrayList<Background>();
    private Car player;
    private int gameSpeed = 1;
    private long interval = System.currentTimeMillis();
    
    Game(Scene scene)
    {
        this.scene = scene;
        
        PLAYER_START_X = this.scene.WIDTH / 2;
        PLAYER_START_Y = this.scene.HEIGHT - 200;
    }
    
    public boolean getReadyForNextScene()
    {
        return readyForNextScene;
    }
    
    public void setScene() {
        this.scene.getBackground().setColor(new Color(0, 0, 0));
        this.scene.getBackground().fill();
        
        for (int i = 1; i <= 2; i++)
            {
                Background bg = new Background(i, this.scene.WIDTH, this.scene.HEIGHT);
                this.scene.addObject(bg, 0, 0);
                bg.setStartPos();
                
                backgrounds.add(bg);
            }
        
        player = new Dumbrarri();
        this.scene.addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }
    
    public void act() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - interval > 5000) {
            gameSpeed++;
            for (Background bg : backgrounds)
            {
                bg.setSpeed(gameSpeed);
            }
            
            interval = currentTime;
        }
    }
}
