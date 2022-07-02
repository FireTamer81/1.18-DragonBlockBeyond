package io.firetamer.dragonblockbeyond.race.client.model;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class SimpleGeoPlayerModel<T extends IAnimatable> extends SimpleGeoEntityModel<T> implements IAnimatablePlayerModel<T> {
    protected final String bodyBoneName;
    protected final String rightArmBoneName;
    protected final String leftArmBoneName;
    protected final float armSwingScale;

    public SimpleGeoPlayerModel(String namespace, String name) {
        this(namespace, name, new Properties<>());
    }

    public SimpleGeoPlayerModel(String namespace, String textureType, String name) {
        this(namespace, textureType, name, new Properties<>());
    }

    public SimpleGeoPlayerModel(String namespace, String name, Properties<?> properties) {
        this(namespace, "entity", name, properties);
    }

    public SimpleGeoPlayerModel(String namespace, String textureType, String name, Properties<?> properties) {
        super(namespace, textureType, name, properties);
        this.bodyBoneName = properties.bodyBoneName;
        this.rightArmBoneName = properties.rightArmBoneName;
        this.leftArmBoneName = properties.leftArmBoneName;
        this.armSwingScale = properties.armSwingScale;
    }

    @SuppressWarnings("deprecation")
    @Override
    public final void setLivingAnimations(T animatable, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        this.setLivingAnimations(animatable, null, uniqueID, customPredicate);
    }

    @Override
    public void setLivingAnimations(T animatable, @Nullable AbstractClientPlayer player, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(animatable, uniqueID, customPredicate);

        if (bodyBoneName != null && rightArmBoneName != null && leftArmBoneName != null && customPredicate != null) {
            IBone body = this.getAnimationProcessor().getBone(bodyBoneName);
            IBone rightArm = this.getAnimationProcessor().getBone(rightArmBoneName);
            IBone leftArm = this.getAnimationProcessor().getBone(leftArmBoneName);

            float limbSwing = customPredicate.getLimbSwing();
            float limbSwingAmount = customPredicate.getLimbSwingAmount();

            rightArm.setRotationX(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount * armSwingScale);
            leftArm.setRotationX(Mth.cos(limbSwing * 0.6662F) * limbSwingAmount * armSwingScale);

            animateArms(player, customPredicate.getPartialTick(), body, rightArm, leftArm);
        }
    }

    @Override
    public final ResourceLocation getModelLocation(T animatable) {
        return this.getModelLocation(animatable, null);
    }

    @Override
    public ResourceLocation getModelLocation(T animatable, @Nullable AbstractClientPlayer player) {
        return super.getModelLocation(animatable);
    }

    // @Override
    // public final ResourceLocation getAnimationFileLocation(T animatable) {
    //     return this.getAnimationFileLocation(animatable, null);
    // }
    //
    // @Override
    // public ResourceLocation getAnimationFileLocation(T animatable, @Nullable AbstractClientPlayer player) {
    //     return super.getAnimationFileLocation(animatable);
    // }

    @Override
    public final ResourceLocation getTextureLocation(T animatable) {
        return this.getTextureLocation(animatable, null);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable, @Nullable AbstractClientPlayer player) {
        return super.getTextureLocation(animatable);
    }

    public static class Properties<T extends Properties<T>> extends SimpleGeoEntityModel.Properties<T> {
        protected String bodyBoneName;
        protected String rightArmBoneName;
        protected String leftArmBoneName;
        protected float armSwingScale = 1.0F;

        public T bodyBone(String bodyBone) {
            this.bodyBoneName = bodyBone;
            return getSelf();
        }

        public T armBones(String rightArmBone, String leftArmBone) {
            this.rightArmBoneName = rightArmBone;
            this.leftArmBoneName = leftArmBone;
            return getSelf();
        }

        public T armSwingScale(float scale) {
            this.armSwingScale = scale;
            return getSelf();
        }
    }
}
