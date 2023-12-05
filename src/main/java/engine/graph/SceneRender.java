package engine.graph;

import engine.scene.Entity;
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
        
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("/Users/jareemhoff/dev/java/sandbox/resources/shaders/Scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("..//Users/jareemhoff/dev/java/sandbox/resources/shaders/Scene.frag", GL_FRAGMENT_SHADER));
        try {
            shaderProgram = new ShaderProgram(shaderModuleDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createUniforms();
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }
    
    public void createUniforms() {
        uniformsMap = new UniformsMap(shaderProgram.getProgramId());
        uniformsMap.createUniform("projectionMatrix");
        uniformsMap.createUniform("modelMatrix");
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        
        Collection<Model> models = scene.getModelMap().values();
        for (Model model : models) {
            model.getMeshList().stream().forEach(mesh -> {
                glBindVertexArray(mesh.getVaoId());
                List<Entity> entities = model.getEntitiesList();
                for (Entity entity : entities) {
                    uniformsMap.setUniform("modelMatrix", entity.getModelMatrix());
                    glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                }
            });
        }

        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}
