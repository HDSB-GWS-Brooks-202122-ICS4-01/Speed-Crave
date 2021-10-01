import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    private Color color;
    private final int FONT_SIZE;
    private final int WIDTH, HEIGHT;
    
    public Text(Color c, int fs, int w, int h)
    {
        color = c;
        FONT_SIZE = fs;
        
        WIDTH = w;
        HEIGHT = h;
    }
    
    public void setPos(int x, int y)
    {
        setLocation(x, y);
    }
    
    public void setText(String newText){        
        GreenfootImage img = getImage();
        
        img.clear();
        img.scale(WIDTH, HEIGHT);
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.clear();
        
        img.setColor(color);
        img.setFont(new Font("Calibre", true, false, FONT_SIZE));
        img.drawString(newText, 0, h / 2);
        
        
        setImage(img);
    }
    
    /**
     * Act - do whatever the Text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
