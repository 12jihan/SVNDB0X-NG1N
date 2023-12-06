package engine.graph;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import engine.Window;
import engine.scene.Scene;

public class Render {

    private SceneRender sceneRender;
    private GuiRender guiRender;

    public Render(Window window) throws Exception {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        sceneRender = new SceneRender();
        guiRender = new GuiRender(window);
    }

    public void cleanup() {
        sceneRender.cleanup();
        guiRender.cleanup();

    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        sceneRender.render(scene);
    }

    public void resize(int width, int height) {
        guiRender.resize(width, height);
    }
}
