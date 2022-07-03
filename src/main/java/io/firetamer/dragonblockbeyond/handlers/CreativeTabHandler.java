package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.namek.NamekModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;

public class CreativeTabHandler {

    public static ItemStack[] blockTabItems = {
            CommonObjects.CLAY_DIRT.get().asItem().getDefaultInstance(),
            CommonObjects.DIRTY_STONE.get().asItem().getDefaultInstance(),

            MachinesModule.FABRICATOR_BLOCK.get().asItem().getDefaultInstance(),

            StrongBlockModule.WARENAI_FULL_BLOCK.get().asItem().getDefaultInstance(),
            StrongBlockModule.WARENAI_STAIRS_BLOCK.get().asItem().getDefaultInstance(),
            StrongBlockModule.WARENAI_SLAB_BLOCK.get().asItem().getDefaultInstance(),
            StrongBlockModule.WARENAI_FENCE_BLOCK.get().asItem().getDefaultInstance(),
            //StrongBlockModule.WARENAI_WALL_BLOCK.get().asItem().getDefaultInstance(),

            StrongBlockModule.WARENAI_GLASS.get().asItem().getDefaultInstance(),
            StrongBlockModule.WARENAI_GLASS_SLAB.get().asItem().getDefaultInstance(),
            StrongBlockModule.WARENAI_GLASS_STAIRS.get().asItem().getDefaultInstance(),

            NamekModule.NAMEK_GRASS_BLOCK.get().asItem().getDefaultInstance()
    };

    public static ItemStack[] itemTabItems = {
            CommonObjects.TEST_ITEM.get().getDefaultInstance(),

            StrongBlockModule.PAINT_BUCKET.get().getDefaultInstance()
    };





    public static class DBBBlocksTab extends CreativeModeTab {
        public DBBBlocksTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return net.minecraft.world.level.block.Blocks.DIRT.asItem().getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {
            list.addAll(Arrays.asList(blockTabItems));
        }
    }

    public static class DBBItemsTab extends CreativeModeTab {
        public DBBItemsTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return Items.FLINT.getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {
            list.addAll(Arrays.asList(itemTabItems));
        }
    }
}
