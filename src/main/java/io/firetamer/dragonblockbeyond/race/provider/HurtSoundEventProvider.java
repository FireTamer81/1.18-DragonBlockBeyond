package io.firetamer.dragonblockbeyond.race.provider;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface HurtSoundEventProvider {
    HurtSoundEventProvider DEFAULT = (entity, random, damageSource) -> null;

    @Nullable
    SoundEvent getHurtSoundEvent(LivingEntity entity, Random random, DamageSource damageSource);
}
