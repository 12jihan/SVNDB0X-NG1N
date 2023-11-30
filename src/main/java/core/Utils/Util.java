package core.Utils;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.lwjgl.system.MemoryUtil;

public class Util {
  public static FloatBuffer storeDataInFloatBuffer(float[] data) {
    FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
    buffer.put(data).flip();
    return buffer;
  }

  public static IntBuffer storeDataInIntBuffer(int[] data) {
    IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
    buffer.put(data).flip();
    return buffer;
  }

  public static String loadResource(String filename) throws Exception {
    String result;
    System.out.println("About to start trying to read the filepath: \n" + filename);
    try (
        InputStream in = Util.class.getResourceAsStream(filename);
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
      System.out.println("file is path is: \n\t" + filename);
      result = scanner.useDelimiter("\\A").next();
    }

    System.out.println("~~~~~~~~\n\nscanner results: \n\n~~~~~~~~\n\n" + result + "\n\n~~~~~~~~\n\n");
    return result;
  }
}
