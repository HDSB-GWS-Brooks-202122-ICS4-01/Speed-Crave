import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * When in contact with the player car it will result in the car to spin out.
 * 
 * Selim Abdelwahab
 * 2021-10-13
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
    
    /**
     * Act - do whatever the OilPuddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
