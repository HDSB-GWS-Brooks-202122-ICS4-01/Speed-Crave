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
    
    private final Intro introScreen;
    private final Game gameScreen;
    private final End endScreen;
    
    public final String GAME_NAME = "SPEED CRAVE";
    
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
            endScreen = new End(this);
            
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
                endScreen.setScene();
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
                
                if (gameScreen.getReadyForNextScene()) {
                    gamestate = 3;
                    resetScene();
                }
                break;
            case 3: // End screen
                break;
        }
    }
}

class Intro
{
    private final Scene scene;
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
    
        this.scene.getBackground().setColor(new Color(255, 255, 255));
        this.scene.getBackground().setFont(new Font("Calibre", true, false, 90));
        this.scene.getBackground().drawString(this.scene.GAME_NAME, 0, 200);
        
        startBtn = new StartBtn(this.scene.mi);
    
        this.scene.addObject(startBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
    }
    
    public void act() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        System.out.println(mouse.getX());
        if (startBtn.getClicked())
            readyForNextScene = true;
    }
}

class Game
{
    private final Scene scene;
    private boolean readyForNextScene = false; 
    private final int PLAYER_START_X, PLAYER_START_Y;
    private Background[] backgrounds = new Background[2];
    private Car player;
    private int gameSpeed = 1;
    private long interval = System.currentTimeMillis();
    private long startTime = interval;
    
    private Random rand = new Random();
    private int[] spawnLocations = {150, 250, 350, 450, 550};
    private ArrayList<CarAI> carsAI = new ArrayList<CarAI>();
    
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
    
    private int getCarX()
    {
        return spawnLocations[rand.nextInt(spawnLocations.length - 1)];
    }
    
    private int getCarY()
    {
        return -100 - rand.nextInt(200);
    }
    
    public void setScene() {
        this.scene.getBackground().setColor(new Color(0, 0, 0));
        this.scene.getBackground().fill();
        
        for (int i = 1; i <= 2; i++)
        {
            Background bg = new Background(i, this.scene.WIDTH, this.scene.HEIGHT);
            this.scene.addObject(bg, 0, 0);
            bg.setStartPos();
                
            backgrounds[i - 1] = bg;
        }
        
        for (int i = 0; i < 3; i++)
        {
            CarAI car;
            int carType = (int)Math.floor(Math.random()*3);
            
            if (carType == 0)
                car = new BlueCar((int)Math.floor(Math.random()*(10-3)+2), gameSpeed);
            else if (carType == 1)
                car = new GreenCar((int)Math.floor(Math.random()*(10-3)+2), gameSpeed);
            else if (carType == 2)
                car = new Ambulance((int)Math.floor(Math.random()*(10-3)+2), gameSpeed);
            else
                car = new BlueCar((int)Math.floor(Math.random()*(10-3)+2), gameSpeed);
                
            this.scene.addObject(car, getCarX(), getCarY());
            carsAI.add(car);
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
            
            for (CarAI car : carsAI)
            {
                car.incrementSpeed();
                
                if (car.checkNeedsReset())
                {
                    car.setPos(getCarX(), getCarY()); 
                }
                
                if (player.checkIntersects(car))
                        readyForNextScene = true;
            }
            
            interval = currentTime;
        } else {
            for (CarAI car : carsAI)
            {
                if (car.checkNeedsReset())
                    car.setPos(getCarX(), getCarY()); 
                    
                if (player.checkIntersects(car))
                    readyForNextScene = true;

            }
        }
    }
}

class End
{
        private final Scene scene;
        
        End(Scene scene)
        {
            this.scene = scene;
        }
        
        public void setScene()
        {
            this.scene.getBackground().setColor(new Color(93, 173, 226));
            this.scene.getBackground().fill();
        }
}