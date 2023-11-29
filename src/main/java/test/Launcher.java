package test;

import core.EngineManager;
import core.WindowManager;

/**
 * SVNDB0X NGIN
 *
 */
public class Launcher 
{
    private static WindowManager window;
    private static TestGame game;

    public static void main( String[] args )
    {
        System.out.println( "\n Hello SVNDB0X NGIN! \n" );
        window = new WindowManager("SVNDB0X NGIN", 640, 360, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
