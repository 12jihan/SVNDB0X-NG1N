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
    private LightControls lightControls;


    // private Entity[][] terrainEntities;

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
        String terrainModelId = "terrain";
        Model terrainModel = ModelLoader.loadModel(terrainModelId, "/Users/jareemhoff/dev/java/sandbox/resources/textures/terrain/terrain.obj",
                scene.getTextureCache());
        scene.addModel(terrainModel);
        Entity terrainEntity = new Entity("terrainEntity", terrainModelId);
        terrainEntity.setScale(100.0f);
        terrainEntity.updateModelMatrix();
        scene.addEntity(terrainEntity);

        SceneLights sceneLights = new SceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        ambientLight.setIntensity(0.2f);
        ambientLight.setColor(0.3f, 0.3f, 0.3f);
        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1),
                new Vector3f(0, 0, -1.4f), 1.0f));

        DirLight dirLight = sceneLights.getDirLight();
        dirLight.setPosition(0, 1, 0);
        dirLight.setIntensity(1.0f);
        scene.setSceneLights(sceneLights);

        
        SkyBox skyBox = new SkyBox("/Users/jareemhoff/dev/java/sandbox/resources/textures/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(50);
        scene.setSkyBox(skyBox);
        
        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 1f));
        
        lightControls = new LightControls(scene);
        scene.setGuiInstance(lightControls);
        scene.getCamera().moveUp(0.1f);

    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }
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
        // Nothing to be done here.
    }
}
