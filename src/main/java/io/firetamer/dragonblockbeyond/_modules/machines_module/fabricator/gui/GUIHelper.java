package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;
import io.firetamer.dragonblockbeyond.util.DBBColor;

public class GUIHelper {
    
    
    
    
    
    
    public static void drawFillColor(Matrix4f stack, float x1, float y1, float x2, float y2, GUIColor fillColor) {
        int[] fillColorArray = fillColor.getRGBA();

        float j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }
        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        BufferBuilder builder = Tessellator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();

        builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        builder.vertex(stack, x1, y2, 0.0F).color(fillColorArray[0], fillColorArray[1], fillColorArray[2], fillColorArray[3]).endVertex();
        builder.vertex(stack, x2, y2, 0.0F).color(fillColorArray[0], fillColorArray[1], fillColorArray[2], fillColorArray[3]).endVertex();
        builder.vertex(stack, x2, y1, 0.0F).color(fillColorArray[0], fillColorArray[1], fillColorArray[2], fillColorArray[3]).endVertex();
        builder.vertex(stack, x1, y1, 0.0F).color(fillColorArray[0], fillColorArray[1], fillColorArray[2], fillColorArray[3]).endVertex();
        builder.end();

        WorldVertexBufferUploader.end(builder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
