package de.maxhenkel.storage.gui;

import de.maxhenkel.storage.items.AdvancedShulkerBoxItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.item.ItemStack;

public class AdvancedShulkerboxSlot extends ShulkerBoxSlot {
    public AdvancedShulkerboxSlot(IInventory inventoryIn, int slotIndexIn, int xPosition, int yPosition) {
        super(inventoryIn, slotIndexIn, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (!super.isItemValid(stack)) {
            return false;
        }

        if (stack.getItem() instanceof AdvancedShulkerBoxItem) {
            return false;
        }

        return true;
    }
}
