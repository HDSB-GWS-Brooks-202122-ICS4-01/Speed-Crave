import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartBtn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HomeBtn extends Btn
{
    private final String STILL_IMAGE = ".\\images\\btn_home_0.png";
    private final String HOVER_IMAGE = ".\\images\\btn_home_1.png";
    private final int WIDTH, HEIGHT;
    private int selectedImg = 0;
    
    HomeBtn()
    {
        WIDTH = getImage().getWidth();
        HEIGHT = getImage().getHeight();
    }
    
    /**
     * Act - do whatever the StartBtn wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (isHover()) {   
            super.checkMouseClick();
            if (selectedImg == 0) {
                setImage(HOVER_IMAGE);
                
                GreenfootImage img = getImage();
                img.scale((int)(img.getWidth() * 1.5), (int)(img.getHeight() * 1.5));
                setImage(img);
                
                selectedImg = 1;
            }
        } else {
            if (selectedImg == 1) {
                setImage(STILL_IMAGE);
                
                GreenfootImage img = getImage();
                img.scale(WIDTH, HEIGHT);
                setImage(img);
                
                selectedImg = 0;
            }
        }
    }
}
