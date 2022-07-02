package io.firetamer.dragonblockbeyond.mixin;

import io.firetamer.dragonblockbeyond.race.api.IAmbientSoundHolder;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IAmbientSoundHolder {
    @Unique
    private Entity hundredmediaraces$attackTarget;

    private PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttackHead(Entity target, CallbackInfo ci) {
        this.hundredmediaraces$attackTarget = target;
    }

    @ModifyArg(method = "attack",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"),
            index = 4)
    private SoundEvent redirectAttackInvokePlaySound(SoundEvent soundEvent) {
        return RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.attackSound().getAttackSoundEvent((Player) (Object) this, this.random, this.hundredmediaraces$attackTarget))
                .orElse(soundEvent);
    }
}
