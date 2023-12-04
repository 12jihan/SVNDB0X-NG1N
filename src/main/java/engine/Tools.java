package engine;

import java.io.IOException;
import java.nio.file.*;

public class Tools {
  private Tools() {

  }

  public static String readFile(String filePath) throws Exception {
    String str;

    try {
      str = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
      throw new Exception("Error reading file [" + filePath + "]", e);
    }
    
    return str;
  }
}
