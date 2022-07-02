package io.firetamer.dragonblockbeyond.race.provider;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@FunctionalInterface
public interface StepSoundEventProvider {
    StepSoundEventProvider DEFAULT = (entity, random, pos, state) -> null;

    @Nullable
    SoundEvent getStepSoundEvent(LivingEntity entity, Random random, BlockPos pos, BlockState state);
}
