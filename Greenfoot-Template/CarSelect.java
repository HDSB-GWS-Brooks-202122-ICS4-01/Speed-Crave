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
    
    /**
    * This method returns an integer value corresponding with a new game state.
    * @return int      Integer value for the next state. 0 indicates that the game state should not change.
    */
    public int getNextState()
    {
        return nextState;
    }
    
    /**
     * This method sets the end screen scene.
     */
    public void setScene()
    {
        // Get background
        GreenfootImage bg = this.scene.getBackground();
        
        // Set background color
        bg.setColor(Color.WHITE);
        bg.fill();
        
        // Add encourging texts
        this.scene.addText("Pick your poison", new Font("TIMES", true, true, 80), Color.BLACK, this.scene.WIDTH / 2, 200, true, true);
        
        // Display the selected car
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        this.scene.addObject(display, this.scene.WIDTH / 2, this.scene.HEIGHT / 2);
        
        // Add buttons to world
        this.scene.addObject(nextBtn, this.scene.WIDTH - 130, this.scene.HEIGHT / 2);
        this.scene.addObject(prevBtn, 130, this.scene.HEIGHT / 2);
        this.scene.addObject(homeBtn, this.scene.WIDTH / 2, this.scene.HEIGHT - 100);
        this.scene.addObject(carName, this.scene.WIDTH / 2, this.scene.HEIGHT - 200);
        
        setCarName();
    }
    
    /**
     * This method will return the image string path of the selecyed car.
     * @return     The string image path for the car selected.
     */
    public String getCarPath()
    {
        return PREFIX + CARS[selectedCar] + ENDFIX;
    }
    
    /**
     * This method will retutrn the index of the selected car.
     * 
     * @return     the selcted car
     */
    public int getCarSelected()
    {
        return selectedCar;
    }
    
    /**
     * This method will display the name of the car.
     */
    private void setCarName()
    {
        carName.setText(CAR_NAMES[selectedCar]);
    }
    
    /**
     * This method will display the next car.
     */
    private void nextCar()
    {
        selectedCar++;
        
        if (selectedCar >= MAX_CARS)
            selectedCar = 0;
            
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        setCarName();
    }
    
    /**
     * This method will display the previous car.
     */
    private void previousCar()
    {
        selectedCar--;
        
        if (selectedCar < 0)
            selectedCar = MAX_CARS - 1;
            
        display.setNewImage(PREFIX + CARS[selectedCar] + ENDFIX);
        setCarName();
    }
    
    /**
     * Act - do whatever the CarSelect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
}
