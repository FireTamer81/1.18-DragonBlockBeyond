package io.firetamer.dragonblockbeyond.item_groups;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DBBItemGroup extends CreativeModeTab {

    public DBBItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() { return Items.FLINT.getDefaultInstance(); }

    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        //items.add(ItemInit.WARENAI_CRYSTAL.getDefaultInstance());

        //items.add(NamekModule.AJISA_FLOWERS.getDefaultInstance());
    }
}
