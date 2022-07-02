package io.firetamer.dragonblockbeyond.race.client.renderer.layer;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.firetamer.dragonblockbeyond.race.client.GeoRenderingUtil;
import io.firetamer.dragonblockbeyond.race.client.animatable.IGeoArmorProvider;
import io.firetamer.dragonblockbeyond.race.client.renderer.GeoPlayerRenderer;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.Collection;
import java.util.List;

public class GeoPlayerArmorLayer<T extends IAnimatable> extends GeoPlayerLayerRenderer<T> {
    protected static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    protected final Multimap<String, Pair<GeoBone, ResourceLocation>> armorBoneMap = MultimapBuilder.hashKeys().arrayListValues().build();
    protected final IGeoArmorProvider<T> armorProvider;
    protected boolean isRenderingArmorBone = false;
    protected float currentPartialTick = 1.0F;

    public GeoPlayerArmorLayer(GeoPlayerRenderer<T> entityRenderer) {
        this(entityRenderer, IGeoArmorProvider.getDefaultInstance());
    }

    public GeoPlayerArmorLayer(GeoPlayerRenderer<T> entityRenderer, IGeoArmorProvider<T> armorProvider) {
        super(entityRenderer);
        this.armorProvider = armorProvider;
    }

    @Override
    public boolean renderOnFirstPersonArm() {
        return true;
    }

    @Override
    public void renderPre(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        super.renderPre(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);

        Minecraft mc = Minecraft.getInstance();
        // We only know about the camera type for the local player, so default to third person back for other players
        CameraType cameraType = mc.player == player ? mc.options.getCameraType() : CameraType.THIRD_PERSON_BACK;
        armorBoneMap.clear();
        currentPartialTick = partialTick;

        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack itemStack = player.getItemBySlot(slot);
            GeoModel armorModel = this.armorProvider.getArmorModel(slot, itemStack, cameraType, player, animatable);

            if (armorModel != null) {
                ResourceLocation textureLocation = this.armorProvider.getTextureLocation(slot, itemStack, cameraType, player, animatable);

                if (textureLocation != null) {
                    List<String> rootArmorBones = this.armorProvider.getRootArmorBones(slot, itemStack, cameraType, player, animatable);

                    if (rootArmorBones.isEmpty()) {
                        populateArmorBoneMap(armorModel.topLevelBones, textureLocation);
                    } else {
                        for (String boneName : rootArmorBones) {
                            armorModel.getBone(boneName).ifPresent(rootBone -> populateArmorBoneMap(rootBone.childBones, textureLocation));
                        }
                    }
                }
            }
        }
    }

    protected void populateArmorBoneMap(List<GeoBone> bones, ResourceLocation textureLocation) {
        bones.forEach(bone -> armorBoneMap.put(bone.getName(), Pair.of(bone, textureLocation)));
    }

    @Nullable
    @Override
    public VertexConsumer renderRecursivelyPre(GeoBone bone, PoseStack poseStack, AbstractClientPlayer player, T animatable, VertexConsumer vertexConsumer, int packedLight, int overlay,
            float red, float green, float blue, float alpha) {
        if (isRenderingArmorBone)
            return null;

        Collection<Pair<GeoBone, ResourceLocation>> armorData = this.armorBoneMap.get(bone.getName());
        if (armorData.isEmpty())
            return null;

        this.isRenderingArmorBone = true;
        for (Pair<GeoBone, ResourceLocation> armorPair : armorData) {
            GeoBone armorBone = armorPair.getLeft();
            ResourceLocation armorTextureLocation = armorPair.getRight();
            MultiBufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

            // Create a new vertex consumer for the armor texture
            vertexConsumer = getVertexConsumer(bone, poseStack, player, animatable, packedLight, this.currentPartialTick, bufferSource, armorBone, armorTextureLocation);
            renderArmorBone(bone, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha, armorBone);

            // Create a new vertex consumer mimicking the original if it exists to end the one we just used
            RenderType currentRenderType = this.getRenderer().getCurrentRenderType();
            if (currentRenderType != null)
                vertexConsumer = bufferSource.getBuffer(currentRenderType);
        }
        this.isRenderingArmorBone = false;

        return vertexConsumer;
    }

    @NotNull
    protected VertexConsumer getVertexConsumer(GeoBone bone, PoseStack poseStack, AbstractClientPlayer player, T animatable, int packedLight, float partialTick, MultiBufferSource bufferSource,
            GeoBone armorBone, ResourceLocation armorTextureLocation) {
        return bufferSource.getBuffer(this.getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, armorTextureLocation));
    }

    protected void renderArmorBone(GeoBone baseBone, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, float red, float green, float blue, float alpha,
            GeoBone armorBone) {
        poseStack.pushPose();
        RenderUtils.translate(baseBone, poseStack);
        RenderUtils.moveToPivot(baseBone, poseStack);
        RenderUtils.rotate(baseBone, poseStack);
        RenderUtils.scale(baseBone, poseStack);
        RenderUtils.moveBackFromPivot(baseBone, poseStack);

        renderActually(baseBone, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha, armorBone);

        poseStack.popPose();
    }

    protected void renderActually(GeoBone baseBone, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, float red, float green, float blue, float alpha,
            GeoBone armorBone) {
        GeoRenderingUtil.renderRecursively(armorBone, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderPost(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, T animatable, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        super.renderPost(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);

        this.currentPartialTick = 1.0F;
    }
}
