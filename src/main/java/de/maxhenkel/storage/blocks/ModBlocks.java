package de.maxhenkel.storage.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {

    public static final ModChestBlock OAK_CHEST = new ModChestBlock("oak_chest", WoodType.OAK);
    public static final ModChestBlock SPRUCE_CHEST = new ModChestBlock("spruce_chest", WoodType.SPRUCE);
    public static final ModChestBlock BIRCH_CHEST = new ModChestBlock("birch_chest", WoodType.BIRCH);
    public static final ModChestBlock ACACIA_CHEST = new ModChestBlock("acacia_chest", WoodType.ACACIA);
    public static final ModChestBlock JUNGLE_CHEST = new ModChestBlock("jungle_chest", WoodType.JUNGLE);
    public static final ModChestBlock DARK_OAK_CHEST = new ModChestBlock("dark_oak_chest", WoodType.DARK_OAK);

    public static final ModBarrelBlock OAK_BARREL = new ModBarrelBlock("oak_barrel");
    public static final ModBarrelBlock SPRUCE_BARREL = new ModBarrelBlock("spruce_barrel");
    public static final ModBarrelBlock BIRCH_BARREL = new ModBarrelBlock("birch_barrel");
    public static final ModBarrelBlock ACACIA_BARREL = new ModBarrelBlock("acacia_barrel");
    public static final ModBarrelBlock JUNGLE_BARREL = new ModBarrelBlock("jungle_barrel");
    public static final ModBarrelBlock DARK_OAK_BARREL = new ModBarrelBlock("dark_oak_barrel");

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

                OAK_BARREL,
                SPRUCE_BARREL,
                BIRCH_BARREL,
                ACACIA_BARREL,
                JUNGLE_BARREL,
                DARK_OAK_BARREL,

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

                OAK_BARREL.toItem(),
                SPRUCE_BARREL.toItem(),
                BIRCH_BARREL.toItem(),
                ACACIA_BARREL.toItem(),
                JUNGLE_BARREL.toItem(),
                DARK_OAK_BARREL.toItem(),

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
