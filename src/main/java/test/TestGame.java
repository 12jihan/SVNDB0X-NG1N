package test;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector3f;

import core.IAppLogic;
import core.ObjectLoader;
import core.RenderManager;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Model;
import core.entity.Texture;

public class TestGame implements IAppLogic {

    private int direction = 0;
    private float color = 0.0f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;

    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
            -0.5f,  0.5f, 0f,
            -0.5f, -0.5f, 0f,
             0.5f, -0.5f, 0f,
             0.5f,  0.5f, 0f,
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0, 
        };

        Model model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("/Users/jareemhoff/dev/java/sandbox/src/main/java/resources/textures/grassblock.png")));

        entity = new Entity(model, new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), 1);
    }

    @Override
    public void input() {
        if (window.isKeyPressed(GLFW_KEY_UP))
            direction = 1;
        else if (window.isKeyPressed(GLFW_KEY_DOWN))
            direction = -1;
        else
            direction = 0;
    }

    @Override
    public void update() {
        color += direction * 0.01f;
        if (color > 1)
            color = 1.0f;
        else if (color <= 0.0f)
            color = 0.0f;

        if (entity.getPos().x < -1.5f)
            entity.getPos().x = 1.5f;
        entity.getPos().x -= 0.01f;
    }

    @Override
    public void render() {
        if (window.isResize()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.render(entity);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }

}
