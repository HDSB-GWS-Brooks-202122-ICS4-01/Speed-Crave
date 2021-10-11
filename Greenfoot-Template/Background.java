import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends Actor
{
    private final int WIDTH, HEIGHT;
    private final int SCREEN_HEIGHT;
    private int moveSpeed = 1;
    private int step = 1;
    
    /**
     * Constructor for the Background class.
     * @param gameSpeed         The speed at which all objects are running at
     * @param id                The index of the background (starts at 1)
     * @param SCREEN_HEIGHT     Height of the world
     */
    Background(int gameSpeed, int id, int SCREEN_HEIGHT)
    {
        moveSpeed = gameSpeed;
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
         
        this.step = id;
        
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }
    
    /**
     * This method sets the starting postion of the background.
     * @return Nothing.
     */
    public void setStartPos()
    {
        int y = (-HEIGHT * step) + (HEIGHT / 2) + SCREEN_HEIGHT;
        
        setLocation(getX(), y); 
        step = 1;
    }
    
    /**
     * This method resets the position of the background.
     * @return Nothing
     */
    public void setOffset()
    {
        setLocation(getX(), getY() - HEIGHT * 2); 
    }
    
    /**
     * This method sets the move speed of the background.
     * @param speed     New move speed.
     * @return          Nothing
     */
    public void setSpeed(int speed){
        this.moveSpeed = speed;
    }
    
    /**
     * Act - do whatever the Background wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() + moveSpeed);
        
        if (getY() >= HEIGHT)
            setOffset();
    }
}
