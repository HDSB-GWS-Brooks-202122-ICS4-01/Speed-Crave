import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * Write a description of class OilPuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OilPuddle extends Consumable
{
    private static final String imagePath = ".\\images\\oil_puddle (1).png";
    private long timeSinceRemoved = System.currentTimeMillis();
    private boolean startedTimer = false;
    private final Random RAND = new Random();
    
    /**
     * Constructor for the OilPuddle class.
     */
    OilPuddle()
    {
        super(imagePath, 1, 1, false);
    }
    
    /**
     * This method resets the OilPuddle class.
     */
    private void reset() {
        startedTimer = false;
        
        super.setRotation(RAND.nextInt(360));
    }
    
    public void act()
    {
        // Call the parent act
        super.act();
        
        // Check if the oil puddle is off the world
        if (super.getOutOfTheWorld()) {
            if (!startedTimer) { // Set timer
                timeSinceRemoved = System.currentTimeMillis();
                startedTimer = true;
            } else if (System.currentTimeMillis() - timeSinceRemoved >= 10000) { // Timer exceeded 10 seconds
                super.setDoneForTheRun(true);
            }
        }
        
        // If the oil puddle got replaced at the top of the screen, reset it's attributes.
        if (super.getWasReset()) {
            reset();
            super.setWasReset(false);
        }
    }
}
