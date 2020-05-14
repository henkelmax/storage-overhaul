package de.maxhenkel.storage.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class HugeChestContainer extends Container {

    private IInventory inventory;
    private int numRows;
    private int numCols;
    private int numPlayerInvCols;

    public HugeChestContainer(int id, PlayerInventory playerInventory, IInventory inventory, int numRows) {
        super(Containers.HUGE_CHEST_CONTAINER, id);
        this.inventory = inventory;
        this.numRows = numRows;
        this.numPlayerInvCols = 9;
        this.numCols = 12;

        inventory.openInventory(playerInventory.player);
        int i = (numRows - 4) * 18;

        for (int j = 0; j < numRows; j++) {
            for (int k = 0; k < numCols; k++) {
                this.addSlot(new Slot(inventory, k + j * numCols, 8 + k * 18, 18 + j * 18));
            }
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < numPlayerInvCols; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + l * numPlayerInvCols + 9, 35 + j1 * 18, 104 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < numPlayerInvCols; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 35 + i1 * 18, 162 + i));
        }
    }

    public HugeChestContainer(int id, PlayerInventory playerInventory, int numRows) {
        this(id, playerInventory, new Inventory(numRows * 12), numRows);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < numRows * numCols) {
                if (!mergeItemStack(itemstack1, numRows * numCols, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, 0, numRows * numCols, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        inventory.closeInventory(playerIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }
}
