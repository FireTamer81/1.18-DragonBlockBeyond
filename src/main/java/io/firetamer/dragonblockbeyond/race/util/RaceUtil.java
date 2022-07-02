package io.firetamer.dragonblockbeyond.race.util;

import com.mojang.brigadier.context.CommandContext;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID)
public class RaceUtil {


    public static void setRace(@Nullable CommandSourceStack source, LivingEntity entity, RaceHolder raceHolder, @Nullable Race race, @Nullable String variant) {
        raceHolder.setCurrentRace(race, variant, true);

        if (race != null) {
            race.summonRaceParticles(entity, (ServerLevel) entity.level);
            race.playRaceedToSound(entity, (ServerLevel) entity.level);
            if (source != null)
                sendRaceSuccessMessage(source, entity, race);
        }
    }

    public static void sendRaceSuccessMessage(@NotNull CommandSourceStack source, LivingEntity entity, Race race) {
        if (entity == source.getEntity() && entity instanceof Player player) {
            Component message = new TranslatableComponent("commands.dragonblockbeyond.race.success.self", race.getDescription()).withStyle(ChatFormatting.GREEN);
            // player.connection.send(new STitlePacket(STitlePacket.Type.TITLE, message));
            player.displayClientMessage(message, true);
        } else {
            Component message = new TranslatableComponent("commands.dragonblockbeyond.race.success.other", entity.getDisplayName(), race.getDescription()).withStyle(ChatFormatting.GREEN);
            source.sendSuccess(message, false);
        }
    }

    @NotNull
    public static Component getClaerRaceSuccessMessage(CommandContext<CommandSourceStack> context, LivingEntity entity) {
        if (entity == context.getSource().getEntity()) {
            return new TranslatableComponent("commands.dragonblockbeyond.derace.success.self").withStyle(ChatFormatting.RED);
        } else {
            return new TranslatableComponent("commands.dragonblockbeyond.derace.success.other", entity.getDisplayName()).withStyle(ChatFormatting.RED);
        }
    }



}
