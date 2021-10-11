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
    private final int WIDTH, HEIGHT;
    private int moveSpeed;
    private int gameSpeed = 1;
    
    private int expectedXPos = 0;
    private boolean checkedLaneSwitchForRun = false;
    private final int RAND_UPPER = 6;
    private int randLimit = RAND_UPPER;
    
    public boolean needsReset = false;
    
    CarAI(int moveSpeed, int gameSpeed, String imgPath)
    {
        this.moveSpeed = moveSpeed;
        this.gameSpeed = gameSpeed;
        
        setImage(imgPath);
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
    }
    
    public void setPos(int x, int y)
    {
        setLocation(x, y);
        expectedXPos = x;
        
        checkedLaneSwitchForRun = false;
        needsReset = false;
    }

    public void incrementSpeed()
    {
        gameSpeed++;
    }
    
    public void setGameSpeed(int gameSpeed)
    {
        this.gameSpeed = gameSpeed;
    }
    
    public void setMoveSpeed(int speed)
    {
        moveSpeed = speed;
    }
    
    public boolean checkOutOfBounds()
    {
        return getY() - HEIGHT > getWorld().getHeight();
    }
    
    public boolean checkNeedsReset()
    {
        return needsReset;
    }
    
    private void checkOtherCars()
    {
        List<CarAI> cars = getNeighbours(200, false, CarAI.class);
            
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
    
    public void checkLaneSwitch(Actor player)
    {        
        int yAI = getY();
        int xAI = getX();
    
        int yPlayer = player.getY();
        int xPlayer = player.getX();

        
        if (!checkedLaneSwitchForRun && Math.abs(xPlayer - xAI) <= 100 && Math.abs(xPlayer - xAI) > 10) {
            checkedLaneSwitchForRun = true;
            int randCheck = (int)Math.floor(Math.random()*(randLimit-1));
       
            if (Math.abs(yPlayer - yAI) > 250 && yAI < yPlayer) {
                if (randCheck != 0) {
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
    
    private void setNewGoToPos(int newX)
    {
        expectedXPos = newX;
    }
    
    public boolean checkCarIsOnExpectedPos()
    {
        return getX() == expectedXPos;
    }
    
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
