import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;


/**
 * Write a description of class CarAI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CarAI extends Actor
{
    private final int WIDTH, HEIGHT;                    // Width and height of image
    private int moveSpeed;                              // Move speed of car
    private int gameSpeed = 1;                          // Game speed (how fast the "player" is going)
    
    private int expectedXPos = 0;                       // Where the AI car is supposed to be in the x postion
    private boolean checkedLaneSwitchForRun = false;    // Tracks if the AI car attempted to switch lanes in a run.
    private final int RAND_UPPER = 1;                   // Max random value for lane switch.
    private int randLimit = RAND_UPPER;                 // The random limit (decrease with each run)
    
    private final Random RAND = new Random();
    
    public boolean needsReset = false;                  // Keeps track if the AI car needs to be reset.
    
    /**
     * Constructor for CarAI object.
     * @param moveSpeed     int value for how fast the car travels.
     * @param gameSpeed     int value for how fast the car travels backwards.
     * @param imgPath       String value for the image path.
     */
    CarAI(int moveSpeed, int gameSpeed, String imgPath)
    {
        this.moveSpeed = moveSpeed;
        this.gameSpeed = gameSpeed;
        
        // Setting image of car
        setImage(imgPath);
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
    }
    
    /**
     * This method sets the position of the car, and resets the expected x position value.
     * @param x     The new int value for the x location of the AI car.
     * @param y     The new int value for the y location of the AI car.
     */
    public void setPos(int x, int y)
    {
        setLocation(x, y);
        expectedXPos = x;
        
        checkedLaneSwitchForRun = false;
        needsReset = false;
    }
    
    /**
     * This method sets the game speed value.
     * @param gameSpeed     New int value for how fast the car travels backwards.
     */
    public void setGameSpeed(int gameSpeed)
    {
        this.gameSpeed = gameSpeed;
    }
    
    /**
     * This method sets the move speed value.
     * @param speed     New int value for how fast the car travels forward.
     */
    public void setMoveSpeed(int speed)
    {
        moveSpeed = speed;
    }
    
    /**
     * This method checks if the car is completely outside the height border.
     * @retutn boolean      true or false.
     */
    public boolean checkOutOfBounds()
    {
        return getY() - HEIGHT > getWorld().getHeight();
    }
    
    /**
     * This method returns if the car needs to be reseted or not.
     * @return boolean      
     */
    public boolean checkNeedsReset()
    {
        return needsReset;
    }
    
    /**
     * This checks if the AI car is colliding with another car and switches lanes accordingly.
     */
    private void checkOtherCars()
    {
        List<CarAI> cars = getNeighbours(100, true, CarAI.class);
            
        for (CarAI car : cars)
        {
            if ((Math.abs(car.getX() - getX()) < 50 && car.getY() < getY() && car.moveSpeed < moveSpeed) || (intersects(car) && getY() < car.getY())) {
               if (getX() <= car.getX())
                    setNewGoToPos(getX() - 100);
                else
                    setNewGoToPos(getX() + 100);
                
                break;
            }            
        }
    }
    
    /**
     * This checks if the AI car wants to switch lanes in attempts to collide with the player car.
     * @param player     Player object which is a subclass of the Actor class.
     */
    public void checkLaneSwitch(Actor player)
    {        
        int yAI = getY();               // y location of AI car
        int xAI = getX();               // X location of AI car
    
        int yPlayer = player.getY();    // y location of player car
        int xPlayer = player.getX();    // x location of player car

        
        if (!checkedLaneSwitchForRun && Math.abs(xPlayer - xAI) <= 100 && Math.abs(xPlayer - xAI) > 10 && Math.abs(yPlayer - yAI) > 100 && yAI < yPlayer) {
            checkedLaneSwitchForRun = true;
            int randCheck = RAND.nextInt(randLimit);
            System.out.println(randCheck);
            if (randCheck != 0) { // Unless the randCheck value is 0, the AI car won't switch lanes.
                if (randLimit > 1)
                        randLimit--;
                   return;
                }
                
            if (xPlayer > xAI)
                setNewGoToPos(xAI + 100);
            else if (xPlayer < xAI)
                setNewGoToPos(xAI - 100);
                
            randLimit = RAND_UPPER;
        }
    }
    
    /**
     * This method checks if the car is hitting the edge.
     * @return  boolean True or false
     */
    public boolean checkHitEdge() 
    {
        if (getX() < expectedXPos) { //Moving to the right
            return getX() + WIDTH * Math.cos((getRotation() * Math.PI) / 180.0) >= 600;
        } else { // Moving to the left
             return getX() - WIDTH * Math.cos((getRotation() * Math.PI) / 180.0) <= 100;
        }
    }
    
    /**
     * This method sets the new expected x postion.
     * @param newX     New int value for the x position of the AI car.
     */
    private void setNewGoToPos(int newX)
    {
        expectedXPos = newX;
    }
    
    /**
     * This method checks if the AI car is on the expected x postion.
     * @retutn boolean     true or false.
     */
    public boolean checkCarIsOnExpectedPos()
    {
        return getX() == expectedXPos;
    }
    
    /**
     * This method moves the AI car to the expected x postion.
     * @param currentRotation     Current rotation of the vehichle between (0-360).
     */
    private void moveCarToExpectedPos(int currentRotation)
    {
        if (Math.abs(expectedXPos - getX()) <= moveSpeed)
            setLocation(expectedXPos, getY());
        
        if (getX() < expectedXPos) {
           setLocation(getX() + moveSpeed, getY());
           
           if (currentRotation < 30 || currentRotation >= 330)
               setRotation(currentRotation + 2);
        } else if (getX() > expectedXPos) {
            setLocation(getX() - moveSpeed, getY());
            
            if (currentRotation > 330 || currentRotation <= 30)
                setRotation(currentRotation - 2);
        }
    }
    
    /**
     * Act - do whatever the CarAI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() - moveSpeed);
        setLocation(getX(), getY() + gameSpeed);
        
        int currentRotation = getRotation();
        
        if (checkOutOfBounds())
            needsReset = true;
            
        if (!checkCarIsOnExpectedPos())
            moveCarToExpectedPos(currentRotation);
        else if (currentRotation != 0)
                {
                    if (currentRotation <= 30)
                        setRotation(currentRotation - 2);
                    else
                        setRotation(currentRotation + 2);
                }
                
        checkOtherCars(); 
        
        if (checkHitEdge())
        {
            if (getX() > getWorld().getWidth() / 2)
                setPos(getX() - moveSpeed, getY());
            else
                setPos(getX() + moveSpeed, getY());
        }
    }
}
