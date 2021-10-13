import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartBtn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
