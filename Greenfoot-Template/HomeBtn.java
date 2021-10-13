import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with "home" displayed on it
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class HomeBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_home_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_home_1.png";
    
    /**
     * Constructor for the HomeBtn class
     */
    HomeBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}
