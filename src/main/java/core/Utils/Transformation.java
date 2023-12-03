package core.Utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.entity.Entity;

public class Transformation {

    public static Matrix4f createTransformationMatrix(Entity entity) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPos())
                .rotateX((float) Math.toRadians(entity.getRot().x))
                .rotateY((float) Math.toRadians(entity.getRot().y))
                .rotateZ((float) Math.toRadians(entity.getRot().z))
                .scale(entity.getScale());

        return matrix;
    }
}
