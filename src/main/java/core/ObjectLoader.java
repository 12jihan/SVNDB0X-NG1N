package core;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import core.Utils.Util;
import core.entity.Model;

public class ObjectLoader {
    
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public Model loadModel(float[] vertices) {
        int id = createVAO();
        storeDataInAttribList(0, 3, vertices);
        unbind();
        return new Model(id, vertices.length / 3);
    }

    private int createVAO() {
        int id = glGenVertexArrays();
        vaos.add(id);
        glBindVertexArray(id);
        return id;
    }

    private void storeDataInAttribList(int attribNo, int vertexCount, float[] data) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Util.storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNo, vertexCount, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbind() {
        glBindVertexArray(0);
    }

    public void cleanup() {
        for(int vao : vaos)
            glDeleteVertexArrays(vao);
        for(int vbo : vbos)
            glDeleteBuffers(vbo);
    }
}
