package io.firetamer.dragonblockbeyond._modules.strongblock_module.client.colorhandlers;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.items.WarenaiBlockItem;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class WarenaiBlockItemColor implements ItemColor {
    public int getColor(ItemStack stack, int tintindex) {
        if (stack.getItem() instanceof WarenaiBlockItem) {
            if (!stack.hasTag() || !stack.getTag().contains("color")) {
                return 0;
            }
            CompoundTag compound = stack.getTag();
            if (compound.contains("color")) {
                return compound.getInt("color");
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
