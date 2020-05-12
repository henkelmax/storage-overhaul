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

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                OAK_CHEST,
                SPRUCE_CHEST,
                BIRCH_CHEST,
                ACACIA_CHEST,
                JUNGLE_CHEST,
                DARK_OAK_CHEST
        );
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                OAK_CHEST.toItem(),
                SPRUCE_CHEST.toItem(),
                BIRCH_CHEST.toItem(),
                ACACIA_CHEST.toItem(),
                JUNGLE_CHEST.toItem(),
                DARK_OAK_CHEST.toItem()
        );
    }

}
