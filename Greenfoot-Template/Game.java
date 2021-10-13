import greenfoot.*;
import java.util.*;
import java.io.*;

class Game
{
    private final Scene scene;
    private int nextState = 0; 
    
    private int intervalSinceCarHasBeenAdded = 0;
    private int intervalToAddCar = 1;
    
    private Text gameScore;
    
    private int gameSpeed = 5;
    private long interval = System.currentTimeMillis();
    private long startTime = interval;
    private long scoreInterval = interval;
    
    private Random rand = new Random();
    private int[] spawnLocations = {150, 250, 350, 450, 550};
    
    private final int PLAYER_START_X, PLAYER_START_Y;
    
    private Background[] backgrounds = new Background[2];
    
    private Car player;
    private final String CAR;
    private final Bar G_BAR = new Bar(150, 30);
    
    private final ArrayList<CarAI> carsAI = new ArrayList<CarAI>();
    private final String[] CAR_PATHS = { "car_BlackOut", "car_BlueStrip", "car_GreenStrip", "car_PinkStrip", "car_RedStrip", "car_WhiteStrip",  "car01-n", "car02-n", "car03-n", "ambulance-n"};
    private final int CAR_PATHS_LENGTH = CAR_PATHS.length;
    private final boolean GTA_MODE = false;
    
    private Gas gas;
    private OilPuddle oil;
    
    private boolean gameover = false;
    private long timeSinceGameover = 0;
    
    private int score = 0;
    
    /**
     * Constructor for the game class.
     * @param scene     The world.
     * @param car       The path of the car (player's selected car).
     */
    Game(Scene scene, String car)
    {   
        this.scene = scene;
        CAR = car;
        
        PLAYER_START_X = scene.WIDTH / 2;
        PLAYER_START_Y = scene.HEIGHT - 200;
    }
    
    /**
     * This method returns an integer value corresponding with a new game state.
     * @return      Integer value for the next state. 0 indicates that the game state should not change.
     */
    public int getNextState()
    {
        return nextState;
    }
    
    /**
     * This method will return the user's score.
     * @return int      A value greater than 0 corresponding to the player's score.
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * This method retutrns a random integer within a given list.
     * @return int      Some random integer value from a list.
     */
    private int getSpawnX()
    {
        return spawnLocations[rand.nextInt(spawnLocations.length)];
    }
    
    /**
     * This method retutns a random integer within a range.
     * @param firstRun      Boolean value for if the game is in it's initializing state.
     * @return int          Negative integer value, with a maximum of -100.
     */
    private int getSpawnY(boolean firstRun)
    {
        if (!firstRun)
            return -100 - rand.nextInt(100 * carsAI.size());
            
        return -200;
    }
    
    /**
     * This method handles adding a car to the world.
     * @param firstRun      Boolean value for if the game is in it's initializing state.
     */
    private void addCar(boolean firstRun)
    {
        CarAI car;
        int carType = rand.nextInt(CAR_PATHS_LENGTH);
        
        int xPos = getSpawnX();             // Random x position
        int yPos = getSpawnY(firstRun);     // y = -200
        
        // Generate random speed between 2-8.
        int speed = (int)Math.floor(Math.random()*(8-3)+2);
        
        
        car = new CarAI(speed, gameSpeed, ".\\images\\" + CAR_PATHS[carType] + ".png", GTA_MODE);
        
        // Add car to world and array.
        scene.addObject(car, xPos, yPos);
        car.setPos(xPos, yPos);
        carsAI.add(car);
    }
    
    /**
     * This method sets the game screen scene.
     */
    public void setScene() {
        // Add backgrounds to world
        for (int i = 1; i <= 2; i++)
        {
            Background bg = new Background(gameSpeed, i, scene.HEIGHT);
            scene.addObject(bg, scene.WIDTH / 2, 0);
            bg.setStartPos();
                
            backgrounds[i - 1] = bg;
        }
        
        // Add the gasoline to the world
        gas = new Gas();
        scene.addObject(gas, getSpawnX(), -200);
        
        oil = new OilPuddle();
        scene.addObject(oil, getSpawnX(), -300);
        
        // Add the player to the world
        player = new Car(this.CAR, G_BAR);
        scene.addObject(player, PLAYER_START_X, PLAYER_START_Y);
        
        // Add 3 cars in the world
        for (int i = 0; i < 3; i++)
        {
            addCar(true);
        }
        
        // Add the score text
        gameScore = new Text(Color.WHITE, new greenfoot.Font("MONOSPACED", true, false, 40));
        scene.addObject(gameScore, scene.WIDTH / 2, 30);    
        
        // Add the fuel bar
        scene.addObject(G_BAR, 5, scene.HEIGHT - 5);
        G_BAR.alignLeftX();
        G_BAR.alignBottomY();
        
        // Set the draw order of the objects
        scene.setPaintOrder(Text.class, Bar.class, Car.class, CarAI.class, Gas.class, OilPuddle.class, Background.class);
    }
    
    /**
     * This method sets all cars and the background to have gameSpeed of 0.
     * @param currentTime       Long value for time at which the player crashed or ran out of fuel.
     * @param crashed           Boolean value. If true that means the player crashed, if falls it means the player ran out of gas.
     */
    private void setActorStatesToGameover(long currentTime, boolean crashed)
    {
        gameSpeed = 0;
        
        for (Background bg : backgrounds)
        {
            bg.setSpeed(gameSpeed);
        }
        
        for (CarAI car : carsAI)
        {
            car.setGameSpeed(gameSpeed);
        }
        
        player.setAllowedToMove(false);
        
        gas.setGameSpeed(0);
        oil.setGameSpeed(0);
                
        gameover = true;
        timeSinceGameover = currentTime;
        
        gameScore.setColor(Color.RED);
        gameScore.setPos(scene.WIDTH / 2, scene.HEIGHT / 2);
        if (crashed)
            gameScore.setText("You Crashed!");
        else
            gameScore.setText("You Ran Out Of Gas!");
    }
    
    /**
     * This method updates the score text.
     */
    private void drawTimeAlive()
    {
        String timeAlive = Integer.toString(getScore());
        
        gameScore.setText("SCORE: " + timeAlive);
    }
    
    /**
     * Act - do whatever the Game wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        long currentTime = System.currentTimeMillis();
                
        if (!gameover) {
            drawTimeAlive();
            
            // This is needed because if the user pauses the program it will give them a huge score boost because time elapsed since program starts still grows.
            if (currentTime - scoreInterval >= 1000) {
                score++;
                scoreInterval = currentTime;
            }
            
            // Is true at 5 second intervals.
            if (currentTime - interval >= 5000 && gameSpeed < 20) {
                gameSpeed++;

                interval = currentTime;
                intervalSinceCarHasBeenAdded++;
                
                player.setGameSpeed(gameSpeed);
                
                gas.setGameSpeed(gameSpeed);
                oil.setGameSpeed(gameSpeed);
                
                for (Background bg : backgrounds) {
                    bg.setSpeed(gameSpeed);
                }
            }
            
            // adds a car every 10 seconds.
            if (intervalSinceCarHasBeenAdded >= 2) {
                intervalSinceCarHasBeenAdded = 0;
                
                addCar(false);
            }
            
            // Is true if the player has no gas left.
            if (player.checkOutOfFuel()) {
                setActorStatesToGameover(currentTime, false);
            }
            
            // If the gas is off the screen, reset it's position.
            if (gas.getDoneForTheRun())
                gas.reset(getSpawnX(), -400);
                
            // If the gas is off the screen, reset it's position.
            if (oil.getDoneForTheRun())
                oil.reset(getSpawnX(), -400);
            
            // True if the player collides with the gas.
            if (player.checkGasIntersects(gas)) {
                player.addFuel();
                gas.reset(getSpawnX(), -400);
            } 
            
            // True if the player collides with a gas puddle
            if (player.checkOilIntersects(oil)) {
                player.setSlipperyMode(true);
            }
                
            for (CarAI car : carsAI) {
                car.setGameSpeed(gameSpeed);
                
                // If the care isn't already switching lanes => call a method that sees if the AI car can switch lanes and sabotage the player.
                if (car.checkCarIsOnExpectedPos())
                    car.checkLaneSwitch(player);
                
                // Checks if the AI car is off screen, if so it will have it's positions reset.
                if (car.checkNeedsReset())
                    car.setPos(getSpawnX(), getSpawnY(false)); 
                
                // True if the player collides with the car ai
                if (player.checkCarIntersects(car)) {
                    setActorStatesToGameover(currentTime, true);
                    car.setMoveSpeed(0);
                }
            }
        } else if (currentTime - timeSinceGameover >= 3000)
            nextState = 3;
    }
}