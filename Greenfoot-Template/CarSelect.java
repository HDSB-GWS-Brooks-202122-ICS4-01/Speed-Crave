import greenfoot.*;

/**
 * Purpose of this class is to display options of cars for the user to choose from.
 * 
 * @author Selim Abdelwahab 
 * @version 2021-10-02
 */
public class CarSelect  
{
    private final Scene scene;
    private Display display = new Display();
    private NextBtn nextBtn = new NextBtn();
    private PrevBtn prevBtn = new PrevBtn();
    private Btn homeBtn = new HomeBtn();
    private Text carName = new Text(Color.BLACK, new Font("MONOSPACED", true, false, 40));
    
    private final String PREFIX = ".\\images\\car_";
    private final String ENDFIX = ".png";
    private final String[] CARS = { "BlackOut", "BlueStrip", "GreenStrip", "PinkStrip", "RedStrip", "WhiteStrip" };
    private final String[] CAR_NAMES = { "Black Out", "Blue Stripe", "Green Stripe", "Pink Stripe", "Red Stripe", "White Stripe" };
    private final int MAX_CARS = CARS.length;
    
    private int nextState = 0;
    
    private int selectedCar;
    
    private boolean readyForNextScene = false; 

    /**
     * Constructor for objects of class CarSelect
     */
    public CarSelect(Scene scene, int selectedCar)
    {
        this.scene = scene;
        this.selectedCar = selectedCar;
    }
    
    public void setScene()
    {
        GreenfootImage bg = this.scene.getBackground();
        
        bg.setColor(Color.WHITE);
        bg.fill();
        
        this.scene.addText("Pick your poison", new Font("TIMES", true, true, 80), Color.BLACK, this.scene.WIDTH / 2, 200, true, true);
        
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        this.scene.addObject(display, this.scene.WIDTH / 2, this.scene.HEIGHT / 2);

        this.scene.addObject(nextBtn, this.scene.WIDTH - 130, this.scene.HEIGHT / 2);
        this.scene.addObject(prevBtn, 130, this.scene.HEIGHT / 2);
        this.scene.addObject(homeBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 100);
        this.scene.addObject(carName, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
        
        setCarName();
    }
    
    public int getNextState()
    {
        return nextState;
    }
    
    public String getCarPath()
    {
        return PREFIX + CARS[selectedCar] + ENDFIX;
    }
    
    public int getCarSelected()
    {
        return selectedCar;
    }
    
    private void setCarName()
    {
        carName.setText(CAR_NAMES[selectedCar]);
    }
    
    private void nextCar()
    {
        selectedCar++;
        
        if (selectedCar >= MAX_CARS)
            selectedCar = 0;
            
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        setCarName();
    }
    
    private void previousCar()
    {
        selectedCar--;
        
        if (selectedCar < 0)
            selectedCar = MAX_CARS - 1;
            
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        setCarName();
    }
    
    public void act()
    {
        if (nextBtn.getClicked()) {
            nextCar();
            nextBtn.reset();
        }
        
        if (prevBtn.getClicked()) {
            previousCar();
            prevBtn.reset();
        }
        
        if (homeBtn.getClicked())
            nextState = 1;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
}
