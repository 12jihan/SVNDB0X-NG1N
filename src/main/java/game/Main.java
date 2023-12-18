package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Engine;
import engine.IAppLogic;
import engine.MouseInput;
import engine.Window;
import engine.graph.Model;
import engine.graph.Render;
import engine.scene.Camera;
import engine.scene.Entity;
import engine.scene.Fog;
import engine.scene.ModelLoader;
import engine.scene.Scene;
import engine.scene.SkyBox;
import engine.scene.lights.AmbientLight;
import engine.scene.lights.DirLight;
import engine.scene.lights.LightControls;
import engine.scene.lights.PointLight;
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

    private static final int NUM_CHUNKS = 4;
    private Entity[][] terrainEntities;
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
        String wallNoNormalsModelId = "quad-no-normals-model";
        Model quadModelNoNormals = ModelLoader.loadModel(wallNoNormalsModelId,
                "/Users/jareemhoff/dev/java/sandbox/resources/textures/wall/wall_nonormals.obj",
                scene.getTextureCache());
        scene.addModel(quadModelNoNormals);

        Entity wallLeftEntity = new Entity("wallLeftEntity", wallNoNormalsModelId);
        wallLeftEntity.setPosition(-3f, 0, 0);
        wallLeftEntity.setScale(2.0f);
        wallLeftEntity.updateModelMatrix();
        scene.addEntity(wallLeftEntity);

        String wallModelId = "quad-model";
        Model wallQuadModel = ModelLoader.loadModel(wallModelId,
                "/Users/jareemhoff/dev/java/sandbox/resources/textures/wall/wall.obj",
                scene.getTextureCache());
        scene.addModel(wallQuadModel);

        // String quadModelId = "quad-model";
        // Model quadModel = ModelLoader.loadModel("quad-model",
        // "/Users/jareemhoff/dev/java/sandbox/resources/textures/terrain/quad/quad.obj",
        // scene.getTextureCache());
        // scene.addModel(quadModel);

        Entity wallRightEntity = new Entity("wallRightEntity", wallModelId);
        wallRightEntity.setPosition(3f, 0, 0);
        wallRightEntity.setScale(2.0f);
        wallRightEntity.updateModelMatrix();
        scene.addEntity(wallRightEntity);

        // Terrain building over here:
        // int numRows = NUM_CHUNKS * 2 + 1;
        // int numCols = numRows;
        // terrainEntities = new Entity[numRows][numCols];
        // for (int j = 0; j < numRows; j++) {
        // for (int i = 0; i < numCols; i++) {
        // Entity entity = new Entity("TERRAIN_" + j + "_" + i, quadModelId);
        // terrainEntities[j][i] = entity;
        // scene.addEntity(entity);
        // }
        // }

        // All lights are over here:
        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.2f);
        DirLight dirLight = sceneLights.getDirLight();
        dirLight.setPosition(1, 1, 0);
        dirLight.setIntensity(1.0f);
        scene.setSceneLights(sceneLights);

        // SceneLights sceneLights = new SceneLights();
        // AmbientLight ambientLight = sceneLights.getAmbientLight();
        // ambientLight.setIntensity(0.2f);
        // ambientLight.setColor(0.3f, 0.3f, 0.3f);
        // sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1),
        // new Vector3f(0, 0, -1.4f), 1.0f));

        // DirLight dirLight = sceneLights.getDirLight();
        // dirLight.setPosition(0, 1, 0);
        // dirLight.setIntensity(1.0f);
        // scene.setSceneLights(sceneLights);

        // Skybox over here:
        /*
         * @TODO: Skybox is not working properly. It is not rendering the skybox when I
         * change to different
         * textures.
         */
        // SkyBox skyBox = new
        // SkyBox("/Users/jareemhoff/dev/java/sandbox/resources/textures/skybox/skybox.obj",
        // scene.getTextureCache());
        // skyBox.getSkyBoxEntity().setScale(50);
        // scene.setSkyBox(skyBox);

        // Scene builinging everything over here:
        // scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.09f), 1f));
        // lightControls = new LightControls(scene);
        // scene.setGuiInstance(lightControls);
        Camera camera = scene.getCamera();
        camera.moveUp(5.0f);
        camera.addRotation((float) Math.toRadians(90), 0);
        camera.addRotation(0, (float) Math.toRadians(90));
        lightAngle = -35;

        // updateTerrain(scene);
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
        dirLight.getDirection().x = (float) Math.sin(angRad);
        dirLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        // updateTerrain(scene);
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
