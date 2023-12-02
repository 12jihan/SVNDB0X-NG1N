package core;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {

    private final int programID;
    private int vertexShaderID, fragmentShaderID;

    private final Map<String, Integer> uniforms;

    public ShaderManager() throws Exception {
        programID = glCreateProgram();
        if (programID == 0)
            throw new Exception("Unable to create shader:\n\n");

        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programID, uniformName);
        if (uniformLocation < 0) 
            throw new Exception("Could not find uniform: \n " + uniformName);
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniform_name, Matrix4f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniform_name), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniform_name, int value) {
        glUniform1i(uniforms.get(uniform_name), value);
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = glCreateShader(shaderType);
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

        if (glGetProgrami(programID, GL_LINK_STATUS) == 0)
            throw new Exception("Error linking shader code " + " Info " + glGetProgramInfoLog(programID, 1024));

        if (vertexShaderID != 0)
            glDetachShader(programID, vertexShaderID);
        if (fragmentShaderID != 0)
            glDetachShader(programID, fragmentShaderID);

        glValidateProgram(programID);
        
        /*
         * Turns out this doesn't work for mac.
         * So this will turn this off when on a mac. 
         */
        if (!WindowManager.getSyscheck()) {
            if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
                throw new Exception(
                        "\n\nUnable to validate shader code: \n\n\t" + glGetProgramInfoLog(programID, 1024));
        }
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programID != 0)
            glDeleteProgram(programID);
    }
}
