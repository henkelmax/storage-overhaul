package de.maxhenkel.storage.items;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {

    public static ModMinecartItem OAK_MINECART = new ModMinecartItem(() -> ModEntities.OAK_CHEST_MINECART);
    public static ModMinecartItem SPRUCE_MINECART = new ModMinecartItem(() -> ModEntities.SPRUCE_CHEST_MINECART);
    public static ModMinecartItem BIRCH_MINECART = new ModMinecartItem(() -> ModEntities.BIRCH_CHEST_MINECART);
    public static ModMinecartItem ACACIA_MINECART = new ModMinecartItem(() -> ModEntities.ACACIA_CHEST_MINECART);
    public static ModMinecartItem JUNGLE_MINECART = new ModMinecartItem(() -> ModEntities.JUNGLE_CHEST_MINECART);
    public static ModMinecartItem DARK_OAK_MINECART = new ModMinecartItem(() -> ModEntities.DARK_OAK_CHEST_MINECART);

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
