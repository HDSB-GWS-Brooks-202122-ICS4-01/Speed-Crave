import greenfoot.*;
import java.util.*;

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
    
    private Random rand = new Random();
    private int[] spawnLocations = {150, 250, 350, 450, 550};
    
    private final int PLAYER_START_X, PLAYER_START_Y;
    
    private Background[] backgrounds = new Background[2];
    
    private Car player;
    private final String CAR;
    private final GasolineBar G_BAR = new GasolineBar(150, 30);
    
    private final ArrayList<CarAI> carsAI = new ArrayList<CarAI>();
    private final String[] CAR_PATHS = { "car_BlackOut", "car_BlueStrip", "car_GreenStrip", "car_PinkStrip", "car_RedStrip", "car_WhiteStrip",  "car01-n", "car02-n", "car03-n", "ambulance-n"};
    private final int CAR_PATHS_LENGTH = CAR_PATHS.length;
    
    private Gas gas;
    
    private boolean gameover = false;
    private long timeSinceGameover = 0;
    
    private int score;
    
    Game(Scene scene, String car)
    {
        this.scene = scene;
        CAR = car;
        
        PLAYER_START_X = scene.WIDTH / 2;
        PLAYER_START_Y = scene.HEIGHT - 200;
    }
    
    public int getNextState()
    {
        return nextState;
    }
    
    private void setScore(long currentTime)
    {
        score = (int)((currentTime - startTime) / 1000);
    }
    
    public int getScore()
    {
        return score;
    }
    
    private int getSpawnX()
    {
        return spawnLocations[rand.nextInt(spawnLocations.length)];
    }
    
    private int getSpawnY(boolean firstRun)
    {
        if (!firstRun)
            return -100 - rand.nextInt(100 * carsAI.size());
            
        return -200;
    }
    
    private void addCar(boolean firstRun)
    {
        CarAI car;
        int carType = rand.nextInt(CAR_PATHS_LENGTH);
        
        int xPos = getSpawnX();
        int yPos = getSpawnY(firstRun);
        
        int speed = (int)Math.floor(Math.random()*(5-2)+1);
        
        
        car = new CarAI(speed, gameSpeed, ".\\images\\" + CAR_PATHS[carType] + ".png");
            
        scene.addObject(car, xPos, yPos);
        car.setPos(xPos, yPos);
        carsAI.add(car);
    }
    
    public void setScene() {
        scene.getBackground().setColor(new Color(0, 0, 0));
        scene.getBackground().fill();
        
        for (int i = 1; i <= 2; i++)
        {
            Background bg = new Background(gameSpeed, i, scene.HEIGHT);
            scene.addObject(bg, scene.WIDTH / 2, 0);
            bg.setStartPos();
                
            backgrounds[i - 1] = bg;
        }
        
        gas = new Gas();
        scene.addObject(gas, getSpawnX(), -200);
        
        player = new Car(this.CAR, G_BAR);
        scene.addObject(player, PLAYER_START_X, PLAYER_START_Y);
        
        for (int i = 0; i < 3; i++)
        {
            addCar(true);
        }
        
        gameScore = new Text(Color.WHITE, new greenfoot.Font("MONOSPACED", true, false, 40));
        scene.addObject(gameScore, scene.WIDTH / 2, 30);    
        
        scene.addObject(G_BAR, 5, scene.HEIGHT - 5);
        G_BAR.alignLeftX();
        G_BAR.alignBottomY();
        
        scene.setPaintOrder(Text.class, GasolineBar.class, Car.class, CarAI.class, Gas.class, Background.class);
    }
    
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
                
        gameover = true;
        timeSinceGameover = currentTime;
        
        gameScore.setColor(Color.RED);
        gameScore.setPos(scene.WIDTH / 2, scene.HEIGHT / 2);
        if (crashed)
            gameScore.setText("You Crashed!");
        else
            gameScore.setText("You Ran Out Of Gas!");
    }
    
    private void drawTimeAlive(long currentTime)
    {
        setScore(currentTime);
        String timeAlive = Integer.toString(getScore());
        
        gameScore.setText("SCORE: " + timeAlive);
    }
    
    public void act() {
        long currentTime = System.currentTimeMillis();
                
        if (!gameover) {
            drawTimeAlive(currentTime);
            
            if (currentTime - interval > 5000 && gameSpeed < 20) {
                gameSpeed++;

                interval = currentTime;
                intervalSinceCarHasBeenAdded++;
                
                gas.setGameSpeed(gameSpeed);
            }
            
            if (intervalSinceCarHasBeenAdded >= 2) {
                intervalSinceCarHasBeenAdded = 0;
                
                addCar(false);
            }
            
            if (player.checkOutOfFuel()) {
                setActorStatesToGameover(currentTime, false);
            }
            
            if (gas.getDoneForTheRun())
                gas.reset(getSpawnX(), -400);
            
            if (player.checkGasIntersects(gas)) {
                player.addFuel();
                gas.reset(getSpawnX(), -400);
            }
            
            for (Background bg : backgrounds) {
                bg.setSpeed(gameSpeed);
            }
                
            for (CarAI car : carsAI) {
                car.setGameSpeed(gameSpeed);
                
                if (car.checkCarIsOnExpectedPos())
                    car.checkLaneSwitch(player);
                
                if (car.checkNeedsReset())
                {
                    car.setPos(getSpawnX(), getSpawnY(false)); 
                }
                
                if (player.checkCarIntersects(car)) {
                    setActorStatesToGameover(currentTime, true);
                    car.setMoveSpeed(0);
                }
            }
        } else if (currentTime - timeSinceGameover > 5000)
            nextState = 3;
    }
}