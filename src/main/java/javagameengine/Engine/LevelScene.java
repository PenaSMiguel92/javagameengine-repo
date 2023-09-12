package javagameengine.Engine;

import javagameengine.Util.Color;

public class LevelScene extends Scene {
    public LevelScene() {
        System.out.println("Inside Level Scene");
        Window.get().setColor(new Color(1, 1, 1, 1));
    }

    @Override
    public void update(float dt) {

    }
    
}
