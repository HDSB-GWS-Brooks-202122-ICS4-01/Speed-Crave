import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * This is the class that the player controlls.
 * 
 * @author Selim Abdelwahab
 * @version 2021-10-13
 */
public class Car extends Actor
{
    private final int WIDTH, HEIGHT;
    
    private int direction = 0;              // 1: move right, -1: move left, 0: move straight.
    private int prevDirec = 0;
    
    private final double SPEED = 8;
    private final int TURN_SPEED = 4;
    
    private boolean allowedToMove = true;
    
    private final int MAX_GAS = 1000;
    private int gasoline = 1000;
    
    private final Bar G_BAR;
    
    private boolean slipperyMode = false;
    private long timeInSlipperyMode = 0;
    private int slipperyModeSeconds = 0;
    
    private int gameSpeed = 1;
    private final Random RAND = new Random();
    
    /**
     * Constructor for the Car class.
     */
    Car (String imgPath, Bar gbar)
    {
        setImage(imgPath);
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
        
        G_BAR = gbar;
    }
    
    /**
     * This method sets the car to be able to move or not move.
     * @param value   true or false value to set the access of the car.
     */
    public void setAllowedToMove(boolean value)
    {
        allowedToMove = value;
    }
    
    /**
     * This method will add gas to the car.
     */
    public void addFuel()
    {
        gasoline += 200;
        
        if (gasoline > MAX_GAS)
            gasoline = MAX_GAS;
    }
    
    /**
     * This method will set the slipper mode integer.
     * @param value     true or false.
     */
    public void setSlipperyMode(boolean value)
    {
        slipperyMode = value;
        
        timeInSlipperyMode = System.currentTimeMillis();
        slipperyModeSeconds = 0;
    }
    
    /**
     * This method checks if the car is intersecting with an AI car.
     * @param obj   Car AI object
     */
    public boolean checkCarIntersects(CarAI obj)
    {
        return intersects(obj);
    }
    
    /**
     * This method checks if the car is intersecting with the oil puddle.
     * @param obj   OilPuddle object
     */
    public boolean checkOilIntersects(OilPuddle obj)
    {
        return intersects(obj);
    }
    
    /**
     * This method checks if the car is intersecting with the gasoline.
     * @param obj   Gas object
     */
    public boolean checkGasIntersects(Gas obj)
    {
        return intersects(obj);
    }
    
    /**
     * This method checks if the car is out of fuel, and returns the boolean value.
     * @retutn boolean true or false
     */
    public boolean checkOutOfFuel()
    {
        return gasoline <= 0;
    }
    
    /**
     * This method sets the direction of travel.
     */
    public void setDirection()
    {   
        if ((Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) && !(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")))
            direction = 1;
        else if ((Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) && !(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")))
            direction = -1;
        else {
            if (direction != 0)
                prevDirec = direction;
            direction = 0;
        }
    }
    
    /**
     * This retutns the fuel remaining as a percentage.
     * @return double   a decimal point value from 0-1
     */
    private double getFuelPerc()
    {
        return (double)gasoline/(double)MAX_GAS;
    }
    
    /**
     * This method calculates the turn percentage and returns it.
     * @param  currentRot    Current rotation of the car
     * @return doube         Double value between 0-1.
     */
    public double getTurnPerc(double currentRot)
    {
        if (currentRot > 180) {
            double offset = 360 - currentRot;
            
            return offset / (30.0 + TURN_SPEED);
        } else {
            return currentRot / (30.0 + TURN_SPEED);
        }
    }
    
    /**
     * This method checks if the car is hitting the edge.
     * @return  boolean True or false
     */
    public boolean checkHitEdge() 
    {
        if (direction == 1) {
            return getX() + WIDTH * Math.cos((getRotation() * Math.PI) / 180.0) >= 600;
        } else {
            return getX() - WIDTH * Math.cos((getRotation() * Math.PI) / 180.0) <= 100;
        }
    }
    
    public void setGameSpeed(int gamespeed)
    {
        this.gameSpeed = gameSpeed;
    }
    
    /**
     * Act - do whatever the Car wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */  
    public void act()
    {
        if (allowedToMove) {
            if (!slipperyMode) {
                gasoline -= gameSpeed;
                G_BAR.setPerc(getFuelPerc());
                
                int currentRotation = getRotation();
                
                setDirection();
                
                if (direction != 0) {                    
                    int x = getX(), y = getY();
                    
                    if (!checkHitEdge())
                        setLocation((int)(x + SPEED * direction * getTurnPerc((double)currentRotation)), y);
                    else {
                        setLocation((int)(x - SPEED * direction * getTurnPerc((double)currentRotation)), y);
                    }
                }
                 
                switch (direction)
                {
                    
                    case 0:
                        if (currentRotation != 0)
                        {
                            if (currentRotation <= (30 + 30 % TURN_SPEED))
                                setRotation(currentRotation - TURN_SPEED);
                            else
                                setRotation(currentRotation + TURN_SPEED);
                        }
                        break;
                    case 1:
                        if (currentRotation < (30 + 30 % TURN_SPEED) || currentRotation >= (330 - 30 % TURN_SPEED))
                            setRotation(currentRotation + TURN_SPEED);
                        break;
                    case -1:
                        if (currentRotation > (330 - 30 % TURN_SPEED ) || currentRotation <= (30 + 30 % TURN_SPEED))
                            setRotation(currentRotation - TURN_SPEED);
                        break;
                }
            } else {
                long ct = System.currentTimeMillis();
                if (ct - timeInSlipperyMode >= 1000) {
                    timeInSlipperyMode = ct;
                    slipperyModeSeconds++;
                }
                if (direction == 0) {
                    int rand = RAND.nextInt(2);
                    
                    if (rand == 1)
                        direction = 1;
                    else
                        direction = -1;
                }
                
                if (checkHitEdge())
                    direction *= -1;
                    
                setLocation(getX() + (int)(2 * direction), getY());
                setRotation(getRotation() + direction * 30);
                
                if (slipperyModeSeconds >= 3)
                    setSlipperyMode(false);
            }
        }
    }
}
