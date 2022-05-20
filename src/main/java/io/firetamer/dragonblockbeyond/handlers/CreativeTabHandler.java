package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.ColorableStrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.BlockInit;
import io.firetamer.dragonblockbeyond.common_registration.ItemInit;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class CreativeTabHandler {

    public static ItemStack[] blockTabItems = {
            BlockInit.TEST_BLOCK.get().asItem().getDefaultInstance(),

            ColorableStrongBlockModule.WARENAI_FULL_BLOCK.get().asItem().getDefaultInstance()
    };

    public static ItemStack[] itemTabItems = {
            ItemInit.TEST_ITEM.get().getDefaultInstance(),

            ColorableStrongBlockModule.PAINT_BUCKET.get().getDefaultInstance()
    };





    public static class DBBBlocksTab extends CreativeModeTab {
        public DBBBlocksTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return Blocks.DIRT.asItem().getDefaultInstance();
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
