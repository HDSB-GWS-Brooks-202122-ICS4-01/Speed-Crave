import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gas extends Consumable
{
    private static final String IMAGE_PATH = ".\\images\\canister.png";
    
    /**
     * Constructor for the Gas class.
     */
    Gas()
    {
        super(IMAGE_PATH, 2, 3, true);
    }
    
    /**
     * Act - do whatever the Gas wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        
        if (super.getOutOfTheWorld() && !super.getDoneForTheRun())
            super.setDoneForTheRun(true);
    }
}