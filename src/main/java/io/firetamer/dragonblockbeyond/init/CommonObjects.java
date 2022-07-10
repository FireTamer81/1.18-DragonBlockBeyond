package io.firetamer.dragonblockbeyond.init;

import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.ModuleBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public class CommonObjects extends ModuleBase {
    public static void init(){}


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public static final RegistryObject<Item> TEST_ITEM = RegistryHandler.ITEMS.register("test_item",
            () -> new Item(getItemProperties()));

    public static final RegistryObject<Block> DIRTY_STONE = registerBlock("dirty_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> CLAY_DIRT = registerBlock("clay_dirt", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    //Won't be turned to namek grass
    public static final RegistryObject<Block> COARSE_CLAY_DIRT = registerBlock("coarse_clay_dirt", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);
}
