import greenfoot.*;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;

class Intro
{
    private final Scene scene;
    private Btn startBtn;
    private Btn carSelectBtn;
    private int nextState = 0; 
    
    Intro(Scene scene)
    {
        this.scene = scene;
    }
    
    public int getNextState()
    {
        return nextState;
    }
    
    public void setScene() {
        

        scene.getBackground().setColor(new Color(50, 50, 50));
        scene.getBackground().fill();

        scene.addText(scene.GAME_NAME, new greenfoot.Font("MONOSPACED", true, false, 90), Color.WHITE, scene.WIDTH / 2, 200, true, true);    
        
        scene.addText("CONTROLS", new greenfoot.Font("MONOSPACED", true, true, 50), Color.WHITE, scene.WIDTH / 2, 300, true, true);
        scene.addText("'A' / 'Left Key' To strafe left", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, scene.WIDTH / 2, 350, false, true);
        scene.addText("'D' / 'Right Key' To strafe right", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, scene.WIDTH / 2, 400, false, true);
        scene.addText("Dodge cars to win!", new greenfoot.Font("ARIAL", false, false, 30), Color.GRAY, scene.WIDTH / 2, 450, false, true);
        scene.addText("STAY SAFE", new greenfoot.Font("ARIAL", true, false, 40), Color.GRAY, scene.WIDTH / 2, 500, true, true);
        
        scene.addText("It gets fast. (Like really fast)", new greenfoot.Font("MONOSPACED", false, false, 20), Color.GRAY, 10, scene.HEIGHT - 10, false, false);
        
        startBtn = new StartBtn();
        scene.addObject(startBtn, scene.WIDTH / 2, scene.HEIGHT - 250);
        
        carSelectBtn = new CarSelectBtn();
        scene.addObject(carSelectBtn, scene.WIDTH / 2, scene.HEIGHT - 150);
    }
    
    public void act() {            
        if (startBtn.getClicked())
            nextState = 2;
            
        if (carSelectBtn.getClicked())
            nextState = 4;
    }
}