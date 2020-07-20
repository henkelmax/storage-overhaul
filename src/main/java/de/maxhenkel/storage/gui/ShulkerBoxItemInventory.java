package de.maxhenkel.storage.gui;

import de.maxhenkel.corelib.inventory.ShulkerBoxInventory;
import de.maxhenkel.storage.blocks.tileentity.AdvancedShulkerBoxTileEnitity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class ShulkerBoxItemInventory extends ShulkerBoxInventory {

    public ShulkerBoxItemInventory(PlayerEntity player, ItemStack shulkerBox) {
        super(player, shulkerBox);
    }

    @Override
    protected SoundEvent getOpenSound() {
        return AdvancedShulkerBoxTileEnitity.getOpenSound();
    }

    @Override
    protected SoundEvent getCloseSound() {
        return AdvancedShulkerBoxTileEnitity.getCloseSound();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ShulkerBoxContainer(i, playerInventory);
    }

}
