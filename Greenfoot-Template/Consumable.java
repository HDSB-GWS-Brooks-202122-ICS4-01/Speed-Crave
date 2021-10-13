import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Consumable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Consumable extends Actor
{
    private int gameSpeed = 1;
    private boolean doneForTheRun = false;
    private boolean outOfTheWorld = false;
    private boolean wasReset = false;
    private final boolean ALLOW_ROTATION_ANIMATION;
    
    private final int WIDTH, HEIGHT;            // Width and height of the image
    
    /**
     * Constructor for the Gas class.
     */
    Consumable(String imagePath, int scaleX, int scaleY, boolean allowRotAni)
    {
        setImage(imagePath);
        
        // Scaling the image to an appropriate size.
        GreenfootImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.scale(w / scaleX, h / scaleY);
        
        setImage(img);
        w = img.getWidth();
        h = img.getHeight();
        
        // Setting the width and height.
        WIDTH = w;
        HEIGHT = h;
        
        ALLOW_ROTATION_ANIMATION = allowRotAni;
    }
    
    /**
     * This method restes the x and y position of the consumable and resets some variables.
     * @param x     New int value for the x location.
     * @param y     New int value for the y location.
     */
    public void reset(int x, int y)
    {
        setLocation(x, y);
        doneForTheRun = false;
        outOfTheWorld = false;
        
        wasReset = true;
    }
    
    /**
     * This method sets if the class was reset.
     * @param value         true or false.
     */
    public void setWasReset(boolean value)
    {
        wasReset = value;
    }
    
    /**
     * This method retutns if the consumable was reset.
     * @retutrn boolean         true or false.
     */
    public boolean getWasReset()
    {
        return wasReset;
    }
    
    /**
     * This method sets the gameSpeed (moveSpeed) of the consumable object.
     * @param speed     New int value for how fast the consumable travels backwards.
     */
    public void setGameSpeed(int newSpeed)
    {
        gameSpeed = newSpeed;
    }
    
    /**
     * This method sets if the consumable is done for the run.
     * @param value         true or false.
     */
    public void setDoneForTheRun(boolean value)
    {
        doneForTheRun = value;
    }
    
    /**
     * This method retutns if the consumable is done for the run.
     * @retutrn boolean         true or false.
     */
    public boolean getDoneForTheRun()
    {
        return doneForTheRun;
    }
    
    /**
     * This method retutns if the consumable is outside the world.
     * @retutrn boolean         true or false.
     */
    public boolean getOutOfTheWorld()
    {
        return outOfTheWorld;
    }
    
    /**
     * Act - do whatever the Consumable wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() + gameSpeed);
        
        if (ALLOW_ROTATION_ANIMATION)
            setRotation(getRotation() + 1);
        
        if (getY() - HEIGHT > getWorld().getHeight())
            outOfTheWorld = true;
    }
}
