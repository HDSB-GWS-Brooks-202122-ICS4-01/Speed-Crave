import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gas extends Actor
{
    private int gameSpeed = 1;
    private boolean doneForTheRun = false;
    
    private final int WIDTH, HEIGHT;            // Width and height of the image
    
    /**
     * Constructor for the Gas class.
     */
    Gas()
    {
        // Scaling the image to an appropriate size.
        GreenfootImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.scale(w/2, h/3);
        
        setImage(img);
        
        // Setting the width and height.
        WIDTH = w;
        HEIGHT = h;
    }
    
    /**
     * This method sets the gameSpeed (moveSpeed) of the gas object.
     * @param speed     New int value for how fast the gasoline travels backwards.
     */
    public void setGameSpeed(int newSpeed)
    {
        gameSpeed = newSpeed;
    }
    
    /**
     * This method retutns if the gasoline is done for the run.
     * @retutrn boolean         true or false.
     */
    public boolean getDoneForTheRun()
    {
        return doneForTheRun;
    }
    
    /**
     * This method restes the x and y position of the gasoline and resets some variables.
     * @param x     New int value for the x location.
     * @param y     New int value for the y location.
     */
    public void reset(int x, int y)
    {
        setLocation(x, y);
        doneForTheRun = false;
    }
    
    /**
     * Act - do whatever the Gas wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() + gameSpeed);
        setRotation(getRotation() + 1);
        
        if (getY() - HEIGHT > getWorld().getHeight())
            doneForTheRun = true;
    }
}
