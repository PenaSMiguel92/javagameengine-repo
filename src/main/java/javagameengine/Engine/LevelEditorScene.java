package javagameengine.Engine;

import javagameengine.Engine.*;
import javagameengine.Util.Color;
import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    public LevelEditorScene() {
        System.out.println("Inside Level Editor Scene");
    }

    @Override
    public void update(float dt) {
        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Color lastColor = Window.get().getColor();
            Window.get().setColor(new Color(lastColor.getR() - dt * 5.0f, 
                                            lastColor.getG() - dt * 5.0f,
                                            lastColor.getB() - dt * 5.0f,
                                          1)
                                 );

        } else if (changingScene) {
            Window.changeScene(1);
        }
            
    }
}
