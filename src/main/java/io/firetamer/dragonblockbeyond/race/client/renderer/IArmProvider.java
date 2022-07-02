package io.firetamer.dragonblockbeyond.race.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.firetamer.dragonblockbeyond.race.client.ArmRenderingUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;

public interface IArmProvider<M extends EntityModel<? super AbstractClientPlayer>> {
    @Nullable
    ModelPart getArmModelPart(AbstractClientPlayer player, HumanoidArm side, LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model);

    default RenderType getArmRenderType(AbstractClientPlayer player, LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model) {
        return RenderType.entityCutout(renderer.getTextureLocation(player));
    }

    default void renderArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float equippedProgress, float swingProgress, float partialTick,
            HumanoidArm side, LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model) {
        ModelPart armModelPart = this.getArmModelPart(player, side, renderer, model);
        if (armModelPart == null)
            return;

        poseStack.pushPose();

        RenderType renderType = getArmRenderType(player, renderer, model);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        if (preRenderArmCallback(player, poseStack, equippedProgress, swingProgress, partialTick, side, renderer, model, armModelPart))
            renderArmActually(player, poseStack, vertexConsumer, packedLight, renderer, model, armModelPart);
        postRenderArmCallback(player, poseStack, renderer, model, armModelPart);

        poseStack.popPose();
    }

    /**
     * Called before the arm model part is rendered.
     *
     * @return {@code true} if the arm model part should be rendered, {@code false} otherwise
     */
    default boolean preRenderArmCallback(AbstractClientPlayer player, PoseStack poseStack, float equippedProgress, float swingProgress, float partialTick, HumanoidArm side,
            LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model, ModelPart armModelPart) {
        ArmRenderingUtil.transformArm(poseStack, equippedProgress, swingProgress, side, partialTick);
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        return true;
    }

    /**
     * Called after the arm model part is rendered,
     * regardless of if {@link #preRenderArmCallback(AbstractClientPlayer, PoseStack, float, float, float, HumanoidArm, LivingEntityRenderer, EntityModel, ModelPart)} returned {@code true or not}.
     */
    default void renderArmActually(AbstractClientPlayer player, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model, ModelPart armModelPart) {
        armModelPart.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    default void postRenderArmCallback(AbstractClientPlayer player, PoseStack poseStack, LivingEntityRenderer<? super AbstractClientPlayer, M> renderer, M model, ModelPart armModelPart) {}
}
