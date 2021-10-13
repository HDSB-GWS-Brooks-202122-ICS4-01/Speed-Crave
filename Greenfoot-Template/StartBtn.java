import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with "start" displayed on it
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class StartBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_start_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_start_1.png";
    
    /**
     * Constructor for the StartBtn class
     */
    StartBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}
