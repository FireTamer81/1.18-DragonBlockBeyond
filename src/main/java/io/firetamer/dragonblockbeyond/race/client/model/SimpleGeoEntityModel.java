package io.firetamer.dragonblockbeyond.race.client.model;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.List;

public class SimpleGeoEntityModel<T extends IAnimatable> extends SimpleAnimatedGeoModel<T> {
    protected final String headBoneName;
    protected final List<String> legABoneNames;
    protected final List<String> legBBoneNames;
    protected final float legSwingScale;

    public SimpleGeoEntityModel(String namespace, String name) {
        this(namespace, name, new Properties<>());
    }

    public SimpleGeoEntityModel(String namespace, String textureType, String name) {
        this(namespace, textureType, name, new Properties<>());
    }

    public SimpleGeoEntityModel(String namespace, String name, Properties<?> properties) {
        this(namespace, "entity", name, properties);
    }

    public SimpleGeoEntityModel(String namespace, String textureType, String name, Properties<?> properties) {
        super(namespace, textureType, name);
        this.headBoneName = properties.headBoneName;
        this.legABoneNames = properties.legABoneNames;
        this.legBBoneNames = properties.legBBoneNames;
        this.legSwingScale = properties.legSwingScale;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (headBoneName != null && customPredicate != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            float netHeadYaw = extraData.netHeadYaw;
            float headPitch = extraData.headPitch;

            IBone head = this.getAnimationProcessor().getBone(headBoneName);
            head.setRotationX(headPitch * ((float) Math.PI / 180F));
            head.setRotationY(netHeadYaw * ((float) Math.PI / 180F));
        }

        if (legABoneNames != null && customPredicate != null) {
            float limbSwing = customPredicate.getLimbSwing();
            float limbSwingAmount = customPredicate.getLimbSwingAmount();

            legABoneNames.forEach(legBoneName -> animateLeg(this.getAnimationProcessor().getBone(legBoneName), limbSwing, limbSwingAmount, 0.0F));
        }

        if (legBBoneNames != null && customPredicate != null) {
            float limbSwing = customPredicate.getLimbSwing();
            float limbSwingAmount = customPredicate.getLimbSwingAmount();

            legBBoneNames.forEach(legBoneName -> animateLeg(this.getAnimationProcessor().getBone(legBoneName), limbSwing, limbSwingAmount, (float) Math.PI));
        }
    }

    protected void animateLeg(IBone leg, float limbSwing, float limbSwingAmount, float limbSwingOffset) {
        leg.setRotationX(Mth.cos(limbSwing * 0.6662F + limbSwingOffset) * 1.4F * limbSwingAmount * legSwingScale);
    }

    protected void animateArms(@Nullable LivingEntity player, float partialTicks, IBone body, IBone rightArm, IBone leftArm) {
        if (player == null || body == null || rightArm == null || leftArm == null)
            return;
        float originalAttackAnim = player.getAttackAnim(partialTicks);
        if (originalAttackAnim <= 0.0F)
            return;

        HumanoidArm attackHand = player.swingingArm == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
        IBone attackArm = attackHand == HumanoidArm.LEFT ? leftArm : rightArm;
        float f = originalAttackAnim;
        body.setRotationY(Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F);
        if (attackHand == HumanoidArm.LEFT) {
            body.setRotationY(body.getRotationY() * -1.0F);
        }

        // rightArm.setPivotZ(MathHelper.sin(body.getRotationY()) * 5.0F);
        // rightArm.setPivotX(-MathHelper.cos(body.getRotationY()) * 5.0F);
        // leftArm.setPivotZ(-MathHelper.sin(body.getRotationY()) * 5.0F);
        // leftArm.setPivotX(MathHelper.cos(body.getRotationY()) * 5.0F);
        rightArm.setRotationY(rightArm.getRotationY() + body.getRotationY());
        leftArm.setRotationY(leftArm.getRotationY() + body.getRotationY());
        leftArm.setRotationX(leftArm.getRotationX() + body.getRotationY());
        f = 1.0F - originalAttackAnim;
        f *= f;
        f *= f;
        f = 1.0F - f;
        IBone head = this.getAnimationProcessor().getBone(headBoneName);
        if (head != null) {
            float g = Mth.sin(f * (float) Math.PI);
            float h = Mth.sin(originalAttackAnim * (float) Math.PI) * -(head.getRotationX() - 0.7F) * 0.75F;
            attackArm.setRotationX(attackArm.getRotationX() + (g * 1.2F + h));
        }
        attackArm.setRotationY(attackArm.getRotationY() + (body.getRotationY() * 2.0F));
        attackArm.setRotationZ(attackArm.getRotationZ() + (Mth.sin(originalAttackAnim * 3.1415927F) * -0.4F));
    }

    public static class Properties<T extends Properties<T>> {
        protected String headBoneName;
        protected List<String> legABoneNames;
        protected List<String> legBBoneNames;
        protected float legSwingScale = 1.0F;

        @SuppressWarnings("unchecked")
        protected T getSelf() {
            return (T) this;
        }

        public T headBone(String headBone) {
            this.headBoneName = headBone;
            return getSelf();
        }

        public T legABones(String... legABones) {
            this.legABoneNames = List.of(legABones);
            return getSelf();
        }

        public T legBBones(String... legBBones) {
            this.legBBoneNames = List.of(legBBones);
            return getSelf();
        }

        public T legSwingScale(float scale) {
            this.legSwingScale = scale;
            return getSelf();
        }
    }
}
