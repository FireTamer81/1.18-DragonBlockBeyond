package io.firetamer.dragonblockbeyond.util.quadHelpers;

import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.client.model.QuadTransformer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuadHelper {
    public static BakedQuad scaleQuad(BakedQuad quad, float scaleX, float scaleY, float scaleZ) {
        QuadTransformer transformer = new QuadTransformer(new Transformation(Matrix4f.createScaleMatrix(scaleX, scaleY, scaleZ)));
        return transformer.processOne(quad);
    }

    public static List<BakedQuad> scaleModel(List<BakedQuad> quads, float scaleX, float scaleY, float scaleZ) {
        QuadTransformer transformer = new QuadTransformer(new Transformation(Matrix4f.createScaleMatrix(scaleX, scaleY, scaleZ)));
        return transformer.processMany(quads);
    }

    public static BakedQuad rotateQuad(BakedQuad quads, int rotX, int rotY, int rotZ) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        QuadTransformer transformer = new QuadTransformer(new Transformation(rotateMatrix(matrix4f, rotX, rotY, rotZ)));
        return transformer.processOne(quads);
    }

    public static List<BakedQuad> rotateModel(List<BakedQuad> quads, int rotX, int rotY, int rotZ) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        QuadTransformer transformer = new QuadTransformer(new Transformation(rotateMatrix(matrix4f, rotX, rotY, rotZ)));
        return transformer.processMany(quads);
    }

    public static BakedQuad translateQuad(BakedQuad quad, Vector3f translation) {
        int[] vertexData = quad.getVertices();
        double[] vertexFloats = Arrays.stream(vertexData).mapToDouble(Float::intBitsToFloat).toArray();
        for (int i = 0; i < 4; i++) {
            vertexFloats[i * 8] += translation.x();
            vertexFloats[i * 8 + 1] += translation.y();
            vertexFloats[i * 8 + 2] += translation.z();
        }
        vertexData = Arrays.stream(vertexFloats).distinct().mapToInt(k -> Float.floatToIntBits((float) k)).toArray();
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    }

    public static List<BakedQuad> translateModel(List<BakedQuad> quads, Vector3f translation) {
        return quads.parallelStream().map(k -> translateQuad(k, translation)).collect(Collectors.toList());
    }

    //Angle to rotate, Angle to EulerAngle : 2Pi
    public static Matrix4f makeRotMatrix_X(int xRot) {
        double eulerAngle = (xRot / 360f) * Math.PI * 2;
        return new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, (float) Math.cos(eulerAngle), (float) -Math.sin(eulerAngle), 0,
                0, (float) Math.sin(eulerAngle), (float) Math.cos(eulerAngle), 0,
                0, 0, 0, 1
        });
    }

    public static Matrix4f makeRotMatrix_Y(int yRot) {
        double eulerAngle = (yRot / 360f) * Math.PI * 2;
        return new Matrix4f(new float[]{
                (float) Math.cos(eulerAngle), 0, (float) Math.sin(eulerAngle), 0,
                0, 1, 0, 0,
                (float) -Math.sin(eulerAngle), 0, (float) Math.cos(eulerAngle), 0,
                0, 0, 0, 1
        });
    }

    public static Matrix4f makeRotMatrix_Z(int zRot) {
        double eulerAngle = (zRot / 360f) * Math.PI * 2;
        return new Matrix4f(new float[]{
                (float) Math.cos(eulerAngle), -(float) Math.sin(eulerAngle), 0, 0,
                (float) Math.sin(eulerAngle), (float) Math.cos(eulerAngle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    public static Matrix4f rotateMatrix(Matrix4f matrix4f, int xRot, int yRot, int zRot) {
        matrix4f.multiply(makeRotMatrix_X(xRot));
        matrix4f.multiply(makeRotMatrix_Y(yRot));
        matrix4f.multiply(makeRotMatrix_Z(zRot));
        return matrix4f;
    }
}

