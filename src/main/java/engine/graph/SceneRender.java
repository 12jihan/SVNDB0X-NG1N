package engine.graph;

import engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.*;
import static org.lwjgl.opengl.GL30.*;


public class SceneRender {
    private ShaderProgram shaderProgram;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("/Users/jareemhoff/dev/java/sandbox/resources/shaders/scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("/Users/jareemhoff/dev/java/sandbox/resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
        try {
            shaderProgram = new ShaderProgram(shaderModuleDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawArrays(GL_TRIANGLES, 0, mesh.getNumVertices());
        });
        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}
