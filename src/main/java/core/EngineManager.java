package core;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.lwjgl.glfw.GLFWErrorCallback;

public class EngineManager {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frametime = 1.0f / FRAMERATE;

    private boolean isRunning;
    
    private WindowManager window;
    private GLFWErrorCallback errorCallback;

    private void init() throws Exception {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        // Launcher.getWindow();
    }
}
