import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays images on the screen with no further intent.
 * 
 * Selim Abdelwahab
 * 2021-10-13
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
