import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GtaModeBtn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GtaModeBtn extends Btn
{
    private static final String STILL_UNSELECTED = ".\\images\\btn_gta_mode_00.png";
    private static final String HOVER_UNSELECTED = ".\\images\\btn_gta_mode_01.png";
    
    private static final String STILL_SELECTED = ".\\images\\btn_gta_mode_10.png";
    private static final String HOVER_SELECTED = ".\\images\\btn_gta_mode_11.png";
    
    private boolean selected = false;
    private long timeSinceClick = 0;
    
    /**
     * Constructor for the GtaModeBtn class
     * @param       A boolean value for if the button was already selected previously.
     */
    GtaModeBtn(boolean selected)
    {
        super(STILL_UNSELECTED, STILL_SELECTED);
        
        this.selected = selected;
    }
    
    /**
     * This method returns if the button is selected.
     * @param boolean       true or false.
     */
    public boolean getSelected()
    {
        return selected;
    }
    
    /**
     * Act - do whatever the GtaModeBtn wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (super.getClicked() && System.currentTimeMillis() - timeSinceClick >= 1000) {
            selected = !selected;
            super.reset();
            
            timeSinceClick = System.currentTimeMillis();
        }
        
        if (super.isHover()) {   
            checkMouseClick();
            if (selected)
                setImage(HOVER_SELECTED);
            else
                setImage(HOVER_UNSELECTED);
                            
        } else {
            if (selected)
                setImage(STILL_SELECTED);
            else
                setImage(STILL_UNSELECTED);
        }
    }
}
