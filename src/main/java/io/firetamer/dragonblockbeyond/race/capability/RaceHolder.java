package io.firetamer.dragonblockbeyond.race.capability;


import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import io.firetamer.dragonblockbeyond.network.PacketHandler;
import io.firetamer.dragonblockbeyond.init.RaceInit;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.util.TagUtil;
import io.firetamer.dragonblockbeyond.race.event.RaceEvent;
import io.firetamer.dragonblockbeyond.race.api.IAmbientSoundHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

public class RaceHolder extends PlayerCapability {
    @Nullable
    private Race currentRace;
    @Nullable
    private String currentVariant;

    @Nullable
    private EntityDimensions dimensionsOverride;

    public RaceHolder(Player entity) {
        super(entity);
    }

    @Nullable
    public Race getCurrentRace() {
        return currentRace;
    }

    @Nullable
    public String getCurrentVariant() {
        return currentVariant;
    }


    public void setCurrentRace(@Nullable Race target, boolean sync) {
        setCurrentRace(target, null, sync);
    }

    public void setCurrentRace(@Nullable Race target, @Nullable String variant, boolean sync) {
        if (target != null && variant != null && !target.getVariants().contains(variant))
            variant = null;
        RaceEvent raceEvent = new RaceEvent(this.livingEntity, this.currentRace, this.currentVariant, target, variant);
        if (MinecraftForge.EVENT_BUS.post(raceEvent))
            return;

        IAmbientSoundHolder.get(this.player).setAmbientSoundTime(0);

        if (this.currentRace != null && !this.livingEntity.level.isClientSide)
            this.currentRace.onDerace(this.livingEntity);
        this.currentRace = raceEvent.getTargetRace();
        this.currentVariant = this.currentRace == null ? null : raceEvent.getTargetVariant();

        refreshRace();

        if (this.currentRace != null && !this.livingEntity.level.isClientSide)
            this.currentRace.onRaceedTo(this.livingEntity);
        if (sync)
            this.updateTracking();
    }

    @Nullable
    public EntityDimensions getDimensionsOverride() {
        return this.dimensionsOverride;
    }

    public void setDimensionsOverride(@Nullable EntityDimensions dimensionsOverride, boolean sync) {
        this.dimensionsOverride = dimensionsOverride;

        if (sync)
            this.updateTracking();
    }

    protected void refreshRace() {
        this.livingEntity.refreshDimensions();
        this.livingEntity.reapplyPosition();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        if (this.currentRace != null)
            nbt.putString("CurrentRace", this.currentRace.getRegistryName().toString());

        if (this.currentVariant != null)
            nbt.putString("CurrentVariant", this.currentVariant);

        if (this.dimensionsOverride != null)
            nbt.put("DimensionsOverride", TagUtil.createDimensionsTag(this.dimensionsOverride));

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("CurrentRace", Tag.TAG_STRING)) {
            this.currentRace = RaceInit.getRegistry().getValue(new ResourceLocation(nbt.getString("CurrentRace")));
        } else {
            this.currentRace = null;
        }

        if (nbt.contains("CurrentVariant", Tag.TAG_STRING)) {
            this.currentVariant = nbt.getString("CurrentVariant");
        } else {
            this.currentVariant = null;
        }

        if (nbt.contains("DimensionsOverride", Tag.TAG_COMPOUND)) {
            this.dimensionsOverride = TagUtil.getDimensions(nbt.getCompound("DimensionsOverride"));
        } else {
            this.dimensionsOverride = null;
        }

        refreshRace();
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.livingEntity.getId(), RaceHolderAttacher.RACE_HOLDER_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return PacketHandler.INSTANCE;
    }
}
