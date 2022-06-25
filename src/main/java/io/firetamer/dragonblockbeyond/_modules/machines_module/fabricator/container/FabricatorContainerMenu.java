package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container;

import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlockTile;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget.ModResultSlot;
import io.firetamer.dragonblockbeyond.util.library_candidates.BaseContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FabricatorContainerMenu extends BaseContainerMenu {
    public static final int fabricatorInventoryManagementSlotsX = -13;
    public static final int fabricatorInventoryManagementSlotsY = 76;

    public FabricatorContainerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public FabricatorContainerMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(MachinesModule.FABRICATOR_MENU_TYPE.get(), pContainerId, inv, entity, 9, 0, 24);
    }

    @Override
    public void addContainerSlots(Inventory playerInv) {
        //super.addContainerSlots(playerInv);

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            int f = -1;



            for(int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(
                        handler,
                        ++f,
                        (fabricatorInventoryManagementSlotsX + 8) + (j * 18) + 13,
                        fabricatorInventoryManagementSlotsY
                ));
            }
        });
    }
}
