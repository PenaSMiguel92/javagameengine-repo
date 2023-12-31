package javagameengine.Engine;

import javagameengine.Renderer.*;
import javagameengine.Util.Time;

import org.lwjgl.*;
import java.nio.*;
import org.joml.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene {

    private float[] vertexArray = {
        //position              //color
         100.5f, -0.5f,  0.0f,      1.0f, 0.0f, 0.0f, 1.0f, //bottom right    0
        -0.5f,  100.5f,  0.0f,      0.0f, 1.0f, 0.0f, 1.0f, //top left        1
         100.5f,  100.5f,  0.0f,      0.0f, 0.0f, 1.0f, 1.0f, //top right       2
        -0.5f, -0.5f,  0.0f,      1.0f, 1.0f, 0.0f, 1.0f  //bottom left     3
    };
    //Important: must be in counter-clockwise order
    private int[] elementArray = {
            2, 1, 0, //top right triangle
            0, 1, 3
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;

    public LevelEditorScene() {
        
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        

      

        //generate VAO, VBO, and EBO buffer objects, and send to GPU

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        //Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        final int locationPosition = 0;
        final int positionOffset = 0;
        final int locationColor = 1;
        final int colorOffset = positionsSize * floatSizeBytes; 
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(locationPosition, positionsSize,
                              GL_FLOAT, false,
                              vertexSizeBytes, positionOffset);
        glEnableVertexAttribArray(locationPosition);

        glVertexAttribPointer(locationColor, colorSize,
                              GL_FLOAT, false,
                              vertexSizeBytes, colorOffset);
        glEnableVertexAttribArray(locationColor);
    }

    @Override
    public void update(float dt) {
        camera.position.x -= dt * 50.0f;
        camera.position.y -= dt * 20.0f;

        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
        //bind the vao that we're using
        glBindVertexArray(vaoID);

        //enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        
        defaultShader.detach();
    }
}
