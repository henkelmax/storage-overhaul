package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.tileentity.render.ChestAtlases;
import de.maxhenkel.storage.blocks.tileentity.render.StorageOverhaulChestRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.concurrent.Callable;

public class ModTileEntities {

    public static TileEntityType<StorageOverhaulChestTileEntity> OAK_CHEST;
    public static TileEntityType<StorageOverhaulChestTileEntity> SPRUCE_CHEST;
    public static TileEntityType<StorageOverhaulChestTileEntity> BIRCH_CHEST;
    public static TileEntityType<StorageOverhaulChestTileEntity> ACACIA_CHEST;
    public static TileEntityType<StorageOverhaulChestTileEntity> JUNGLE_CHEST;
    public static TileEntityType<StorageOverhaulChestTileEntity> DARK_OAK_CHEST;

    public static Callable<StorageOverhaulChestTileEntity> OAK_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(OAK_CHEST, ChestAtlases.CHEST_OAK);
    public static Callable<StorageOverhaulChestTileEntity> SPRUCE_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(SPRUCE_CHEST, ChestAtlases.CHEST_SPRUCE);
    public static Callable<StorageOverhaulChestTileEntity> BIRCH_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(BIRCH_CHEST, ChestAtlases.CHEST_BIRCH);
    public static Callable<StorageOverhaulChestTileEntity> ACACIA_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(ACACIA_CHEST, ChestAtlases.CHEST_ACACIA);
    public static Callable<StorageOverhaulChestTileEntity> JUNGLE_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(JUNGLE_CHEST, ChestAtlases.CHEST_JUNGLE);
    public static Callable<StorageOverhaulChestTileEntity> DARK_OAK_CHEST_CREATOR = () -> new StorageOverhaulChestTileEntity(DARK_OAK_CHEST, ChestAtlases.CHEST_DARK_OAK);

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        OAK_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(OAK_CHEST, ChestAtlases.CHEST_OAK), ModBlocks.OAK_CHEST).build(null);
        OAK_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "oak_chest"));
        event.getRegistry().register(OAK_CHEST);

        SPRUCE_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(SPRUCE_CHEST, ChestAtlases.CHEST_SPRUCE), ModBlocks.SPRUCE_CHEST).build(null);
        SPRUCE_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "spruce_chest"));
        event.getRegistry().register(SPRUCE_CHEST);

        BIRCH_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(BIRCH_CHEST, ChestAtlases.CHEST_BIRCH), ModBlocks.BIRCH_CHEST).build(null);
        BIRCH_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "birch_chest"));
        event.getRegistry().register(BIRCH_CHEST);

        ACACIA_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(ACACIA_CHEST, ChestAtlases.CHEST_ACACIA), ModBlocks.ACACIA_CHEST).build(null);
        ACACIA_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "acacia_chest"));
        event.getRegistry().register(ACACIA_CHEST);

        JUNGLE_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(JUNGLE_CHEST, ChestAtlases.CHEST_JUNGLE), ModBlocks.JUNGLE_CHEST).build(null);
        JUNGLE_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "jungle_chest"));
        event.getRegistry().register(JUNGLE_CHEST);

        DARK_OAK_CHEST = TileEntityType.Builder.create(() -> new StorageOverhaulChestTileEntity(DARK_OAK_CHEST, ChestAtlases.CHEST_DARK_OAK), ModBlocks.DARK_OAK_CHEST).build(null);
        DARK_OAK_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "dark_oak_chest"));
        event.getRegistry().register(DARK_OAK_CHEST);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.OAK_CHEST, StorageOverhaulChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.SPRUCE_CHEST, StorageOverhaulChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.BIRCH_CHEST, StorageOverhaulChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ACACIA_CHEST, StorageOverhaulChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.JUNGLE_CHEST, StorageOverhaulChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.DARK_OAK_CHEST, StorageOverhaulChestRenderer::new);
    }
}
