import greenfoot.*;


class End
{
        private final Scene scene;
        private int nextState = 0;
        
        private Btn homeBtn;

        End(Scene scene)
        {
            this.scene = scene;
        }
        
        public void setScene()
        {
            scene.getBackground().setColor(new Color(93, 173, 226));
            scene.getBackground().fill();
            
            homeBtn = new HomeBtn();
            scene.addObject(homeBtn, scene.WIDTH / 2, scene.HEIGHT - 200);
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