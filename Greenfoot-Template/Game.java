import greenfoot.*;
import java.util.*;

class Game
{
    private final Scene scene;
    private int nextState = 0; 
    private final int PLAYER_START_X, PLAYER_START_Y;
    
    private Background[] backgrounds = new Background[2];
    private Car player;
    private final String car;
    
    private final ArrayList<CarAI> carsAI = new ArrayList<CarAI>();
    private final String[] CAR_PATHS = { "car_BlackOut", "car_BlueStrip", "car_GreenStrip", "car_PinkStrip", "car_RedStrip", "car_WhiteStrip",  "car01-n", "car02-n", "car03-n", "ambulance-n"};
    private final int CAR_PATHS_LENGTH = CAR_PATHS.length;
    
    private int intervalSinceCarHasBeenAdded = 0;
    private int intervalToAddCar = 1;
    
    private Text gameScore;
    
    private int gameSpeed = 5;
    private long interval = System.currentTimeMillis();
    private long startTime = interval;
    
    private Random rand = new Random();
    private int[] spawnLocations = {150, 250, 350, 450, 550};
    
    private boolean gameover = false;
    private long timeSinceGameover = 0;
    
    Game(Scene scene, String car)
    {
        this.scene = scene;
        this.car = car;
        
        PLAYER_START_X = scene.WIDTH / 2;
        PLAYER_START_Y = scene.HEIGHT - 200;
    }
    
    public int getNextState()
    {
        return nextState;
    }
    
    private int getCarX()
    {
        return spawnLocations[rand.nextInt(spawnLocations.length)];
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
        int carType = rand.nextInt(CAR_PATHS_LENGTH);
        
        int xPos = getCarX();
        int yPos = getCarY(firstRun);
        
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
            Background bg = new Background(gameSpeed, i, scene.WIDTH, scene.HEIGHT);
            scene.addObject(bg, 0, 0);
            bg.setStartPos();
                
            backgrounds[i - 1] = bg;
        }
        
        player = new Car(this.car);
        scene.addObject(player, PLAYER_START_X, PLAYER_START_Y);
        
        for (int i = 0; i < 3; i++)
        {
            addCar(true);
        }
        
        gameScore = new Text(new Color(255, 255, 255), new greenfoot.Font("ARIAL", true, false, 30));
        scene.addObject(gameScore, scene.WIDTH / 2, 20);        
    }
    
    private void setActorStatesToGameover(long currentTime)
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
                
        gameover = true;
        timeSinceGameover = currentTime;
        
        scene.addText("You Crashed!", new Font("MONOSPACED", true, false, 50), Color.RED, scene.WIDTH / 2, scene.HEIGHT / 2, true, true);
    }
    
    private void drawTimeAlive(long currentTime)
    {
        String timeAlive = Integer.toString((int)((currentTime - startTime) / 1000));
        
        gameScore.setText("SCORE: " + timeAlive);
    }
    
    public void act() {
        long currentTime = System.currentTimeMillis();
                
        if (!gameover) {
            drawTimeAlive(currentTime);
            
            if (intervalSinceCarHasBeenAdded >= intervalToAddCar) {
                intervalSinceCarHasBeenAdded = 0;
                
                addCar(false);
            }
        
            if (currentTime - interval > 5000 && gameSpeed < 20) {
                gameSpeed++;
                for (Background bg : backgrounds)
                {
                    bg.setSpeed(gameSpeed);
                }
                
                for (CarAI car : carsAI)
                {
                    car.incrementSpeed();
                    
                    if (car.checkCarIsOnExpectedPos())
                        car.checkLaneSwitch(player);
                    
                    if (car.checkNeedsReset())
                    {
                        car.setPos(getCarX(), getCarY(false)); 
                    }
                    
                    if (player.checkIntersects(car)) {
                        setActorStatesToGameover(currentTime);
                        car.setMoveSpeed(0);
                    }
                }
                
                //player.increaseAttributes();
                
                interval = currentTime;
                intervalSinceCarHasBeenAdded++;
            } else {
                for (CarAI car : carsAI)
                {
                    if (car.checkCarIsOnExpectedPos())
                        car.checkLaneSwitch(player);
                    
                    if (car.checkNeedsReset())
                        car.setPos(getCarX(), getCarY(false)); 
                        
                    if (player.checkIntersects(car)) {
                        setActorStatesToGameover(currentTime);
                        car.setMoveSpeed(0);
                    }
    
                }
            }
        } else if (currentTime - timeSinceGameover > 5000)
            nextState = 3;
    }
}