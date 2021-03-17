package de.maxhenkel.storage;

import de.maxhenkel.storage.gui.Containers;
import de.maxhenkel.storage.gui.HugeChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public enum ChestTier {

    BASE_TIER(0, 3 * 9), TIER_1(1, 4 * 9), TIER_2(2, 5 * 9), TIER_3(3, 6 * 9);

    private int tier, numSlots;

    ChestTier(int tier, int numSlots) {
        this.tier = tier;
        this.numSlots = numSlots;
    }

    public int numSlots() {
        return numSlots;
    }

    public int getTier() {
        return tier;
    }

    public static ChestTier byTier(int tier) {
        for (ChestTier t : values()) {
            if (t.getTier() == tier) {
                return t;
            }
        }
        return BASE_TIER;
    }

    public Container getContainer(int id, PlayerInventory player, IInventory inventory) {
        int slotCount = inventory.getContainerSize();
        int rows;
        if (slotCount % 18 == 0) {
            rows = slotCount / 18;
            switch (rows) {
                case 4:
                    return new HugeChestContainer(Containers.GENERIC_18x4, id, player, inventory, 4);
                case 5:
                    return new HugeChestContainer(Containers.GENERIC_18x5, id, player, inventory, 5);
                case 6:
                    return new HugeChestContainer(Containers.GENERIC_18x6, id, player, inventory, 6);
                case 7:
                    return new HugeChestContainer(Containers.GENERIC_18x7, id, player, inventory, 7);
                case 8:
                    return new HugeChestContainer(Containers.GENERIC_18x8, id, player, inventory, 8);
            }
        }
        if (slotCount % 9 == 0) {
            rows = slotCount / 9;
            switch (rows) {
                case 3:
                    return ChestContainer.threeRows(id, player, inventory);
                case 4:
                    return new ChestContainer(ContainerType.GENERIC_9x4, id, player, inventory, 4);
                case 5:
                    return new ChestContainer(ContainerType.GENERIC_9x5, id, player, inventory, 5);
                case 6:
                    return ChestContainer.sixRows(id, player, inventory);
            }
        }

        return ChestContainer.threeRows(id, player, inventory);
    }
}
