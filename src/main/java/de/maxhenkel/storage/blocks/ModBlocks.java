package de.maxhenkel.storage.blocks;

import de.maxhenkel.storage.ChestTier;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {

    public static final ModChestBlock OAK_CHEST = new ModChestBlock("oak_chest", WoodType.OAK, ChestTier.BASE_TIER);
    public static final ModChestBlock SPRUCE_CHEST = new ModChestBlock("spruce_chest", WoodType.SPRUCE, ChestTier.BASE_TIER);
    public static final ModChestBlock BIRCH_CHEST = new ModChestBlock("birch_chest", WoodType.BIRCH, ChestTier.BASE_TIER);
    public static final ModChestBlock ACACIA_CHEST = new ModChestBlock("acacia_chest", WoodType.ACACIA, ChestTier.BASE_TIER);
    public static final ModChestBlock JUNGLE_CHEST = new ModChestBlock("jungle_chest", WoodType.JUNGLE, ChestTier.BASE_TIER);
    public static final ModChestBlock DARK_OAK_CHEST = new ModChestBlock("dark_oak_chest", WoodType.DARK_OAK, ChestTier.BASE_TIER);

    public static final ModChestBlock OAK_CHEST_TIER_1 = new ModChestBlock("oak_chest_tier_1", WoodType.OAK, ChestTier.TIER_1);
    public static final ModChestBlock SPRUCE_CHEST_TIER_1 = new ModChestBlock("spruce_chest_tier_1", WoodType.SPRUCE, ChestTier.TIER_1);
    public static final ModChestBlock BIRCH_CHEST_TIER_1 = new ModChestBlock("birch_chest_tier_1", WoodType.BIRCH, ChestTier.TIER_1);
    public static final ModChestBlock ACACIA_CHEST_TIER_1 = new ModChestBlock("acacia_chest_tier_1", WoodType.ACACIA, ChestTier.TIER_1);
    public static final ModChestBlock JUNGLE_CHEST_TIER_1 = new ModChestBlock("jungle_chest_tier_1", WoodType.JUNGLE, ChestTier.TIER_1);
    public static final ModChestBlock DARK_OAK_CHEST_TIER_1 = new ModChestBlock("dark_oak_chest_tier_1", WoodType.DARK_OAK, ChestTier.TIER_1);

    public static final ModChestBlock OAK_CHEST_TIER_2 = new ModChestBlock("oak_chest_tier_2", WoodType.OAK, ChestTier.TIER_2);
    public static final ModChestBlock SPRUCE_CHEST_TIER_2 = new ModChestBlock("spruce_chest_tier_2", WoodType.SPRUCE, ChestTier.TIER_2);
    public static final ModChestBlock BIRCH_CHEST_TIER_2 = new ModChestBlock("birch_chest_tier_2", WoodType.BIRCH, ChestTier.TIER_2);
    public static final ModChestBlock ACACIA_CHEST_TIER_2 = new ModChestBlock("acacia_chest_tier_2", WoodType.ACACIA, ChestTier.TIER_2);
    public static final ModChestBlock JUNGLE_CHEST_TIER_2 = new ModChestBlock("jungle_chest_tier_2", WoodType.JUNGLE, ChestTier.TIER_2);
    public static final ModChestBlock DARK_OAK_CHEST_TIER_2 = new ModChestBlock("dark_oak_chest_tier_2", WoodType.DARK_OAK, ChestTier.TIER_2);

    public static final ModChestBlock OAK_CHEST_TIER_3 = new ModChestBlock("oak_chest_tier_3", WoodType.OAK, ChestTier.TIER_3);
    public static final ModChestBlock SPRUCE_CHEST_TIER_3 = new ModChestBlock("spruce_chest_tier_3", WoodType.SPRUCE, ChestTier.TIER_3);
    public static final ModChestBlock BIRCH_CHEST_TIER_3 = new ModChestBlock("birch_chest_tier_3", WoodType.BIRCH, ChestTier.TIER_3);
    public static final ModChestBlock ACACIA_CHEST_TIER_3 = new ModChestBlock("acacia_chest_tier_3", WoodType.ACACIA, ChestTier.TIER_3);
    public static final ModChestBlock JUNGLE_CHEST_TIER_3 = new ModChestBlock("jungle_chest_tier_3", WoodType.JUNGLE, ChestTier.TIER_3);
    public static final ModChestBlock DARK_OAK_CHEST_TIER_3 = new ModChestBlock("dark_oak_chest_tier_3", WoodType.DARK_OAK, ChestTier.TIER_3);

    public static final ModBarrelBlock OAK_BARREL = new ModBarrelBlock("oak_barrel");
    public static final ModBarrelBlock SPRUCE_BARREL = new ModBarrelBlock("spruce_barrel");
    public static final ModBarrelBlock BIRCH_BARREL = new ModBarrelBlock("birch_barrel");
    public static final ModBarrelBlock ACACIA_BARREL = new ModBarrelBlock("acacia_barrel");
    public static final ModBarrelBlock JUNGLE_BARREL = new ModBarrelBlock("jungle_barrel");
    public static final ModBarrelBlock DARK_OAK_BARREL = new ModBarrelBlock("dark_oak_barrel");

    public static final StorageBarrelBlock OAK_STORAGE_BARREL = new StorageBarrelBlock("oak_storage_barrel");
    public static final StorageBarrelBlock SPRUCE_STORAGE_BARREL = new StorageBarrelBlock("spruce_storage_barrel");
    public static final StorageBarrelBlock BIRCH_STORAGE_BARREL = new StorageBarrelBlock("birch_storage_barrel");
    public static final StorageBarrelBlock ACACIA_STORAGE_BARREL = new StorageBarrelBlock("acacia_storage_barrel");
    public static final StorageBarrelBlock JUNGLE_STORAGE_BARREL = new StorageBarrelBlock("jungle_storage_barrel");
    public static final StorageBarrelBlock DARK_OAK_STORAGE_BARREL = new StorageBarrelBlock("dark_oak_storage_barrel");

    public static final AdvancedShulkerBoxBlock WHITE_SHULKER_BOX = new AdvancedShulkerBoxBlock("white_shulker_box", DyeColor.WHITE);
    public static final AdvancedShulkerBoxBlock ORANGE_SHULKER_BOX = new AdvancedShulkerBoxBlock("orange_shulker_box", DyeColor.ORANGE);
    public static final AdvancedShulkerBoxBlock MAGENTA_SHULKER_BOX = new AdvancedShulkerBoxBlock("magenta_shulker_box", DyeColor.MAGENTA);
    public static final AdvancedShulkerBoxBlock LIGHT_BLUE_SHULKER_BOX = new AdvancedShulkerBoxBlock("light_blue_shulker_box", DyeColor.LIGHT_BLUE);
    public static final AdvancedShulkerBoxBlock YELLOW_SHULKER_BOX = new AdvancedShulkerBoxBlock("yellow_shulker_box", DyeColor.YELLOW);
    public static final AdvancedShulkerBoxBlock LIME_SHULKER_BOX = new AdvancedShulkerBoxBlock("lime_shulker_box", DyeColor.LIME);
    public static final AdvancedShulkerBoxBlock PINK_SHULKER_BOX = new AdvancedShulkerBoxBlock("pink_shulker_box", DyeColor.PINK);
    public static final AdvancedShulkerBoxBlock GRAY_SHULKER_BOX = new AdvancedShulkerBoxBlock("gray_shulker_box", DyeColor.GRAY);
    public static final AdvancedShulkerBoxBlock LIGHT_GRAY_SHULKER_BOX = new AdvancedShulkerBoxBlock("light_gray_shulker_box", DyeColor.LIGHT_GRAY);
    public static final AdvancedShulkerBoxBlock CYAN_SHULKER_BOX = new AdvancedShulkerBoxBlock("cyan_shulker_box", DyeColor.CYAN);
    public static final AdvancedShulkerBoxBlock PURPLE_SHULKER_BOX = new AdvancedShulkerBoxBlock("purple_shulker_box", DyeColor.PURPLE);
    public static final AdvancedShulkerBoxBlock BLUE_SHULKER_BOX = new AdvancedShulkerBoxBlock("blue_shulker_box", DyeColor.BLUE);
    public static final AdvancedShulkerBoxBlock BROWN_SHULKER_BOX = new AdvancedShulkerBoxBlock("brown_shulker_box", DyeColor.BROWN);
    public static final AdvancedShulkerBoxBlock GREEN_SHULKER_BOX = new AdvancedShulkerBoxBlock("green_shulker_box", DyeColor.GREEN);
    public static final AdvancedShulkerBoxBlock RED_SHULKER_BOX = new AdvancedShulkerBoxBlock("red_shulker_box", DyeColor.RED);
    public static final AdvancedShulkerBoxBlock BLACK_SHULKER_BOX = new AdvancedShulkerBoxBlock("black_shulker_box", DyeColor.BLACK);

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                OAK_CHEST,
                SPRUCE_CHEST,
                BIRCH_CHEST,
                ACACIA_CHEST,
                JUNGLE_CHEST,
                DARK_OAK_CHEST,

                OAK_CHEST_TIER_1,
                SPRUCE_CHEST_TIER_1,
                BIRCH_CHEST_TIER_1,
                ACACIA_CHEST_TIER_1,
                JUNGLE_CHEST_TIER_1,
                DARK_OAK_CHEST_TIER_1,

                OAK_CHEST_TIER_2,
                SPRUCE_CHEST_TIER_2,
                BIRCH_CHEST_TIER_2,
                ACACIA_CHEST_TIER_2,
                JUNGLE_CHEST_TIER_2,
                DARK_OAK_CHEST_TIER_2,

                OAK_CHEST_TIER_3,
                SPRUCE_CHEST_TIER_3,
                BIRCH_CHEST_TIER_3,
                ACACIA_CHEST_TIER_3,
                JUNGLE_CHEST_TIER_3,
                DARK_OAK_CHEST_TIER_3,

                OAK_BARREL,
                SPRUCE_BARREL,
                BIRCH_BARREL,
                ACACIA_BARREL,
                JUNGLE_BARREL,
                DARK_OAK_BARREL,

                OAK_STORAGE_BARREL,
                SPRUCE_STORAGE_BARREL,
                BIRCH_STORAGE_BARREL,
                ACACIA_STORAGE_BARREL,
                JUNGLE_STORAGE_BARREL,
                DARK_OAK_STORAGE_BARREL,

                WHITE_SHULKER_BOX,
                ORANGE_SHULKER_BOX,
                MAGENTA_SHULKER_BOX,
                LIGHT_BLUE_SHULKER_BOX,
                YELLOW_SHULKER_BOX,
                LIME_SHULKER_BOX,
                PINK_SHULKER_BOX,
                GRAY_SHULKER_BOX,
                LIGHT_GRAY_SHULKER_BOX,
                CYAN_SHULKER_BOX,
                PURPLE_SHULKER_BOX,
                BLUE_SHULKER_BOX,
                BROWN_SHULKER_BOX,
                GREEN_SHULKER_BOX,
                RED_SHULKER_BOX,
                BLACK_SHULKER_BOX
        );
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                OAK_CHEST.toItem(),
                SPRUCE_CHEST.toItem(),
                BIRCH_CHEST.toItem(),
                ACACIA_CHEST.toItem(),
                JUNGLE_CHEST.toItem(),
                DARK_OAK_CHEST.toItem(),

                OAK_CHEST_TIER_1.toItem(),
                SPRUCE_CHEST_TIER_1.toItem(),
                BIRCH_CHEST_TIER_1.toItem(),
                ACACIA_CHEST_TIER_1.toItem(),
                JUNGLE_CHEST_TIER_1.toItem(),
                DARK_OAK_CHEST_TIER_1.toItem(),

                OAK_CHEST_TIER_2.toItem(),
                SPRUCE_CHEST_TIER_2.toItem(),
                BIRCH_CHEST_TIER_2.toItem(),
                ACACIA_CHEST_TIER_2.toItem(),
                JUNGLE_CHEST_TIER_2.toItem(),
                DARK_OAK_CHEST_TIER_2.toItem(),

                OAK_CHEST_TIER_3.toItem(),
                SPRUCE_CHEST_TIER_3.toItem(),
                BIRCH_CHEST_TIER_3.toItem(),
                ACACIA_CHEST_TIER_3.toItem(),
                JUNGLE_CHEST_TIER_3.toItem(),
                DARK_OAK_CHEST_TIER_3.toItem(),

                OAK_BARREL.toItem(),
                SPRUCE_BARREL.toItem(),
                BIRCH_BARREL.toItem(),
                ACACIA_BARREL.toItem(),
                JUNGLE_BARREL.toItem(),
                DARK_OAK_BARREL.toItem(),

                OAK_STORAGE_BARREL.toItem(),
                SPRUCE_STORAGE_BARREL.toItem(),
                BIRCH_STORAGE_BARREL.toItem(),
                ACACIA_STORAGE_BARREL.toItem(),
                JUNGLE_STORAGE_BARREL.toItem(),
                DARK_OAK_STORAGE_BARREL.toItem(),

                WHITE_SHULKER_BOX.toItem(),
                ORANGE_SHULKER_BOX.toItem(),
                MAGENTA_SHULKER_BOX.toItem(),
                LIGHT_BLUE_SHULKER_BOX.toItem(),
                YELLOW_SHULKER_BOX.toItem(),
                LIME_SHULKER_BOX.toItem(),
                PINK_SHULKER_BOX.toItem(),
                GRAY_SHULKER_BOX.toItem(),
                LIGHT_GRAY_SHULKER_BOX.toItem(),
                CYAN_SHULKER_BOX.toItem(),
                PURPLE_SHULKER_BOX.toItem(),
                BLUE_SHULKER_BOX.toItem(),
                BROWN_SHULKER_BOX.toItem(),
                GREEN_SHULKER_BOX.toItem(),
                RED_SHULKER_BOX.toItem(),
                BLACK_SHULKER_BOX.toItem()
        );
    }

}
