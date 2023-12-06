package engine.graph;

import engine.scene.Entity;
import engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

import java.util.*;
import static org.lwjgl.opengl.GL30.*;


public class SceneRender {
    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("/Users/jareemhoff/dev/java/sandbox/resources/shaders/Scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("/Users/jareemhoff/dev/java/sandbox/resources/shaders/Scene.frag", GL_FRAGMENT_SHADER));
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
        uniformsMap.createUniform("txtSampler");
        uniformsMap.createUniform("viewMatrix");
        uniformsMap.createUniform("material.diffuse");
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        uniformsMap.setUniform("txtSampler", 0);
        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        uniformsMap.setUniform("viewMatrix", scene.getCamera().getViewMatrix());
        
        Collection<Model> models = scene.getModelMap().values();
        TextureCache textureCache = scene.getTextureCache();
        for (Model model : models) {
            List<Entity> entities = model.getEntitiesList();

            for (Material material : model.getMaterialList()) {
                uniformsMap.setUniform("material.diffuse", material.getDiffuseColor());
                Texture texture = textureCache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                for (Mesh mesh : material.getMeshList()) {
                    glBindVertexArray(mesh.getVaoId());
                    for (Entity entity : entities) {
                        uniformsMap.setUniform("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }
        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}
