package de.maxhenkel.storage.blocks;

import de.maxhenkel.storage.blocks.tileentity.ModTileEntities;
import de.maxhenkel.storage.items.render.ChestItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {

    public static final StorageOverhaulChestBlock OAK_CHEST = new StorageOverhaulChestBlock("oak_chest", () -> ModTileEntities.OAK_CHEST, () -> new ChestItemRenderer(ModTileEntities.OAK_CHEST_CREATOR.call()));
    public static final StorageOverhaulChestBlock SPRUCE_CHEST = new StorageOverhaulChestBlock("spruce_chest", () -> ModTileEntities.SPRUCE_CHEST, () -> new ChestItemRenderer(ModTileEntities.SPRUCE_CHEST_CREATOR.call()));
    public static final StorageOverhaulChestBlock BIRCH_CHEST = new StorageOverhaulChestBlock("birch_chest", () -> ModTileEntities.BIRCH_CHEST, () -> new ChestItemRenderer(ModTileEntities.BIRCH_CHEST_CREATOR.call()));
    public static final StorageOverhaulChestBlock ACACIA_CHEST = new StorageOverhaulChestBlock("acacia_chest", () -> ModTileEntities.ACACIA_CHEST, () -> new ChestItemRenderer(ModTileEntities.ACACIA_CHEST_CREATOR.call()));
    public static final StorageOverhaulChestBlock JUNGLE_CHEST = new StorageOverhaulChestBlock("jungle_chest", () -> ModTileEntities.JUNGLE_CHEST, () -> new ChestItemRenderer(ModTileEntities.JUNGLE_CHEST_CREATOR.call()));
    public static final StorageOverhaulChestBlock DARK_OAK_CHEST = new StorageOverhaulChestBlock("dark_oak_chest", () -> ModTileEntities.DARK_OAK_CHEST, () -> new ChestItemRenderer(ModTileEntities.DARK_OAK_CHEST_CREATOR.call()));

    public static final StorageOverhaulBarrelBlock OAK_BARREL = new StorageOverhaulBarrelBlock("oak_barrel", () -> ModTileEntities.OAK_BARREL);
    public static final StorageOverhaulBarrelBlock SPRUCE_BARREL = new StorageOverhaulBarrelBlock("spruce_barrel", () -> ModTileEntities.SPRUCE_BARREL);
    public static final StorageOverhaulBarrelBlock BIRCH_BARREL = new StorageOverhaulBarrelBlock("birch_barrel", () -> ModTileEntities.BIRCH_BARREL);
    public static final StorageOverhaulBarrelBlock ACACIA_BARREL = new StorageOverhaulBarrelBlock("acacia_barrel", () -> ModTileEntities.ACACIA_BARREL);
    public static final StorageOverhaulBarrelBlock JUNGLE_BARREL = new StorageOverhaulBarrelBlock("jungle_barrel", () -> ModTileEntities.JUNGLE_BARREL);
    public static final StorageOverhaulBarrelBlock DARK_OAK_BARREL = new StorageOverhaulBarrelBlock("dark_oak_barrel", () -> ModTileEntities.DARK_OAK_BARREL);

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
