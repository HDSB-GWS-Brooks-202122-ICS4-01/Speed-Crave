import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnStart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Btn extends Actor
{   
    public final String PREFIX = ".\\images\\";
    private String STILL_IMAGE;
    private String HOVER_IMAGE;
    private int WIDTH, HEIGHT;
    private int selectedImg = 0;
    
    private boolean isClicked = false;
    private boolean isHovered = false;
    
    /**
     * Constructor for the Btn class.
     * @param w     The Width of the image
     * @param h     The height of the image
     */
    public void setDims(int w, int h)
    {
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
}
