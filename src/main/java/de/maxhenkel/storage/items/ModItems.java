package de.maxhenkel.storage.items;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {

    public static ModMinecartItem OAK_MINECART = new ModMinecartItem(() -> ModBlocks.OAK_CHEST);
    public static ModMinecartItem SPRUCE_MINECART = new ModMinecartItem(() -> ModBlocks.SPRUCE_CHEST);
    public static ModMinecartItem BIRCH_MINECART = new ModMinecartItem(() -> ModBlocks.BIRCH_CHEST);
    public static ModMinecartItem ACACIA_MINECART = new ModMinecartItem(() -> ModBlocks.ACACIA_CHEST);
    public static ModMinecartItem JUNGLE_MINECART = new ModMinecartItem(() -> ModBlocks.JUNGLE_CHEST);
    public static ModMinecartItem DARK_OAK_MINECART = new ModMinecartItem(() -> ModBlocks.DARK_OAK_CHEST);
    public static ModMinecartItem CRIMSON_MINECART = new ModMinecartItem(() -> ModBlocks.CRIMSON_CHEST);
    public static ModMinecartItem WARPED_MINECART = new ModMinecartItem(() -> ModBlocks.WARPED_CHEST);

    public static ModMinecartItem OAK_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.OAK_CHEST_TIER_1);
    public static ModMinecartItem SPRUCE_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.SPRUCE_CHEST_TIER_1);
    public static ModMinecartItem BIRCH_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.BIRCH_CHEST_TIER_1);
    public static ModMinecartItem ACACIA_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.ACACIA_CHEST_TIER_1);
    public static ModMinecartItem JUNGLE_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.JUNGLE_CHEST_TIER_1);
    public static ModMinecartItem DARK_OAK_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.DARK_OAK_CHEST_TIER_1);
    public static ModMinecartItem CRIMSON_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.CRIMSON_CHEST_TIER_1);
    public static ModMinecartItem WARPED_MINECART_TIER_1 = new ModMinecartItem(() -> ModBlocks.WARPED_CHEST_TIER_1);

    public static ModMinecartItem OAK_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.OAK_CHEST_TIER_2);
    public static ModMinecartItem SPRUCE_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.SPRUCE_CHEST_TIER_2);
    public static ModMinecartItem BIRCH_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.BIRCH_CHEST_TIER_2);
    public static ModMinecartItem ACACIA_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.ACACIA_CHEST_TIER_2);
    public static ModMinecartItem JUNGLE_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.JUNGLE_CHEST_TIER_2);
    public static ModMinecartItem DARK_OAK_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.DARK_OAK_CHEST_TIER_2);
    public static ModMinecartItem CRIMSON_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.CRIMSON_CHEST_TIER_2);
    public static ModMinecartItem WARPED_MINECART_TIER_2 = new ModMinecartItem(() -> ModBlocks.WARPED_CHEST_TIER_2);

    public static ModMinecartItem OAK_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.OAK_CHEST_TIER_3);
    public static ModMinecartItem SPRUCE_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.SPRUCE_CHEST_TIER_3);
    public static ModMinecartItem BIRCH_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.BIRCH_CHEST_TIER_3);
    public static ModMinecartItem ACACIA_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.ACACIA_CHEST_TIER_3);
    public static ModMinecartItem JUNGLE_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.JUNGLE_CHEST_TIER_3);
    public static ModMinecartItem DARK_OAK_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.DARK_OAK_CHEST_TIER_3);
    public static ModMinecartItem CRIMSON_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.CRIMSON_CHEST_TIER_3);
    public static ModMinecartItem WARPED_MINECART_TIER_3 = new ModMinecartItem(() -> ModBlocks.WARPED_CHEST_TIER_3);

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                OAK_MINECART.setRegistryName(Main.MODID, "oak_chest_minecart"),
                SPRUCE_MINECART.setRegistryName(Main.MODID, "spruce_chest_minecart"),
                BIRCH_MINECART.setRegistryName(Main.MODID, "birch_chest_minecart"),
                ACACIA_MINECART.setRegistryName(Main.MODID, "acacia_chest_minecart"),
                JUNGLE_MINECART.setRegistryName(Main.MODID, "jungle_chest_minecart"),
                DARK_OAK_MINECART.setRegistryName(Main.MODID, "dark_oak_chest_minecart"),
                CRIMSON_MINECART.setRegistryName(Main.MODID, "crimson_chest_minecart"),
                WARPED_MINECART.setRegistryName(Main.MODID, "warped_chest_minecart"),

                OAK_MINECART_TIER_1.setRegistryName(Main.MODID, "oak_chest_minecart_tier_1"),
                SPRUCE_MINECART_TIER_1.setRegistryName(Main.MODID, "spruce_chest_minecart_tier_1"),
                BIRCH_MINECART_TIER_1.setRegistryName(Main.MODID, "birch_chest_minecart_tier_1"),
                ACACIA_MINECART_TIER_1.setRegistryName(Main.MODID, "acacia_chest_minecart_tier_1"),
                JUNGLE_MINECART_TIER_1.setRegistryName(Main.MODID, "jungle_chest_minecart_tier_1"),
                DARK_OAK_MINECART_TIER_1.setRegistryName(Main.MODID, "dark_oak_chest_minecart_tier_1"),
                CRIMSON_MINECART_TIER_1.setRegistryName(Main.MODID, "crimson_chest_minecart_tier_1"),
                WARPED_MINECART_TIER_1.setRegistryName(Main.MODID, "warped_chest_minecart_tier_1"),

                OAK_MINECART_TIER_2.setRegistryName(Main.MODID, "oak_chest_minecart_tier_2"),
                SPRUCE_MINECART_TIER_2.setRegistryName(Main.MODID, "spruce_chest_minecart_tier_2"),
                BIRCH_MINECART_TIER_2.setRegistryName(Main.MODID, "birch_chest_minecart_tier_2"),
                ACACIA_MINECART_TIER_2.setRegistryName(Main.MODID, "acacia_chest_minecart_tier_2"),
                JUNGLE_MINECART_TIER_2.setRegistryName(Main.MODID, "jungle_chest_minecart_tier_2"),
                DARK_OAK_MINECART_TIER_2.setRegistryName(Main.MODID, "dark_oak_chest_minecart_tier_2"),
                CRIMSON_MINECART_TIER_2.setRegistryName(Main.MODID, "crimson_chest_minecart_tier_2"),
                WARPED_MINECART_TIER_2.setRegistryName(Main.MODID, "warped_chest_minecart_tier_2"),

                OAK_MINECART_TIER_3.setRegistryName(Main.MODID, "oak_chest_minecart_tier_3"),
                SPRUCE_MINECART_TIER_3.setRegistryName(Main.MODID, "spruce_chest_minecart_tier_3"),
                BIRCH_MINECART_TIER_3.setRegistryName(Main.MODID, "birch_chest_minecart_tier_3"),
                ACACIA_MINECART_TIER_3.setRegistryName(Main.MODID, "acacia_chest_minecart_tier_3"),
                JUNGLE_MINECART_TIER_3.setRegistryName(Main.MODID, "jungle_chest_minecart_tier_3"),
                DARK_OAK_MINECART_TIER_3.setRegistryName(Main.MODID, "dark_oak_chest_minecart_tier_3"),
                CRIMSON_MINECART_TIER_3.setRegistryName(Main.MODID, "crimson_chest_minecart_tier_3"),
                WARPED_MINECART_TIER_3.setRegistryName(Main.MODID, "warped_chest_minecart_tier_3")
        );
    }

}
