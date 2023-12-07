package game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;
import engine.*;
import engine.graph.*;
import engine.scene.*;
import engine.scene.lights.*;
import java.lang.Math;
/**
 * 
 * @SVNDB0X_NGIN
 *               This is a Test sandbox engine for learning purposes.
 *               Uses LWJGL3 and OpenGL 3.2
 */
public class Main implements IAppLogic {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private static final int NUM_CHUNKS = 4;

    private Entity[][] terrainEntities;

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
        /*
         * Quad Model
         */
        String quadModelId = "quad-model";
        Model quadModel = ModelLoader.loadModel("quad-model", "/Users/jareemhoff/dev/java/sandbox/resources/quad/quad.obj",
                scene.getTextureCache());
        scene.addModel(quadModel);

        int numRows = NUM_CHUNKS * 2 + 1;
        int numCols = numRows;
        terrainEntities = new Entity[numRows][numCols];
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
                Entity entity = new Entity("TERRAIN_" + j + "_" + i, quadModelId);
                terrainEntities[j][i] = entity;
                scene.addEntity(entity);
            }
        }

        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.2f);
        scene.setSceneLights(sceneLights);

        SkyBox skyBox = new SkyBox("/Users/jareemhoff/dev/java/sandbox/resources/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(50);
        scene.setSkyBox(skyBox);

        scene.getCamera().moveUp(0.1f);

        updateTerrain(scene);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        updateTerrain(scene);
    }

    public void updateTerrain(Scene scene) {
        int cellSize = 10;
        Camera camera = scene.getCamera();
        Vector3f cameraPos = camera.getPosition();
        int cellCol = (int) (cameraPos.x / cellSize);
        int cellRow = (int) (cameraPos.z / cellSize);

        int numRows = NUM_CHUNKS * 2 + 1;
        int numCols = numRows;
        int zOffset = -NUM_CHUNKS;
        float scale = cellSize / 2.0f;
        for (int j = 0; j < numRows; j++) {
            int xOffset = -NUM_CHUNKS;
            for (int i = 0; i < numCols; i++) {
                Entity entity = terrainEntities[j][i];
                entity.setScale(scale);
                entity.setPosition((cellCol + xOffset) * 2.0f, 0, (cellRow + zOffset) * 2.0f);
                entity.getModelMatrix().identity().scale(scale).translate(entity.getPosition());
                xOffset++;
            }
            zOffset++;
        }
    }
}
