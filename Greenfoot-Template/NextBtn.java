import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with the shape of an arrow with the tip to the right.
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class NextBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_next_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_next_1.png";
    
    /**
     * Constructor for the NextBtn class
     */
    NextBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}

