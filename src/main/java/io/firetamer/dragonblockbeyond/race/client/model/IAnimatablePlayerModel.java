package io.firetamer.dragonblockbeyond.race.client.model;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public interface IAnimatablePlayerModel<T extends IAnimatable> extends IAnimatableModel<T> {
    @Deprecated
    @Override
    default void setLivingAnimations(T animatable, Integer uniqueID, AnimationEvent customPredicate) {
        setLivingAnimations(animatable, null, uniqueID, customPredicate);
    }

    default void setLivingAnimations(T animatable, @Nullable AbstractClientPlayer player, Integer uniqueId, AnimationEvent customPredicate) {}

    ResourceLocation getModelLocation(T animatable, @Nullable AbstractClientPlayer player);

    // ResourceLocation getAnimationFileLocation(T animatable, @Nullable AbstractClientPlayer player);

    ResourceLocation getTextureLocation(T animatable, @Nullable AbstractClientPlayer player);
}
