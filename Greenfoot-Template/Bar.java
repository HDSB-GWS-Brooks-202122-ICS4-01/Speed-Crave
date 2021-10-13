import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GasolineBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bar extends Actor
{
    private final int WIDTH, HEIGHT;
    private double perc = 1;
    
    /**
     * Constructor for the Bar class
     * @param w     Int value for the width of the bar.
     * @param h     Int value for the height of the bat,
     */
    Bar(int w, int h)
    {
        WIDTH = w;
        HEIGHT = h;
        
        drawShape();
    }
    
    /**
     * This method sets the percentage of the bar.
     * @param newPerc     Double value for percentage. Between (0-1).
     */
    public void setPerc(double newPerc)
    {
        if (newPerc > 1)
            newPerc = 1;
        else if (newPerc < 0)
            newPerc = 0;
            
        perc = newPerc;
    }
    
    /**
     * This method sets the image of the actor.
     */
    public void drawShape()
    {
        GreenfootImage img = new GreenfootImage(WIDTH, HEIGHT);
        
        img.setColor(new Color(153, 163, 164));
        img.fill();
        
        // The inner rectangle dimensions
        int displayWidth = (int)(WIDTH / 1.1);
        int displayHeight = (int)(HEIGHT / 1.5);
        
        // Starting position of the inner rectangle
        int x = (WIDTH - displayWidth) / 2;
        int y = (HEIGHT - displayHeight) / 2;
        
        // Background color of the inner bar
        img.setColor(Color.WHITE);
        img.fillRect(x, y, displayWidth, displayHeight);
        
        // Inner rectangle of the inner rectange...? Basically the percentage
        img.setColor(new Color(255, 85, 0));
        img.fillRect(x, y, (int)(displayWidth * perc), displayHeight);
        
        setImage(img);  
    }
    
    /**
     * This method aligns the bar so that the x position it has will become it's left most position.
     */
    public void alignLeftX()
    {
        setLocation(getX() + WIDTH / 2, getY());
    }
    
    /**
     * This method aligns the bar so that the y position it has will become the bottom most position.
     */
    public void alignBottomY()
    {
        setLocation(getX(), getY() - HEIGHT / 2);
    }
    
    /**
     * Act - do whatever the Bar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        drawShape();
    }
}
