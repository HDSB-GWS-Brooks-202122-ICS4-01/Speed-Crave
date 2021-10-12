import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * Write a description of class Scene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scene extends World
{
    static public final int WIDTH = 700, HEIGHT = 900;
    public final String GAME_NAME = "SPEED CRAVE";
    
    private int gamestate = 1; // 1: Intro screen, 2: Game screen, 3: End screen
    
    private Intro introScreen;
    private Game gameScreen;
    private End endScreen;
    private CarSelect carSelectScene;
    
    private String carPath = ".\\images\\car_BlackOut.png";
    private int selectedCar = 0;    
    
    /**
     * Constructor for objects of class Scene.
     */
    public Scene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WIDTH, HEIGHT, 1, false);
        
        nextScene();
    }
    
    /**
     * This method adds a text to the screen.
     * 
     * @param   t         The text to be rendered onto the screen.
     * @param   f         The font of the text
     * @param   c         The color of the text
     * @param   x         The x position of the text
     * @param   y         The y position of the text
     * @param   bold      Does the font have the 'bold' property
     * @param   center    Centeres the text if on the x & y if true
     * @return            Nothing
     */
    public void addText(String t, greenfoot.Font f, Color c, int x, int y, boolean bold, boolean center)
    {
        GreenfootImage bg = getBackground();
        Graphics g = bg.getAwtImage().createGraphics();
        FontMetrics fm = g.getFontMetrics(new java.awt.Font(f.getName(), bold ? 1 : 0, f.getSize()));
        
        int w = fm.stringWidth(t);
        x = center ? x - w / 2 : x;
        
        bg.setColor(c);
        bg.setFont(f);
        bg.drawString(t, x, y);
    }
    
    /**
     * This method sets up instantiates the game scenes and calls the method to setup the corresponding scene.
     * 
     * @return     Nothing
     */
    private void nextScene()
    {
        switch (gamestate)
        {
            case 1:
                introScreen = new Intro(this);
                introScreen.setScene();
                break;
            case 2:
                gameScreen = new Game(this, carPath);
                gameScreen.setScene();
                break;
            case 3:
                endScreen = new End(this, gameScreen.getScore());
                endScreen.setScene();
                break;
            case 4:
                carSelectScene = new CarSelect(this, selectedCar);
                carSelectScene.setScene();
                break;
        }
    }
    
    /**
     * This method deletes all objects in the world and calls the next scene method.
     * 
     * @return     Nothing
     */
    private void resetScene() {
        List objects = getObjects(null);
        removeObjects(objects);
        getBackground().fill();
        
        nextScene();
    }
    
    /**
     * This method is called at each frame and calls the current scene's act method.
     * 
     * @return     Nothing
     */
    public void act() {
        int nextState;
        
        switch (gamestate)
        {
            case 1: // Intro screen
                introScreen.act();
                nextState = introScreen.getNextState();
                
                if (nextState != 0) {
                    gamestate = nextState;
                    resetScene();
                }
                break;
            case 2: // Game screen
                gameScreen.act();
                nextState = gameScreen.getNextState();
                
                if (nextState != 0) {
                    gamestate = nextState;
                    resetScene();
                }
                break;
            case 3: // End screen
                endScreen.act();
                nextState = endScreen.getNextState();
                
                if (nextState != 0) {
                    gamestate = nextState;
                    resetScene();
                }
                break;
                
            case 4:
                carSelectScene.act();
                nextState = carSelectScene.getNextState();
                
                if (nextState != 0) {
                    selectedCar = carSelectScene.getCarSelected();
                    carPath = carSelectScene.getCarPath();
                    
                    gamestate = nextState;
                    resetScene();
                }
        }
    }
}