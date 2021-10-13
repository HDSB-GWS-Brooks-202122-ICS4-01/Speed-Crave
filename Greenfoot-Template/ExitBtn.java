import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with "exit" displayed on it
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class ExitBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_exit_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_exit_1.png";
    
    /**
     * Constructor for the Exit class
     */
    ExitBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}
