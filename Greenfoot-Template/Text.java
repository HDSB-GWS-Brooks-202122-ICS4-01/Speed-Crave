import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.font.*;
import java.awt.Rectangle;

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    private Color color;
    private final greenfoot.Font FONT;
    
    public Text(Color c, greenfoot.Font f)
    {
        color = c;
        FONT = f;
    }
    
    public void setPos(int x, int y)
    {
        setLocation(x, y);
    }
    
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
        Rectangle bounds = getStringBounds(g2, newText, 0, 0);
        
        img.scale(fm.stringWidth(newText), FONT.getSize());
        img.setColor(Color.GRAY);
        img.fill();
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        img.setColor(color);
        img.setFont(FONT);
        System.out.println(h);
        img.drawString(newText, 0, (int)bounds.getHeight());
        
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
