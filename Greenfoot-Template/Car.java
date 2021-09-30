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
    
    private final double SPEED;
    private final int TURN_SPEED;
    
    Car (double speed, int tSpeed)
    {
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
        
        this.SPEED = speed;
        this.TURN_SPEED = tSpeed;
    }
    
    public boolean checkIntersects(CarAI obj)
    {
        return intersects(obj);
    }
    
    public void setDirection()
    {
        if ((Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) && !(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")))
            direction = 1;
        else if ((Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) && !(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")))
            direction = -1;
        else
            direction = 0;
    }
    
    public double getTurnPerc(double currentRot)
    {
        if (currentRot > 180) {
            double offset = 360 - currentRot;
            
            return offset / 30.0;
        } else {
            return currentRot / 30.0;
        }
    }
    
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
                    if (currentRotation > 180)
                        setRotation(currentRotation + TURN_SPEED);
                    else
                        setRotation(currentRotation - TURN_SPEED);
                }
                break;
            case 1:
                if (currentRotation < 30 || currentRotation >= 330)
                    setRotation(currentRotation + TURN_SPEED);
                break;
            case -1:
                if (currentRotation > 330 || (currentRotation < 330 && currentRotation != 330))
                    setRotation(currentRotation - TURN_SPEED);
                break;
        }
    }
}
