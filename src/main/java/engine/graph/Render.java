package engine.graph;

import org.lwjgl.opengl.GL;
import engine.Window;
import engine.scene.Scene;

import static org.lwjgl.opengl.GL13.*;

public class Render {

    private SceneRender sceneRender;
    private GuiRender guiRender;
    private SkyBoxRender skyBoxRender;


    public Render(Window window) throws Exception {
        GL.createCapabilities();
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_DEPTH_TEST);
        // glEnable(GL_CULL_FACE);
        // glCullFace(GL_BACK);
        // glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        sceneRender = new SceneRender();
        guiRender = new GuiRender(window);
        skyBoxRender = new SkyBoxRender();

    }

    public void cleanup() {
        sceneRender.cleanup();
        guiRender.cleanup();
        skyBoxRender.cleanup();
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        skyBoxRender.render(scene);
        sceneRender.render(scene);
        guiRender.render(scene);
    }

    public void resize(int width, int height) {
        guiRender.resize(width, height);
    }
}
