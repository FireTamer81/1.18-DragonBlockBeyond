package io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class WarenaiBlockStateProperties {
    public static final EnumProperty<WarenaiBlockConditionEnum> BLOCK_CONDITION = EnumProperty.create("block_condition", WarenaiBlockConditionEnum.class);
    public static final EnumProperty<WarenaiBlockPatternEnum> BLOCK_PATTERN = EnumProperty.create("block_pattern", WarenaiBlockPatternEnum.class);



}
