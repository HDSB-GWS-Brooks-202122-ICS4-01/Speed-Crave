import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays some sort of numeric value in a bar setting.
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class Bar extends Actor
{
    private final int WIDTH, HEIGHT;    // Original width and height of the bar (dims it's created with)
    private int currentW, currentH;     // The current width and height of the bar
    private double perc = 1;            // Percentage of the bar (0-1)
    private boolean blink = false;      // Blink --> means that the bar is flashing
    private long blinkTimer = 0;        // Time interval for blink
    private int ogX, ogY;               // Original x, and y positions
    
    /**
     * Constructor for the Bar class
     * @param w     Int value for the width of the bar.
     * @param h     Int value for the height of the bar.
     */
    Bar(int w, int h)
    {
        WIDTH = w;
        HEIGHT = h;
        
        currentW = w;
        currentH = h;
    }
    
    /**
     * This method sets the current location as the original
     */
    public void setCurrentAsOgLoc() 
    {
        ogX = getX();
        ogY = getY();
    }
    
    /**
     * This method sets the current location on the orignal positions.
     */
    public void setOgLocation()
    {
        setLocation(ogX, ogY);
    }
    
    /**
     * This method returns the original width of the bar.
     * @return int      Original width of the bar.
     */
    public int getOgWidth()
    {
        return WIDTH;
    }
    
    /**
     * This method returns the original height of the bar.
     * @return int      Original hieght of the bar.
     */
    public int getOgHeight()
    {
        return HEIGHT;
    }
    
    /**
     * This method returns the current width of the bar.
     * @return int      Current width of the bar.
     */
    public int getWidth()
    {
        return currentW;
    }
    
    /**
     * This method returns the current height of the bar.
     * @return int      Current height of the bar.
     */
    public int getHeight()
    {
        return currentH;
    }
    
    /**
     * This method resizes the bar's dimensions and takes an extra parameter to set it's blinking state.
     * @param w             The new width
     * @param h             The new height
     * @param blink         The new blink state
     */
    public void resize(int w, int h, boolean blink)
    {
        currentW = w;
        currentH = h;
        this.blink = blink;
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
        GreenfootImage img = new GreenfootImage(currentW, currentH);
        
        img.setColor(new Color(153, 163, 164));
        img.fill();
        
        // The inner rectangle dimensions
        int displayWidth = (int)(currentW / 1.1);
        int displayHeight = (int)(currentH / 1.5);
        
        // Starting position of the inner rectangle
        int x = (currentW - displayWidth) / 2;
        int y = (currentH - displayHeight) / 2;
        
        // Background color of the inner bar
        img.setColor(Color.WHITE);
        img.fillRect(x, y, displayWidth, displayHeight);
        
        // Inner rectangle of the inner rectange...? Basically the percentage bar
        boolean draw = true;
        if (blink) {
            if (System.currentTimeMillis() - blinkTimer < 1000)
                draw = true;
            else if (System.currentTimeMillis() - blinkTimer < 1000 + 500 * perc / 0.25)
                draw = false;
            else 
                blinkTimer = System.currentTimeMillis();
        }
            
        if (draw) {
            img.setColor(new Color(255, 85, 0));
            img.fillRect(x, y, (int)(displayWidth * perc), displayHeight);
        }
        
        setImage(img);  
    }
    
    /**
     * This method aligns the bar so that the x position it has will become it's left most position.
     */
    public void alignLeftX()
    {
        setLocation(ogX + currentW / 2, getY());
    }
    
    /**
     * This method aligns the bar so that the y position it has will become the bottom most position.
     */
    public void alignBottomY()
    {
        setLocation(getX(), ogY - currentH / 2);
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
