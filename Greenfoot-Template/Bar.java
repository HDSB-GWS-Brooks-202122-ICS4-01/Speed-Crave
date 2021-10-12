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
        
        int dWidth = (int)(WIDTH / 1.1);
        int dHeight = (int)(HEIGHT / 1.5);
        
        int x = (WIDTH - dWidth) / 2;
        int y = (HEIGHT - dHeight) / 2;
        
        img.setColor(Color.WHITE);
        img.fillRect(x, y, dWidth, dHeight);
        
        img.setColor(new Color(255, 85, 0));
        img.fillRect(x, y, (int)(dWidth * perc), dHeight);
        
        setImage(img);  
    }
    
    public void alignLeftX()
    {
        setLocation(getX() + WIDTH / 2, getY());
    }
    
    public void alignBottomY()
    {
        setLocation(getX(), getY() - HEIGHT / 2);
    }
    
    public void act()
    {
        drawShape();
    }
}
