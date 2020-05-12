package de.maxhenkel.storage.blocks;

import de.maxhenkel.storage.blocks.tileentity.ModTileEntities;
import de.maxhenkel.storage.items.render.ChestItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {

    public static final ModChestBlock OAK_CHEST = new ModChestBlock("oak_chest", () -> ModTileEntities.OAK_CHEST, () -> new ChestItemRenderer(ModTileEntities.OAK_CHEST_CREATOR.call()));
    public static final ModChestBlock SPRUCE_CHEST = new ModChestBlock("spruce_chest", () -> ModTileEntities.SPRUCE_CHEST, () -> new ChestItemRenderer(ModTileEntities.SPRUCE_CHEST_CREATOR.call()));
    public static final ModChestBlock BIRCH_CHEST = new ModChestBlock("birch_chest", () -> ModTileEntities.BIRCH_CHEST, () -> new ChestItemRenderer(ModTileEntities.BIRCH_CHEST_CREATOR.call()));
    public static final ModChestBlock ACACIA_CHEST = new ModChestBlock("acacia_chest", () -> ModTileEntities.ACACIA_CHEST, () -> new ChestItemRenderer(ModTileEntities.ACACIA_CHEST_CREATOR.call()));
    public static final ModChestBlock JUNGLE_CHEST = new ModChestBlock("jungle_chest", () -> ModTileEntities.JUNGLE_CHEST, () -> new ChestItemRenderer(ModTileEntities.JUNGLE_CHEST_CREATOR.call()));
    public static final ModChestBlock DARK_OAK_CHEST = new ModChestBlock("dark_oak_chest", () -> ModTileEntities.DARK_OAK_CHEST, () -> new ChestItemRenderer(ModTileEntities.DARK_OAK_CHEST_CREATOR.call()));

    public static final ModBarrelBlock OAK_BARREL = new ModBarrelBlock("oak_barrel", () -> ModTileEntities.OAK_BARREL);
    public static final ModBarrelBlock SPRUCE_BARREL = new ModBarrelBlock("spruce_barrel", () -> ModTileEntities.SPRUCE_BARREL);
    public static final ModBarrelBlock BIRCH_BARREL = new ModBarrelBlock("birch_barrel", () -> ModTileEntities.BIRCH_BARREL);
    public static final ModBarrelBlock ACACIA_BARREL = new ModBarrelBlock("acacia_barrel", () -> ModTileEntities.ACACIA_BARREL);
    public static final ModBarrelBlock JUNGLE_BARREL = new ModBarrelBlock("jungle_barrel", () -> ModTileEntities.JUNGLE_BARREL);
    public static final ModBarrelBlock DARK_OAK_BARREL = new ModBarrelBlock("dark_oak_barrel", () -> ModTileEntities.DARK_OAK_BARREL);

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
                DARK_OAK_BARREL
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
                DARK_OAK_BARREL.toItem()
        );
    }

}
