package core;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {
    private final int programID;
    private int vertexShaderID, fragmentShaderID;

    public ShaderManager() throws Exception {
        programID = glCreateProgram();
        if(programID == 0)
            throw new Exception("Could no create shader");
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = glCreateShader(shaderType);
        if(shaderID == 0)
            throw new Exception("Error creating shader. Type : " + shaderType);
        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0)
            throw new Exception("Error compiling shader code: Type: " + shaderType + " \n Info: " + glGetShaderInfoLog(shaderID, 1024));
        
        glAttachShader(programID, shaderID);

        return shaderID;
    }

    public void link() throws Exception {
        glLinkProgram(programID);
        if(glGetProgrami(programID, GL_LINK_STATUS) == 0)
            throw new Exception("Error linking shader code " + " Info " + glGetProgramInfoLog(programID, 1024));

        if(vertexShaderID != 0)
            glDetachShader(programID, vertexShaderID);

        if(fragmentShaderID != 0)
            glDetachShader(programID, vertexShaderID);

        glValidateProgram(programID);
        if(glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
            throw new Exception("Unable to validate shader code: " + glGetProgramInfoLog(programID, 1024));
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if(programID != 0)
        glDeleteProgram(programID);
    }
}
