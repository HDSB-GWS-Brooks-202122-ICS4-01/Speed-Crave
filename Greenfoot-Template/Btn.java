import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnStart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Btn extends Actor
{
    private MouseInfo mi;
    private boolean isHovered = false;
    
    public final String PREFIX = ".\\images\\";
    private String STILL_IMAGE;
    private String HOVER_IMAGE;
    private int WIDTH, HEIGHT;
    private int selectedImg = 0;
    private boolean isClicked = false;
    
    /**
     * Act - do whatever the BtnStart wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Btn(MouseInfo mi)
    {
        this.mi = mi;
    }
    
    public void setDims(int w, int h)
    {
        WIDTH = w;
        HEIGHT = h; 
    }
    
    public boolean getClicked()
    {
        return isClicked;
    }
    
    public void checkMouseMoved()
    {
        if (Greenfoot.mouseMoved(this))
            isHovered = true;
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
            isHovered = false;
    }
    
    public boolean isHover() {
        checkMouseMoved();
        return isHovered;
    }
    
    public void checkMouseClick()
    {
        MouseInfo mc = Greenfoot.getMouseInfo();
        if (mc != null)
        {
            int btnNum = mc.getButton();
            if (btnNum != 0) {
                
                isClicked = true;
            }
        }
    }
    
    public void centerOnLocation()
    {
        int w = getImage().getWidth();
        int h = getImage().getHeight();
        
        setLocation(getX() - w / 2, getY() - h / 2);
    }
}
