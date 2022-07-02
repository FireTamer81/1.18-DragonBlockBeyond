package io.firetamer.dragonblockbeyond.race.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityDimensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagUtil {
    @NotNull
    public static CompoundTag createDimensionsTag(@Nullable EntityDimensions dimensions) {
        if (dimensions == null)
            return new CompoundTag();

        CompoundTag tag = new CompoundTag();
        tag.putFloat("Width", dimensions.width);
        tag.putFloat("Height", dimensions.height);
        tag.putBoolean("Fixed", dimensions.fixed);

        return tag;
    }

    @Nullable
    public static EntityDimensions getDimensions(@Nullable CompoundTag tag) {
        if (tag == null || !tag.contains("Width"))
            return null;

        return new EntityDimensions(tag.getFloat("Width"), tag.getFloat("Height"), tag.getBoolean("Fixed"));
    }
}
