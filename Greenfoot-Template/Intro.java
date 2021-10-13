import greenfoot.*;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;

/**
 * Displays instruction and several buttons.
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
class Intro
{
    private final Scene SCENE;      // The world object
    private Btn startBtn;           // Start button => sets gamestate to game screen
    private Btn carSelectBtn;       // Car button => sets gamestate to car select screen
    private GtaModeBtn gtaModeBtn;  // Button to set the game difficulty.
    private int nextState = 0;      // The next gamestate. 0 means remain in this state
    
    /**
     * Constructor for the End class
     * @param SCENE     The world object.
     */
    Intro(Scene SCENE)
    {
        this.SCENE = SCENE;
    }
    
    /**
    * This method returns an integer value corresponding with a new game state.
    * @return int      Integer value for the next state. 0 indicates that the game state should not change.
    */
    public int getNextState()
    {
        return nextState;
    }
    
    public void setScene() {
        // Change background color.
        SCENE.getBackground().setColor(new Color(50, 50, 50));
        SCENE.getBackground().fill();
        
        // Add game title
        SCENE.addText(SCENE.GAME_NAME, new greenfoot.Font("MONOSPACED", true, false, 90), Color.WHITE, SCENE.WIDTH / 2, 100, true, true);    
        
        // Add controls to screen
        SCENE.addText("CONTROLS", new greenfoot.Font("MONOSPACED", true, true, 50), Color.WHITE, SCENE.WIDTH / 2, 200, true, true);
        SCENE.addText("'A' / 'Left Key' To strafe left", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 250, false, true);
        SCENE.addText("'D' / 'Right Key' To strafe right", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 300, false, true);
        SCENE.addText("Dodge cars and oil puddles to win!", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 400, false, true);
        SCENE.addText("Don't forget to pick-up gas!", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 450, false, true);
        SCENE.addText("TRY TO SCORE 100", new greenfoot.Font("ARIAL", true, false, 40), Color.GRAY, SCENE.WIDTH / 2, 550, true, true);
        
        // Nice little hidden message. Nothing but the truth...
        SCENE.addText("It gets fast. (Like really fast)", new greenfoot.Font("MONOSPACED", false, false, 20), Color.GRAY, 10, SCENE.HEIGHT - 10, false, false);
        
        // Add gta mode button to the world
        gtaModeBtn = new GtaModeBtn(SCENE.gtaMode);
        SCENE.addObject(gtaModeBtn, SCENE.WIDTH / 2, SCENE.HEIGHT - 225);
        
        // Add start button to the world
        startBtn = new StartBtn();
        SCENE.addObject(startBtn, 150, SCENE.HEIGHT - 100);
        
        // Add car select button to the world
        carSelectBtn = new CarSelectBtn();
        SCENE.addObject(carSelectBtn, SCENE.WIDTH - 150, SCENE.HEIGHT - 100);
    }

    /**
     * Act - do whatever the Intro wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {       
        // Start the game
        if (startBtn.getClicked()) {
            nextState = 2;
            SCENE.updateGtaMode(gtaModeBtn.getSelected());
        }
            
        // Go to car  select scene
        if (carSelectBtn.getClicked())
            nextState = 4;
    }
}