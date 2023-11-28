package core;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        WindowManager window = new WindowManager("Hello World Game!", 1280, 720, false);
        window.init();

        while(window.windowShouldClose()) {
            window.update();
        }

        window.cleanup();
    }
}
