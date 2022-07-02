package io.firetamer.dragonblockbeyond.race.event;

import com.mojang.brigadier.CommandDispatcher;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import io.firetamer.dragonblockbeyond.race.commands.RaceCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class NMCommonForgeEvents {
    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(NMCommonForgeEvents::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addListener(NMCommonForgeEvents::onEntitySize);
        MinecraftForge.EVENT_BUS.addListener(NMCommonForgeEvents::onEntityJoinWorld);
    }

    private static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        RaceCommand.register(dispatcher);
    }

    private static void onEntitySize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player entity) {
            RaceHolderAttacher.getRaceHolder(entity).ifPresent(raceHolder -> {
                EntityDimensions dimensions = raceHolder.getDimensionsOverride();
                Race currentRace = raceHolder.getCurrentRace();
                if (dimensions == null && currentRace == null)
                    return;

                float eyeHeight;
                if (dimensions == null) {
                    dimensions = currentRace.getDimensions(entity);
                    eyeHeight = currentRace.getEyeHeight(entity);
                } else {
                    eyeHeight = 0.85F * dimensions.height;
                }
                float scale = event.getPose() == Pose.CROUCHING ? 0.85F : 1.0F;

                event.setNewSize(dimensions);
                event.setNewEyeHeight(eyeHeight * scale);
            });
        }
    }

    private static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player entity) {
            RaceHolderAttacher.getCurrentRace(entity).ifPresent(currentRace -> currentRace.setMaxRaceHealth(entity));
        }
    }
}
