import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnStart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Btn extends Actor
{   
    public final String PREFIX = ".\\images\\";     // Prefix of all image paths
    private String STILL_IMAGE;                     // Path for the still image (Mouse not over)
    private String HOVER_IMAGE;                     // Path for the hover image (Mouse is over)
    private int WIDTH, HEIGHT;                      // Original width and height of image
    private int selectedImg = 0;                    // Which image is selected (0 = still, 1 = hover)
    
    private boolean isClicked = false;              // Tracks if the button is clicked
    private boolean isHovered = false;              // Tracks if the button is hovered
    
    public Btn(String si, String hi)
    {
        STILL_IMAGE = si;
        HOVER_IMAGE = hi;

        setImage(si);
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
    }
    
    /**
     * This method sets the dimension of the button
     * @param w     The Width of the image
     * @param h     The height of the image
     */
    public void setDims(int w, int h)
    {
        this.STILL_IMAGE = STILL_IMAGE;
        this.HOVER_IMAGE = HOVER_IMAGE;
        
        WIDTH = w;
        HEIGHT = h; 
    }
    
    /**
     * This methods reset the properties of the button
     */
    public void reset() 
    {
        isClicked = false;
        isHovered = false;
    }
    
    /**
     * This method returns if the btn was clicked.
     * @return isClicked    Boolean value that stores if the button was clicked.
     */
    public boolean getClicked()
    {
        return isClicked;
    }
    
    /**
     * This method checks if the mouse if hovering over the button and sets the isHovered property accordingly.
     */
    public void checkMouseMoved()
    {
        if (Greenfoot.mouseMoved(this))
            isHovered = true;
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
            isHovered = false;
    }
    
    /**
     * This method calls 
     * @return isHoverd     This boolean variables stores if the mouse is hovering over the button.
     */
    public boolean isHover() {
        checkMouseMoved();
        return isHovered;
    }
    
    /**
     * This method checks if the button is clicked and sets the isClicked variable accordingly.
     */
    public void checkMouseClick()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi != null)
        {
            int btnNum = mi.getButton();
            if (btnNum != 0)
                isClicked = true;
        }
    }
    
    /**
     * Act - do whatever the Btn wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (isHover()) {   
            checkMouseClick();
            if (selectedImg == 0) {
                setImage(HOVER_IMAGE);
                
                GreenfootImage img = getImage();
                img.scale((int)(img.getWidth() * 1.5), (int)(img.getHeight() * 1.5));
                setImage(img);
                
                selectedImg = 1;
            }
        } else {
            if (selectedImg == 1) {
                setImage(STILL_IMAGE);
                
                GreenfootImage img = getImage();
                img.scale(WIDTH, HEIGHT);
                setImage(img);
                
                selectedImg = 0;
            }
        }
    }
}
