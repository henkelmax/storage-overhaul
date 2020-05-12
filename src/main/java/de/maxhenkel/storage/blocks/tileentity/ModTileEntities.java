package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.tileentity.render.ChestAtlases;
import de.maxhenkel.storage.blocks.tileentity.render.ModChestRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.concurrent.Callable;

public class ModTileEntities {

    public static TileEntityType<ModChestTileEntity> OAK_CHEST;
    public static TileEntityType<ModChestTileEntity> SPRUCE_CHEST;
    public static TileEntityType<ModChestTileEntity> BIRCH_CHEST;
    public static TileEntityType<ModChestTileEntity> ACACIA_CHEST;
    public static TileEntityType<ModChestTileEntity> JUNGLE_CHEST;
    public static TileEntityType<ModChestTileEntity> DARK_OAK_CHEST;

    public static TileEntityType<ModBarrelTileEntity> OAK_BARREL;
    public static TileEntityType<ModBarrelTileEntity> SPRUCE_BARREL;
    public static TileEntityType<ModBarrelTileEntity> BIRCH_BARREL;
    public static TileEntityType<ModBarrelTileEntity> ACACIA_BARREL;
    public static TileEntityType<ModBarrelTileEntity> JUNGLE_BARREL;
    public static TileEntityType<ModBarrelTileEntity> DARK_OAK_BARREL;

    public static Callable<ModChestTileEntity> OAK_CHEST_CREATOR = () -> new ModChestTileEntity(OAK_CHEST, ChestAtlases.CHEST_OAK);
    public static Callable<ModChestTileEntity> SPRUCE_CHEST_CREATOR = () -> new ModChestTileEntity(SPRUCE_CHEST, ChestAtlases.CHEST_SPRUCE);
    public static Callable<ModChestTileEntity> BIRCH_CHEST_CREATOR = () -> new ModChestTileEntity(BIRCH_CHEST, ChestAtlases.CHEST_BIRCH);
    public static Callable<ModChestTileEntity> ACACIA_CHEST_CREATOR = () -> new ModChestTileEntity(ACACIA_CHEST, ChestAtlases.CHEST_ACACIA);
    public static Callable<ModChestTileEntity> JUNGLE_CHEST_CREATOR = () -> new ModChestTileEntity(JUNGLE_CHEST, ChestAtlases.CHEST_JUNGLE);
    public static Callable<ModChestTileEntity> DARK_OAK_CHEST_CREATOR = () -> new ModChestTileEntity(DARK_OAK_CHEST, ChestAtlases.CHEST_DARK_OAK);

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        OAK_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(OAK_CHEST, ChestAtlases.CHEST_OAK), ModBlocks.OAK_CHEST).build(null);
        OAK_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "oak_chest"));
        event.getRegistry().register(OAK_CHEST);

        SPRUCE_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(SPRUCE_CHEST, ChestAtlases.CHEST_SPRUCE), ModBlocks.SPRUCE_CHEST).build(null);
        SPRUCE_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "spruce_chest"));
        event.getRegistry().register(SPRUCE_CHEST);

        BIRCH_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(BIRCH_CHEST, ChestAtlases.CHEST_BIRCH), ModBlocks.BIRCH_CHEST).build(null);
        BIRCH_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "birch_chest"));
        event.getRegistry().register(BIRCH_CHEST);

        ACACIA_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(ACACIA_CHEST, ChestAtlases.CHEST_ACACIA), ModBlocks.ACACIA_CHEST).build(null);
        ACACIA_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "acacia_chest"));
        event.getRegistry().register(ACACIA_CHEST);

        JUNGLE_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(JUNGLE_CHEST, ChestAtlases.CHEST_JUNGLE), ModBlocks.JUNGLE_CHEST).build(null);
        JUNGLE_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "jungle_chest"));
        event.getRegistry().register(JUNGLE_CHEST);

        DARK_OAK_CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(DARK_OAK_CHEST, ChestAtlases.CHEST_DARK_OAK), ModBlocks.DARK_OAK_CHEST).build(null);
        DARK_OAK_CHEST.setRegistryName(new ResourceLocation(Main.MODID, "dark_oak_chest"));
        event.getRegistry().register(DARK_OAK_CHEST);

        OAK_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(OAK_BARREL), ModBlocks.OAK_BARREL).build(null);
        OAK_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "oak_barrel"));
        event.getRegistry().register(OAK_BARREL);

        SPRUCE_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(SPRUCE_BARREL), ModBlocks.SPRUCE_BARREL).build(null);
        SPRUCE_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "spruce_barrel"));
        event.getRegistry().register(SPRUCE_BARREL);

        BIRCH_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(BIRCH_BARREL), ModBlocks.BIRCH_BARREL).build(null);
        BIRCH_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "birch_barrel"));
        event.getRegistry().register(BIRCH_BARREL);

        ACACIA_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(ACACIA_BARREL), ModBlocks.ACACIA_BARREL).build(null);
        ACACIA_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "acacia_barrel"));
        event.getRegistry().register(ACACIA_BARREL);

        JUNGLE_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(JUNGLE_BARREL), ModBlocks.JUNGLE_BARREL).build(null);
        JUNGLE_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "jungle_barrel"));
        event.getRegistry().register(JUNGLE_BARREL);

        DARK_OAK_BARREL = TileEntityType.Builder.create(() -> new ModBarrelTileEntity(DARK_OAK_BARREL), ModBlocks.DARK_OAK_BARREL).build(null);
        DARK_OAK_BARREL.setRegistryName(new ResourceLocation(Main.MODID, "dark_oak_barrel"));
        event.getRegistry().register(DARK_OAK_BARREL);

    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.OAK_CHEST, ModChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.SPRUCE_CHEST, ModChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.BIRCH_CHEST, ModChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ACACIA_CHEST, ModChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.JUNGLE_CHEST, ModChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.DARK_OAK_CHEST, ModChestRenderer::new);
    }
}
