package io.firetamer.dragonblockbeyond.race.provider;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@FunctionalInterface
public interface SoundEventProvider {
    SoundEventProvider DEFAULT = (entity, random) -> null;

    @Nullable
    SoundEvent getSoundEvent(LivingEntity entity, Random random);
}
