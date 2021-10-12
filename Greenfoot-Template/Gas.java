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
    
    private final int WIDTH, HEIGHT;
    
    Gas()
    {
        GreenfootImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.scale(w/2, h/3);
        
        setImage(img);
        
        WIDTH = w;
        HEIGHT = h;
    }
    
    public void setGameSpeed(int newSpeed)
    {
        gameSpeed = newSpeed;
    }
    
    public boolean getDoneForTheRun()
    {
        return doneForTheRun;
    }
    
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
