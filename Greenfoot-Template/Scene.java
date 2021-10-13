import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;

/**
 * This class handles all the gamestate and links all the objects to one another.
 * 
 * @author Selim Abdelwahab
 * @version 2021-10-13
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
    
    private final String DATA_FILE_PATH = ".\\data\\data.txt";
    public int highscore = 0;
    public boolean gtaMode = false;
    
    /**
     * Constructor for objects of class Scene.
     */
    public Scene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WIDTH, HEIGHT, 1, false);
        
        ArrayList<String> lines = new ArrayList<String>();
        
        // Read data
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH));
            
            String line;
            
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            // Get keys and value
            for (String l : lines) {
                if (l.contains("highscore")) {
                    String[] keyAndVal = l.split(":");
                    String value = keyAndVal[1];
                    
                    value.trim();
                    value.replace(" ", "");
                    
                    highscore = Integer.parseInt(value);
                } else if (l.contains("gta_mode")) {
                    String[] keyAndVal = l.split(":");
                    String value = keyAndVal[1];
                    
                    value.trim();
                    value.replace(" ", "");
                    
                    gtaMode = Boolean.parseBoolean(value);
                }
            }
        } catch (IndexOutOfBoundsException e) {     // File found but not the highscore
            try {
                FileWriter wr = new FileWriter(DATA_FILE_PATH);
                wr.write("highscore:0");
                wr.close();
            } catch (IOException i) {
                System.out.println("Error occured trying to add highscore");
                i.printStackTrace();
            }
        } catch (IOException e) {                   // If the file isn't found, create a new one
            File newFile = new File(".\\data\\data.txt");
            
            try {
                if (newFile.createNewFile()) {
                    FileWriter wr = new FileWriter(newFile);
                    wr.write("highscore:0");
                    wr.close();
                } 
            } catch (IOException i) {
                System.out.println("An error has occured attempting to create a data file.");
                e.printStackTrace();
            }
        }
        nextScene();
    }
    
    /**
     * This method takes in a score and a highscore and does some logic checks to see if the data file should store the new highscore.
     * @param score         The score that the user was able to achieve in this run.
     * @param highscore     The highest score the user has achieved
     */
    public void updateHighScore(int score, int highscore) {
        if (score > highscore) {
            try {
                FileWriter wr = new FileWriter(DATA_FILE_PATH);
                wr.write("highscore:" + Integer.toString(score) + "\ngta_mode:"+gtaMode);
                wr.close();
            } catch (IOException e) {
                System.out.println("An error has occured attempting to update highscore");
                e.printStackTrace();
            }
            
            this.highscore = score;
        }
    }
    
    /**
     * This method updates the gta mode data peice in the data file
     * @param selected      Is the gta mode set to true or false
     */
    public void updateGtaMode(boolean selected) {
            try {
                FileWriter wr = new FileWriter(DATA_FILE_PATH);
                wr.write("highscore:" + Integer.toString(highscore)+"\ngta_mode:" + selected);
                wr.close();
            } catch (IOException e) {
                System.out.println("An error has occured attempting to update highscore");
                e.printStackTrace();
            }
            
            this.gtaMode = selected;
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
        // Get the background.
        GreenfootImage bg = getBackground();
        Graphics g = bg.getAwtImage().createGraphics();
        FontMetrics fm = g.getFontMetrics(new java.awt.Font(f.getName(), bold ? 1 : 0, f.getSize()));
        
        // Get width of the text
        int w = fm.stringWidth(t);
        x = center ? x - w / 2 : x;
        
        // Add the text to the screen with the provided properties (color, and font).
        bg.setColor(c);
        bg.setFont(f);
        bg.drawString(t, x, y);
    }
    
    /**
     * This method sets up instantiates the game scenes and calls the method to setup the corresponding scene.
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
                gameScreen = new Game(this, carPath, gtaMode);
                gameScreen.setScene();
                break;
            case 3:
                endScreen = new End(this, gameScreen.getScore(), highscore);
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
     */
    private void resetScene() {
        // Get a list of all the objects in the world
        List objects = getObjects(null);
        // Remove all the objects in the world
        removeObjects(objects);
        
        // Initialize the next scene
        nextScene();
    }
    
    /**
     * Act - do whatever the Scene wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
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
                
            case 4: // Care select screen
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