package io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties;

import net.minecraft.util.StringRepresentable;

public enum WarenaiBlockPatternEnum implements StringRepresentable {
    SMOOTH("smooth"),
    LARGE_BRICK("large_brick"),
    SMALL_BRICK("small_brick");

    private final String name;

    WarenaiBlockPatternEnum(String pName) {
        name = pName;
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
