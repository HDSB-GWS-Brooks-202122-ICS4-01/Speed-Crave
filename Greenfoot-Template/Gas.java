import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Meant to be collected to give player fuel.
 * 
 * Selim Abdelwahab
 * 2021-10-13
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