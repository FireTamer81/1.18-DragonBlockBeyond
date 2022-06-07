package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container;

import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlockTile;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FabricatorContainerMenu extends AbstractContainerMenu {
    private final FabricatorBlockTile blockEntity;
    private final Level level;
    private static final int playerInvOffsetY = 24;
    private static final int playerInvOffsetX = 0;
    private final FabricatorInvContainer fabricatorStorageSlots = new FabricatorInvContainer(this, 24);
    private final FabricatorInvContainer fabricatorOutputSlots = new FabricatorInvContainer(this, 24);

    public FabricatorContainerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public FabricatorContainerMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(MachinesModule.FABRICATOR_MENU_TYPE.get(), pContainerId);
        checkContainerSize(inv, 41);
        blockEntity = ((FabricatorBlockTile) entity);
        this.level = inv.player.level;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 44, -9));
            this.addSlot(new SlotItemHandler(handler, 1, 44, 13));
            this.addSlot(new SlotItemHandler(handler, 2, 44, 37));
            this.addSlot(new SlotItemHandler(handler, 3, 80, -13));
            this.addSlot(new ModResultSlot(handler, 4, 116, 13));
        });
    }





    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 41;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);

        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }

        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);

        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, MachinesModule.FABRICATOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                //int xPos = playerInvOffsetX < 0 ? (8 + l * 18) + playerInvOffsetX : (8 + l * 18) - playerInvOffsetX;
                //int yPos = playerInvOffsetY < 0 ? (86 + i * 18) + playerInvOffsetY : (86 + i * 18) - playerInvOffsetY;
                int xPos = (8 + l * 18) + playerInvOffsetX;
                int yPos = (86 + i * 18) + playerInvOffsetY;

                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, xPos, yPos));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            //int xPos = playerInvOffsetX < 0 ? (8 + i * 18) + playerInvOffsetX : (8 + i * 18) - playerInvOffsetX;
            //int yPos = playerInvOffsetY < 0 ? 144 + playerInvOffsetY : 144 - playerInvOffsetY;
            int xPos = (8 + i * 18) + playerInvOffsetX;
            int yPos = 144 + playerInvOffsetY;

            this.addSlot(new Slot(playerInventory, i, xPos, yPos));
        }
    }
}
