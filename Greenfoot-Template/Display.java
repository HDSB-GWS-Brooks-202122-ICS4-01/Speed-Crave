import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Display here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Display extends Actor
{
    /**
     * Constructor for the Display class
     * @param path     String path for image to display.
     */
    public void setNewImage(String path)
    {
        setImage(path);
        
        GreenfootImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.scale(w * 5, h * 5);
        
        setImage(img);
    }
}
