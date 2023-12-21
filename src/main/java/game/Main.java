package game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Engine;
import engine.IAppLogic;
import engine.MouseInput;
import engine.Window;
import engine.graph.Model;
import engine.graph.Render;
import engine.scene.AnimationData;
import engine.scene.Camera;
import engine.scene.Entity;
import engine.scene.Fog;
import engine.scene.ModelLoader;
import engine.scene.Scene;
import engine.scene.SkyBox;
import engine.scene.lights.AmbientLight;
import engine.scene.lights.DirLight;
import engine.scene.lights.SceneLights;

/**
 * 
 * @SVNDB0X_NGIN
 *               This is a Test sandbox engine for learning purposes.
 *               Uses LWJGL3 and OpenGL 3.2
 */
public class Main implements IAppLogic {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private float lightAngle;

    private static final int NUM_CHUNKS = 3;
    private Entity[][] terrainEntities;
    private AnimationData animationData;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Window.WindowOptions opts = new Window.WindowOptions();
        opts.antiAliasing = true;
        opts.height = 720;
        opts.width = 1280;
        Engine gameEng = new Engine("SVNDB0X NGIN", opts, main);
        gameEng.start();
    }

    @Override
    public void cleanup() {
        // Nothing done here yet.
    }

    @Override
    public void init(Window window, Scene scene, Render render) throws Exception {
        String terrainModelId = "terrain";
        Model terrainModel = ModelLoader.loadModel(terrainModelId,
                "/Users/jareemhoff/dev/java/sandbox/resources/models/terrain/terrain.obj",
                scene.getTextureCache(), false);
        scene.addModel(terrainModel);
        Entity terrainEntity = new Entity("terrainEntity", terrainModelId);
        terrainEntity.setScale(100.0f);
        terrainEntity.updateModelMatrix();
        scene.addEntity(terrainEntity);

        String bobModelId = "bobModel";
        Model bobModel = ModelLoader.loadModel(bobModelId,
                "/Users/jareemhoff/dev/java/sandbox/resources/models/xbot/Jogging 3.dae",
                scene.getTextureCache(), true);
        scene.addModel(bobModel);
        Entity bobEntity = new Entity("bobEntity", bobModelId);
        bobEntity.setScale(1);
        bobEntity.updateModelMatrix();
        animationData = new AnimationData(bobModel.getAnimationList().get(0));
        bobEntity.setAnimationData(animationData);
        scene.addEntity(bobEntity);

        SceneLights sceneLights = new SceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        ambientLight.setIntensity(0.5f);
        ambientLight.setColor(0.3f, 0.3f, 0.3f);

        DirLight dirLight = sceneLights.getDirLight();
        dirLight.setPosition(0, 1, 0);
        dirLight.setIntensity(1.0f);
        scene.setSceneLights(sceneLights);

        SkyBox skyBox = new SkyBox("/Users/jareemhoff/dev/java/sandbox/resources/models/skybox/skybox.obj",
                scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(100);
        skyBox.getSkyBoxEntity().updateModelMatrix();
        scene.setSkyBox(skyBox);

        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 0.02f));

        Camera camera = scene.getCamera();
        camera.setPosition(0f, 1.0f, 2f);
        // camera.addRotation((float) Math.toRadians(15.0f), (float) Math.toRadians(390.f));
        
        lightAngle = 0;
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();

        // Move when key is pressed:
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
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveDown(move);
        }

        // Move faster when shift is pressed:
        if (window.isKeyPressed(GLFW_KEY_W) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveForward(move + 2);
        } else if (window.isKeyPressed(GLFW_KEY_S) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveBackwards(move + 2);
        } else if (window.isKeyPressed(GLFW_KEY_A) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveLeft(move + 2);
        } else if (window.isKeyPressed(GLFW_KEY_D) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveRight(move + 2);
        } else if (window.isKeyPressed(GLFW_KEY_SPACE) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveUp(move + 2);
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move + 2);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            lightAngle -= 2.5f;
            if (lightAngle < -90) {
                lightAngle = -90;
            }
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            lightAngle += 2.5f;
            if (lightAngle > 90) {
                lightAngle = 90;
            }
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }

        SceneLights sceneLights = scene.getSceneLights();
        DirLight dirLight = sceneLights.getDirLight();
        double angRad = Math.toRadians(lightAngle);
        dirLight.getDirection().z = (float) Math.sin(angRad);
        dirLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        // animationData.nextFrame();
    }

    // public void updateTerrain(Scene scene) {
    //     int cellSize = 10;
    //     Camera camera = scene.getCamera();
    //     Vector3f cameraPos = camera.getPosition();
    //     int cellCol = (int) (cameraPos.x / cellSize);
    //     int cellRow = (int) (cameraPos.z / cellSize);
    //     int numRows = NUM_CHUNKS * 2 + 1;
    //     int numCols = numRows;
    //     int zOffset = -NUM_CHUNKS;
    //     float scale = cellSize / 2.0f;
    //     for (int j = 0; j < numRows; j++) {
    //         int xOffset = -NUM_CHUNKS;
    //         for (int i = 0; i < numCols; i++) {
    //             Entity entity = terrainEntities[j][i];
    //             entity.setScale(scale);
    //             entity.setPosition((cellCol + xOffset) * 2.0f, 0, (cellRow + zOffset) * 2.0f);
    //             entity.getModelMatrix().identity().scale(scale).translate(entity.getPosition());
    //             xOffset++;
    //         }
    //         zOffset++;
    //     }
    // }
}
