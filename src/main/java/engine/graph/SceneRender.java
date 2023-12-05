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
        
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/Scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/Scene.frag", GL_FRAGMENT_SHADER));
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

        System.out.println("\nMesh Data:\n"  );
        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        });
        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}
