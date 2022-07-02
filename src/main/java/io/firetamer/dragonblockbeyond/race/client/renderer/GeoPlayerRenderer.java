package io.firetamer.dragonblockbeyond.race.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.firetamer.dragonblockbeyond.race.client.animatable.IHasGeoRenderer;
import io.firetamer.dragonblockbeyond.race.client.model.IAnimatablePlayerModel;
import io.firetamer.dragonblockbeyond.race.client.renderer.layer.GeoPlayerLayerRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.scores.Team;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.compat.PatchouliCompat;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.ArrayList;
import java.util.List;

public class GeoPlayerRenderer<T extends IAnimatable> extends EntityRenderer<AbstractClientPlayer> implements IGeoRenderer<T> {
    protected final AnimatedGeoModel<T> modelProvider;
    protected final T animatable;
    protected final List<GeoPlayerLayerRenderer<T>> layerRenderers = new ArrayList<>();
    protected AbstractClientPlayer currentRenderingPlayer;
    protected RenderType currentRenderType;
    protected MultiBufferSource currentBufferSource;
    protected ResourceLocation currentTextureLocation;

    public GeoPlayerRenderer(EntityRendererProvider.Context context, AnimatedGeoModel<T> modelProvider, T animatable) {
        super(context);
        this.modelProvider = modelProvider;
        this.animatable = animatable;
        if (this.animatable instanceof IHasGeoRenderer hasGeoRenderer) {
            hasGeoRenderer.setGeoRenderer(this);
        }
    }

    public T getAnimatable() {
        return animatable;
    }

    @Nullable
    public AbstractClientPlayer getCurrentRenderingPlayer() {
        return currentRenderingPlayer;
    }

    @Nullable
    public RenderType getCurrentRenderType() {
        return currentRenderType;
    }

    @Nullable
    public MultiBufferSource getCurrentBufferSource() {
        return currentBufferSource;
    }

    @Nullable
    public ResourceLocation getCurrentTextureLocation() {
        return currentTextureLocation;
    }

    @Override
    public final void render(AbstractClientPlayer player, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.render(player, this.animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @SuppressWarnings("unchecked")
    public void render(AbstractClientPlayer player, T animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.currentRenderingPlayer = player;
        this.currentBufferSource = bufferSource;

        poseStack.pushPose();

        boolean shouldSit = player.isPassenger() && player.getVehicle() != null && player.getVehicle().shouldRiderSit();
        EntityModelData entityModelData = new EntityModelData();
        entityModelData.isSitting = shouldSit;
        entityModelData.isChild = player.isBaby();

        float yBodyRot = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
        float yHeadRot = Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot);
        float netHeadYaw = yHeadRot - yBodyRot;
        float headPitch = Mth.lerp(partialTick, player.xRotO, player.getXRot());

        if (shouldSit && player.getVehicle() instanceof LivingEntity livingentity) {
            yBodyRot = Mth.rotLerp(partialTick, livingentity.yBodyRotO,
                    livingentity.yBodyRot);
            netHeadYaw = yHeadRot - yBodyRot;
            float wrappedNetHeadYaw = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85F, 85F);

            yBodyRot = yHeadRot - wrappedNetHeadYaw;
            if (wrappedNetHeadYaw * wrappedNetHeadYaw > 2500F) {
                yBodyRot += wrappedNetHeadYaw * 0.2F;
            }

            netHeadYaw = yHeadRot - yBodyRot;
        }

        if (player.getPose() == Pose.SLEEPING) {
            Direction direction = player.getBedOrientation();
            if (direction != null) {
                float eyeHeight = player.getEyeHeight(Pose.STANDING) - 0.1F;
                poseStack.translate(-direction.getStepX() * eyeHeight, 0.0D, -direction.getStepZ() * eyeHeight);
            }
        }

        float ageInTicks = this.getAgeInTicks(player, partialTick);
        this.applyRotations(player, poseStack, ageInTicks, yBodyRot, partialTick);
        this.preRenderCallback(player, poseStack, partialTick);

        float limbSwingAmount = 0F;
        float limbSwing = 0F;
        if (!shouldSit && player.isAlive()) {
            limbSwingAmount = Math.min(1F, Mth.lerp(partialTick, player.animationSpeedOld, player.animationSpeed));
            limbSwing = (player.isBaby() ? 3 : 1) * (player.animationPosition - player.animationSpeed * (1F - partialTick));
        }
        entityModelData.headPitch = -headPitch;
        entityModelData.netHeadYaw = -netHeadYaw;

        IAnimatablePlayerModel<T> animatedPlayerModel = modelProvider instanceof IAnimatablePlayerModel ? (IAnimatablePlayerModel) modelProvider : null;
        GeoModel model = modelProvider.getModel(animatedPlayerModel != null ? animatedPlayerModel.getModelLocation(animatable, player) : modelProvider.getModelLocation(animatable));
        AnimationEvent<T> predicate = new AnimationEvent<>(animatable, limbSwing, limbSwingAmount, partialTick,
                !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F), List.of(entityModelData, player));
        if (animatedPlayerModel != null) {
            animatedPlayerModel.setLivingAnimations(animatable, player, this.getUniqueID(animatable), predicate);
        } else {
            modelProvider.setLivingAnimations(animatable, this.getUniqueID(animatable), predicate);
        }

        poseStack.translate(0, 0.01F, 0);
        ResourceLocation textureLocation = getTextureLocation(player);
        this.currentTextureLocation = textureLocation;
        RenderSystem.setShaderTexture(0, textureLocation);
        int renderColor = getRenderColor(animatable, partialTick, poseStack, bufferSource, null, packedLight).getColor();
        RenderType renderType = getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, textureLocation);
        this.currentRenderType = renderType;
        boolean visible = !player.isInvisibleTo(Minecraft.getInstance().player);

        if (!player.isSpectator()) {
            for (GeoPlayerLayerRenderer<T> layerRenderer : this.layerRenderers) {
                layerRenderer.renderPre(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }

        render(model, animatable, partialTick, renderType, poseStack, bufferSource, null, packedLight,
                GeoEntityRenderer.getPackedOverlay(player, this.getOverlayProgress(player, partialTick)),
                FastColor.ARGB32.red(renderColor) / 255F, FastColor.ARGB32.green(renderColor) / 255F,
                FastColor.ARGB32.blue(renderColor) / 255F, visible ? FastColor.ARGB32.alpha(renderColor) / 255F : 0F);

        if (!player.isSpectator()) {
            for (GeoPlayerLayerRenderer<T> layerRenderer : this.layerRenderers) {
                layerRenderer.renderPost(poseStack, bufferSource, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }

        if (ModList.get().isLoaded("patchouli"))
            PatchouliCompat.patchouliLoaded(poseStack);

        poseStack.popPose();

        // Renders nameplate
        super.render(player, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        this.currentRenderingPlayer = null;
        this.currentBufferSource = null;
        this.currentRenderType = null;
        this.currentTextureLocation = null;
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, float red, float green, float blue, float alpha) {
        for (GeoPlayerLayerRenderer<T> layerRenderer : this.layerRenderers) {
            VertexConsumer nextBuilder = layerRenderer.renderRecursivelyPre(bone, poseStack, this.currentRenderingPlayer, this.animatable, vertexConsumer, packedLight, overlay,
                    red, green, blue, alpha);
            if (nextBuilder != null)
                vertexConsumer = nextBuilder;
        }

        IGeoRenderer.super.renderRecursively(bone, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha);
    }

    protected float getOverlayProgress(AbstractClientPlayer player, float partialTick) {
        return 0F;
    }

    protected void preRenderCallback(AbstractClientPlayer player, PoseStack poseStack, float partialTick) {}

    @Override
    public Color getRenderColor(T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer vertexConsumer, int packedLight) {
        return IGeoRenderer.super.getRenderColor(animatable, partialTick, poseStack, bufferSource, vertexConsumer, packedLight);
    }

    @Override
    public AnimatedGeoModel<T> getGeoModelProvider() {
        return this.modelProvider;
    }

    protected void applyRotations(AbstractClientPlayer player, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        Pose pose = player.getPose();
        if (pose != Pose.SLEEPING) {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180F - rotationYaw));
        }

        if (player.deathTime > 0) {
            float deathAnim = Math.min(1F, (float) Math.sqrt((player.deathTime + partialTick - 1F) / 20F * 1.6F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(deathAnim * this.getMaxDeathRotation(player)));
        } else if (player.isAutoSpinAttack()) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90F - player.getXRot()));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(ageInTicks * -75F));
        } else if (pose == Pose.SLEEPING) {
            Direction direction = player.getBedOrientation();
            float facingAngle = direction != null ? getFacingAngle(direction) : rotationYaw;
            poseStack.mulPose(Vector3f.YP.rotationDegrees(facingAngle));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(this.getMaxDeathRotation(player)));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(270F));
        } else {
            String name = ChatFormatting.stripFormatting(player.getName().getString());
            if (("Dinnerbone".equals(name) || "Grumm".equals(name)) && player.isModelPartShown(PlayerModelPart.CAPE)) {
                poseStack.translate(0.0D, (player.getBbHeight() + 0.1F), 0.0D);
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
            }
        }
    }

    protected static float getFacingAngle(Direction direction) {
        switch (direction) {
            case SOUTH:
                return 90F;
            case NORTH:
                return 270F;
            case EAST:
                return 180F;
            case WEST:
            default:
                return 0F;
        }
    }

    protected float getMaxDeathRotation(AbstractClientPlayer player) {
        return 90F;
    }

    @Override
    public boolean shouldShowName(AbstractClientPlayer player) {
        double distSqr = this.entityRenderDispatcher.distanceToSqr(player);
        double maxDist = player.isDiscrete() ? 32.0 : 64.0;
        if (distSqr >= maxDist * maxDist)
            return false;

        Minecraft minecraft = Minecraft.getInstance();
        boolean visible = !player.isInvisibleTo(minecraft.player);

        if (player != minecraft.player) {
            Team otherTeam = player.getTeam();
            Team ourTeam = minecraft.player.getTeam();
            if (otherTeam != null) {
                switch (otherTeam.getNameTagVisibility()) {
                    case ALWAYS:
                        return visible;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return ourTeam == null ? visible : otherTeam.isAlliedTo(ourTeam) && (otherTeam.canSeeFriendlyInvisibles() || visible);
                    case HIDE_FOR_OWN_TEAM:
                        return ourTeam == null ? visible : !otherTeam.isAlliedTo(ourTeam) && visible;
                    default:
                        return true;
                }
            }
        }

        return Minecraft.renderNames() && player != minecraft.getCameraEntity() && visible && !player.isVehicle();
    }

    protected float getAgeInTicks(LivingEntity entity, float partialTick) {
        return entity.tickCount + partialTick;
    }

    @Deprecated
    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return getTextureLocation(this.currentRenderingPlayer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResourceLocation getTextureLocation(@Nullable AbstractClientPlayer player) {
        return this.modelProvider instanceof IAnimatablePlayerModel
                ? ((IAnimatablePlayerModel<T>) this.modelProvider).getTextureLocation(this.animatable, player)
                : this.modelProvider.getTextureLocation(this.animatable);
    }

    public final boolean addLayer(GeoPlayerLayerRenderer<T> layer) {
        return this.layerRenderers.add(layer);
    }

    public List<GeoPlayerLayerRenderer<T>> getLayerRenderers() {
        return layerRenderers;
    }

    @Override
    public Integer getUniqueID(T animatable) {
        return this.currentRenderingPlayer == null ? animatable.hashCode() : this.currentRenderingPlayer.getId();
    }
}
