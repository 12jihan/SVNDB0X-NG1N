package engine.graph;

import org.lwjgl.opengl.GL30;

import engine.Tools;

import static org.lwjgl.opengl.GL20.*;

import java.util.*;

public class ShaderProgram {
    private final int programId;
    
    public ShaderProgram(List<ShaderModuleData> shaderModuleDataList) throws Exception {
        programId = glCreateProgram();
        System.out.println("programId:\n" + programId);
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderModuleDataList.forEach(s -> {
            try {
                shaderModules.add(createShader(Tools.readFile(s.shaderFile), s.shaderType));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        link(shaderModules);

    }

    public void bind() {
        glUseProgram(programId);
    }
    
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public int getProgramId() {
        return programId;
    }

    private void link(List<Integer> shaderModules) {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shader code:\n" + glGetProgramInfoLog(programId, 1024));
        }

        shaderModules.forEach(s -> glDetachShader(programId, s));
        shaderModules.forEach(GL30::glDeleteShader);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void validate() {
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public record ShaderModuleData(String shaderFile, int shaderType) {
    }

}
