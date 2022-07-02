package io.firetamer.dragonblockbeyond.mixin;

import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public Level level;

    @Shadow
    @Final
    protected Random random;

    @Shadow
    protected abstract void playStepSound(BlockPos pos, BlockState state);

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    protected abstract float getEyeHeight(Pose pose, EntityDimensions size);

    @Redirect(method = "move",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;playStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void onMoveInvokePlayStepSound(Entity instance, BlockPos pos, BlockState state) {
        if (!((Object) this instanceof Player entity)) {
            this.playStepSound(pos, state);
            return;
        }

        SoundEvent stepSoundEvent = RaceHolderAttacher.getRaceSoundData(entity)
                .map(raceSoundData -> raceSoundData.stepSound().getStepSoundEvent(entity, this.random, pos, state))
                .orElse(null);

        if (stepSoundEvent == null) {
            this.playStepSound(pos, state);
            return;
        }

        // Default impl of playStepSound copied here so it works on overrides
        if (!state.getMaterial().isLiquid()) {
            BlockState blockstate = this.level.getBlockState(pos.above());
            SoundType soundtype = blockstate.is(Blocks.SNOW)
                    ? blockstate.getSoundType(level, pos, (Entity) (Object) this)
                    : state.getSoundType(level, pos, (Entity) (Object) this);
            this.playSound(stepSoundEvent, soundtype.getVolume() * 0.15F, soundtype.getPitch());
        }
    }

    @Redirect(method = "playStepSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SoundType;getStepSound()Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent onPlayStepSoundInvokeGetStepSound(SoundType stepSound, BlockPos pos, BlockState state) {
        if (!((Object) this instanceof Player entity))
            return stepSound.getStepSound();

        return RaceHolderAttacher.getRaceSoundData(entity)
                .map(raceSoundData -> raceSoundData.stepSound().getStepSoundEvent(entity, this.random, pos, state))
                .orElseGet(stepSound::getStepSound);
    }

    @ModifyVariable(method = "getBoundingBoxForPose", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/EntityDimensions;width:F"), ordinal = 0)
    public EntityDimensions modifyVariableGetBoundingBoxForPoseEntityDimensions(EntityDimensions dimensions, Pose pose) {
        return ForgeEventFactory.getEntitySizeForge((Entity) (Object) this, pose, dimensions, getEyeHeight(pose, dimensions)).getNewSize();
    }
}
