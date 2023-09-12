package javagameengine.Engine;
import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" + //
            "\n" + //
            "layout (location=0) in vec3 aPos;\n" + //
            "layout (location=1) in vec4 aColor;\n" + //
            "\n" + //
            "\n" + //
            "out vec4 fColor;\n" + //
            "\n" + //
            "void main() {\n" + //
            "    fColor = aColor;\n" + //
            "    gl_Position = vec4(aPos, 1.0);\n" + //
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" + //
            "\n" + //
            "in vec4 fColor;\n" + //
            "\n" + //
            "out vec4 color;\n" + //
            "\n" + //
            "void main() {\n" + //
            "    color = fColor;\n" + //
            "}";

    private int vertexID, fragmentID, shaderProgram;

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        //compile and link shaders

        //load and compile the vertex shader

        vertexID = glCreateShader(GL_VERTEX_SHADER);

        //pass shader source to gpu
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        //Check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl' \n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        //pass shader source to gpu
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        //Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl' \n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        //check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl' \n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }
    }

    @Override
    public void update(float dt) {
    
            
    }
}
