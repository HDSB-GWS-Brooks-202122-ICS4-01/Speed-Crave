import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with the shape of an arrow facing left
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class PrevBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_prev_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_prev_1.png";
    
    /**
     * Constructor for the PrevBtn class
     */
    PrevBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}

