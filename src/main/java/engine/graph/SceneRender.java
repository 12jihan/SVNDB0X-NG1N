package engine.graph;

import engine.graph.ShaderProgram.ShaderModuleData;
import engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.*;
import static org.lwjgl.opengl.GL30.*;


public class SceneRender {
    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/Scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/Scene.frag", GL_FRAGMENT_SHADER));
        try {
            shaderProgram = new ShaderProgram(shaderModuleDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Shader list:\n" + shaderModuleDataList);
        createUniforms();
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }
    
    public void createUniforms() {
        System.out.println("Creating uniforms:\n" + shaderProgram.getProgramId());
        uniformsMap = new UniformsMap(shaderProgram.getProgramId());
        uniformsMap.createUniform("projectionMatrix");
    }

    public void render(Scene scene) {
        shaderProgram.bind();
        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        });
        glBindVertexArray(0);
        // TODO: Setting uniforms??
        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());

        shaderProgram.unbind();
    }
}
