package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;

public class FabricatorInvContainer implements Container, StackedContentsCompatible {
    private final NonNullList<ItemStack> items;
    private final AbstractContainerMenu parentMenu;

    public FabricatorInvContainer(AbstractContainerMenu parentMenuIn, int itemListSizeIn) {
        this.items = NonNullList.withSize(itemListSizeIn, ItemStack.EMPTY);
        this.parentMenu = parentMenuIn;
    }


    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int listIndex) {
        return listIndex >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(listIndex);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, p_18942_, p_18943_);
        if (!itemstack.isEmpty()) {
            this.parentMenu.slotsChanged(this);
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.items, p_18951_);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        this.items.set(p_18944_, p_18945_);
        this.parentMenu.slotsChanged(this);
    }

    @Override
    public void setChanged() { }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents p_40281_) {
        for(ItemStack itemstack : this.items) {
            p_40281_.accountSimpleStack(itemstack);
        }
    }
}
