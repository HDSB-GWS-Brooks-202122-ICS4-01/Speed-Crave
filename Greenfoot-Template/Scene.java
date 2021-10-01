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
    
    private Intro introScreen;
    private Game gameScreen;
    private End endScreen;
    
    public final String GAME_NAME = "SPEED CRAVE";
    
    /**
     * Constructor for objects of class Scene.
     * 
     */
    
    public Scene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
            super(WIDTH, HEIGHT, 1, false);
        
            
            nextScene();
    }
    
    private void nextScene()
    {
        switch (gamestate)
        {
            case 1:
                introScreen = new Intro(this);
                introScreen.setScene();
                break;
            case 2:
                gameScreen = new Game(this);
                gameScreen.setScene();
                break;
            case 3:
                endScreen = new End(this);
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
                endScreen.act();
                
                if (endScreen.getReadyForNextScene()) {
                    gamestate = 1;
                    resetScene();
                }
                break;
        }
    }
}

class Intro
{
    private final Scene scene;
    private Btn startBtn;
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
        this.scene.getBackground().setColor(new Color(50, 50, 50));
        this.scene.getBackground().fill();
    
        this.scene.getBackground().setColor(new Color(255, 255, 255));
        this.scene.getBackground().setFont(new Font("Calibre", true, false, 90));
        this.scene.getBackground().drawString(this.scene.GAME_NAME, this.scene.WIDTH/2 - 640/2, 200);
        
        startBtn = new StartBtn();
        this.scene.addObject(startBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
    }
    
    public void act() {
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
    
    private ArrayList<CarAI> carsAI = new ArrayList<CarAI>();
    private int intervalSinceCarHasBeenAdded = 0;
    private int intervalToAddCar = 10;
    
    private Text gameScore;
    
    private int gameSpeed = 10;
    private long interval = System.currentTimeMillis();
    private long startTime = interval;
    
    private Random rand = new Random();
    private int[] spawnLocations = {150, 250, 350, 450, 550};
    
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
    
    private int getCarY(boolean firstRun)
    {
        if (!firstRun)
            return -100 - rand.nextInt(100 * carsAI.size());
        return -200;
    }
    
    private void addCar(boolean firstRun)
    {
        CarAI car;
        int carType = (int)Math.floor(Math.random()*3);
        
        int xPos = getCarX();
        int yPos = getCarY(firstRun);
        
        int speed = (int)Math.floor(Math.random()*(10-3)+2);
        
        if (carType == 0)
            car = new BlueCar(speed, gameSpeed);
        else if (carType == 1)
            car = new GreenCar(speed, gameSpeed);
        else if (carType == 2)
            car = new Ambulance(speed, gameSpeed);
        else
            car = new BlueCar(speed, gameSpeed);
            
        this.scene.addObject(car, xPos, yPos);
        car.setPos(xPos, yPos);
        carsAI.add(car);
    }
    
    public void setScene() {
        this.scene.getBackground().setColor(new Color(0, 0, 0));
        this.scene.getBackground().fill();
        
        for (int i = 1; i <= 2; i++)
        {
            Background bg = new Background(gameSpeed, i, this.scene.WIDTH, this.scene.HEIGHT);
            this.scene.addObject(bg, 0, 0);
            bg.setStartPos();
                
            backgrounds[i - 1] = bg;
        }
        
        player = new Dumbrarri();
        this.scene.addObject(player, PLAYER_START_X, PLAYER_START_Y);
        
        for (int i = 0; i < 3; i++)
        {
            addCar(true);
        }
        
        gameScore = new Text(new Color(255, 255, 255), 20, this.scene.WIDTH, 200);
        this.scene.addObject(gameScore, this.scene.WIDTH / 2, 20);        
    }
    
    private void drawTimeAlive(long currentTime)
    {
        String timeAlive = Integer.toString((int)((currentTime - startTime) / 1000));
        
        gameScore.setText("Time: " + timeAlive);
    }
    
    public void act() {
        long currentTime = System.currentTimeMillis();
        drawTimeAlive(currentTime);
        
        if (intervalSinceCarHasBeenAdded > intervalToAddCar) {
            intervalSinceCarHasBeenAdded = 0;
            intervalToAddCar *= 2;
            
            addCar(false);
        }
        
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
                    car.setPos(getCarX(), getCarY(false)); 
                }
                
                if (player.checkIntersects(car))
                        readyForNextScene = true;
            }
            
            interval = currentTime;
            intervalSinceCarHasBeenAdded++;
        } else {
            for (CarAI car : carsAI)
            {
                if (car.checkCarIsOnExpectedPos())
                    car.checkLaneSwitch(player);
                
                if (car.checkNeedsReset())
                    car.setPos(getCarX(), getCarY(false)); 
                    
                if (player.checkIntersects(car))
                    readyForNextScene = true;

            }
        }
    }
}

class End
{
        private final Scene scene;
        private boolean readyForNextScene = false;
        
        private Btn homeBtn;

        End(Scene scene)
        {
            this.scene = scene;
        }
        
        public void setScene()
        {
            this.scene.getBackground().setColor(new Color(93, 173, 226));
            this.scene.getBackground().fill();
            
            homeBtn = new HomeBtn();
            this.scene.addObject(homeBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
        }
        
        public boolean getReadyForNextScene()
        {
            return readyForNextScene;
        }
        
        public void act()
        {
            if (homeBtn.getClicked())
                readyForNextScene = true;
        }
}