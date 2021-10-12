import greenfoot.*;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;

class Intro
{
    private final Scene SCENE;
    private Btn startBtn;
    private Btn carSelectBtn;
    private int nextState = 0; 
    
    Intro(Scene SCENE)
    {
        this.SCENE = SCENE;
    }
    
    public int getNextState()
    {
        return nextState;
    }
    
    public void setScene() {
        SCENE.getBackground().setColor(new Color(50, 50, 50));
        SCENE.getBackground().fill();

        SCENE.addText(SCENE.GAME_NAME, new greenfoot.Font("MONOSPACED", true, false, 90), Color.WHITE, SCENE.WIDTH / 2, 200, true, true);    
        
        SCENE.addText("CONTROLS", new greenfoot.Font("MONOSPACED", true, true, 50), Color.WHITE, SCENE.WIDTH / 2, 300, true, true);
        SCENE.addText("'A' / 'Left Key' To strafe left", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 350, false, true);
        SCENE.addText("'D' / 'Right Key' To strafe right", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 400, false, true);
        SCENE.addText("Dodge cars to win, and pick-up gas!", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, SCENE.WIDTH / 2, 450, false, true);
        SCENE.addText("TRY TO SCORE 100", new greenfoot.Font("ARIAL", true, false, 40), Color.GRAY, SCENE.WIDTH / 2, 550, true, true);
        
        SCENE.addText("It gets fast. (Like really fast)", new greenfoot.Font("MONOSPACED", false, false, 20), Color.GRAY, 10, SCENE.HEIGHT - 10, false, false);
        
        startBtn = new StartBtn();
        SCENE.addObject(startBtn, SCENE.WIDTH / 2, SCENE.HEIGHT - 250);
        
        carSelectBtn = new CarSelectBtn();
        SCENE.addObject(carSelectBtn, SCENE.WIDTH / 2, SCENE.HEIGHT - 150);
    }
    
    public void act() {            
        if (startBtn.getClicked())
            nextState = 2;
            
        if (carSelectBtn.getClicked())
            nextState = 4;
    }
}