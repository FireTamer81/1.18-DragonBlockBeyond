package io.firetamer.dragonblockbeyond.util;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

public class QuadCreator {
    public static BakedQuad createQuad(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, Direction side) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();
        if (side == null) {
            BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
            builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
            putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
            return builder.build();
        }
        return new BakedQuadBuilder(sprite).build();
    }

    public static BakedQuad createQuadTransparent(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, Direction side, float alpha) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();
        if (side == null) {
            BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
            builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
            putVertexTransparent(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f, alpha);
            putVertexTransparent(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f, alpha);
            putVertexTransparent(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f, alpha);
            putVertexTransparent(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f, alpha);
            return builder.build();
        }
        return new BakedQuadBuilder(sprite).build();
    }

    public static BakedQuad createTriangle(DBVector3d v1, DBVector3d v2, DBVector3d v3, TextureAtlasSprite sprite, Direction side) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();
        if (side == null) {
            BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
            builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
            putVertex(builder, normal, v1.x, v1.y, v1.z, tw / 2f, th / 2f, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
            return builder.build();
        }
        return new BakedQuadBuilder(sprite).build();
    }

    private static void putVertex(BakedQuadBuilder builder, DBVector3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {
        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0; j < elements.size(); j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getU(u);
                            float iv = sprite.getV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    private static void putVertexTransparent(BakedQuadBuilder builder, DBVector3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b, float a) {
        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0; j < elements.size(); j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, a);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getU(u);
                            float iv = sprite.getV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }
}
