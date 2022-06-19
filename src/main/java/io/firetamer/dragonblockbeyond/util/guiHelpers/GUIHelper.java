package io.firetamer.dragonblockbeyond.util.guiHelpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class GUIHelper {






    public static void blit(PoseStack poseStack, int p_93230_, int p_93231_, int p_93232_, int p_93233_, int p_93234_, int p_93235_, int zOffset) {
        blit(poseStack, p_93230_, p_93231_, zOffset, (float)p_93232_, (float)p_93233_, p_93234_, p_93235_, 256, 256);
    }

    public static void blit(PoseStack poseStack, int p_93145_, int p_93146_, int p_93147_, float p_93148_, float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_) {
        innerBlit(poseStack, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93152_, p_93153_);
    }

    private static void innerBlit(PoseStack poseStack, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
        innerBlit(poseStack.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_);
    }

    private static void innerBlit(Matrix4f matrix4f, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)p_93114_, (float)p_93117_, (float)p_93118_).uv(p_93119_, p_93122_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93115_, (float)p_93117_, (float)p_93118_).uv(p_93120_, p_93122_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93115_, (float)p_93116_, (float)p_93118_).uv(p_93120_, p_93121_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93114_, (float)p_93116_, (float)p_93118_).uv(p_93119_, p_93121_).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
}
