package io.firetamer.dragonblockbeyond.item_groups;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class DBBBlocksItemGroup extends CreativeModeTab {

    public DBBBlocksItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() { return Blocks.DIRT.asItem().getDefaultInstance(); }

    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        //items.add(BlockInit.DIRTY_STONE.asItem().getDefaultInstance());
        //items.add(TimeChamberModule.TIME_CHAMBER_DOOR_BLOCK.asItem().getDefaultInstance());
    }
}
