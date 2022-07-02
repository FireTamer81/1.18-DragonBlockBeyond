package io.firetamer.dragonblockbeyond.mixin;

import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow
    private Entity entity;

    @ModifyArg(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(D)D"), index = 0)
    private double setupModifyArgGetMaxZoom(double startingDistance) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (this.entity != player || player == null)
            return startingDistance;

        Race currentRace = RaceHolderAttacher.getCurrentRaceUnwrap(player);
        if (currentRace == null)
            return startingDistance;

        return currentRace.getCameraDistance(player);
    }
}
