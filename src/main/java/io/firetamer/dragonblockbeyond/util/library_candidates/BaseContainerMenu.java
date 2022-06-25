package io.firetamer.dragonblockbeyond.util.library_candidates;

import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlockTile;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseContainerMenu extends AbstractContainerMenu {
    public final BlockEntity blockEntity;
    private final Level level;
    private final MenuType<?> menuType;
    private static int tileEntitySlotCount;
    private int playerInvOffsetX; //These were previously static, if there is an issue with them return them to that state.
    private int playerInvOffsetY;

    protected BaseContainerMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inv, BlockEntity blockEntityIn,
                                int tileEntitySlotCount, int playerInvOffsetX, int playerInvOffsetY) {
        super(menuType, containerId);

        this.blockEntity = blockEntityIn;
        this.level = inv.player.level;
        this.menuType = menuType;
        this.tileEntitySlotCount = tileEntitySlotCount;
        this.playerInvOffsetX = playerInvOffsetX;
        this.playerInvOffsetY = playerInvOffsetY;

        addContainerSlots(inv);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, MachinesModule.FABRICATOR.get());
    }


    public void addContainerSlots(Inventory playerInv) {
        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            int f = -1;


            //Below are examples of adding more slots to the container (like a chest)
            //It is advised that you mimic the below examples in your own implementation of this class.

            //Adds individual slots, uses the f variable to assign slot id ina  controlled way.
            /*
            //The crafting slots
            this.addSlot(new SlotItemHandler(handler, ++f, 44, -11));
            this.addSlot(new SlotItemHandler(handler, ++f, 44, 13));
            this.addSlot(new SlotItemHandler(handler, ++f, 44, 37));
            this.addSlot(new SlotItemHandler(handler, ++f, 44, 61));

            this.addSlot(new SlotItemHandler(handler, ++f, 20, -11));
            this.addSlot(new SlotItemHandler(handler, ++f, 20, 13));
            this.addSlot(new SlotItemHandler(handler, ++f, 20, 37));
            this.addSlot(new SlotItemHandler(handler, ++f, 20, 61));
            */

            //Adds a row of 9 slots
            /*
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(
                        handler,
                        ++f,
                        (slotRowTopLeftX + 8) + (j * 18) + 13,
                        slotRowTopLeftY
                ));
            }
            */
        });
    }






    /******************************************************************************************************************/
    //Util Methods
    /******************************************************************************************************************/

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
            //Only small edits were made to allow offsetting of the player inventory.
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
    private static final int TE_INVENTORY_SLOT_COUNT = tileEntitySlotCount;  // must be the number of slots you have!

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

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                int xPos = (8 + l * 18) + playerInvOffsetX;
                int yPos = (86 + i * 18) + playerInvOffsetY;

                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, xPos, yPos));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            int xPos = (8 + i * 18) + playerInvOffsetX;
            int yPos = 144 + playerInvOffsetY;

            this.addSlot(new Slot(playerInventory, i, xPos, yPos));
        }
    }
}
