package io.firetamer.dragonblockbeyond._modules.strongblock_module.items;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.tiles.StrongBlockTile;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class PaintBucketItem extends Item {
    public PaintBucketItem() {
        super(new Properties().defaultDurability(500).setNoRepair());
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
        compound.putInt("color", -1);
        compound.putBoolean("isRGBSelected", true);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DBBColor color = new DBBColor(stack.getOrCreateTag().getInt("color"));
        if (StrongBlockModule.hasShiftDown()) {
            MutableComponent red = new TranslatableComponent("gui.dragonblockbeyond.red").append(": " + color.getRed());
            MutableComponent green = new TranslatableComponent("gui.dragonblockbeyond.green").append(": " + color.getGreen());
            MutableComponent blue = new TranslatableComponent("gui.dragonblockbeyond.blue").append(": " + color.getBlue());
            tooltip.add(red.append(", ").append(green).append(", ").append(blue));
            float[] hsb = DBBColor.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());

            MutableComponent hue = new TranslatableComponent("gui.dragonblockbeyond.hue").append(": " +
                    Math.round(hsb[0] * ColorSelectScreen.MAX_VALUE_HUE));
            MutableComponent saturation = new TranslatableComponent("gui.dragonblockbeyond.saturation").append(": " +
                    Math.round(hsb[1] * ColorSelectScreen.MAX_VALUE_SB));
            MutableComponent brightness = new TranslatableComponent("gui.dragonblockbeyond.brightness").append(": " +
                    Math.round(hsb[2] * ColorSelectScreen.MAX_VALUE_SB));
            tooltip.add(hue.append("\u00B0, ").append(saturation).append("%, ").append(brightness).append("%"));
        } else {
            tooltip.add(new TextComponent("#" + Integer.toHexString(color.getRGBA()).substring(2)));
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.MAIN_HAND && playerIn.isShiftKeyDown()) {
            if (worldIn.isClientSide) {
                StrongBlockModule.openColorSelectScreen(playerIn.getMainHandItem().getTag().getInt("color"),
                                                  playerIn.getMainHandItem().getTag().getBoolean("isRGBSelected"));
                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, playerIn.getMainHandItem());
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        if (tileEntity instanceof StrongBlockTile) {
            if (context.getPlayer().isShiftKeyDown()) {
                context.getItemInHand().getTag().putInt("color", ((StrongBlockTile) tileEntity).getColor());
            } else {
                if (!context.getPlayer().getAbilities().instabuild && context.getItemInHand().getOrCreateTag().getInt("color") !=
                    ((StrongBlockTile) tileEntity).getColor()) {
                    if (context.getItemInHand().getDamageValue() == context.getItemInHand().getMaxDamage() - 1) {
                        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.BUCKET));
                    } else {
                        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), e -> e.broadcastBreakEvent(context.getHand()));
                    }
                }
                ((StrongBlockTile) tileEntity).setColor(context.getItemInHand().getTag().getInt("color"));
                context.getLevel()
                       .sendBlockUpdated(context.getClickedPos(),
                               tileEntity.getBlockState(),
                               tileEntity.getBlockState(),
                               Block.UPDATE_ALL_IMMEDIATE);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
