import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.font.*;
import java.awt.Rectangle;

/**
 * Draws text to the screen.
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
public class Text extends Actor
{
    private Color color;                    // Color of the text
    private final greenfoot.Font FONT;      // Font properties
    
    /**
     * Constructor for the Text class.
     * @param c     greenfoot.Color object.
     * @param f     greenfoot.Font object.
     */
    public Text(Color c, greenfoot.Font f)
    {
        color = c;
        FONT = f;
    }
    
    /**
     * This method sets the position of the actor
     * @param x     Integer value for the x position.
     * @param y     Integer value for the y position.
     */
    public void setPos(int x, int y)
    {
        setLocation(x, y);
    }
    
    /**
     * This method sets the color of the text.
     * @param c     greenfoot.Color object
     */
    public void setColor(Color c)
    {
        color = c;
    }
    
    /**
     * This method sets the text of this actor.
     * @param newText     String value of the text to display.
     */
    public void setText(String newText){   
        // Creates new image
        GreenfootImage img = new GreenfootImage(1, 1);
        // Gets image's AWT graphics
        Graphics g = img.getAwtImage().createGraphics();
        Graphics2D g2 = (Graphics2D)(g);
        
        java.awt.Font f = new java.awt.Font(FONT.getName(), FONT.isBold() ? 1 : 0, FONT.getSize());
        
        FontMetrics fm = g.getFontMetrics(f);

        g2.setFont(new java.awt.Font(FONT.getName(), FONT.isBold() ? 1 : 0, FONT.getSize()));
        // Will get the bounds of the letter 'i', important because only the height is neccessary and characters like 'p' are offset so the height is different.
        Rectangle bounds = getStringBounds(g2, "I", 0, 0);
        
        img.scale(fm.stringWidth(newText), FONT.getSize());
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.setColor(color);
        img.setFont(FONT);
 
        img.drawString(newText, 0, (int)(h - (h - bounds.getHeight()) / 2));
        
        setImage(img);
    }
    
    // NOT MY CODE: credits to https://stackoverflow.com/questions/368295/how-to-get-real-string-height-in-java
    private Rectangle getStringBounds(Graphics2D g2, String str,
                                      float x, float y)
    {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }
}
