package io.firetamer.dragonblockbeyond._modules.namek;

import io.firetamer.dragonblockbeyond._modules.namek.blocks.SpreadableNamekGrass;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import io.firetamer.dragonblockbeyond.util.library_candidates.ModuleBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public class NamekModule extends ModuleBase {
    public static void init(){}

    public static final RegistryObject<Block> NAMEK_GRASS_BLOCK = registerBlock("namek_grass_block",
            () -> new SpreadableNamekGrass(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);
}
