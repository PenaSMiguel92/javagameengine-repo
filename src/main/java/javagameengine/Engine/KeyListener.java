package javagameengine.Engine;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {

    }

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW_PRESS:
                get().keyPressed[key] = true;
                break;
            case GLFW_RELEASE:
                get().keyPressed[key] = false;
        }
    }
    
    public static boolean isKeyPressed(int keyCode) {
        if (keyCode >= get().keyPressed.length)
            return false;

        return get().keyPressed[keyCode];
    }
}
