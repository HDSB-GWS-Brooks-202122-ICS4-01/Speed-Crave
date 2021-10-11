import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Car here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Car extends Actor
{
    private final int WIDTH, HEIGHT;
    
    private int direction = 0;  // 1: move right, -1: move left, 0: move strait.
    private int prevDirec = 0;
    
    private final double SPEED = 8;
    private final int TURN_SPEED = 4;
    
    private boolean allowedToMove = true;
    
    /**
     * Constructor for the Car class.
     */
    Car (String imgPath)
    {
        setImage(imgPath);
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
    }
    
    /**
     * This method sets the car to be able to move or not move.
     * @param val   true or false value to set the access of the car.
     */
    public void setAllowedToMove(boolean val)
    {
        allowedToMove = val;
    }
    
    /**
     * This method checks if the car is intersecting with an AI car.
     * @param obj   Car AI object
     */
    public boolean checkIntersects(CarAI obj)
    {
        return intersects(obj);
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
     * This method calculates the turn percentage and returns it.
     * @param  currentRot    Current rotation of the car
     * @return doube         Double value between 0-1.
     */
    public double getTurnPerc(double currentRot)
    {
        if (currentRot > 180) {
            double offset = 360 - currentRot;
            
            return offset / 30.0;
        } else {
            return currentRot / 30.0;
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
    
    /**
     * Act - do whatever the Car wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */  
    public void act()
    {
        if (allowedToMove) {
            int currentRotation = getRotation();
            
            setDirection();
            
            if (direction != 0) {
                boolean hitEdge = checkHitEdge();
                
                int x = getX(), y = getY();
                
                if (!hitEdge)
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
                    if (currentRotation > (330 - 30 % TURN_SPEED) || currentRotation <= (30 + 30 % TURN_SPEED))
                        setRotation(currentRotation - TURN_SPEED);
                    break;
            }
        }
    }
}
