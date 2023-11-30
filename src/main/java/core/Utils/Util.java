package core.Utils;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

public class Util {
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
      FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
      buffer.put(data).flip();
      return buffer;
    }

    
}
