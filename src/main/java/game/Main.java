package game;

import engine.Engine;
import engine.IAppLogic;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Render;
import engine.scene.Scene;

/**
 * SVNDB0X NGIN
 *
 */
public class Main implements IAppLogic {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Engine gameEng = new Engine("SVNDB0X NGIN", new Window.WindowOptions(), main);
        gameEng.start();
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void init(Window window, Scene scene, Render render) throws Exception {
        float[] positions = new float[] {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
        };

        Mesh mesh = new Mesh(positions, 3);
        scene.addMesh("triangle", mesh);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
    }
    
    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
    }

}
