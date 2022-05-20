package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelHelper {

    /**
     * This just builds vectors and is useful for clean code
     *
     * @param x x component
     * @param y y component
     * @param z z component
     * @return 3D-Vector with input values. Why am I writing this...
     */
    public static DBVector3d v(double x, double y, double z) {
        return new DBVector3d(x, y, z);
    }


    /**
     * This method is used to put all parameters for a vertex. A vertex in this context
     * is something like a point or a vector with special attributes. These include the
     * texture, the light level and so on. This method is needed for the quads (which
     * are something like the faces of a cuboid) and those are determined by 4 vertices
     *
     * @param builder used to construct the quads, content is saved as int array afterwards
     * @param normal  normal vector
     * @param x       x component of the vertex
     * @param y       y component of the vertex
     * @param z       z component of the vertex
     * @param u       u component of the texture, that means the horizontal axis
     * @param v       v component of the texture, that means the vertical axis
     * @param sprite  the texture for the vertex, sprite[u,v] will be displayed for this vertex
     * @param r       red value
     * @param g       green value
     * @param b       blue value
     */
    private static void putVertex(BakedQuadBuilder builder, Vector3d normal,
                                  double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {

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

    /**
     * This method is used to create quads. Simply put, a quad is one face of a block
     * This method calls the putVertex method four times, because we need 4 vertices
     * to determine a rectangle, which is the face of the block. For this reason we
     * need 4 vectors, the texture, and 4 u and v values
     *
     * @param v1        first vector/point of the face
     * @param v2        second vector/point of the face
     * @param v3        third vector/point of the face
     * @param v4        forth vector/point of the face
     * @param sprite    texture of the block, saved as TextureAtlasSprite
     * @param ulow      low u value, determines the left corners of the sprite
     * @param uhigh     high u value, determines the right corners of the sprite
     * @param vlow      low v value, determines the top corners of the sprite
     * @param vhigh     high v value, determines the bottom corners of the sprite
     * @return Baked quad i.e. the completed face of a block
     */
    public static BakedQuad createQuad(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, float ulow, float uhigh, float vlow, float vhigh, int tintIndex) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        builder.setQuadTint(tintIndex);
        putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vlow, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, ulow, vhigh, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vhigh, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, uhigh, vlow, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    public static BakedQuad createQuad(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, float ulow, float uhigh, float vlow, float vhigh, int tintIndex, boolean invert) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();
        if (invert) {
            normal = normal.reverse();
        }

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        builder.setQuadTint(tintIndex);
        if (invert) {
            putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, ulow, vhigh, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vhigh, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v4.x, v4.y, v4.z, uhigh, vlow, sprite, 1.0f, 1.0f, 1.0f);
        } else {
            putVertex(builder, normal, v4.x, v4.y, v4.z, ulow, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, uhigh, vhigh, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vhigh, sprite, 1.0f, 1.0f, 1.0f);
        }
        return builder.build();
    }
    public static BakedQuad createQuadInverted(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, float ulow, float uhigh, float vlow, float vhigh, int tintIndex) {
        return createQuadInverted(v1, v2, v3, v4, sprite, ulow, uhigh, vlow, vhigh, tintIndex, false);
    }

    public static BakedQuad createQuadInverted(DBVector3d v1, DBVector3d v2, DBVector3d v3, DBVector3d v4, TextureAtlasSprite sprite, float ulow, float uhigh, float vlow, float vhigh, int tintIndex, boolean invert) {
        DBVector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();
        if (invert) {
            normal = normal.reverse();
        }

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        builder.setQuadTint(tintIndex);
        if (invert) {
            putVertex(builder, normal, v4.x, v4.y, v4.z, ulow, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, uhigh, vhigh, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vhigh, sprite, 1.0f, 1.0f, 1.0f);
        } else {
            putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v2.x, v2.y, v2.z, uhigh, vlow, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vhigh, sprite, 1.0f, 1.0f, 1.0f);
            putVertex(builder, normal, v4.x, v4.y, v4.z, ulow, vhigh, sprite, 1.0f, 1.0f, 1.0f);
        }
        return builder.build();
    }

    /**
     * Important Note: These versions of the methods always has the quads rendered as true. My best guess as for why this is possible is because logic
     * Might cause some quads to not render when they should because the are not actually obstructed.
     * -----------------------------------------------------------------------
     * This method is used to create a cuboid from six faces. Input values determine
     * the dimensions of the cuboid and the texture to apply.
     * Example 1: full block with dirt texture would be:
     * (0f,1f,0f,1f,0f,1f, >path of dirt texture<, 0)
     * I.e. the dimensions of the block are from (0,0,0) to (16,16,16)
     * Example 2: oak fence:
     * (6/16f,10/16f,0f,1f,6/16f,10/16f, >oak planks<, 0)
     * I.e. the dimensions of a fence, being (6,0,6) to (10,16,10)
     *
     * @param xl        low x component of the block
     * @param xh        high x component of the block
     * @param yl        low y component of the block
     * @param yh        high y component of the block
     * @param zl        low z component of the block
     * @param zh        high z component of the block
     * @param texture   TextureAtlasSprite of the block
     * @return List of baked quads, i.e. List of six faces
     */


	@SuppressWarnings("resource")
    public static List<BakedQuad> createCuboid(float xl, float xh, float yl, float yh, float zl, float zh, TextureAtlasSprite texture, int tintIndex,
                                               boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        List<BakedQuad> quads = new ArrayList<>();
        //Eight corners of the block
        DBVector3d NWU = v(xl, yh, zl); //North-West-Up
        DBVector3d SWU = v(xl, yh, zh); //...
        DBVector3d NWD = v(xl, yl, zl);
        DBVector3d SWD = v(xl, yl, zh);
        DBVector3d NEU = v(xh, yh, zl);
        DBVector3d SEU = v(xh, yh, zh);
        DBVector3d NED = v(xh, yl, zl);
        DBVector3d SED = v(xh, yl, zh); //South-East-Down
        if (xh - xl > 1 || yh - yl > 1 || zh - zl > 1) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.displayClientMessage(new TextComponent("message.blockcarpentry.block_error"), true);
            }
            return quads;
        }
        if (xl < 0) { xl++; xh++; }
        if (xh > 1) { xh--; xl--; }
        if (yl < 0) { yl++; yh++; }
        if (yh > 1) { yh--; yl--; }
        if (zl < 0) { zl++; zh++; }
        if (zh > 1) { zh--; zl--; }

        if (up) { quads.add(createQuad(NWU, SWU, SEU, NEU, texture, xl * 16, xh * 16, zl * 16, zh * 16, tintIndex)); }
        if (down) { quads.add(createQuad(NED, SED, SWD, NWD, texture, xh * 16, xl * 16, 16 - zl * 16, 16 - zh * 16, tintIndex)); }
        if (west) { quads.add(createQuad(NWU, NWD, SWD, SWU, texture, zl * 16, zh * 16, 16 - yh * 16, 16 - yl * 16, tintIndex)); }
        if (east) { quads.add(createQuad(SEU, SED, NED, NEU, texture, 16 - zh * 16, 16 - zl * 16, 16 - yh * 16, 16 - yl * 16, tintIndex)); }
        if (north) { quads.add(createQuad(NEU, NED, NWD, NWU, texture, 16 - xh * 16, 16 - xl * 16, 16 - yh * 16, 16 - yl * 16, tintIndex)); }
        if (south) { quads.add(createQuad(SWU, SWD, SED, SEU, texture, xl * 16, xh * 16, 16 - yh * 16, 16 - yl * 16, tintIndex)); }

        return quads;
    }

    public static List<BakedQuad> createCuboid(float xl, float xh, float yl, float yh, float zl, float zh, TextureAtlasSprite texture, int tintIndex) {
        List<BakedQuad> quads = new ArrayList<>();
        //Eight corners of the block
        DBVector3d NWU = v(xl, yh, zl); //North-West-Up
        DBVector3d SWU = v(xl, yh, zh); //South-West-Up
        DBVector3d NWD = v(xl, yl, zl); //North-West-Down
        DBVector3d SWD = v(xl, yl, zh); //South-West-Down
        DBVector3d NEU = v(xh, yh, zl); //North-East-Up
        DBVector3d SEU = v(xh, yh, zh); //South-East-Up
        DBVector3d NED = v(xh, yl, zl); //North-East-Down
        DBVector3d SED = v(xh, yl, zh); //South-East-Down

        if (xh - xl > 1 || yh - yl > 1 || zh - zl > 1) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.displayClientMessage(new TextComponent("message.blockcarpentry.block_error"), true);
            }
            return quads;
        }

        if (xl < 0) { xl++; xh++; }
        if (xh > 1) { xh--; xl--; }
        if (yl < 0) { yl++; yh++; }
        if (yh > 1) { yh--; yl--; }
        if (zl < 0) { zl++; zh++; }
        if (zh > 1) { zh--; zl--; }

        quads.add(createQuad(NWU, SWU, SEU, NEU, texture, xl * 16, xh * 16, zl * 16, zh * 16, tintIndex));
        quads.add(createQuad(NED, SED, SWD, NWD, texture, xh * 16, xl * 16, 16 - zl * 16, 16 - zh * 16, tintIndex));
        quads.add(createQuad(NWU, NWD, SWD, SWU, texture, zl * 16, zh * 16, 16 - yh * 16, 16 - yl * 16, tintIndex));
        quads.add(createQuad(SEU, SED, NED, NEU, texture, 16 - zh * 16, 16 - zl * 16, 16 - yh * 16, 16 - yl * 16, tintIndex));
        quads.add(createQuad(NEU, NED, NWD, NWU, texture, 16 - xh * 16, 16 - xl * 16, 16 - yh * 16, 16 - yl * 16, tintIndex));
        quads.add(createQuad(SWU, SWD, SED, SEU, texture, xl * 16, xh * 16, 16 - yh * 16, 16 - yl * 16, tintIndex));

        return quads;
    }
}
