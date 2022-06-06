package io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties;

import net.minecraft.util.StringRepresentable;

public enum WarenaiBlockConditionEnum implements StringRepresentable {
    POLISHED("polished"),
    NORMAL("normal"),
    SCUFFED("scuffed"),
    CRACKED1("cracked1"),
    CRACKED2("cracked2"),
    CRACKED3("cracked3"),
    CRACKED4("cracked4");

    private final String name;

    WarenaiBlockConditionEnum(String pName) {
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
