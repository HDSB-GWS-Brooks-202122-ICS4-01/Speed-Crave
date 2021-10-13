import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CarSelectBtn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CarSelectBtn extends Btn
{
    private static final String STILL_IMAGE = ".\\images\\btn_car_0.png";
    private static final String HOVER_IMAGE = ".\\images\\btn_car_1.png";
    
    /**
     * Constructor for the CarSelectBtn class
     */
    CarSelectBtn()
    {
        super(STILL_IMAGE, HOVER_IMAGE);
    }
}
