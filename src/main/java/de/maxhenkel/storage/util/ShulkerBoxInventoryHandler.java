package de.maxhenkel.storage.util;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;


public class ShulkerBoxInventoryHandler extends ItemStackHandler {

    protected final ItemStack box;
    protected CompoundNBT blockEntityTag;

    public ShulkerBoxInventoryHandler(ItemStack box) {
        super(27);
        this.box = box;
        CompoundNBT tag = box.getTag();
        if (tag != null) {
            if (tag.contains("BlockEntityTag")) {
                this.blockEntityTag = tag.getCompound("BlockEntityTag");
                ItemStackHelper.loadAllItems(this.blockEntityTag, stacks);
            }
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        CompoundNBT tag = box.getOrCreateTag();
        if (this.blockEntityTag == null) {
            tag.put("BlockEntityTag", this.blockEntityTag = new CompoundNBT());
        } else {
            tag.put("BlockEntityTag", this.blockEntityTag);
        }
        ItemStackHelper.saveAllItems(this.blockEntityTag, stacks, true);
    }


}
