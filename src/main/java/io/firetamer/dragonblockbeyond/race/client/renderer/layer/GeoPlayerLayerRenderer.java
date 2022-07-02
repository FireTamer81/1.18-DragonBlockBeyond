package io.firetamer.dragonblockbeyond.race.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.firetamer.dragonblockbeyond.race.client.model.IAnimatablePlayerModel;
import io.firetamer.dragonblockbeyond.race.client.renderer.GeoPlayerRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public abstract class GeoPlayerLayerRenderer<T extends IAnimatable> {
    private final GeoPlayerRenderer<T> entityRenderer;

    protected GeoPlayerLayerRenderer(GeoPlayerRenderer<T> entityRenderer) {
        this.entityRenderer = entityRenderer;
    }

    protected void renderModelIfVisible(GeoModelProvider<T> modelProvider, ResourceLocation textureLocation,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable,
            float partialTick, float red, float green, float blue, float alpha) {
        if (!player.isInvisible()) {
            this.renderModel(modelProvider, textureLocation, poseStack, bufferSource, packedLight, player, animatable, partialTick, red, green, blue, alpha);
        }
    }

    protected void renderModelIfVisible(GeoModel model, ResourceLocation textureLocation,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable,
            float partialTick, float red, float green, float blue, float alpha) {
        if (!player.isInvisible()) {
            this.renderModel(model, textureLocation, poseStack, bufferSource, packedLight, player, animatable, partialTick, red, green, blue, alpha);
        }
    }

    protected void renderModel(GeoModelProvider<T> modelProvider, ResourceLocation textureLocation,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable,
            float partialTick, float red, float green, float blue, float alpha) {
        GeoModel model = modelProvider.getModel(getModelLocation(modelProvider, player, animatable));
        renderModel(model, textureLocation, poseStack, bufferSource, packedLight, player, animatable, partialTick, red, green, blue, alpha);
    }

    protected void renderModel(GeoModel model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable,
            float partialTick, float red, float green, float blue, float alpha) {
        RenderType renderType = this.getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, textureLocation);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        this.getRenderer().render(model, animatable, partialTick, renderType, poseStack, bufferSource, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(player, 0.0F),
                red, green, blue, alpha);
    }

    @SuppressWarnings("unchecked")
    protected ResourceLocation getModelLocation(GeoModelProvider<T> modelProvider, AbstractClientPlayer player, T animatable) {
        if (modelProvider instanceof IAnimatablePlayerModel<?>) {
            return ((IAnimatablePlayerModel<T>) modelProvider).getModelLocation(animatable, player);
        } else {
            return modelProvider.getModelLocation(animatable);
        }
    }

    @SuppressWarnings("unchecked")
    protected ResourceLocation getTextureLocation(GeoModelProvider<T> modelProvider, AbstractClientPlayer player, T animatable) {
        if (modelProvider instanceof IAnimatablePlayerModel<?>) {
            return ((IAnimatablePlayerModel<T>) modelProvider).getTextureLocation(animatable, player);
        } else {
            return modelProvider.getTextureLocation(animatable);
        }
    }

    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource,
            @Nullable VertexConsumer vertexConsumer, int packedLight, ResourceLocation textureLocation) {
        return RenderType.entityCutout(textureLocation);
    }

    public GeoModelProvider<T> getEntityModel() {
        return this.entityRenderer.getGeoModelProvider();
    }

    public GeoPlayerRenderer<T> getRenderer() {
        return this.entityRenderer;
    }

    protected ResourceLocation getTextureLocation(AbstractClientPlayer player, T animatable) {
        return this.entityRenderer.getTextureLocation(player);
    }

    public boolean renderOnFirstPersonArm() {
        return false;
    }

    public void renderArmPre(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.renderOnFirstPersonArm()) {
            this.renderPre(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
        }
    }

    public void renderArmPost(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.renderOnFirstPersonArm()) {
            this.renderPost(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
        }
    }

    public void renderPre(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {}

    public void renderPost(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Nullable
    public VertexConsumer renderRecursivelyPre(GeoBone bone, PoseStack poseStack, AbstractClientPlayer player, T animatable, VertexConsumer vertexConsumer, int packedLight, int overlay, float red,
            float green, float blue, float alpha) {
        return null;
    }
}
