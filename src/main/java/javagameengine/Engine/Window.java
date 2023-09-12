package javagameengine.Engine;

import javagameengine.Util.*;
import javagameengine.Engine.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;



public class Window {
    private Vector2 windowSize = new Vector2();
    private String title;
    private long glfwWindow;

    private Color color = new Color();

    private boolean fadeToBlack = false;

    private static Window window = null;
    private static Scene currentScene;

    private Window() {
        this.windowSize = new Vector2(1920, 1080);
        this.title = "mario";
        this.color = new Color(1, 1, 1, 1);
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                //currentScene.init();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
        }
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        //free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        //setup error callback;
        GLFWErrorCallback.createPrint(System.err).set();

        //initialize glfw
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //congifure glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //create the window
        glfwWindow = glfwCreateWindow((int) windowSize.getX(), (int) windowSize.getY(), this.title, NULL, NULL);

        if (glfwWindow == NULL)
            throw new IllegalStateException("Failed to create the GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        
        // make the opengl context current
        glfwMakeContextCurrent(glfwWindow);

        //enable v-sync
        glfwSwapInterval(1);

        //make the window visible
        glfwShowWindow(glfwWindow);

        //this line is critical for lwjgl's interoperation with glfw's opengl context,
        // or any context that is managed externally. LWJGL detects the context that is current in the current thread.
        //creates the GLCapabilities instance and makes the opengl bindings available for use.

        GL.createCapabilities();

        Window.changeScene(0);
    }   

    public void loop() {
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor((float) this.color.getR(), (float) this.color.getG(), (float) this.color.getB(),
                    (float) this.color.getA());
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }
            

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
