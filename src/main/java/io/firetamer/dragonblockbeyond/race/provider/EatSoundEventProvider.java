package io.firetamer.dragonblockbeyond.race.provider;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface EatSoundEventProvider {
    EatSoundEventProvider DEFAULT = (entity, random, itemStack) -> null;

    @Nullable
    SoundEvent getEatSoundEvent(LivingEntity entity, Random random, ItemStack itemStack);
}
