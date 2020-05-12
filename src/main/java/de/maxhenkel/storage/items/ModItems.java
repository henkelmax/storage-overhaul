package de.maxhenkel.storage.items;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {

    public static StorageOverhaulMinecartItem OAK_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.OAK_CHEST_MINECART);
    public static StorageOverhaulMinecartItem SPRUCE_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.SPRUCE_CHEST_MINECART);
    public static StorageOverhaulMinecartItem BIRCH_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.BIRCH_CHEST_MINECART);
    public static StorageOverhaulMinecartItem ACACIA_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.ACACIA_CHEST_MINECART);
    public static StorageOverhaulMinecartItem JUNGLE_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.JUNGLE_CHEST_MINECART);
    public static StorageOverhaulMinecartItem DARK_OAK_MINECART = new StorageOverhaulMinecartItem(() -> ModEntities.DARK_OAK_CHEST_MINECART);

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                OAK_MINECART.setRegistryName(Main.MODID, "oak_chest_minecart"),
                SPRUCE_MINECART.setRegistryName(Main.MODID, "spruce_chest_minecart"),
                BIRCH_MINECART.setRegistryName(Main.MODID, "birch_chest_minecart"),
                ACACIA_MINECART.setRegistryName(Main.MODID, "acacia_chest_minecart"),
                JUNGLE_MINECART.setRegistryName(Main.MODID, "jungle_chest_minecart"),
                DARK_OAK_MINECART.setRegistryName(Main.MODID, "dark_oak_chest_minecart")
        );
    }

}
