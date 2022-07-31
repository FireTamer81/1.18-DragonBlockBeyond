package io.firetamer.dragonblockbeyond._modules.strongblock_module.items;

import java.util.List;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class WarenaiBlockItem extends BlockItem {
    public WarenaiBlockItem(Block blockIn) {
        super(blockIn, new Properties());
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (allowdedIn(group)) {
            items.add(getDefaultInstance());
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag compound = stack.getOrCreateTag();
        compound.putInt("color", 0xffffff);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        FireLibColor color = new FireLibColor(stack.getOrCreateTag().getInt("color"));

        if (StrongBlockModule.hasShiftDown()) {
            MutableComponent red = new TranslatableComponent("gui.dragonblockbeyond.red").append(": " + color.getRed());
            MutableComponent green = new TranslatableComponent("gui.dragonblockbeyond.green").append(": " + color.getGreen());
            MutableComponent blue = new TranslatableComponent("gui.dragonblockbeyond.blue").append(": " + color.getBlue());
            tooltip.add(red.append(", ").append(green).append(", ").append(blue));
            float[] hsb = FireLibColor.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
            MutableComponent hue = new TranslatableComponent("gui.dragonblockbeyond.hue").append(": " +
                    Math.round(hsb[0] * ColorSelectScreen.MAX_VALUE_HUE));
            MutableComponent saturation = new TranslatableComponent("gui.dragonblockbeyond.saturation").append(": " +
                    Math.round(hsb[1] * ColorSelectScreen.MAX_VALUE_SB));
            MutableComponent brightness = new TranslatableComponent("gui.dragonblockbeyond.brightness").append(": " +
                    Math.round(hsb[2] * ColorSelectScreen.MAX_VALUE_SB));
            tooltip.add(hue.append("°, ").append(saturation).append("%, ").append(brightness).append("%"));
        } else {
            tooltip.add(new TextComponent("Color #" + Integer.toHexString(color.getRGBA()).substring(2)));
        }
    }
}
