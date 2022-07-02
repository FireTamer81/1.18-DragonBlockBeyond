package io.firetamer.dragonblockbeyond.mixin;

import io.firetamer.dragonblockbeyond.race.api.IAmbientSoundHolder;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IAmbientSoundHolder {
    @Unique
    private int hundredmediaraces$ambientSoundTime;

    private LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    public abstract float getVoicePitch();

    @Shadow
    @Nullable
    protected abstract SoundEvent getHurtSound(DamageSource damageSource);

    @Shadow
    @Nullable
    protected abstract SoundEvent getDeathSound();

    @Shadow public abstract SoundEvent getEatingSound(ItemStack p_21202_);

    @ModifyConstant(method = "getCurrentSwingDuration", constant = @Constant(intValue = 6))
    private int onGetCurrentSwingDurationConstant(int constant) {
        if(!(((Object)this) instanceof Player)) {
            return  constant;
        }
        int swingDuration = RaceHolderAttacher.getRaceSwingDuration((Player) (Object) this);

        return swingDuration == -1 ? constant : swingDuration;
    }

    @Redirect(method = {"eat", "triggerItemUseEffects"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getEatingSound(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent onEatInvokeGetEatingSound(LivingEntity instance, ItemStack itemStack) {
        if(!(((Object)this) instanceof Player)) {
            return getEatingSound(itemStack);
        }
        return RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.eatSound().getEatSoundEvent((LivingEntity) (Object) this, this.random, itemStack))
                .orElse(instance.getEatingSound(itemStack));
    }

    @Inject(method = "getEatingSound", at = @At("HEAD"), cancellable = true)
    private void onGetEatingSoundHead(ItemStack itemStack, CallbackInfoReturnable<SoundEvent> cir) {
        if(!(((Object)this) instanceof Player)) {
            return;
        }
        RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.eatSound().getEatSoundEvent((LivingEntity) (Object) this, this.random, itemStack))
                .ifPresent(cir::setReturnValue);
    }

    @Redirect(method = {"hurt", "handleEntityEvent"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getDeathSound()Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent onHurtInvokeGetDeathSound(LivingEntity instance) {
        if(!(((Object)this) instanceof Player)) {
            return getDeathSound();
        }
        return RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.deathSound().getSoundEvent((LivingEntity) (Object) this, this.random))
                .orElse(getDeathSound());
    }

    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void onGetDeathSoundHead(CallbackInfoReturnable<SoundEvent> cir) {
        if(!(((Object)this) instanceof Player)) {
            return;
        }
        RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.deathSound().getSoundEvent((LivingEntity) (Object) this, this.random))
                .ifPresent(cir::setReturnValue);
    }

    @Redirect(method = {"playHurtSound", "handleEntityEvent"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent onPlayHurtSoundInvokeGetHurtSound(LivingEntity instance, DamageSource damageSource) {
        if(!(((Object)this) instanceof Player)) {
            return getHurtSound(damageSource);
        }
        return RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.hurtSound().getHurtSoundEvent((LivingEntity) (Object) this, this.random, damageSource))
                .orElse(getHurtSound(damageSource));
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void onGetHurtSoundHead(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if(!(((Object)this) instanceof Player)) {
            return;
        }
        RaceHolderAttacher.getRaceSoundData((Player) (Object) this)
                .map(raceSoundData -> raceSoundData.hurtSound().getHurtSoundEvent((LivingEntity) (Object) this, this.random, damageSource))
                .ifPresent(cir::setReturnValue);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTickHead(CallbackInfo ci) {
        // If this entity is a mob, we can just mixin Mob#playAmbientSound instead
        if ((LivingEntity) (Object) this instanceof Mob)
            return;

        RaceHolderAttacher.getRaceSoundData((Player) (Object) this).ifPresent(raceSoundData -> {
            if (this.isAlive() && this.random.nextInt(1000) < this.hundredmediaraces$ambientSoundTime++) {
                SoundEvent soundEvent = raceSoundData.ambientSound().getSoundEvent((LivingEntity) (Object) this, this.random);

                if (soundEvent != null)
                    this.playSound(soundEvent, this.getSoundVolume(), this.getVoicePitch());

                this.hundredmediaraces$ambientSoundTime = -raceSoundData.ambientSoundInterval();
            }
        });
    }

    @Override
    public int getAmbientSoundTime() {
        return this.hundredmediaraces$ambientSoundTime;
    }

    @Override
    public void setAmbientSoundTime(int ambientSoundTime) {
        this.hundredmediaraces$ambientSoundTime = ambientSoundTime;
    }
}
