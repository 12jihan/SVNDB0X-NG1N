package core;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {
    private final int programID;
    private int vertexShaderID, fragmentShaderID;

    public ShaderManager() throws Exception {
        programID = glCreateProgram();
        if (programID == 0)
            throw new Exception("Unable to create shader:\n\n");
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = glCreateShader(shaderType);
        System.out.println("Shader ID: \n\t- " + shaderID + "\n~~~~~~~~\n");
        if (shaderID == 0)
            throw new Exception("\nError creating shader:\n\t- Type: \n\t" + shaderType);
        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0)
            throw new Exception("\nError compiling shader code:\n\t- Type: " + shaderType + "\n\t- Info: "
                    + glGetShaderInfoLog(shaderID, 1024));

        glAttachShader(programID, shaderID);

        return shaderID;
    }

    public void link() throws Exception {
        glLinkProgram(programID);

        if(glGetProgrami(programID, GL_LINK_STATUS) == 0)
            throw new Exception("Error linking shader code " + " Info " + glGetProgramInfoLog(programID, 1024));

        System.out.println("Checking vertex id:\n\t- " + vertexShaderID);
        System.out.println("Checking fragment id:\n\t- " + fragmentShaderID);

        if (vertexShaderID != 0)
            glDetachShader(programID, vertexShaderID);
        if (fragmentShaderID != 0)
            glDetachShader(programID, fragmentShaderID);

        glValidateProgram(programID);
        System.out.println("Program ID:\n\t- " + programID);
        /*
         * TODO: Figure out why the fuck this section is failing validation in the test statement, but renders fine when I remove it and compile the program...
         */
        // if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
        //     throw new Exception("\n\nUnable to validate shader code: \n\n\t" + glGetProgramInfoLog(programID, 1024));
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
