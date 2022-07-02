package io.firetamer.dragonblockbeyond.race.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.firetamer.dragonblockbeyond.race.client.event.RaceRenderEvents;
import io.firetamer.dragonblockbeyond.race.client.renderer.IArmProvider;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class ArmRenderingUtil {
    @SuppressWarnings("unchecked")
    public static <M extends EntityModel<? super AbstractClientPlayer>> void renderArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            float equippedProgress, float swingProgress, HumanoidArm side, LivingEntityRenderer<? super AbstractClientPlayer, M> renderer) {
        M model = renderer.getModel();
        if (model instanceof IArmProvider) {
            ((IArmProvider<M>) model).renderArm(player, poseStack, bufferSource, packedLight, equippedProgress, swingProgress, RaceRenderEvents.getPlayerRenderPartialTick(), side, renderer, model);
        }
    }

    public static void transformArm(PoseStack poseStack, float equippedProgress, float swingProgress, HumanoidArm side, float partialTick) {
        float f = side == HumanoidArm.LEFT ? -1.0F : 1.0F;
        float f1 = Mth.sqrt(swingProgress);
        float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
        float f4 = -0.4F * Mth.sin(swingProgress * (float) Math.PI);

        poseStack.translate(f * (f2 + 0.64000005F), f3 - 0.6F + equippedProgress * -0.6F, f4 - 0.71999997F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));

        float f5 = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
        float f6 = Mth.sin(f1 * (float) Math.PI);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));

        poseStack.translate(f * -1, 3.6F, 3.5D);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
        poseStack.translate(f * 5.6F, 0, 0);
    }
}
