import greenfoot.*;
import java.util.Random;


class End
{
        private final Scene SCENE;
        private final Random RAND = new Random(); 
        private int nextState = 0;
        
        private Btn homeBtn;
        private final int SCORE;

        End(Scene SCENE, int SCORE)
        {
            this.SCENE = SCENE;
            this.SCORE = SCORE;
        }
        
        private String getScoreText()
        {
            if (SCORE < 20) 
                return new String[]{"Are you even\ntrying?", "Don't give up\nso easily!"}[RAND.nextInt(2)];
            else if (SCORE < 40)
                return new String[]{"Someone failed their\nG2 road test...", "Who allowed you\nto drive?"}[RAND.nextInt(2)];
            else if (SCORE < 60)
                return new String[]{"Hmmm, not bad.\nNot good either.", "Surely...\nSurley, you can\ndo better?"}[RAND.nextInt(2)];
            else if (SCORE < 80)
                return "You're my future\ngetaway driver.";
            else if (SCORE < 100)
                return "WOW!\nNow Aim for a triple\ndigit score.";
            
            return "A master, you are!";
        }
        
        public void setScene()
        {        
            SCENE.getBackground().setColor(new Color(93, 173, 226));
            SCENE.getBackground().fill();
            
            String[] messages = getScoreText().split("\n");
            Font mFont = new Font("MONOSPACED", true, false, 50);
            Font sFont = new Font("ARIAL", true, false, 60);
            
            for (int i = 0; i < messages.length; i++)
            {
                SCENE.addText(messages[i], mFont, Color.BLACK, SCENE.WIDTH/2, 100 + 50 * i, false, true);
            }
            
            SCENE.addText("SCORE", sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2, false, true);
            SCENE.addText(Integer.toString(SCORE), sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2 + 60, false, true);
            
            homeBtn = new HomeBtn();
            SCENE.addObject(homeBtn, SCENE.WIDTH / 2, SCENE.HEIGHT - 200);
        }
        
        public int getNextState()
        {
            return nextState;
        }
        
        public void act()
        {
            if (homeBtn.getClicked())
                nextState = 1;
        }
}