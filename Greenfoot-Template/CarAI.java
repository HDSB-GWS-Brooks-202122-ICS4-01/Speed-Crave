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
    
    private int expectedXPos = 0;
    private boolean checkedLaneSwitchForRun = false;
    private int randLimit = 10;
    
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
        expectedXPos = x;
        
        checkedLaneSwitchForRun = false;
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
    
    public void checkLaneSwitch(Actor player)
    {        
        int yAI = getY();
        int xAI = getX();
        
        int yPlayer = player.getY();
        int xPlayer = player.getX();
        
        
        if (!checkedLaneSwitchForRun && Math.abs(xPlayer - xAI) <= 100 && Math.abs(xPlayer - xAI) > 30) {
            checkedLaneSwitchForRun = true;
            int randCheck = (int)Math.floor(Math.random()*(randLimit-1));
            
            if (Math.abs(yPlayer - yAI) > 250 && yAI < yPlayer) {
                if (randCheck != 1) {
                    if (randLimit > 2)
                        randLimit -= 2;
                    return;
                }
                    
                if (xPlayer > xAI)
                    expectedXPos = xAI + 100;
                else if (xPlayer < xAI)
                    expectedXPos = xAI - 100;
                    
                randLimit = 10;
            }
        }
    }
    
    public boolean checkCarIsOnExpectedPos()
    {
        return getX() == expectedXPos || Math.abs(getX() - expectedXPos) <= 10;
    }
    
    private void moveCarToExpectedPos()
    {
        if (Math.abs(expectedXPos - getX()) <= 10)
            setLocation(expectedXPos, getY());
     
        if (getX() < expectedXPos)
           setLocation(getX() + moveSpeed, getY());
        else if (getX() > expectedXPos)
            setLocation(getX() - moveSpeed, getY());
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
            
        if (!checkCarIsOnExpectedPos())
            moveCarToExpectedPos();
    }
}
