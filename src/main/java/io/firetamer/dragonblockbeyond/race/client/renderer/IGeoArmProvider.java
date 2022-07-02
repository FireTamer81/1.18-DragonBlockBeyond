package io.firetamer.dragonblockbeyond.race.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.firetamer.dragonblockbeyond.race.client.ArmRenderingUtil;
import io.firetamer.dragonblockbeyond.race.client.renderer.layer.GeoPlayerLayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public interface IGeoArmProvider<T extends IAnimatable> {
    @Nullable
    GeoBone getArmBone(AbstractClientPlayer player, HumanoidArm side, T animatable, GeoPlayerRenderer<T> renderer);

    default void renderGeoArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float equippedProgress, float swingProgress,
            float partialTick, HumanoidArm side, T animatable, GeoPlayerRenderer<T> renderer) {
        GeoBone armBone = this.getArmBone(player, side, animatable, renderer);
        if (armBone == null)
            return;

        poseStack.pushPose();

        RenderType renderType = renderer.getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, renderer.getTextureLocation(player));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        preRenderArmCallback(player, poseStack, packedLight, equippedProgress, swingProgress, partialTick, side, animatable, renderer, armBone);
        renderArmActually(player, poseStack, vertexConsumer, packedLight, animatable, renderer, armBone);
        postRenderArmCallback(player, poseStack, packedLight, equippedProgress, swingProgress, partialTick, side, animatable, renderer, armBone);

        poseStack.popPose();
    }

    default void preRenderArmCallback(AbstractClientPlayer player, PoseStack poseStack, int packedLight, float equippedProgress, float swingProgress, float partialTick, HumanoidArm side, T animatable,
                                      GeoPlayerRenderer<T> renderer, GeoBone armBone) {
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        if (!player.isSpectator()) {
            for (GeoPlayerLayerRenderer<T> layerRenderer : renderer.getLayerRenderers()) {
                layerRenderer.renderArmPre(poseStack, bufferSource, packedLight, player, animatable, 0.0F, 0.0F, partialTick, renderer.getAgeInTicks(player, partialTick), 0.0F, 0.0F);
            }
        }

        ArmRenderingUtil.transformArm(poseStack, equippedProgress, swingProgress, side, partialTick);
    }

    default void renderArmActually(AbstractClientPlayer player, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, T animatable, GeoPlayerRenderer<T> renderer,
            GeoBone armBone) {
        renderer.renderRecursively(armBone, poseStack, vertexConsumer, packedLight, GeoEntityRenderer.getPackedOverlay(player, 0), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    default void postRenderArmCallback(AbstractClientPlayer player, PoseStack poseStack, int packedLight, float equippedProgress, float swingProgress, float partialTick, HumanoidArm side, T animatable,
                                       GeoPlayerRenderer<T> renderer, GeoBone armBone) {
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        if (!player.isSpectator()) {
            for (GeoPlayerLayerRenderer<T> layerRenderer : renderer.getLayerRenderers()) {
                layerRenderer.renderArmPost(poseStack, bufferSource, packedLight, player, animatable, 0.0F, 0.0F, partialTick, renderer.getAgeInTicks(player, partialTick), 0.0F, 0.0F);
            }
        }
    }
}
