package io.firetamer.dragonblockbeyond.race.provider;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface AttackSoundEventProvider {
    AttackSoundEventProvider DEFAULT = (player, random, target) -> null;

    @Nullable
    SoundEvent getAttackSoundEvent(Player player, Random random, Entity target);
}
