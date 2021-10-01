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
    private final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private int moveSpeed = 1;
    private final int ID;
    private int step = 1;
    
    Background(int gameSpeed, int id, int SCREEN_WIDTH, int SCREEN_HEIGHT)
    {
        moveSpeed = gameSpeed;
        
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
        
        ID = id;   
        step = ID;
        
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }
    
    public void setStartPos()
    {
        int x = SCREEN_WIDTH / 2;
        int y = (-HEIGHT * step) + (HEIGHT / 2) + SCREEN_HEIGHT;
        
        setLocation(x, y); 
        step = 1;
    }
    
    public void setOffset()
    {
        setLocation(getX(), getY() - HEIGHT * 2); 
    }
    
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
