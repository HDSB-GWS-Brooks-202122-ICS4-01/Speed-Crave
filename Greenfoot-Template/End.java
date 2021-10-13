import greenfoot.*;
import java.util.Random;

/**
 * The end screen of the game. Displays score and highscore, exit and home buttons.
 * 
 * Selim Abdelwahab
 * 2021-10-13
 */
class End
{
        private final Scene SCENE;
        private final Random RAND = new Random(); 
        private int nextState = 0;
        
        private Btn homeBtn;
        private Btn exitBtn;
        private final int SCORE;
        private final int HIGHSCORE;
        
        /**
         * Constructor for the End class
         * @param SCENE     The world object.
         * @param SCORE     The score the user gets at the end of the game.
         */
        End(Scene SCENE, int SCORE, int HIGHSCORE)
        {
            this.SCENE = SCENE;
            this.SCORE = SCORE;
            this.HIGHSCORE = HIGHSCORE;
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
         * This method returns a string based on the player's score.
         * @return String          Text to be outputed.
         */
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
        
        /**
         * This method sets the end screen scene.
         */
        public void setScene()
        {      
            // Set background color.
            SCENE.getBackground().setColor(new Color(210, 180, 222));
            SCENE.getBackground().fill();
            
            // Get post game message
            String[] messages = getScoreText().split("\n");
            Font mFont = new Font("MONOSPACED", true, false, 50);
            Font sFont = new Font("ARIAL", true, false, 60);
            
            // Display the post game message
            for (int i = 0; i < messages.length; i++)
            {
                SCENE.addText(messages[i], mFont, Color.BLACK, SCENE.WIDTH/2, 100 + 50 * i, false, true);
            }
            
            // Display the score
            SCENE.addText("SCORE", sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2, false, true);
            SCENE.addText(Integer.toString(SCORE), sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2 + 60, false, true);
            
            String highscoreText = (SCORE > HIGHSCORE) ? "NEW HIGHSCORE" : "HIGHSCORE"; 
            
            // Display the score
            SCENE.addText(highscoreText, sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2 + 180, false, true);
            SCENE.addText(Integer.toString((SCORE > HIGHSCORE) ? SCORE : HIGHSCORE), sFont, Color.BLACK, SCENE.WIDTH/2, SCENE.HEIGHT / 2 + 240, false, true);
            
            // Add home button
            homeBtn = new HomeBtn();
            SCENE.addObject(homeBtn, 150, SCENE.HEIGHT - 100);
            
            // Add exit button
            exitBtn = new ExitBtn();
            SCENE.addObject(exitBtn, SCENE.WIDTH - 150, SCENE.HEIGHT - 100);
            
            SCENE.updateHighScore(SCORE, HIGHSCORE);
        }
        
        /**
         * Act - do whatever the End wants to do. This method is called whenever
         * the 'Act' or 'Run' button gets pressed in the environment.
         */
        public void act()
        {
            if (homeBtn.getClicked())
                nextState = 1;
                
            if (exitBtn.getClicked())
                System.exit(0);
        }
}