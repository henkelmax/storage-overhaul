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

    public static TileEntityType<StorageOverhaulBarrelTileEntity> OAK_BARREL;
    public static TileEntityType<StorageOverhaulBarrelTileEntity> SPRUCE_BARREL;
    public static TileEntityType<StorageOverhaulBarrelTileEntity> BIRCH_BARREL;
    public static TileEntityType<StorageOverhaulBarrelTileEntity> ACACIA_BARREL;
    public static TileEntityType<StorageOverhaulBarrelTileEntity> JUNGLE_BARREL;
    public static TileEntityType<StorageOverhaulBarrelTileEntity> DARK_OAK_BARREL;

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

        OAK_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(OAK_BARREL), ModBlocks.OAK_BARREL).build(null);
        OAK_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "oak_barrel"));
        event.getRegistry().register(OAK_BARREL);

        SPRUCE_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(SPRUCE_BARREL), ModBlocks.SPRUCE_BARREL).build(null);
        SPRUCE_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "spruce_barrel"));
        event.getRegistry().register(SPRUCE_BARREL);

        BIRCH_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(BIRCH_BARREL), ModBlocks.BIRCH_BARREL).build(null);
        BIRCH_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "birch_barrel"));
        event.getRegistry().register(BIRCH_BARREL);

        ACACIA_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(ACACIA_BARREL), ModBlocks.ACACIA_BARREL).build(null);
        ACACIA_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "acacia_barrel"));
        event.getRegistry().register(ACACIA_BARREL);

        JUNGLE_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(JUNGLE_BARREL), ModBlocks.JUNGLE_BARREL).build(null);
        JUNGLE_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "jungle_barrel"));
        event.getRegistry().register(JUNGLE_BARREL);

        DARK_OAK_BARREL = TileEntityType.Builder.create(() -> new StorageOverhaulBarrelTileEntity(DARK_OAK_BARREL), ModBlocks.DARK_OAK_BARREL).build(null);
        DARK_OAK_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "dark_oak_barrel"));
        event.getRegistry().register(DARK_OAK_BARREL);

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
