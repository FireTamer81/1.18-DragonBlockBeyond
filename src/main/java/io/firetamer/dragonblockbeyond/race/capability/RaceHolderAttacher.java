package io.firetamer.dragonblockbeyond.race.capability;

import com.mojang.datafixers.util.Pair;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.api.RaceSoundData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RaceHolderAttacher extends CapabilityAttacher {
    private static final Class<RaceHolder> CAPABILITY_CLASS = RaceHolder.class;
    public static final Capability<RaceHolder> RACE_HOLDER_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation RACE_HOLDER_RL = new ResourceLocation(DragonBlockBeyond.MOD_ID, "race_holder");

    @Nullable
    public static RaceHolder getRaceHolderUnwrap(Player entity) {
        return getRaceHolder(entity).orElse(null);
    }

    public static LazyOptional<RaceHolder> getRaceHolder(Player entity) {
        return entity.getCapability(RACE_HOLDER_CAPABILITY);
    }

    public static Optional<Race> getCurrentRace(Player entity) {
        return getRaceHolder(entity).resolve().map(RaceHolder::getCurrentRace);
    }

    @Nullable
    public static Race getCurrentRaceUnwrap(Player entity) {
        return getCurrentRace(entity).orElse(null);
    }








    public static Optional<String> getCurrentVariant(Player entity) {
        return getRaceHolder(entity).resolve().map(RaceHolder::getCurrentVariant);
    }

    @Nullable
    public static String getCurrentVariantUnwrap(Player entity) {
        return getCurrentVariant(entity).orElse(null);
    }

    public static Optional<Pair<Race, String>> getCurrentRaceVariantPair(Player entity) {
        return getRaceHolder(entity).resolve().map(raceHolder -> Pair.of(raceHolder.getCurrentRace(), raceHolder.getCurrentVariant()));
    }

    @Nullable
    public static Pair<Race, String> getCurrentRaceVariantPairUnwrap(Player entity) {
        return getCurrentRaceVariantPair(entity).orElse(null);
    }

    public static boolean isRace(Player entity, Race race) {
        return getCurrentRaceUnwrap(entity) == race;
    }

    public static boolean isRace(Player entity, TagKey<Race> tag) {
        Race race = getCurrentRaceUnwrap(entity);
        return race != null && race.is(tag);
    }

    public static int getRaceSwingDuration(Player entity) {
        return getCurrentRace(entity).map(m -> m.getSwingDuration(entity)).orElse(-1);
    }

    public static Optional<RaceSoundData> getRaceSoundData(Player entity) {
        return getCurrentRace(entity).map(Race::getRaceSoundData);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player entity) {
        genericAttachCapability(event, new RaceHolder(entity), RACE_HOLDER_CAPABILITY, RACE_HOLDER_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(RaceHolderAttacher::attach, RaceHolderAttacher::getRaceHolder, true);
    }
}
