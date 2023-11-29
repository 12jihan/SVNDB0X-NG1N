package core;

/**
 * Hello world!
 *
 */
public class Launcher 
{
    private static WindowManager window;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        window = new WindowManager("SVNDB0X NGIN", 0, 0, false);
        window.init();

        while(!window.windowShouldClose()) {
            window.update();
        }

        window.cleanup();
    }

    
}
