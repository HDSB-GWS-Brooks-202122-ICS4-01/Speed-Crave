import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with "car" displayed on it
 * 
 * Selim Abdelwahab
 * 2021-10-13
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
