import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    public boolean needsReset = false;
    
    CarAI(int moveSpeed, int gameSpeed, int WIDTH, int HEIGHT)
    {
        this.moveSpeed = moveSpeed;
        this.gameSpeed = gameSpeed;
        
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    
    public void setPos(int x, int y)
    {
        setLocation(x, y);
        needsReset = false;
    }

    public void incrementSpeed()
    {
        gameSpeed++;
    }
    
    public boolean checkOutOfBounds()
    {
        return getY() - HEIGHT > getWorld().getHeight();
    }
    
    public boolean checkNeedsReset()
    {
        return needsReset;
    }
    
    /**
     * Act - do whatever the CarAI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() - moveSpeed);
        setLocation(getX(), getY() + gameSpeed);
        
        if (checkOutOfBounds())
            needsReset = true;
    }
}
